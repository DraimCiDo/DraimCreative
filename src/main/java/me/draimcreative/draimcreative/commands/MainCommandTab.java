package me.draimcreative.draimcreative.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;


public class MainCommandTab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        switch (args.length) {
            case 1:
                if (sender.hasPermission("draimcreative.reload"))
                    list.add("reload");
                if (sender.hasPermission("draimcreative.admin")) {
                    list.add("inventory");
                    list.add("settings");
                    list.add("blacklist");
                }
                break;
            case 2:
                if (sender.hasPermission("draimcreative.admin"))
                    if (args[0].equals("inventory")) {
                        list.add("save");
                        list.add("load");
                    }
                break;
        }
        return list;
    }
}

