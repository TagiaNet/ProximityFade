package net.tagia.proximityfade.api.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Called when a player is no longer near another player
 */
class PlayerCloseSeparateEvent(val separatingPlayer: Player, val separatedPlayer: Player) : Event() {
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
