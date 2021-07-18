package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.settings.Protections;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class PlayerPickup implements Listener {
    private final DraimCreative plugin;

    public PlayerPickup(DraimCreative plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent e) {
        if (!plugin.getSettings().getProtection(Protections.PICKUP)) return;
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        if (!p.getGameMode().equals(GameMode.CREATIVE)) return;
        if (!p.hasPermission("draimcreative.bypass.pickup")) {
            e.setCancelled(true);
        }
    }
}
