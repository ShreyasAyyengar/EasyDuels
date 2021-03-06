package me.shreyasayyengar.easyduels;

import me.shreyasayyengar.easyduels.commands.AcceptCommand;
import me.shreyasayyengar.easyduels.commands.DuelCommand;
import me.shreyasayyengar.easyduels.commands.StatsCommand;
import me.shreyasayyengar.easyduels.database.MySQL;
import me.shreyasayyengar.easyduels.events.GameListener;
import me.shreyasayyengar.easyduels.events.Join;
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

        ConfigManager.init(this);
        registerCommands();
        registerEvents();
        initMySQL();
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
                    "    kills     int default 0 null," +
                    "    losses    int default 0 null," +
                    "    deaths    int default 0 null" +
                    ");").executeUpdate();

        } catch (SQLException x) {
            getLogger().severe("Failed to create MySQL tables...");
        }
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new GameListener(), this);
        this.getServer().getPluginManager().registerEvents(new Join(), this);
    }

    private void registerCommands() {
        this.getCommand("duel").setExecutor(new DuelCommand());
        this.getCommand("accept").setExecutor(new AcceptCommand());
        this.getCommand("stats").setExecutor(new StatsCommand());
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
