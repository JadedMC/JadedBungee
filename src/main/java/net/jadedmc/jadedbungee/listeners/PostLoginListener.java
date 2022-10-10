package net.jadedmc.jadedbungee.listeners;

import net.jadedmc.jadedbungee.JadedBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This listens to the PostLoginEvent event, which is called every time a player successfully joins the server.
 * We use this to announce when a staff member joins to other staff members.
 */
public class PostLoginListener implements Listener {
    private final JadedBungee plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * This is known as Dependency Injection.
     * @param plugin Instance of the plugin.
     */
    public PostLoginListener(JadedBungee plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the event is called.
     * @param event PostLoginEvent.
     */
    @EventHandler
    public void onLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        plugin.customPlayerManager().addPlayer(player);

        String uuid = player.getUniqueId().toString();
        String ip = player.getAddress().getHostString();
        addNameHistory(player);
        addIPHistory(uuid, ip);
    }

    /**
     * Add to the player's ip history.
     * @param uuid UUID of the player.
     * @param ip IP of the player.
     */
    private void addIPHistory(String uuid, String ip) {
        try {
            PreparedStatement statement1 = plugin.mySQL().getConnection().prepareStatement("INSERT INTO player_ips (uuid,ip) VALUES (?,?)");
            statement1.setString(1, uuid);
            statement1.setString(2, ip);
            statement1.executeUpdate();

            PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("UPDATE player_info SET ip = ? WHERE uuid = ?");
            statement2.setString(1, ip);
            statement2.setString(2, uuid);
            statement2.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Add to the player's username history.
     * @param player Player to add to.
     */
    private void addNameHistory(ProxiedPlayer player) {
        try {
            PreparedStatement statement1 = plugin.mySQL().getConnection().prepareStatement("INSERT INTO player_usernames (uuid,username) VALUES (?,?)");
            statement1.setString(1, player.getUniqueId().toString());
            statement1.setString(2, player.getName());
            statement1.executeUpdate();

            PreparedStatement statement2 = plugin.mySQL().getConnection().prepareStatement("UPDATE player_info SET username = ? WHERE uuid = ?");
            statement2.setString(1, player.getName());
            statement2.setString(2, player.getUniqueId().toString());
            statement2.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}