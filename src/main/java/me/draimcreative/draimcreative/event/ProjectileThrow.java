package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.Messages;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

public class ProjectileThrow implements Listener {
    private final DraimCreative plugin;

    public ProjectileThrow(DraimCreative instance) {
        plugin = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDrop(ProjectileLaunchEvent e) {
        ProjectileSource source = e.getEntity().getShooter();
        if (source instanceof Player) {
            Player p = (Player) source;
            if (plugin.getSettings().getProtection(Protections.THROW) && p.getGameMode().equals(GameMode.CREATIVE)) {
                if (!p.hasPermission("draimcreative.bypass.throw")) {
                    if (plugin.getSettings().getBoolean("send-player-messages"))
                        Messages.sendMessage(plugin.getMessageManager(), p, "permission.throw");
                    e.setCancelled(true);
                }
            }
        }
    }
}
