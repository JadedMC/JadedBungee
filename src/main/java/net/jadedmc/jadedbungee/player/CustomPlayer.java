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
                // player_info
                {
                    PreparedStatement getStatement = plugin.mySQL().getConnection().prepareStatement("SELECT * from player_info WHERE uuid = ?");
                    getStatement.setString(1, player.getUniqueId().toString());
                    ResultSet results = getStatement.executeQuery();

                    if(!results.next()) {
                        PreparedStatement insertStatement = plugin.mySQL().getConnection().prepareStatement("INSERT INTO player_info (uuid,username,ip) VALUES (?,?,?)");
                        insertStatement.setString(1, player.getUniqueId().toString());
                        insertStatement.setString(2, player.getName());
                        insertStatement.setString(3, player.getAddress().getHostString());
                        insertStatement.executeUpdate();
                    }
                }

                // player_logins
                {
                    PreparedStatement insertStatement = plugin.mySQL().getConnection().prepareStatement("INSERT INTO player_logins (uuid,username,ip) VALUES (?,?,?)");
                    insertStatement.setString(1, player.getUniqueId().toString());
                    insertStatement.setString(2, player.getName());
                    insertStatement.setString(3, player.getAddress().getHostString());
                    insertStatement.executeUpdate();

                    PreparedStatement updateStatement = plugin.mySQL().getConnection().prepareStatement("UPDATE player_info SET username = ?, ip = ? WHERE uuid = ?");
                    updateStatement.setString(1, player.getUniqueId().toString());
                    updateStatement.setString(2, player.getName());
                    updateStatement.setString(3, player.getAddress().getHostString());
                    updateStatement.executeUpdate();
                }

                // player rank
                {
                    PreparedStatement getStatement = plugin.mySQL().getConnection().prepareStatement("SELECT * FROM luckperms_players WHERE uuid = ?");
                    getStatement.setString(1, player.getUniqueId().toString());
                    ResultSet results = getStatement.executeQuery();

                    if(results.next()) {
                        rank = Rank.valueOf(results.getString(3).toUpperCase());
                    }
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
