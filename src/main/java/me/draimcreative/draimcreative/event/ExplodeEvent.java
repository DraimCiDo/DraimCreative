package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.log.BlockLog;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.Messages;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplodeEvent implements Listener {

    private final DraimCreative plugin;

    public ExplodeEvent(DraimCreative draimc)
    {
        plugin = draimc;
    }

    @EventHandler
    public void onBlockBreak(BlockExplodeEvent e) {
        if (plugin.getSettings().getProtection(Protections.LOOT)) {
            for(Block block: e.blockList())
            {
                BlockLog blockLog = plugin.getDataManager().getBlockFrom(block.getLocation());
                if (blockLog != null) {
                    if (blockLog.isCreative()) {
                        block.setType(Material.AIR);
                        plugin.getDataManager().removeBlock(blockLog.getLocation());
                    }
                }
            }
        }
    }
    @EventHandler
    public void onBlockBreak(EntityExplodeEvent e) {
        if (plugin.getSettings().getProtection(Protections.LOOT)) {
            for(Block block: e.blockList())
            {
                BlockLog blockLog = plugin.getDataManager().getBlockFrom(block.getLocation());
                if (blockLog != null) {
                    if (blockLog.isCreative()) {
                        block.setType(Material.AIR);
                        plugin.getDataManager().removeBlock(blockLog.getLocation());
                    }
                }
            }
        }
    }
}

