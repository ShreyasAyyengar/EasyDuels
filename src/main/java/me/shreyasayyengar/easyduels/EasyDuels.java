package me.shreyasayyengar.easyduels;

import me.shreyasayyengar.easyduels.database.MySQL;
import me.shreyasayyengar.easyduels.utils.ConfigManager;
import me.shreyasayyengar.easyduels.utils.GameManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class EasyDuels extends JavaPlugin {

    private MySQL database;
    private GameManager manager;

    public static EasyDuels getInstance() {
        return JavaPlugin.getPlugin(EasyDuels.class);
    }

    @Override
    public void onEnable() {

        registerCommands();
        registerEvents();
        initMySQL();
        ConfigManager.init(this);
        manager = new GameManager();
        getLogger().info("Plugin started with no errors present!");

    }

    private void initMySQL() {

        try {

            database = new MySQL(
                    (String) ConfigManager.getMySQL("username"),
                    (String) ConfigManager.getMySQL("password"),
                    (String) ConfigManager.getMySQL("database"),
                    (String) ConfigManager.getMySQL("host"),
                    (int) ConfigManager.getMySQL("port"));

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

    public MySQL getDatabase() {
        return database;
    }

    public GameManager getGameManager() {
        return manager;
    }

}
