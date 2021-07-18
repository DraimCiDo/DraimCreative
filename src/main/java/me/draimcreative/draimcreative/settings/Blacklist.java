package me.draimcreative.draimcreative.settings;

import me.draimlib.utils.LoreUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum Blacklist {
    PLACE("place", Material.BARRIER, "Ограничение на установку блока", "Установка блоков"),
    USE("use", Material.AMETHYST_BLOCK, "Ограничение использование предмета", "Предметы"),
    GET("get", Material.AMETHYST_BLOCK, "Ограничение на перетаскивание предметов в инв", "Перемещение предметов"),
    BREAK("break", Material.AMETHYST_BLOCK, "Ограничение на поломку блоков", "Ломание блоков"),
    COMMANDS("commands", Material.AMETHYST_BLOCK, "Ограничение использования блоков", "Команды");

    private final String name;
    private final Material icon;
    private final String desc;
    private final String displayName;

    Blacklist(String name, Material icon, String desc, String displayName) {
        this.name = name;
        this.icon = icon;
        this.desc = desc;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIconItem(boolean value) {
        ItemStack item = new ItemStack(this.icon);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.DARK_BLUE + "");
            lore.addAll(LoreUtils.formatLoreString(ChatColor.RESET + this.desc));
            lore.add(ChatColor.DARK_BLUE + "");
            if (value)
                lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Статус: Включено");
            else
                lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Статус: Выключено");
            itemMeta.setLore(lore);
            itemMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.BOLD + ChatColor.GOLD + this.displayName);
            if(value)
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(itemMeta);
        }
        return item;
    }
}
