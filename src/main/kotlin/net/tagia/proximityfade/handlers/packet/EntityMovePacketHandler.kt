package net.tagia.proximityfade.handlers.packet

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMovement
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityRelativeMove
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityRelativeMoveAndRotation
import net.tagia.proximityfade.ProximityFadeManager

class EntityMovePacketHandler(val manager: ProximityFadeManager) : PacketListener {

    private val packetHandlers = mapOf(
        PacketType.Play.Server.ENTITY_MOVEMENT to ::handleMovement,
        PacketType.Play.Server.ENTITY_RELATIVE_MOVE to ::handleRelativeMove,
        PacketType.Play.Server.ENTITY_RELATIVE_MOVE_AND_ROTATION to ::handleRelativeMoveAndRotation,
    )

    override fun onPacketSend(e: PacketSendEvent) {
        packetHandlers[e.packetType]?.invoke(e)
    }

    private fun handleMovement(e: PacketSendEvent) {
        val wrapper = WrapperPlayServerEntityMovement(e)
        if (manager.eidToPlayer(wrapper.entityId) == null)
            return
    }

    private fun handleRelativeMove(e: PacketSendEvent) {
        val wrapper = WrapperPlayServerEntityRelativeMove(e)
        if (manager.eidToPlayer(wrapper.entityId) == null) {
            return
        }
    }

    private fun handleRelativeMoveAndRotation(e: PacketSendEvent) {
        val wrapper = WrapperPlayServerEntityRelativeMoveAndRotation(e)
        if (manager.eidToPlayer(wrapper.entityId) == null)
            return
    }

}