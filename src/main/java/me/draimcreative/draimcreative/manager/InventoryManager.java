package me.draimcreative.draimcreative.manager;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.settings.UserData;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InventoryManager {
    private final Player p;
    private final UserData cm;
    private final DraimCreative plugin;

    public InventoryManager(Player p, DraimCreative instance) {
        this.p = p;
        this.plugin = instance;
        this.cm = new UserData(p, plugin);
    }

    public static String itemStackArrayToBase64(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(items.length);

            for (ItemStack item : items) {
                dataOutput.writeObject(item);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Не удается сохранить стаки предметов.", e);
        }
    }

    public Player getPlayer() {
        return this.p;
    }

    public void loadInventory(GameMode gm) {
        String gm_name = gm.name();
        if (cm.contains(gm_name + ".content") && cm.isString(gm_name + ".content") && cm.contains(gm_name + ".armor") && cm.isString(gm_name + ".armor")) {
            try {
                p.getInventory().setContents(this.itemStackArrayFromBase64(cm.getString(gm_name + ".content")));
                p.getInventory().setArmorContents(this.itemStackArrayFromBase64(cm.getString(gm_name + ".armor")));
            } catch (IOException e) {
                plugin.getLogger().severe(e.getMessage());
            }
            if (plugin.getConfig().getBoolean("log"))
                this.plugin.getLogger().info("Загружен инвентарь игрка " + p.getName() + " из файла " + p.getUniqueId() + ".yml для режима " + gm_name);
        } else {
            p.getInventory().clear();
            if (plugin.getConfig().getBoolean("log"))
                this.plugin.getLogger().info("Очищен инвентарь игрока " + p.getName() + " (" + p.getUniqueId() + ") потому что, не найдены предметы в режиме " + gm_name);
        }

    }

    public void saveInventory(GameMode gm) {
        String gm_name = gm.name();
        String[] encoded = this.playerInventoryToBase64(p.getInventory());
        cm.set(gm_name + ".content", encoded[0]);
        cm.set(gm_name + ".armor", encoded[1]);
        if (cm.contains(gm_name + ".content") && cm.isString(gm_name + ".content") && cm.contains(gm_name + ".armor") && cm.isString(gm_name + ".armor")) {
            cm.save();
            if (plugin.getConfig().getBoolean("log"))
                this.plugin.getLogger().info("Сохранен инвентарь игрока " + p.getName() + " в файл " + p.getUniqueId() + ".yml для режима " + gm_name);
        }
    }

    private String[] playerInventoryToBase64(PlayerInventory playerInventory) throws IllegalStateException {
        String content = this.toBase64(playerInventory);
        String armor = itemStackArrayToBase64(playerInventory.getArmorContents());

        return new String[]{content, armor};
    }

    private String toBase64(Inventory inventory) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Не возможно сохранить стак предметов. ", e);
        }
    }

    private ItemStack[] itemStackArrayFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Не удалось декодировать тип класса.", e);
        }
    }
}
