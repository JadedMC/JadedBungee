package net.jadedmc.jadedbungee.player;

import net.jadedmc.jadedbungee.JadedBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomPlayer {
    private final JadedBungee plugin;
    private Rank rank = Rank.DEFAULT;

    public CustomPlayer(JadedBungee plugin, ProxiedPlayer player) {
        this.plugin = plugin;

        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            try {
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM luckperms_players WHERE uuid = ?");
                statement.setString(1, player.getUniqueId().toString());
                ResultSet results = statement.executeQuery();

                if(results.next()) {
                    rank = Rank.valueOf(results.getString(3).toUpperCase());
                }
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    public Rank getRank() {
        return rank;
    }

}
