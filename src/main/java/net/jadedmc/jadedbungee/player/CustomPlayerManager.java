package net.jadedmc.jadedbungee.player;

import net.jadedmc.jadedbungee.JadedBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomPlayerManager {
    private final JadedBungee plugin;
    private final Map<UUID, CustomPlayer> players = new HashMap<>();

    public CustomPlayerManager(JadedBungee plugin) {
        this.plugin = plugin;
    }

    public void addPlayer(ProxiedPlayer player) {
        players.put(player.getUniqueId(), new CustomPlayer(plugin, player));
    }

    public CustomPlayer getPlayer(ProxiedPlayer player) {
        if(players.containsKey(player.getUniqueId())) {
            return players.get(player.getUniqueId());
        }

        return null;
    }

    public void removePlayer(ProxiedPlayer player) {
        players.remove(player.getUniqueId());
    }
}
