package net.tagia.proximityfade.utils

import com.github.retrooper.packetevents.protocol.entity.data.EntityData
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes
import com.github.retrooper.packetevents.protocol.player.Equipment
import com.github.retrooper.packetevents.protocol.player.EquipmentSlot
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEquipment
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.experimental.or

object PacketsUtil {
    private val airPacketItem = SpigotConversionUtil.fromBukkitItemStack(ItemStack.of(Material.AIR))

    fun setPartialEquipmentPacket(player: Player): WrapperPlayServerEntityEquipment {
        val encounteredPlayerBoots = player.equipment.boots
        val bootsPacketItem = SpigotConversionUtil.fromBukkitItemStack(
            if (encounteredPlayerBoots.type != Material.AIR)
                encounteredPlayerBoots
            else
                ItemStack.of(Material.LEATHER_BOOTS)
        )
        return WrapperPlayServerEntityEquipment(
            player.entityId,
            listOf(
                Equipment(EquipmentSlot.HELMET, airPacketItem),
                Equipment(EquipmentSlot.CHEST_PLATE, airPacketItem),
                Equipment(EquipmentSlot.LEGGINGS, airPacketItem),
                Equipment(EquipmentSlot.BOOTS, bootsPacketItem),
                Equipment(EquipmentSlot.MAIN_HAND, airPacketItem),
                Equipment(EquipmentSlot.OFF_HAND, airPacketItem),
            )
        )
    }

    fun setNoEquipmentPacket(player: Player): WrapperPlayServerEntityEquipment {
        return WrapperPlayServerEntityEquipment(
            player.entityId,
            listOf(
                Equipment(EquipmentSlot.HELMET, airPacketItem),
                Equipment(EquipmentSlot.CHEST_PLATE, airPacketItem),
                Equipment(EquipmentSlot.LEGGINGS, airPacketItem),
                Equipment(EquipmentSlot.BOOTS, airPacketItem),
                Equipment(EquipmentSlot.MAIN_HAND, airPacketItem),
                Equipment(EquipmentSlot.OFF_HAND, airPacketItem),
            )
        )
    }

    fun restoreEquipmentPacket(player: Player): WrapperPlayServerEntityEquipment {
        val playerEquipment = player.equipment

        val equipmentList = buildList {
            add(createEquipment(EquipmentSlot.HELMET, playerEquipment.helmet))
            add(createEquipment(EquipmentSlot.CHEST_PLATE, playerEquipment.chestplate))
            add(createEquipment(EquipmentSlot.LEGGINGS, playerEquipment.leggings))
            add(createEquipment(EquipmentSlot.BOOTS, playerEquipment.boots))
            add(createEquipment(EquipmentSlot.MAIN_HAND, playerEquipment.itemInMainHand))
            add(createEquipment(EquipmentSlot.OFF_HAND, playerEquipment.itemInOffHand))
        }

        return WrapperPlayServerEntityEquipment(
            player.entityId,
            equipmentList
        )
    }

    fun createEquipment(slot: EquipmentSlot, item: ItemStack): Equipment {
        return Equipment(slot, SpigotConversionUtil.fromBukkitItemStack(item))
    }

    fun addInvisibilityPacket(player: Player): WrapperPlayServerEntityMetadata {
        val entityMetadata = SpigotConversionUtil.getEntityMetadata(player)

        return WrapperPlayServerEntityMetadata(
            player.entityId,
            entityMetadata.map { entityData ->
                when {
                    entityData.index == 0 && entityData.type == EntityDataTypes.BYTE -> {
                        EntityData(0, EntityDataTypes.BYTE, entityData.value as Byte or 0x20)
                    }

                    entityData.index == 10 && entityData.type == EntityDataTypes.PARTICLES -> {
                        EntityData(10, EntityDataTypes.PARTICLES, listOf())
                    }

                    else -> entityData
                }
            }
        )
    }

    fun restoreInvisibilityPacket(player: Player): WrapperPlayServerEntityMetadata {
        val entityMetadata = SpigotConversionUtil.getEntityMetadata(player)
        return WrapperPlayServerEntityMetadata(
            player.entityId,
            entityMetadata
        )
    }

//    fun foo(player: Player?): Boolean {
//        if (plugin.eidToPlayer(e.user.entityId) == null)
//            return false
//
//        val viewer = e.getPlayer<Player>()
//        val targetEid = wrapper.entityId
//
//        val nearby = plugin.getNearbyEntityIds(viewer)!!
//
//        if (!nearby.contains(targetEid))
//            return false
//
//        return true
//    }
}