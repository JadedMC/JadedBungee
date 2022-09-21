package net.jadedmc.jadedbungee.features.chat;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.channels.GlobalChannel;
import net.jadedmc.jadedbungee.features.chat.channels.PartyChannel;
import net.jadedmc.jadedbungee.features.chat.channels.StaffChannel;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class ChannelManager {
    private final List<Channel> channels = new ArrayList<>();
    private final Map<UUID, Channel> players = new HashMap<>();

    public ChannelManager(JadedBungee plugin) {
        channels.add(new GlobalChannel());
        channels.add(new PartyChannel());
        channels.add(new StaffChannel(plugin));
    }

    public Channel getChannel(String name) {
        for(Channel channel : channels) {
            if(name.equalsIgnoreCase(channel.getName())) {
                return channel;
            }
        }

        return channels.get(0);
    }

    public void chat(ProxiedPlayer player, String message) {
        if(players.containsKey(player.getUniqueId())) {
            players.get(player.getUniqueId()).chat(player, message);
        }
        else {
            channels.get(0).chat(player, message);
        }
    }

    public void removePlayer(ProxiedPlayer player) {
        players.remove(player.getUniqueId());
    }
}