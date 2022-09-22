package net.jadedmc.jadedbungee.features.chat.channels;

import net.jadedmc.jadedbungee.features.chat.Channel;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
    public GlobalChannel() {
        super("GLOBAL", "", "g", "general", "leave", "none", "exit", "public", "pub", "all");
    }

    /**
     * Processes a chat message sent to the channel.
     * @param player Player sending the message.
     * @param message Message being sent.
     * @return false, passing the message through to the sub-server.
     */
    @Override
    public boolean chat(ProxiedPlayer player, String message) {
        return false;
    }
}