package net.tagia.proximityfade.handlers.packet

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import net.tagia.proximityfade.ProximityFadeManager
import net.tagia.proximityfade.utils.PacketsUtil
import org.bukkit.entity.Player

class EntityMetadataPacketHandler(val manager: ProximityFadeManager) : PacketListener {

    override fun onPacketSend(e: PacketSendEvent) {
        if (e.packetType != PacketType.Play.Server.ENTITY_METADATA)
            return

        handleEntityMetadataPacket(e)
    }


    private fun handleEntityMetadataPacket(e: PacketSendEvent) {
        val wrapper = WrapperPlayServerEntityMetadata(e)
        if (manager.eidToPlayer(e.user.entityId) == null)
            return

        val targetEid = wrapper.entityId
        val nearby = manager.getNearbyEntityIds(e.getPlayer<Player>())!!
        if (!nearby.contains(targetEid))
            return

        wrapper.entityMetadata = PacketsUtil.addInvisibilityPacket(manager.eidToPlayer(targetEid)!!).entityMetadata
        wrapper.write()
        e.markForReEncode(true)
    }

}