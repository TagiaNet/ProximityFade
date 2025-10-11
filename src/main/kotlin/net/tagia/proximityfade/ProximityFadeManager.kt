package net.tagia.proximityfade

import net.tagia.proximityfade.api.ProximityFadeAPI
import org.bukkit.entity.Player

class ProximityFadeManager(
    private val entityIdPlayers: HashMap<Int, Player>,
    private val nearbyPlayers: HashMap<Int, HashSet<Int>>,
    private val closePlayers: HashMap<Int, HashSet<Int>>,
) : ProximityFadeAPI {

    override fun eidToPlayer(entityId: Int): Player? {
        return entityIdPlayers[entityId]
    }

    override fun getNearbyEntityIds(entityId: Int): Set<Int>? {
        return nearbyPlayers[entityId]
            ?.toSet()
    }

    override fun getNearbyEntityIds(player: Player): Set<Int>? {
        return getNearbyEntityIds(player.entityId)
            ?.toSet()
    }

    override fun getNearbyPlayers(entityId: Int): Set<Player>? {
        return getNearbyEntityIds(entityId)?.map { eid ->
            eidToPlayer(eid)!!
        }?.toSet()
    }

    override fun getNearbyPlayers(player: Player): Set<Player>? {
        return getNearbyPlayers(player.entityId)
    }


    override fun getCloseEntityIds(entityId: Int): Set<Int>? {
        return closePlayers[entityId]
            ?.toSet()
    }

    override fun getCloseEntityIds(player: Player): Set<Int>? {
        return getCloseEntityIds(player.entityId)
            ?.toSet()
    }

    override fun getClosePlayers(entityId: Int): Set<Player>? {
        return getCloseEntityIds(entityId)?.map { eid ->
            eidToPlayer(eid)!!
        }?.toSet()
    }

    override fun getClosePlayers(player: Player): Set<Player>? {
        return getClosePlayers(player.entityId)
    }

}