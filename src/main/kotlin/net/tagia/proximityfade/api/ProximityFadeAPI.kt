package net.tagia.proximityfade.api

import org.bukkit.Bukkit
import org.bukkit.entity.Player


interface ProximityFadeAPI {

    companion object {
        @JvmStatic
        fun getAPI(): ProximityFadeAPI {
            return Bukkit.getServer().servicesManager.getRegistration(ProximityFadeAPI::class.java)!!.provider
        }
    }

    fun eidToPlayer(entityId: Int): Player?

    fun getNearbyEntityIds(entityId: Int): Set<Int>?

    fun getNearbyEntityIds(player: Player): Set<Int>?

    fun getNearbyPlayers(entityId: Int): Set<Player>?

    fun getNearbyPlayers(player: Player): Set<Player>?

    fun getCloseEntityIds(entityId: Int): Set<Int>?

    fun getCloseEntityIds(player: Player): Set<Int>?

    fun getClosePlayers(entityId: Int): Set<Player>?

    fun getClosePlayers(player: Player): Set<Player>?
}