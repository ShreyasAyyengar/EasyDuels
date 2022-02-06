package me.shreyasayyengar.easyduels.utils;

import me.shreyasayyengar.easyduels.EasyDuels;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;

public class ConfigManager {

    private static EasyDuels main;

    private ConfigManager() {
    }

    public static void init(EasyDuels main) {
        ConfigManager.main = main;
        main.getConfig().options().configuration();
        main.saveDefaultConfig();
    }

    public static Object getMySQL(String key) {
        return main.getConfig().get("MySQL." + key);
    }

    public static String getSystemMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("system-messages." + path));
    }

    public static Location getLocation(boolean first) {
        return first ? main.getConfig().getLocation("spawns.spawn1") : main.getConfig().getLocation("spawns.spawn2");
    }

    public static Location getLocation(String path) {
        return main.getConfig().getLocation("spawns." + path);
    }

    public static Set<String> getKits() {
        return new HashSet<>(EasyDuels.getInstance().getConfig().getConfigurationSection("kits").getKeys(false));
    }

    public static String getDefaultKit() {
        return main.getConfig().getString("default-kit");
    }


}
