package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class InventoryMove implements Listener {

    private final DraimCreative plugin;
    private final HashMap<UUID, Long> cdtime = new HashMap<>();

    public InventoryMove(DraimCreative instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryCreativeEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null)
            itemStack = e.getCursor();
        else if (itemStack.getType().equals(Material.AIR))
            itemStack = e.getCursor();
        if (e.getClick().equals(ClickType.DROP) || e.getClick().equals(ClickType.CONTROL_DROP) ||
                e.getClick().equals(ClickType.WINDOW_BORDER_LEFT) || e.getClick().equals(ClickType.WINDOW_BORDER_RIGHT) ||
                e.getClick().equals(ClickType.UNKNOWN)) {
            if (plugin.getSettings().getProtection(Protections.DROP) && !player.hasPermission("draimcreative.bypass.drop")) {
                if (plugin.getSettings().getBoolean("send-player-messages"))
                    Messages.sendMessage(plugin.getMessageManager(), player, "permission.drop");
                e.setCancelled(true);
            }
            return;
        }
        List<String> blacklist = plugin.getSettings().getGetBL();
        if(blacklist.size() > 0)
            if (blacklist.stream().anyMatch(itemStack.getType().name()::equalsIgnoreCase)) {
                if (!player.hasPermission("draimcreative.bypass.blacklist.get")) {
                    if (cdtime.get(player.getUniqueId()) == null || (cdtime.get(player.getUniqueId()) + 1000) <= System.currentTimeMillis()) {
                        if (cdtime.get(player.getUniqueId()) != null) {
                            cdtime.remove(player.getUniqueId());
                        }
                        String blget = plugin.getLang().getString("blacklist.get");
                        HashMap<String, String> replaceMap = new HashMap<>();
                        replaceMap.put("{ITEM}", itemStack.getType().name());
                        if (blget != null)
                            Messages.sendMessage(plugin.getMessageManager(), player, "blacklist.get", replaceMap);
                        cdtime.put(player.getUniqueId(), System.currentTimeMillis());
                    }
                    e.setCancelled(true);
                }
            }
    }

/*
   private ItemStack addLore(ItemStack item, Player p) {
        if (item == null)
            return null;
        if (p == null)
            return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return item;
        }
        List<?> lore = this.plugin.getSettings().getLore();
        List<String> lore_t = new ArrayList<>();

        if (lore != null) {
            for (Object obj : lore) {
                if (obj instanceof String) {
                    String string = (String) obj;
                    string = string.replace("{PLAYER}", p.getName())
                            .replace("{UUID}", p.getUniqueId().toString())
                            .replace("{ITEM}", item.getType().getKey().getKey());
                    lore_t.add(ChatColor.translateAlternateColorCodes('&',string));
                }
            }
        }
        meta.setLore(lore_t);
        item.setItemMeta(meta);
        return item;
    }

 */
}

