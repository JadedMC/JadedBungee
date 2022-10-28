package net.jadedmc.jadedbungee.features.chat;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This class represents a separated section of chat.
 */
public abstract class Channel {
    private final JadedBungee plugin;
    private final String name;
    private final String permission;
    private final List<String> aliases = new ArrayList<>();
    private boolean passToSubServer = false;

    /**
     * Creates the channel object.
     * @param name Name of the channel.
     * @param permission Permission required to use the channel.
     * @param aliases Various aliases for the channel name.
     */
    public Channel(JadedBungee plugin, String name, String permission, String... aliases) {
        this.plugin = plugin;
        this.name = name;
        this.permission = permission;
        this.aliases.addAll(Arrays.asList(aliases));
    }

    /**
     * Gets a list of aliases for the channel.
     * @return A list of aliases for the channel name.
     */
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Gets the name of the channel.
     * @return Channel name is uppercase.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if a player has permission to use a channel.
     * @param player Player to check permission of.
     * @return Whether they can use the channel.
     */
    public boolean canUse(ProxiedPlayer player) {
        // Returns true if the channel has no permission requirement.
        if(permission.equals("")) {
            return true;
        }

        return player.hasPermission(permission);
    }

    /**
     * Formats the message the player sent.
     * @param player Player who sent the message.
     * @param message Message being formatted.
     * @return Formatted message.
     */
    public abstract String formatMessage(ProxiedPlayer player, String message);

    /**
     * Processes player messages to the channel.
     * @param player Player sending the message.
     * @param message Message being sent.
     * @return Whether the message should be passed to the sub-server.
     */
    public boolean chat(ProxiedPlayer player, String message) {
        // Exits if the message should be handled by the sub server.
        if(passToSubServer) {
            plugin.channelManager().log(this.name, player, message);
            return false;
        }

        // Processes the chat filter.
        if(!passesFilter(player, message)) {
            return true;
        }

        // Displays the chat message to each viewer.
        for(ProxiedPlayer viewer : getViewers(player)) {
            ChatUtils.chat(viewer, formatMessage(player, message));
        }

        // Logs the chat message.
        plugin.channelManager().log(this.getName(), player, message);
        return true;
    }

    /**
     * Gets the list of players who should receive a message from a player.
     * @param player Player to get viewers of.
     * @return Collection of viewers.
     */
    public abstract Collection<ProxiedPlayer> getViewers(ProxiedPlayer player);

    /**
     * Check if a message passes the chat filter.
     * If not, logs the message and alerts staff.
     * @param player Player sending the message.
     * @param message Message being sent.
     * @return Whether it passes the filter.
     */
    public boolean passesFilter(ProxiedPlayer player, String message) {
        if(!plugin.channelManager().getFilterManager().passesFilter(player, message)) {
            ChatUtils.chat(player, this.formatMessage(player, message));
            plugin.channelManager().log(this.getName(), player, message);

            // Loop through viewers to find staff.
            for(ProxiedPlayer viewers : this.getViewers(player)) {
                if(!viewers.hasPermission("staff.filter")) {
                    continue;
                }

                // Send filtered message to staff.
                ChatUtils.chat(viewers, "&c(filter) " + this.formatMessage(player, message));
            }
            return false;
        }

        return true;
    }

    /**
     * Set if a channel should pass its message to the sub server.
     * @param passToSubServer Whether messages should be passed to the sub server.
     */
    public void setPassToSubServer(boolean passToSubServer) {
        this.passToSubServer = passToSubServer;
    }
}