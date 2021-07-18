package me.draimcreative.draimcreative.task;

import me.draimcreative.draimcreative.DraimCreative;
import org.bukkit.Bukkit;

public class SaveTask {
    public static int run(DraimCreative plugin) {
        int interval = plugin.getConfig().getInt("save-interval");
        if (interval > 0) {
            return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () ->
                            plugin.getDataManager().save(),
                    0L, interval * 20L);
        }
        return 0;
    }
}
