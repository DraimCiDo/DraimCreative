package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.log.BlockLog;
import me.draimcreative.draimcreative.settings.Protections;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.ArrayList;
import java.util.List;

public class MonsterSpawnEvent implements Listener {

    private final DraimCreative plugin;

    public MonsterSpawnEvent(DraimCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (!plugin.getSettings().getProtection(Protections.SPAWN_BUILD)) return;
        Block baseBlock = e.getLocation().getBlock();
        List<Block> blockList = new ArrayList<>();
        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN)) {
            // Check body
            blockList.add(baseBlock);
            blockList.add(baseBlock.getRelative(0, 1, 0));
            blockList.add(baseBlock.getRelative(0, 2, 0));
        } else if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.BUILD_IRONGOLEM)) {
            // Check body
            blockList.add(baseBlock);
            blockList.add(baseBlock.getRelative(0, 1, 0));
            blockList.add(baseBlock.getRelative(0, 2, 0));
            // Check arms
            blockList.add(baseBlock.getRelative(0, 1, 1));
            blockList.add(baseBlock.getRelative(0, 1, -1));
            // Check arms
            blockList.add(baseBlock.getRelative(1, 1, 0));
            blockList.add(baseBlock.getRelative(-1, 1, 0));
        } else if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.BUILD_SNOWMAN)) {
            // Check body
            blockList.add(baseBlock);
            blockList.add(baseBlock.getRelative(0, 1, 0));
            blockList.add(baseBlock.getRelative(0, 2, 0));
            // Check arms / head
            blockList.add(baseBlock.getRelative(0, 1, 1));
            blockList.add(baseBlock.getRelative(0, 2, 1));
            blockList.add(baseBlock.getRelative(0, 1, -1));
            blockList.add(baseBlock.getRelative(0, 2, -1));
            // Check arms / head
            blockList.add(baseBlock.getRelative(1,1,0));
            blockList.add(baseBlock.getRelative(1,2,0));
            blockList.add(baseBlock.getRelative(-1,1,0));
            blockList.add(baseBlock.getRelative(-1,2,0));
        }
        for (Block block:blockList) {
            BlockLog log = plugin.getDataManager().getBlockFrom(block.getLocation());
            if (log != null) {
                Player player = Bukkit.getPlayer(log.getPlayer().getUniqueId());
                if (player != null)
                    if (player.hasPermission("draimcreative.bypass.spawn_build"))
                        continue;
                e.setCancelled(true);
                return;
            }
        }
    }
}
