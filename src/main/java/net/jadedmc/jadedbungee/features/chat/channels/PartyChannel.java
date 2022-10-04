package net.jadedmc.jadedbungee.features.chat.channels;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.Channel;
import net.jadedmc.jadedbungee.player.CustomPlayer;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

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
        super("PARTY", "", "p");
        this.plugin = plugin;
    }

    /**
     * Processes a chat message sent to the channel.
     * @param player Player sending the message.
     * @param message Message being sent.
     * @return false, passing the message through to the sub-server.
     */
    @Override
    public boolean chat(ProxiedPlayer player, String message) {
        if(plugin.partyManager().getParty(player) == null) {
            ChatUtils.chat(player, "&c&lError &8» &cYou are not in a party!");
            plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("GLOBAL"));
            return true;
        }

        CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(player);
        plugin.partyManager().getParty(player).sendMessage("&7[&aParty&7] " + customPlayer.getRank().getPrefix() + "&7" + player.getName() + " &8» &a" + message);

        return true;
    }
}