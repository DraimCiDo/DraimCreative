package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.Messages;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.util.HashMap;

public class PlayerInteract implements Listener {
    private final DraimCreative plugin;

    public PlayerInteract(DraimCreative instance) {
        plugin = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.getGameMode().equals(GameMode.CREATIVE)) return;
        if (e.getItem() != null) {
            if (plugin.getSettings().getUseBL().stream().anyMatch(e.getItem().getType().name()::equalsIgnoreCase))
                if (!p.hasPermission("draimcreative.bypass.blacklist.use")) {
                    HashMap<String, String> replaceMap = new HashMap<>();
                    replaceMap.put("{ITEM}", e.getItem().getType().name());
                    Messages.sendMessage(plugin.getMessageManager(), p, "blacklist.use", replaceMap);
                    e.setCancelled(true);
                    return;
                }
            if (e.getItem().getItemMeta() instanceof SpawnEggMeta) {
                if (!p.hasPermission("draimcreative.bypass.spawn_egg") && plugin.getSettings().getProtection(Protections.SPAWN)) {
                    if(plugin.getSettings().getBoolean("send-player-messages"))
                        Messages.sendMessage(plugin.getMessageManager(), p, "permission.spawn");
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock() != null) {
                if (e.getClickedBlock().getState() instanceof InventoryHolder || e.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
                    if (!p.hasPermission("draimcreative.container") && plugin.getSettings().getProtection(Protections.CONTAINER)) {
                        if (plugin.getSettings().getBoolean("send-player-messages"))
                            Messages.sendMessage(plugin.getMessageManager(), p, "permission.container");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
