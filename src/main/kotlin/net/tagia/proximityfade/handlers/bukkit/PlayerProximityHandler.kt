package net.tagia.proximityfade.handlers.bukkit

import net.tagia.proximityfade.ProximityFadeManager
import net.tagia.proximityfade.api.events.PlayerCloseEncounterEvent
import net.tagia.proximityfade.api.events.PlayerCloseSeparateEvent
import net.tagia.proximityfade.api.events.PlayerNearbyEncounterEvent
import net.tagia.proximityfade.api.events.PlayerNearbySeparateEvent
import net.tagia.proximityfade.utils.ConfigUtil
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent


class PlayerProximityHandler(
    val manager: ProximityFadeManager,
    val nearbyPlayers: HashMap<Int, HashSet<Int>>,
    val closePlayers: HashMap<Int, HashSet<Int>>,
    val cfg: ConfigUtil
) : Listener {

    val closeDistanceHorizontal = cfg.closeDistanceHorizontal
    val closeDistanceVertical = cfg.closeDistanceVertical
    val nearbyDistanceHorizontal = cfg.nearbyDistanceHorizontal
    val nearbyDistanceVertical = cfg.nearbyDistanceVertical

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        nearbyPlayers[e.player.entityId] = HashSet()
        closePlayers[e.player.entityId] = HashSet()
        updateProximatePlayers(e.player)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        nearbyPlayers.remove(e.player.entityId)
        closePlayers.remove(e.player.entityId)

        // Remove player from every other player's nearby set
        nearbyPlayers.forEach { (_, value) ->
            value.remove(e.player.entityId)
        }
        closePlayers.forEach { (_, value) ->
            value.remove(e.player.entityId)
        }
    }

    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        val player = e.player
        updateProximatePlayers(player)
    }

    private fun updateProximatePlayers(player: Player) {
        val nearbyToPlayer = nearbyPlayers[player.entityId]!!
        val closeToPlayer = closePlayers[player.entityId]!!

        val newNearby = findNearbyPlayers(player)
        val newClose = findClosePlayers(player)

        // Store nearby players
        newNearby.forEach { nearbyEntityId ->
            if (nearbyToPlayer.add(nearbyEntityId)) {
                nearbyPlayers[nearbyEntityId]!!.add(player.entityId)

                PlayerNearbyEncounterEvent(
                    player,
                    manager.eidToPlayer(nearbyEntityId)!!
                ).callEvent()
            }
        }
        newClose.forEach { closeEntityId ->
            if (closeToPlayer.add(closeEntityId)) {
                closePlayers[closeEntityId]!!.add(player.entityId)

                PlayerCloseEncounterEvent(
                    player,
                    manager.eidToPlayer(closeEntityId)!!
                ).callEvent()
            }
        }

        val noLongerNearby = nearbyToPlayer - newNearby
        noLongerNearby.forEach { notNearbyEntityId ->
            nearbyToPlayer.remove(notNearbyEntityId)
            nearbyPlayers[notNearbyEntityId]!!.remove(player.entityId)

            PlayerNearbySeparateEvent(
                player,
                manager.eidToPlayer(notNearbyEntityId)!!
            ).callEvent()
        }

        val noLongerClose = closeToPlayer - newClose
        noLongerClose.forEach { notCloseEntityId ->
            closeToPlayer.remove(notCloseEntityId)
            closePlayers[notCloseEntityId]!!.remove(player.entityId)

            PlayerCloseSeparateEvent(
                player,
                manager.eidToPlayer(notCloseEntityId)!!
            ).callEvent()
        }
    }

    private fun findNearbyPlayers(player: Player): Set<Int> {
        return player.getNearbyEntities(
            nearbyDistanceHorizontal - 1,
            nearbyDistanceVertical - 1,
            nearbyDistanceHorizontal - 1
        )
            .filterIsInstance<Player>()
            .map { it.entityId }
            .toSet()
    }

    private fun findClosePlayers(player: Player): Set<Int> {
        return player.getNearbyEntities(
            closeDistanceHorizontal - 1,
            closeDistanceVertical - 1,
            closeDistanceHorizontal - 1
        )
            .filterIsInstance<Player>()
            .map { it.entityId }
            .toSet()
    }

}