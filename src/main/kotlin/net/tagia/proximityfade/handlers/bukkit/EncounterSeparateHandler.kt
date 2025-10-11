package net.tagia.proximityfade.handlers.bukkit

import com.github.retrooper.packetevents.PacketEvents
import net.tagia.proximityfade.api.events.PlayerCloseEncounterEvent
import net.tagia.proximityfade.api.events.PlayerCloseSeparateEvent
import net.tagia.proximityfade.api.events.PlayerNearbyEncounterEvent
import net.tagia.proximityfade.api.events.PlayerNearbySeparateEvent
import net.tagia.proximityfade.utils.PacketsUtil
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class EncounterSeparateHandler : Listener {

    @EventHandler
    fun onPlayerNearbyEncounter(e: PlayerNearbyEncounterEvent) {
        PacketEvents.getAPI().playerManager.apply {
            sendPacket(e.encounteringPlayer, PacketsUtil.setPartialEquipmentPacket(e.encounteredPlayer))
            sendPacket(e.encounteredPlayer, PacketsUtil.setPartialEquipmentPacket(e.encounteringPlayer))

            sendPacket(e.encounteringPlayer, PacketsUtil.addInvisibilityPacket(e.encounteredPlayer))
            sendPacket(e.encounteredPlayer, PacketsUtil.addInvisibilityPacket(e.encounteringPlayer))
        }
    }

    @EventHandler
    fun onPlayerNearbySeparate(e: PlayerNearbySeparateEvent) {
        PacketEvents.getAPI().playerManager.apply {
            sendPacket(e.separatingPlayer, PacketsUtil.restoreEquipmentPacket(e.separatedPlayer))
            sendPacket(e.separatedPlayer, PacketsUtil.restoreEquipmentPacket(e.separatingPlayer))

            sendPacket(e.separatingPlayer, PacketsUtil.restoreInvisibilityPacket(e.separatedPlayer))
            sendPacket(e.separatedPlayer, PacketsUtil.restoreInvisibilityPacket(e.separatingPlayer))
        }
    }

    @EventHandler
    fun onPlayerCloseEncounter(e: PlayerCloseEncounterEvent) {
        PacketEvents.getAPI().playerManager.apply {
            sendPacket(e.encounteringPlayer, PacketsUtil.setNoEquipmentPacket(e.encounteredPlayer))
            sendPacket(e.encounteredPlayer, PacketsUtil.setNoEquipmentPacket(e.encounteringPlayer))

            sendPacket(e.encounteringPlayer, PacketsUtil.addInvisibilityPacket(e.encounteredPlayer))
            sendPacket(e.encounteredPlayer, PacketsUtil.addInvisibilityPacket(e.encounteringPlayer))
        }
    }

    @EventHandler
    fun onPlayerCloseSeparate(e: PlayerCloseSeparateEvent) {
        // Restore player's partial-equipment
        PacketEvents.getAPI().playerManager.apply {
            sendPacket(e.separatingPlayer, PacketsUtil.setPartialEquipmentPacket(e.separatedPlayer))
            sendPacket(e.separatedPlayer, PacketsUtil.setPartialEquipmentPacket(e.separatingPlayer))
        }
    }

}