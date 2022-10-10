package net.jadedmc.jadedbungee.listeners;

import net.jadedmc.jadedbungee.JadedBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This listens to the ChatEvent event, which is called every time a player send a chat message.
 * We use this to automatically send a message to the staff chat channel if the player has the channel toggled.
 */
public class ChatListener implements Listener {
    private final JadedBungee plugin;

    /**
     * To be able to access the configuration files, we need to pass an instance of the plugin to our listener.
     * This is known as Dependency Injection.
     * @param plugin Instance of the plugin.
     */
    public ChatListener(JadedBungee plugin) {
        this.plugin = plugin;
    }

    /**
     * Runs when the event is called.
     * @param event ChatEvent.
     */
    @EventHandler
    public void onChat(ChatEvent event) {
        // Make sure the message is being sent by a player.
        if(!(event.getSender() instanceof ProxiedPlayer)) {
            return;
        }

        // Make sure the message is not null or empty before we continue.
        if(event.getMessage() == null || event.getMessage().length() == 0) {
            return;
        }

        // Get the player who is sending the message.
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();

        // Make sure the message is not a command.
        if(event.getMessage().substring(0, 1).equalsIgnoreCase("/")) {
            plugin.getProxy().getScheduler().runAsync(plugin, () -> {
                try {
                    PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("INSERT INTO command_logs (server,uuid,username,command) VALUES (?,?,?,?)");
                    statement.setString(1, player.getServer().getInfo().getName());
                    statement.setString(2, player.getUniqueId().toString());
                    statement.setString(3, player.getName());
                    statement.setString(4, event.getMessage());
                    statement.executeUpdate();
                }
                catch (SQLException exception) {
                    exception.printStackTrace();
                }
            });
            return;
        }

        // Uses the channel manager to process the message.
        event.setCancelled(plugin.channelManager().chat(player, event.getMessage()));
    }
}