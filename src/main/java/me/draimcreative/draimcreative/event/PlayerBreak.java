package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.log.BlockLog;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.Messages;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashMap;

public class PlayerBreak implements Listener {
    private final DraimCreative plugin;

    public PlayerBreak(DraimCreative instance) {
        plugin = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            if (plugin.getSettings().getProtection(Protections.BUILD) && !p.hasPermission("draimcreative.bypass.build")) {
                if (plugin.getSettings().getBoolean("send-player-messages"))
                    Messages.sendMessage(plugin.getMessageManager(), p, "permission.build");
                e.setCancelled(true);
            } else if (plugin.getSettings().getBreakBL().stream().anyMatch(e.getBlock().getType().name()::equalsIgnoreCase) && !p.hasPermission("draimcreative.bypass.blacklist.break")) {
                HashMap<String, String> replaceMap = new HashMap<>();
                replaceMap.put("{BLOCK}", e.getBlock().getType().name());
                if (plugin.getSettings().getBoolean("send-player-messages"))
                    Messages.sendMessage(plugin.getMessageManager(), p, "blacklist.place", replaceMap);
                e.setCancelled(true);
            }
        } else {
            if (!p.hasPermission("draimcreative.bypass.log") && plugin.getSettings().getProtection(Protections.LOOT)) {
                BlockLog blockLog = plugin.getDataManager().getBlockFrom(e.getBlock().getLocation());
                if (blockLog != null) {
                    if (blockLog.isCreative()) {
                        e.setDropItems(false);
                        e.setExpToDrop(0);
                        plugin.getDataManager().removeBlock(blockLog.getLocation());
                    }
                }
            }
        }
    }
}
