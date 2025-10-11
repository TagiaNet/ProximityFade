package net.tagia.proximityfade.api.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Called when a player gets near another player
 */
class PlayerCloseEncounterEvent(val encounteringPlayer: Player, val encounteredPlayer: Player) : Event() {
    companion object {
        private val HANDLER_LIST: HandlerList = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLER_LIST
        }
    }


    override fun getHandlers(): HandlerList {
        return HANDLER_LIST
    }
}