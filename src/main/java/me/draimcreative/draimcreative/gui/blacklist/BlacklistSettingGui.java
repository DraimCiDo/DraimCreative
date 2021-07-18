package me.draimcreative.draimcreative.gui.blacklist;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.gui.Gui;
import me.draimcreative.draimcreative.settings.Blacklist;
import me.draimlib.utils.AkuraHeads;
import me.draimlib.utils.MenuUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BlacklistSettingGui extends Gui {

    private final DraimCreative plugin;
    private final HashMap<Integer, Blacklist> menuMap = new HashMap<>();

    public BlacklistSettingGui(Player player, DraimCreative plugin) {
        super(player, plugin);
        this.plugin = plugin;
        setInv(Bukkit.createInventory(null, 3 * 9, plugin.getInvTag() + "Черный список плагина"));
        initItem();
    }


    @Override
    public void initItem() {
        int i = 0;
        MenuUtils.addSeparator(getInv(), 2);
        MenuUtils.addSeparator(getInv(), 3);
        for (Blacklist black : Blacklist.values()) {
            ItemStack itemStack = black.getIconItem(this.plugin.getSettings().getBlackList(black));
            getInv().setItem(i, itemStack);
            menuMap.put(i, black);
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
            Blacklist black = menuMap.get(e.getSlot());
            boolean newValue = !this.plugin.getSettings().getBlackList(black);
            if (this.plugin.getSettings().isLogged())
                this.plugin.getSettings().set("blacklist." + black.getName(), newValue);
            String status = "";
            if (!newValue)
                status = "";
            this.plugin.getLogger().info(
                    ChatColor.GOLD + e.getWhoClicked().getName() + " добавил " + black.getName() + " для " + status);
            initItem();

        }
    }

}
