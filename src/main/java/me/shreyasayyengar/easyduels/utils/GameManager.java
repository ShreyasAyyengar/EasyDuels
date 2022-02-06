package me.shreyasayyengar.easyduels.utils;

import me.shreyasayyengar.easyduels.EasyDuels;
import me.shreyasayyengar.easyduels.objects.DuelRequest;
import me.shreyasayyengar.easyduels.objects.Game;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    public void createGame(UUID player1, UUID player2, String kit) {
        Game game = new Game(player1, player2, kit);
        games.add(game);
        game.startCounter();
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

            int count = 0;

            @Override
            public void run() {

                if (Bukkit.getPlayer(request.getRequester()) == null || Bukkit.getPlayer(request.getTarget()) == null) {
                    request.cancel(true);
                    cancel();
                }
                if (request.hasAccepted()) {
                    request.cancel(false);
                    cancel();
                }

                if (count >= 60) {
                    request.cancel(true);
                    cancel();
                }
                count++;
            }
        }.runTaskTimer(EasyDuels.getInstance(), 0L, 20L);
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

    public void removeGame(Game game) {
        games.remove(game);
    }
}
