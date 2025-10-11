package net.tagia.proximityfade.handlers.bukkit

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * Track player entityId values as they join and leave
 */
class PlayerEntityIdTrackingHandler(val entityIdPlayers: HashMap<Int, Player>) : Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        entityIdPlayers[e.player.entityId] = e.player
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        entityIdPlayers.remove(e.player.entityId)
    }

}