package net.tagia.proximityfade.handlers.packet

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEquipment
import net.tagia.proximityfade.ProximityFadeManager
import net.tagia.proximityfade.utils.PacketsUtil
import org.bukkit.entity.Player

class EntityEquipmentPacketHandler(val manager: ProximityFadeManager) : PacketListener {

    override fun onPacketSend(e: PacketSendEvent) {
        if (e.packetType != PacketType.Play.Server.ENTITY_EQUIPMENT)
            return

        handleEntityEquipmentPacket(e)
    }

    private fun handleEntityEquipmentPacket(e: PacketSendEvent) {
        val wrapper = WrapperPlayServerEntityEquipment(e)
        if (manager.eidToPlayer(e.user.entityId) == null)
            return

        val targetEid = wrapper.entityId
        val nearby = manager.getNearbyEntityIds(e.getPlayer<Player>())!!
        if (!nearby.contains(targetEid))
            return

        val close = manager.getCloseEntityIds(e.getPlayer<Player>())!!
        if (close.contains(targetEid))
            wrapper.equipment = PacketsUtil.setNoEquipmentPacket(manager.eidToPlayer(targetEid)!!).equipment
        else
            wrapper.equipment = PacketsUtil.setPartialEquipmentPacket(manager.eidToPlayer(targetEid)!!).equipment
        wrapper.write()
        e.markForReEncode(true)
    }

}