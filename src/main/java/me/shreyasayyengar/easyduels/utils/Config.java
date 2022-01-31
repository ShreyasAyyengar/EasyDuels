package me.shreyasayyengar.easyduels.utils;

import me.shreyasayyengar.easyduels.EasyDuels;

public class Config {
    private static EasyDuels main;

    public static void init(EasyDuels main) {
        Config.main = main;
        main.getConfig().options().configuration();
        main.saveDefaultConfig();
    }

    public static Object getMySQL(String key) {
        return main.getConfig().get("MySQL." + key);
    }


}
