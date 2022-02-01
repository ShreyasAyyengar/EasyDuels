package me.shreyasayyengar.easyduels.utils;

import me.shreyasayyengar.easyduels.EasyDuels;
import me.shreyasayyengar.easyduels.objects.DuelRequest;
import me.shreyasayyengar.easyduels.objects.Game;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class GameManager {

    private final Set<DuelRequest> requests = new HashSet<>();
    private final Set<Game> games = new HashSet<>();

    public GameManager() {
    }

    public Game getCurrentGame(Player player) {

        for (Game game : games) {

            if (game.getAllPlayers().contains(player.getUniqueId())) return game;
        }

        return null;
    }

    public boolean isPlaying(Player player) {

        for (Game game : games) {

            if (game.getAllPlayers().contains(player.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public void addRequest(DuelRequest request) {
        requests.add(request);

        new BukkitRunnable() {
            @Override
            public void run() {
                request.cancel();
            }
        }.runTaskLater(EasyDuels.getInstance(), 1200L);
    }

    public boolean hasRequest(Player player, Player from) {

        for (DuelRequest request : requests) {

            if (request.getTarget().equals(player.getUniqueId()) && request.getRequester().equals(from.getUniqueId())) {
                return true;
            }
        }

        return false;
    }

    public Set<DuelRequest> getRequests() {
        return requests;
    }
}
