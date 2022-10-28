package net.jadedmc.jadedbungee.features.chat.channels;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.Channel;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;

/**
 * The default chat channel visible to everyone.
 */
public class GlobalChannel extends Channel {

    /**
     * Creates the channel.
     * Name: GLOBAL
     * Permission: none
     * Aliases:
     *   - g
     *   - general
     *   - leave
     *   - none
     *   - exit
     *   - public
     *   - pub
     *   - all
     */
    public GlobalChannel(JadedBungee plugin) {
        super(plugin, "GLOBAL", "", "g", "general", "leave", "none", "exit", "public", "pub", "all");

        // Make the messages pass to the sub server.
        setPassToSubServer(true);
    }

    /**
     * Formats a message sent to this channel.
     * @param player Player who sent the message.
     * @param message Message being formatted.
     * @return Formatted message.
     */
    @Override
    public String formatMessage(ProxiedPlayer player, String message) {
        return "";
    }

    /**
     * Gets the list of players who should receive a message from a player.
     * @param player Player to get viewers of.
     * @return All administrators who can see the message.
     */
    @Override
    public Collection<ProxiedPlayer> getViewers(ProxiedPlayer player) {
        return player.getServer().getInfo().getPlayers();
    }
}