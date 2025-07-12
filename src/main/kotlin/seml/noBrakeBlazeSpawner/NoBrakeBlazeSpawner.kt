package seml.noBrakeBlazeSpawner

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.CreatureSpawner
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.java.JavaPlugin

class NoBrakeBlazeSpawner : JavaPlugin(), Listener{

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)
        logger.info("NoBreakBlazeSpawner 플러그인이 활성화되었습니다.")
    }

    override fun onDisable() {
        logger.info("NoBreakBlazeSpawner 플러그인이 비활성화되었습니다.")
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block
        if (block.type == Material.SPAWNER) {
            val spawner = block.state as CreatureSpawner
            if (spawner.spawnedType == EntityType.BLAZE) {
                event.isCancelled = true
                event.player.sendMessage("블레이즈 스포너는 부술 수 없습니다!")
            }
        }
    }
}
