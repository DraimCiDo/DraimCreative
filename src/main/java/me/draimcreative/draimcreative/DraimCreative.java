package me.draimcreative.draimcreative;

import com.google.common.io.Files;
import me.draimcreative.draimcreative.commands.MainCommand;
import me.draimcreative.draimcreative.commands.MainCommandTab;
import me.draimcreative.draimcreative.event.*;
import me.draimcreative.draimcreative.log.DataManager;
import me.draimcreative.draimcreative.settings.Settings;
import me.draimcreative.draimcreative.task.SaveTask;
import me.draimlib.settings.Configuration;
import me.draimlib.settings.Lang;
import me.draimlib.updater.UpdateChecker;
import me.draimlib.utils.Messages;
import me.draimlib.utils.MessagesManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.EntityType;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class DraimCreative extends JavaPlugin {

    private final String invTag = ChatColor.BOLD + "" + ChatColor.DARK_RED + "DC " + ChatColor.RESET + "> ";
    private Settings settings;
    private Lang lang;
    private MessagesManager messagesManager;
    private DataManager dataManager;
    private int saveTask;

    @Override
    public void onEnable() {
        Messages.log(this, "&9=============================================================");
        UpdateChecker updateChecker = new UpdateChecker(this, 75097);
        if (updateChecker.isUpToDate()) {
            Messages.log(this, "&2DraimCreative &av" + this.getDescription().getVersion());
        } else {
            Messages.log(this, "&2DraimCreative &cv" + this.getDescription().getVersion());
        }

        Messages.log(this, "&9=============================================================");
        Messages.log(this, "&2Создано DraimGooSe для DraimCiDo");
        Messages.log(this, "&9=============================================================");
        this.updateConfig();
        this.loadConfigManager();
        this.registerEvent(this.getServer().getPluginManager());
        Messages.log(this, "&2Чтение загружено!");
        this.registerCommand();
        Messages.log(this, "&2Команды загружены!");
        this.registerPermissions();
        this.loadLog();
        this.saveTask = SaveTask.run(this);
        Messages.log(this, "&9=============================================================");
    }

    public void loadConfigManager() {
        this.settings = new Settings(this);
        Messages.log(this, "&2Конфигурация загружена!");
        this.lang = new Lang(settings.getLang(), this);
        Messages.log(this, "&2Язык загружен! &7[" + settings.getLang() + "]");
        if (this.messagesManager != null) {
            messagesManager.setLang(lang);
            messagesManager.setSettings(settings);
        } else
            this.messagesManager = new MessagesManager(settings, lang, this);
    }

    public void updateConfig() {
        Configuration.updateConfig("lang/ru_RU.yml", this);
        Configuration oldConfig = new Configuration("config.yml", this);
        if (oldConfig.contains("build-protection")) {
            try {
                Files.move(oldConfig.getFile(), new File(oldConfig.getFile().getParentFile(), "old_config.yml"));
                oldConfig = new Configuration("old_config.yml", this);
                this.settings = new Settings(this);
                this.settings.set("protections.entity", oldConfig.getBoolean("entity-protection"));
                this.settings.set("protections.pvp", oldConfig.getBoolean("pvp-protection"));
                this.settings.set("protections.container", oldConfig.getBoolean("container-protection"));
                this.settings.set("protections.spawn", oldConfig.getBoolean("spawn-protection"));
                this.settings.set("protections.drop", oldConfig.getBoolean("drop-protection"));
                this.settings.set("protections.pve", oldConfig.getBoolean("hitmonster-protection"));
                this.settings.set("protections.lore", oldConfig.getBoolean("add-lore"));
                this.settings.set("inventory.adventure", oldConfig.getBoolean("adventure-inventory"));
                this.settings.set("inventory.creative", oldConfig.getBoolean("creative-inventory"));
                this.settings.set("tag", oldConfig.getString("tag"));
                this.settings.set("lang", oldConfig.getString("lang"));
                this.settings.set("log", oldConfig.getBoolean("log"));
                this.settings.set("blacklist", oldConfig.getConfigurationSection("blacklist"));
                this.settings.set("blacklist", oldConfig.getConfigurationSection("blacklist"));
                this.settings.set("save-interval", 300);
                this.settings.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Configuration.updateConfig("config.yml", this);
    }

    private void registerEvent(PluginManager pm) {
        pm.registerEvents(new PlayerBuild(this), this);
        pm.registerEvents(new PlayerBreak(this), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new PlayerInteractEntity(this), this);
        pm.registerEvents(new PlayerInteractAtEntity(this), this);
        pm.registerEvents(new PlayerDrop(this), this);
        pm.registerEvents(new InventoryMove(this), this);
        pm.registerEvents(new PlayerGamemodeChange(this), this);
        pm.registerEvents(new PlayerHitEvent(this), this);
        pm.registerEvents(new PlayerQuit(this), this);
        pm.registerEvents(new PlayerLogin(this), this);
        pm.registerEvents(new PistonEvent(this), this);
        pm.registerEvents(new MonsterSpawnEvent(this), this);
        pm.registerEvents(new ProjectileThrow(this), this);
        pm.registerEvents(new InventoryOpen(this), this);
        pm.registerEvents(new PlayerPreCommand(this), this);
        pm.registerEvents(new PlayerPickup(this), this);
        pm.registerEvents(new ExplodeEvent(this), this);
    }

    private void registerCommand() {
        PluginCommand mainCommand = this.getCommand("draimc");
        if (mainCommand != null) {
            mainCommand.setExecutor(new MainCommand(this));
            mainCommand.setTabCompleter(new MainCommandTab());
        }
    }

    private void registerPermissions() {
        PluginManager pm = getServer().getPluginManager();
        Set<Permission> permissions = pm.getPermissions();
        int n = 0;
        for (EntityType entityType : EntityType.values()) {
            Permission perm = new Permission("draimcreative.bypass.entity." + entityType.name());
            if (!permissions.contains(perm)) {
                pm.addPermission(perm);
                n++;
            }
        }
        Messages.log(this, "&2Права для существ загружены! &7[" + n + "]");
    }

    private void loadLog() {
        dataManager = new DataManager("data", this);
        Messages.log(this,
                "&2Логи загружены из базы! &7[" + dataManager.getBlockLogHashMap().size() + "]");
    }

    public Settings getSettings() {
        return this.settings;
    }

    public Lang getLang() {
        return this.lang;
    }

    public String getInvTag() {
        return invTag;
    }

    public MessagesManager getMessageManager() {
        return messagesManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public void onDisable() {
        if (Bukkit.getScheduler().isCurrentlyRunning(saveTask) || Bukkit.getScheduler().isQueued(saveTask))
            Bukkit.getScheduler().cancelTask(saveTask);
        if(dataManager != null)
            dataManager.saveSync();
    }
}
