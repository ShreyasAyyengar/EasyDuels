package me.shreyasayyengar.easyduels.utils;

import me.shreyasayyengar.easyduels.EasyDuels;
import me.shreyasayyengar.easyduels.database.MySQL;
import me.shreyasayyengar.easyduels.objects.StatType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLUtils {

    private SQLUtils() {
    }


    public static void addStatistic(StatType type, UUID uuid) {

        MySQL database = EasyDuels.getInstance().getDatabase();

        try {
            switch (type) {
                case WIN -> {
                    database.preparedStatement("UPDATE player_stats SET wins = wins + 1 WHERE uuid = '" + uuid + "'").executeUpdate();
                    database.preparedStatement("UPDATE player_stats SET winstreak = winstreak + 1 WHERE uuid = '" + uuid + "'").executeUpdate();
                }

                case KILL -> database.preparedStatement("UPDATE player_stats SET kills = kills + 1 WHERE uuid = '" + uuid + "'").executeUpdate();

                case LOST -> {
                    database.preparedStatement("UPDATE player_stats SET losses = losses + 1 WHERE uuid = '" + uuid + "'").executeUpdate();
                    database.preparedStatement("UPDATE player_stats SET winstreak = 0 WHERE uuid = '" + uuid + "'").executeUpdate();
                }

                case DEATH -> database.preparedStatement("UPDATE player_stats SET deaths = deaths + 1 WHERE uuid = '" + uuid + "'").executeUpdate();
            }
        } catch (SQLException x) {
            x.printStackTrace();
        }

    }

    public static void sendStats(Player player, UUID uuid) throws SQLException {

        MySQL database = EasyDuels.getInstance().getDatabase();

        ResultSet resultSet = database.preparedStatement("select * from player_stats where uuid = '" + uuid + "';").executeQuery();
        resultSet.next();

        player.sendMessage(Utility.colourise("&e-------------------------"));
        player.sendMessage(Utility.colourise("&d" + Bukkit.getPlayer(uuid).getName() + "'s Stats!"));
        player.sendMessage(Utility.colourise("&6Wins: &l" + resultSet.getInt("wins")));
        player.sendMessage(Utility.colourise("&6Kills: &l" + resultSet.getInt("kills")));
        player.sendMessage(Utility.colourise("&6Losses: &l" + resultSet.getInt("losses")));
        player.sendMessage(Utility.colourise("&6Deaths: &l" + resultSet.getInt("deaths")));
        player.sendMessage(Utility.colourise("&e-------------------------"));

    }

    public static void createData(UUID uuid) {
        try {
            ResultSet resultSet = EasyDuels.getInstance().getDatabase().preparedStatement("select count(uuid) from player_stats where uuid = '" + uuid + "';").executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) == 0) {
                EasyDuels.getInstance().getDatabase().preparedStatement(
                        "insert into player_stats (uuid, winstreak, wins, kills, losses, deaths) values('" + uuid + "', default, default, default, default, default);"
                ).executeUpdate();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
