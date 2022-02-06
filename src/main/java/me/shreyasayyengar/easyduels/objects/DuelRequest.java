package me.shreyasayyengar.easyduels.objects;

import me.shreyasayyengar.easyduels.EasyDuels;
import me.shreyasayyengar.easyduels.utils.Utility;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;

import java.util.UUID;

public class DuelRequest {

    private final UUID requester;
    private final UUID target;
    private final String kit;
    private boolean hasAccepted;

    public DuelRequest(UUID requester, UUID target, String kit) {
        this.requester = requester;
        this.target = target;
        this.kit = kit;
        this.hasAccepted = false;

        sendMessages();
    }

    private void sendMessages() {
        Bukkit.getPlayer(requester).sendMessage(Utility.colourise("&aYou have requested " + Bukkit.getPlayer(target).getName() + " to a duel with the kit: &6" + kit));


        TextComponent main = new TextComponent("§d" + Bukkit.getPlayer(requester).getName() + " has requested to duel you with the kit: §6" + kit + "\n");
        TextComponent clickHere = new TextComponent("§e[Click Here To Accept The Duel]");
        clickHere.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§6Click here to accept the duel with " + Bukkit.getPlayer(requester).getName() + "! (60 secs)")));
        clickHere.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept " + Bukkit.getPlayer(requester).getName()));

        Bukkit.getPlayer(target).spigot().sendMessage(main, clickHere);

    }

    public void cancel(boolean wasTimeout) {
        if (wasTimeout) {

            String msg = Utility.colourise("&cThe duel request expired due to time or because a player left the game!");
            if (Bukkit.getPlayer(target) != null) {
                Bukkit.getPlayer(target).sendMessage(msg);
            }

            if (Bukkit.getPlayer(requester) != null) {
                Bukkit.getPlayer(requester).sendMessage(msg);
            }

        }

        EasyDuels.getInstance().getGameManager().getRequests().remove(this);
    }


    public UUID getRequester() {
        return requester;
    }

    public UUID getTarget() {
        return target;
    }

    public boolean hasAccepted() {
        return hasAccepted;
    }

    public void approve() {
        EasyDuels.getInstance().getGameManager().createGame(requester, target, kit);
        EasyDuels.getInstance().getGameManager().getRequests().remove(this);
    }
}
