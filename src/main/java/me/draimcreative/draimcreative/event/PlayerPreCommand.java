package me.draimcreative.draimcreative.event;

import me.draimcreative.draimcreative.DraimCreative;
import me.draimcreative.draimcreative.settings.Protections;
import me.draimlib.utils.Messages;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.Locale;

public class PlayerPreCommand implements Listener {

    DraimCreative plugin;

    public PlayerPreCommand(DraimCreative plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e)
    {
        if(!plugin.getSettings().getProtection(Protections.COMMANDS)) return;
        if(!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;
        String cmd = e.getMessage().toLowerCase().substring(1);
        for (String blCmd:plugin.getSettings().getCommandBL()) {
            Messages.log(plugin, blCmd.toLowerCase() + " / " + cmd);
            if(cmd.startsWith(blCmd.toLowerCase()))
            {
                e.setCancelled(true);
                if(plugin.getSettings().getBoolean("sendmsg"))
                    Messages.sendMessage(plugin.getMessageManager(), e.getPlayer(), "blacklist.commands");
            }
        }
    }
}
