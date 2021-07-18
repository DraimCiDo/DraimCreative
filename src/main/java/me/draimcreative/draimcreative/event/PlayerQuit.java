package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.manager.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    private final DraimCreative plugin;

    public PlayerQuit(DraimCreative instance) {
        plugin = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        InventoryManager im = new InventoryManager(p, plugin);
        im.saveInventory(p.getGameMode());
    }

    @EventHandler(ignoreCancelled = true)
    public void onKicked(PlayerKickEvent e) {
        Player p = e.getPlayer();
        InventoryManager im = new InventoryManager(p, plugin);
        im.saveInventory(p.getGameMode());
    }
}
