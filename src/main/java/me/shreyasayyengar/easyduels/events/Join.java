package me.shreyasayyengar.easyduels.events;

import me.shreyasayyengar.easyduels.utils.SQLUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        SQLUtils.createData(event.getPlayer().getUniqueId());
    }
}
