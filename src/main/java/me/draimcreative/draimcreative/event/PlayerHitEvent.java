package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.Messages;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

public class PlayerHitEvent implements Listener {
    private final DraimCreative plugin;

    public PlayerHitEvent(DraimCreative instance) {
        plugin = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player attacker = (Player) e.getDamager();
            if (attacker.getGameMode().equals(GameMode.CREATIVE)) {
                if (e.getEntity() instanceof Player) {
                    if (!attacker.hasPermission("draimcreative.bypass.pvp") && plugin.getSettings().getProtection(Protections.PVP)) {
                        if (plugin.getSettings().getBoolean("sendmsg"))
                            Messages.sendMessage(plugin.getMessageManager(), attacker, "permission.hit.player");
                        e.setCancelled(true);
                    }
                } else {
                    if (!attacker.hasPermission("draimcreative.bypass.pve") && plugin.getSettings().getProtection(Protections.PVE)) {
                        if (plugin.getSettings().getBoolean("sendmsg"))
                            Messages.sendMessage(plugin.getMessageManager(), attacker, "permission.hit.monster");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent e) {
        ProjectileSource source = e.getEntity().getShooter();
        if (source instanceof Player) {
            Player attacker = (Player) source;
            if (attacker.getGameMode().equals(GameMode.CREATIVE)) {
                if (e.getHitEntity() instanceof Player) {
                    if (!attacker.hasPermission("draimcreative.bypass.pvp") && plugin.getSettings().getProtection(Protections.PVP)) {
                        if (plugin.getSettings().getBoolean("sendmsg"))
                            Messages.sendMessage(plugin.getMessageManager(), attacker, "permission.hit.player");
                        e.setCancelled(true);
                    }
                } else {
                    if (!attacker.hasPermission("draimcreative.bypass.pve") && plugin.getSettings().getProtection(Protections.PVE)) {
                        if (plugin.getSettings().getBoolean("sendmsg"))
                            Messages.sendMessage(plugin.getMessageManager(), attacker, "permission.hit.monster");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
