package me.draimcreative.draimcreative;

import me.
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

public final class DraimCreative extends JavaPlugin {

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
            Messages

        }


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
