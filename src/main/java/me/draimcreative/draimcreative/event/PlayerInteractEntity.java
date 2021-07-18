package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.Messages;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity implements Listener {
    private final DraimCreative plugin;

    public PlayerInteractEntity(DraimCreative instance) {
        plugin = instance;
    }

    /**
     * On use.
     *
     * @param e the event.
     */
    @EventHandler(ignoreCancelled = true)
    public void onUse(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (plugin.getSettings().getProtection(Protections.ENTITY) && p.getGameMode().equals(GameMode.CREATIVE) && !p.hasPermission("draimcreative.bypass.entity")) {
            if (!p.hasPermission("draimcreative.bypass.entity") && !p.hasPermission("draimcreative.bypass.entity." + e.getRightClicked().getType().name().toLowerCase())) {
                if (plugin.getSettings().getBoolean("send-player-messages"))
                    Messages.sendMessage(plugin.getMessageManager(), p, "permission.entity");
                e.setCancelled(true);
            }
        }
    }
}
