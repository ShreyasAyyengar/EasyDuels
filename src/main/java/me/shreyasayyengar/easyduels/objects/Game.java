package me.shreyasayyengar.easyduels.objects;

import me.shreyasayyengar.easyduels.EasyDuels;
import me.shreyasayyengar.easyduels.utils.ConfigManager;
import me.shreyasayyengar.easyduels.utils.SQLUtils;
import me.shreyasayyengar.easyduels.utils.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game {

    private final List<UUID> allPlayers = new ArrayList<>();

    private final UUID player1;
    private final UUID player2;
    private final String kit;

    private final FileConfiguration config = EasyDuels.getInstance().getConfig();

    public Game(UUID player1, UUID player2, String kit) {
        this.player1 = player1;
        this.player2 = player2;
        this.kit = kit;
        allPlayers.add(player1);
        allPlayers.add(player2);
    }

    public UUID getPlayer1() {
        return player1;
    }

    public UUID getPlayer2() {
        return player2;
    }

    public Player getCBPlayer1() {
        return Bukkit.getPlayer(player1);
    }

    public Player getCBPlayer2() {
        return Bukkit.getPlayer(player2);
    }

    public void startCounter() {

        if (areBothOnline()) {

            final int[] seconds = {5};

            new BukkitRunnable() {
                @Override
                public void run() {

                    if (!areBothOnline()) {
                        allPlayers.forEach(uuid -> {
                            if (Bukkit.getPlayer(uuid) != null) {
                                Bukkit.getPlayer(uuid).sendMessage(Utility.colourise("&cStart cancelled!"));
                            }
                        });
                        cancel();
                        return;
                    }
                    if (seconds[0] > 0) {
                        allPlayers.forEach(uuid -> Bukkit.getPlayer(uuid).sendMessage(Utility.colourise("&c" + seconds[0] + " &e until the game begins!")));
                    }

                    if (seconds[0] == 0) {
                        startGame();
                    }

                    seconds[0]--;
                }
            }.runTaskTimer(EasyDuels.getInstance(), 0L, 20L);

        }

    }

    private boolean areBothOnline() {
        return Bukkit.getPlayer(player1) != null && Bukkit.getPlayer(player2) != null;
    }

    public void startGame() {

        getCBPlayer1().teleport(ConfigManager.getLocation(true));
        getCBPlayer2().teleport(ConfigManager.getLocation(false));

        setInventory(kit);

        SQLUtils.addGame();

    }

    private void setInventory(String kit) {
        List<Player> players = new ArrayList<>();
        players.add(getCBPlayer1());
        players.add(getCBPlayer2());

        String acKey = kit + ".armor_content.";
        String invKey = kit + ".inventory_content.";

        ItemStack[] armorContents = {
                new ItemStack(Material.valueOf(config.getString(acKey + "boots.material")), config.getInt(acKey + "boots.amount")),
                new ItemStack(Material.valueOf(config.getString(acKey + "leggings.material")), config.getInt(acKey + "leggings.amount")),
                new ItemStack(Material.valueOf(config.getString(acKey + "chestplate.material")), config.getInt(acKey + "chestplate.amount")),
                new ItemStack(Material.valueOf(config.getString(acKey + "helmet.material")), config.getInt(acKey + "helmet.amount"))
        };

        List<ItemStack> invContents = new ArrayList<>();

        for (String key : config.getConfigurationSection(invKey).getKeys(false)) {

            try {
                int slot = Integer.parseInt(key);
                invContents.add(new ItemStack(Material.valueOf(config.getString(invKey + key + ".material")), config.getInt(invKey + key + ".amount")));

            } catch (NumberFormatException x) {
                Bukkit.getLogger().severe("Please ensure that all materials are written properly and are valid! (" + kit + invKey + key + ".material");
            }
        }

        for (Player player : players) {
            player.getInventory().setArmorContents(armorContents);

            for (int i = 0; i < invContents.size(); i++) {
                player.getInventory().setItem(i, invContents.get(i));
            }
        }

    }

    public List<UUID> getAllPlayers() {
        return allPlayers;
    }
}
