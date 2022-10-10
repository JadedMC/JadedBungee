package net.jadedmc.jadedbungee.features.chat;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.channels.AdminChannel;
import net.jadedmc.jadedbungee.features.chat.channels.GlobalChannel;
import net.jadedmc.jadedbungee.features.chat.channels.PartyChannel;
import net.jadedmc.jadedbungee.features.chat.channels.StaffChannel;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * This class registers Channels and manages what players are using them.
 */
public class ChannelManager {
    private final JadedBungee plugin;
    private final List<Channel> channels = new ArrayList<>();
    private final Map<UUID, Channel> players = new HashMap<>();

    /**
     * Creates the Channel Manager.
     * @param plugin Instance of the plugin.
     */
    public ChannelManager(JadedBungee plugin) {
        this.plugin = plugin;
        channels.add(new GlobalChannel(plugin));
        channels.add(new PartyChannel(plugin));
        channels.add(new StaffChannel(plugin));
        channels.add(new AdminChannel(plugin));
    }

    /**
     * Gets a channel from it's name or aliases.
     * @param name Name or alias of the channel.
     * @return Channel with that name/alias.
     */
    public Channel getChannel(String name) {
        for(Channel channel : channels) {
            if(name.equalsIgnoreCase(channel.getName())) {
                return channel;
            }

            if(channel.getAliases().contains(name.toLowerCase())) {
                return channel;
            }
        }

        return channels.get(0);
    }

    /**
     * Gets the channel the player is currently in.
     * @param player Player to get channel of.
     * @return The channel they are using.
     */
    public Channel getChannel(ProxiedPlayer player) {
        if(players.containsKey(player.getUniqueId())) {
            return players.get(player.getUniqueId());
        }
        else {
            return channels.get(0);
        }
    }

    /**
     * Processes a chat message sent by a player.
     * @param player Player sending the chat message.
     * @param message Message being sent.
     * @return Whether the chat event should be canceled.
     */
    public boolean chat(ProxiedPlayer player, String message) {
        if(players.containsKey(player.getUniqueId())) {
            return players.get(player.getUniqueId()).chat(player, message);
        }
        else {
            return channels.get(0).chat(player, message);
        }
    }

    public void log(String channel, ProxiedPlayer player, String message) {
        String name = player.getName();
        String uuid = player.getUniqueId().toString();
        String server = player.getServer().getInfo().getName();

        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            try {
                PreparedStatement statement = plugin.mySQL().getConnection().prepareStatement("INSERT INTO chat_logs (server,channel,uuid,username,message) VALUES (?,?,?,?,?)");
                statement.setString(1, server);
                statement.setString(2, channel);
                statement.setString(3, uuid);
                statement.setString(4, name);
                statement.setString(5, message);
                statement.executeUpdate();
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * Removes a player from the player list.
     * Used when the player disconnects from the proxy.
     * @param player Player to remove.
     */
    public void removePlayer(ProxiedPlayer player) {
        players.remove(player.getUniqueId());
    }

    /**
     * Sets the chat channel the player is using.
     * @param player Player to set channel of.
     * @param channel Channel the player is using.
     */
    public void setChannel(ProxiedPlayer player, Channel channel) {
        players.put(player.getUniqueId(), channel);
    }
}