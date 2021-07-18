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

public enum Protections {
    CONTAINER("container", Material.CHEST, "Ограничение на открытые сундуков", "Сундуки"),
    SPAWN("spawn", Material.SKELETON_SPAWN_EGG, "Ограничения на спавн мобов", "Спавн мобов"),
    DROP("drop", Material.DROPPER, "Ограничение на выкидывание вещей", "Дроп"),
    PICKUP("pickup", Material.TRIPWIRE_HOOK, "Ограничение на подбор вещей", "Подбор лута"),
    BUILD("build", Material.OAK_PLANKS, "Ограничение на строительство блоков", "Строительство"),
    ENTITY("entity", Material.ITEM_FRAME, "Ограничение на взаимодействие с мобами", "Мобы"),
    PVP("pvp", Material.PLAYER_HEAD, "Ограничение на PVP", "ПвП"),
    PVE("pve", Material.SKELETON_SKULL, "Ограничение на урон по мобам", "Урон мобам"),
    /*
    LORE("lore", Material.NAME_TAG, "", ""),

     */
    LOOT("loot", Material.NAME_TAG, "Ограничение на лут предметов", "лут"),
    SPAWN_BUILD("spawn-build", Material.WITHER_SKELETON_SKULL, "Ограничение на создание боссов", "Боссы"),
    THROW("throw", Material.ARROW, "Ограничение на стрельбу", "Стрельба"),
    COMMANDS("commands", Material.COMMAND_BLOCK, "Ограничение на команды", "Команды");

    private final String name;
    private final Material icon;
    private final String desc;
    private final String displayName;

    Protections(String name, Material icon, String desc, String displayName) {
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
            lore.add(ChatColor.DARK_GRAY + "------");
            lore.addAll(LoreUtils.formatLoreString(ChatColor.RESET + this.desc));
            lore.add(ChatColor.DARK_GRAY + "------");
            if (value)
                lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Статус: Запущен");
            else
                lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Статус: Отключен");
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
