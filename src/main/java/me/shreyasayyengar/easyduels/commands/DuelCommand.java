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

public class DuelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (sender instanceof Player player) {

            if (args.length == 1) { // /duel <name>
                handleRequest(player, args[0], ConfigManager.getDefaultKit());
            }

            if (args.length == 2) { // /duel <name> <kit>
                handleRequest(player, args[0], args[1]);
            }
        }

        return false;
    }

    private void handleRequest(Player sender, String opponent, String kit) {

        if (Bukkit.getPlayer(opponent) == null) {
            sender.sendMessage(Utility.colourise("&cNo player wth the name " + opponent.toUpperCase() + " was found :<"));
            return;
        }

        if (!ConfigManager.getKits().contains(kit)) {
            sender.sendMessage(Utility.colourise("&cThe kit &b' " + kit + "'&c does not exist! Here is a list of valid ones"));

            ConfigManager.getKits().forEach(option -> sender.sendMessage(Utility.colourise("&b  - " + option)));
            return;
        }

        if (EasyDuels.getInstance().getGameManager().isPlaying(sender)) {
            sender.sendMessage(Utility.colourise("&cYou cannot duel someone while already in a duel!"));
            return;
        }

        EasyDuels.getInstance().getGameManager().getRequests().forEach(request -> {
            if (request.getRequester().equals(sender.getUniqueId())) {
                sender.sendMessage(Utility.colourise("&cYou already have a pending duel request to someone else! Please wait for this to expire!"));
            }
        });

        EasyDuels.getInstance().getGameManager().addRequest(new DuelRequest(sender.getUniqueId(), Bukkit.getPlayer(opponent).getUniqueId(), kit));


    }
}
