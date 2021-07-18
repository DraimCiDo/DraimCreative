package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.manager.InventoryManager;
import me.draimlib.utils.Messages;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.HashMap;

public class PlayerGamemodeChange implements Listener {
    private final DraimCreative plugin;

    public PlayerGamemodeChange(DraimCreative instance) {
        plugin = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onGMChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("draimcreative.bypass.inventory")) {
            InventoryManager im = new InventoryManager(p, plugin);
            if (!plugin.getSettings().adventureInvEnable()) {
                if (e.getNewGameMode().equals(GameMode.ADVENTURE)) {
                    im.saveInventory(p.getGameMode());
                    return;
                } else if (p.getGameMode().equals(GameMode.ADVENTURE)) {
                    im.saveInventory(GameMode.SURVIVAL);
                    return;
                }
            }
            if (!plugin.getSettings().creativeInvEnable()) {
                if (e.getNewGameMode().equals(GameMode.CREATIVE)) {
                    im.saveInventory(p.getGameMode());
                    return;
                } else if (p.getGameMode().equals(GameMode.CREATIVE)) {
                    im.saveInventory(GameMode.SURVIVAL);
                    return;
                }
            }
            im.saveInventory(p.getGameMode());
            im.loadInventory(e.getNewGameMode());
            HashMap<String, String> replaceMap = new HashMap<>();
            replaceMap.put("{GAMEMODE}", e.getNewGameMode().name());
            if(plugin.getSettings().getBoolean("send-player-messages"))
                Messages.sendMessage(plugin.getMessageManager(), p, "inventory.change", replaceMap);
        }
    }
}
