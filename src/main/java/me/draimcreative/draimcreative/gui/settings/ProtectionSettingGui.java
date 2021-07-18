package me.draimcreative.draimcreative.gui.settings;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.gui.Gui;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.AkuraHeads;
import me.draimlib.utils.MenuUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;


public class ProtectionSettingGui extends Gui {

    private final DraimCreative plugin;
    private final HashMap<Integer, Protections> menuMap = new HashMap<>();

    public ProtectionSettingGui(Player player, DraimCreative plugin) {
        super(player, plugin);
        this.plugin = plugin;
        setInv(Bukkit.createInventory(null, 3 * 9, plugin.getInvTag() + "Параметры плагина"));
        initItem();
    }

    @Override
    public void initItem() {
        int i = 0;
        MenuUtils.addSeparator(getInv(), 2);
        MenuUtils.addSeparator(getInv(), 3);
        for (Protections prot : Protections.values()) {
            ItemStack itemStack = prot.getIconItem(this.plugin.getSettings().getProtection(prot));
            getInv().setItem(i, itemStack);
            menuMap.put(i, prot);
            i++;
        }
        getInv().setItem(getInv().getSize() - 1, AkuraHeads.CLOSE.getHead());
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().equals(this.getInv())) return;
        e.setCancelled(true);

        if (e.getSlot() == getInv().getSize() - 1) {
            getPlayer().closeInventory();
        }

        if (!getPlayer().hasPermission("draimcreative.admin")) return;
        if (menuMap.containsKey(e.getSlot())) {
            Protections prot = menuMap.get(e.getSlot());
            boolean newValue = !this.plugin.getSettings().getProtection(prot);
            if (this.plugin.getSettings().isLogged())
                this.plugin.getSettings().set("protections." + prot.getName(), newValue);
            String status = "Включено";
            if (!newValue)
                status = "Отключено";
            this.plugin.getLogger().info(
                    ChatColor.GOLD + e.getWhoClicked().getName() + " изменил " + prot.getName() + " на " + status);
            initItem();
        }
    }
}
