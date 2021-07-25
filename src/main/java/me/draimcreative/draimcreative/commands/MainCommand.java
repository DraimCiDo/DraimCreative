package me.draimcreative.draimcreative.commands;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.gui.blacklist.BlacklistSettingGui;
import me.draimcreative.draimcreative.gui.settings.ProtectionSettingGui;
import me.draimcreative.draimcreative.manager.InventoryManager;
import me.draimlib.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    private final DraimCreative plugin;

    public MainCommand(DraimCreative instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Conversable conversable = null;
        if(sender instanceof ConsoleCommandSender)
            conversable = (ConsoleCommandSender) sender;
        else if(sender instanceof Player)
            conversable = (Player) sender;
        if (args.length >= 1) {
            if (args[0].equals("reload")) {
                if (!(sender instanceof Player) || sender.hasPermission("draimcreative.reload")) {
                    plugin.loadConfigManager();
                    Messages.sendMessageText(plugin.getMessageManager(), conversable, " &5Конфигурация перезагружна!");
                } else {
                    Messages.sendMessage(plugin.getMessageManager(), conversable, "permission.general");
                }
            } else if (args[0].equals("settings")) {
                if (sender instanceof Player && sender.hasPermission("draimcreative.admin")) {
                    new ProtectionSettingGui((Player) sender, plugin).show();
                } else {
                    Messages.sendMessage(plugin.getMessageManager(), conversable, "permission.general");
                }
            } else if (args[0].equals("blacklist")) {
                if (sender instanceof  Player && sender.hasPermission("draimcreative.admin")) {
                    Messages.sendMessageText(plugin.getMessageManager(), conversable, " &cДанная функция в разработке!");
                  //  new BlacklistSettingGui((Player) sender, plugin).show();
                } else {
                    Messages.sendMessage(plugin.getMessageManager(), conversable, "permission.general");
                }
            } else if (args[0].equals("inventory") && sender instanceof Player && sender.hasPermission("draimcreative.admin")) {
                if (args.length >= 2) {
                    Player p = (Player) sender;
                    InventoryManager im = new InventoryManager(p, plugin);
                    switch (args[1]) {
                        case "save":
                            im.saveInventory(p.getGameMode());
                            break;
                        case "load":
                            im.loadInventory(p.getGameMode());
                            break;
                        default:
                            break;
                    }
                } else {
                    Messages.sendMessageText(plugin.getMessageManager(), conversable, " &7Аругумент не обнаружен!");
                }
            } else {
                Messages.sendMessageText(plugin.getMessageManager(), conversable, " &7Не известная команда " + args[0] + "!");
            }
        } else {
            Messages.sendMessageText(plugin.getMessageManager(), conversable, " &7Ограничитель креатива - " + plugin.getName() + " v" + plugin.getDescription().getVersion());
        }
        return true;
    }
}