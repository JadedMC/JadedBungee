package net.jadedmc.jadedbungee.features.chat.channels;

import net.jadedmc.jadedbungee.features.chat.Channel;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * A chat channel only visible to members of a party.
 */
public class PartyChannel extends Channel {

    /**
     * Creates the channel.
     * Name: PARTY
     * Permission: none
     * Aliases:
     *   - p
     */
    public PartyChannel() {
        super("PARTY", "", "p");
    }

    /**
     * Processes a chat message sent to the channel.
     * @param player Player sending the message.
     * @param message Message being sent.
     * @return false, passing the message through to the sub-server.
     */
    @Override
    public boolean chat(ProxiedPlayer player, String message) {
        // TODO: Implement Party Chat
        return false;
    }
}