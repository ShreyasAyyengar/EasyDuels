package me.shreyasayyengar.easyduels.commands;

import me.shreyasayyengar.easyduels.utils.SQLUtils;
import me.shreyasayyengar.easyduels.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player player) {

            if (args.length == 1) {

                if (!Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore()) {
                    player.sendMessage(Utility.colourise("&cThat player has not joined the server before!"));
                    return false;
                }

                try {
                    SQLUtils.sendStats(player, Bukkit.getPlayer(args[0]).getUniqueId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return false;
    }
}
