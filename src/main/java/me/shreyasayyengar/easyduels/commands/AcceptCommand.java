package me.shreyasayyengar.easyduels.commands;

import me.shreyasayyengar.easyduels.EasyDuels;
import me.shreyasayyengar.easyduels.objects.DuelRequest;
import me.shreyasayyengar.easyduels.utils.ConfigManager;
import me.shreyasayyengar.easyduels.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {

            if (args.length > 1) {
                player.sendMessage(Utility.colourise("&cInvalid Syntax! /accept <player>"));
                return false;
            }

            String acceptingFrom = args[0];

            for (DuelRequest request : EasyDuels.getInstance().getGameManager().getRequests()) {

                if (request.getTarget().equals(player.getUniqueId()) && request.getRequester().equals(Bukkit.getPlayer(acceptingFrom).getUniqueId())) {
                    request.approve();
                    return false;
                }
            }

            player.sendMessage(Utility.colourise(ConfigManager.getSystemMessage("no-request")));

        }

        return false;
    }
}
