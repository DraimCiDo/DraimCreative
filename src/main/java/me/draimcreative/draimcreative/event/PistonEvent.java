package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.log.BlockLog;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

public class PistonEvent implements Listener {
    private final DraimCreative plugin;

    public PistonEvent(DraimCreative instance) {
        plugin = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onExtend(BlockPistonExtendEvent e) {
        BlockFace pistonDirection = e.getDirection();
        for (Block toMoveBlock : e.getBlocks()) {
            BlockLog blockLog = plugin.getDataManager().getBlockFrom(toMoveBlock.getLocation());
            if (blockLog != null) {
                if (blockLog.isCreative()) {
                    Location movedBlock = toMoveBlock.getLocation().add(pistonDirection.getModX(),
                            pistonDirection.getModY(),
                            pistonDirection.getModZ());
                    plugin.getDataManager().moveBlock(toMoveBlock.getLocation(), movedBlock);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onRetract(BlockPistonRetractEvent e) {
        BlockFace pistonDirection = e.getDirection();
        for (Block toMoveBlock : e.getBlocks()) {
            BlockLog blockLog = plugin.getDataManager().getBlockFrom(toMoveBlock.getLocation());
            if (blockLog != null) {
                if (blockLog.isCreative()) {
                    Location movedBlock = toMoveBlock.getLocation().add(pistonDirection.getModX(),
                            pistonDirection.getModY(),
                            pistonDirection.getModZ());
                    plugin.getDataManager().moveBlock(toMoveBlock.getLocation(), movedBlock);
                }
            }
        }
    }
}
