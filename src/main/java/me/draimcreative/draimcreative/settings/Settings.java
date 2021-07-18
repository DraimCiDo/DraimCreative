package me.draimcreative.draimcreative.settings;

import me.draimcreative.draimcreative.DraimCreative;

import java.util.List;

public class Settings extends me.draimlib.settings.Settings {
    public Settings(DraimCreative instance) {
        super(instance);
    }

    public boolean isLogged() {
        return getBoolean("log");
    }

    public boolean getProtection(Protections protections) {
        return getBoolean("protections." + protections.getName());
    }

    public boolean getBlackList(Blacklist blacklist) {
        return getBoolean("blacklist." + blacklist.getName());
    }

    public boolean creativeInvEnable() {
        return getBoolean("inventory.creative");
    }

    public boolean adventureInvEnable() {
        return getBoolean("inventory.adventure");
    }

    public List<String> getPlaceBL() {
        return getStringList("blacklist.place");
    }

    public List<String> getUseBL() {
        return getStringList("blacklist.use");
    }

    public List<String> getGetBL() {
        return getStringList("blacklist.get");
    }

    public List<String> getBreakBL() {
        return getStringList("blacklist.break");
    }

    public List<String> getCommandBL() {
        return getStringList("blacklist.commands");
    }

    /*
    public List<String> getLore() {
        return getStringList("creative-lore");
    }

     */
}