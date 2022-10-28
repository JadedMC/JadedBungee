package net.jadedmc.jadedbungee.features.chat.channels;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.Channel;
import net.jadedmc.jadedbungee.player.CustomPlayer;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Collection;
import java.util.HashSet;

/**
 * A chat channel only visible to members of a party.
 */
public class PartyChannel extends Channel {
    private final JadedBungee plugin;

    /**
     * Creates the channel.
     * Name: PARTY
     * Permission: none
     * Aliases:
     *   - p
     */
    public PartyChannel(JadedBungee plugin) {
        super(plugin, "PARTY", "", "p");
        this.plugin = plugin;
    }

    /**
     * Formats a message sent to this channel.
     * @param player Player who sent the message.
     * @param message Message being formatted.
     * @return Formatted message.
     */
    @Override
    public String formatMessage(ProxiedPlayer player, String message) {
        CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(player);
        return "&7[&aParty&7] " + customPlayer.getRank().getPrefix() + "&7" + player.getName() + " &8» &a" + message;
    }

    /**
     * Gets the list of players who should receive a message from a player.
     * @param player Player to get viewers of.
     * @return All administrators who can see the message.
     */
    @Override
    public Collection<ProxiedPlayer> getViewers(ProxiedPlayer player) {
        Collection<ProxiedPlayer> viewers = new HashSet<>();

        if(plugin.partyManager().getParty(player) == null) {
            return viewers;
        }

        return plugin.partyManager().getParty(player).getMembers();
    }
}