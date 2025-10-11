package net.tagia.proximityfade

import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerPriority
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import net.tagia.proximityfade.api.ProximityFadeAPI
import net.tagia.proximityfade.handlers.bukkit.EncounterSeparateHandler
import net.tagia.proximityfade.handlers.bukkit.PlayerEntityIdTrackingHandler
import net.tagia.proximityfade.handlers.bukkit.PlayerProximityHandler
import net.tagia.proximityfade.handlers.packet.EntityEquipmentPacketHandler
import net.tagia.proximityfade.handlers.packet.EntityMetadataPacketHandler
import net.tagia.proximityfade.handlers.packet.EntityMovePacketHandler
import net.tagia.proximityfade.utils.ConfigUtil
import org.bstats.bukkit.Metrics
import org.bukkit.entity.Player
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin


class ProximityFade : JavaPlugin() {

    private val plugin = this
    val metrics = Metrics(this, 27556)

    private val entityIdPlayers = hashMapOf<Int, Player>()
    private val nearbyPlayers = hashMapOf<Int, HashSet<Int>>()
    private val closePlayers = hashMapOf<Int, HashSet<Int>>()

    private val manager = ProximityFadeManager(entityIdPlayers, nearbyPlayers, closePlayers)
    private lateinit var cfg: ConfigUtil

    @Suppress("UnstableApiUsage")
    override fun onLoad() {
        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(plugin))
        PacketEvents.getAPI().settings
            .checkForUpdates(false)
        PacketEvents.getAPI().load()

        // Register packet event handlers
        PacketEvents.getAPI().eventManager.apply {
            registerListener(EntityMovePacketHandler(manager), PacketListenerPriority.NORMAL)
            registerListener(EntityMetadataPacketHandler(manager), PacketListenerPriority.NORMAL)
            registerListener(EntityEquipmentPacketHandler(manager), PacketListenerPriority.NORMAL)
        }

        server.servicesManager.register(
            ProximityFadeAPI::class.java,
            manager,
            this,
            ServicePriority.Normal
        )
    }

    override fun onEnable() {
        PacketEvents.getAPI().init()

        plugin.saveDefaultConfig()
        cfg = ConfigUtil(
            config.getDouble("closeDistanceHorizontal"),
            config.getDouble("closeDistanceVertical"),
            config.getDouble("nearbyDistanceHorizontal"),
            config.getDouble("nearbyDistanceVertical")
        )

        //  Register bukkit event handlers
        plugin.server.pluginManager.apply {
            registerEvents(PlayerEntityIdTrackingHandler(entityIdPlayers), plugin)
            registerEvents(
                PlayerProximityHandler(manager, nearbyPlayers, closePlayers, cfg),
                plugin
            )
            registerEvents(EncounterSeparateHandler(), plugin)
        }
    }

    override fun onDisable() {
        PacketEvents.getAPI().terminate()
        server.servicesManager.unregisterAll(this)
        metrics.shutdown()
    }


}
