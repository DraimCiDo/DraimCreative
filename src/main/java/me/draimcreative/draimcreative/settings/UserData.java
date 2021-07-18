package me.draimcreative.draimcreative.settings;

import me.draimlib.settings.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UserData extends Configuration {
    public UserData(Player p, JavaPlugin instance) {
        super(p.getUniqueId() + ".yml", instance, "data");
    }
}
