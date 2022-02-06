package me.shreyasayyengar.easyduels.events;

import me.shreyasayyengar.easyduels.EasyDuels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {

    @EventHandler
    private void onLeave(PlayerQuitEvent event) {

        if (EasyDuels.getInstance().getGameManager().isPlaying(event.getPlayer())) {
            EasyDuels.getInstance().getGameManager().getCurrentGame(event.getPlayer()).win(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event) {

        if (EasyDuels.getInstance().getGameManager().isPlaying(event.getEntity())) {
            EasyDuels.getInstance().getGameManager().getCurrentGame(event.getEntity()).win(event.getEntity().getUniqueId());
        } else System.out.println(false);
    }

    @EventHandler
    private void onDeath(PlayerDropItemEvent event) {

        if (EasyDuels.getInstance().getGameManager().isPlaying(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
