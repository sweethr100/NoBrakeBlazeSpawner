package seml.noBrakeBlazeSpawner

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.CreatureSpawner
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.ChatColor

class NoBrakeBlazeSpawner : JavaPlugin(), Listener{
    private lateinit var config: FileConfiguration
    private var customMsg: String = "블레이즈 스포너는 부술 수 없습니다!"  // 기본 메시지

    override fun onEnable() {
        saveDefaultConfig()
        config = getConfig()
        customMsg = ChatColor.translateAlternateColorCodes('&', config.getString("message", customMsg)!!)

        Bukkit.getPluginManager().registerEvents(this, this)
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        if (block.type == Material.SPAWNER) {
            val spawner = block.state as CreatureSpawner
            if (spawner.spawnedType == EntityType.BLAZE) {
                event.isCancelled = true
                event.player.sendMessage(customMsg)
            }
        }
    }

    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        event.blockList().removeIf { block ->
            block.type == Material.SPAWNER && (block.state as? CreatureSpawner)?.spawnedType == EntityType.BLAZE
        }
    }
}
