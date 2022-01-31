package me.shreyasayyengar.easyduels;

import me.shreyasayyengar.easyduels.database.MySQL;
import me.shreyasayyengar.easyduels.utils.Config;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class EasyDuels extends JavaPlugin {

    private MySQL database;

    @Override
    public void onEnable() {


        registerCommands();
        registerEvents();
        initMySQL();
        Config.init(this);
        getLogger().info("Plugin started with no errors present!");

    }

    private void initMySQL() {

        try {

            database = new MySQL(
                    (String) Config.getMySQL("username"),
                    (String) Config.getMySQL("password"),
                    (String) Config.getMySQL("database"),
                    (String) Config.getMySQL("host"),
                    (int) Config.getMySQL("port"));

            database.preparedStatement("create table if not exists player_stats (" +
                    "    uuid      varchar(36)   null," +
                    "    winstreak int default 0 null," +
                    "    wins      int default 0 null," +
                    "    losses    int default 0 null," +
                    "    kills     int default 0 null," +
                    "    shoots    int default 0 null" +
                    ");").executeUpdate();

        } catch (SQLException x) {
            getLogger().severe("Failed to create MySQL tables...");
        }
    }

    private void registerEvents() {

    }

    private void registerCommands() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static EasyDuels getInstance() {
        return JavaPlugin.getPlugin(EasyDuels.class);
    }

    public MySQL getDatabase() {
        return database;
    }


}
