package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.Messages;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryOpen implements Listener {
    private final DraimCreative plugin;

    public InventoryOpen(DraimCreative instance) {
        plugin = instance;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            if (p.getGameMode().equals(GameMode.CREATIVE) && plugin.getSettings().getProtection(Protections.CONTAINER)) {
                if (isProtectedChest(e.getInventory())) {
                    if (!p.hasPermission("draimcreative.bypass.container")) {
                        if (plugin.getSettings().getBoolean("sendmsg"))
                            Messages.sendMessage(plugin.getMessageManager(), p, "permission.container");
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    public boolean isProtectedChest(Inventory inventory) {
        if (inventory.getType().equals(InventoryType.ENDER_CHEST)) return true;
        if (getProtectedType().contains(inventory.getType())) {
            if (inventory.getHolder() != null)
                return inventory.getHolder().getClass().toString().contains("org.bukkit");
        }
        return false;
    }

    public List<InventoryType> getProtectedType() {
        List<InventoryType> typeList = new ArrayList<>();
        typeList.add(InventoryType.CHEST);
        typeList.add(InventoryType.FURNACE);
        typeList.add(InventoryType.BLAST_FURNACE);
        typeList.add(InventoryType.BARREL);
        typeList.add(InventoryType.BEACON);
        typeList.add(InventoryType.BREWING);
        typeList.add(InventoryType.DISPENSER);
        typeList.add(InventoryType.DROPPER);
        typeList.add(InventoryType.HOPPER);
        typeList.add(InventoryType.SHULKER_BOX);
        typeList.add(InventoryType.LECTERN);
        return typeList;
    }
}
