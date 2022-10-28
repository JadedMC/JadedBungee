package net.jadedmc.jadedbungee.features.chat.filter.filters;

import net.jadedmc.jadedbungee.features.chat.filter.Filter;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

public class RepeatMessageFilter extends Filter {
    private final Map<ProxiedPlayer, String> lastMessage = new HashMap<>();

    public RepeatMessageFilter() {
        setFalseMessage(false);
    }

    public boolean passesFilter(ProxiedPlayer player, String message) {
        if(player.hasPermission("jadedbungee.bypass.repeatfilter")) {
            return true;
        }

        if(lastMessage.get(player) == null) {
            lastMessage.put(player, message);
            return true;
        }

        if(message.length() <= 5 || (message.length() * 2) <= lastMessage.get(player).length() || (lastMessage.get(player).length() * 2) <= message.length()) {
            lastMessage.put(player, message);
            return true;
        }

        if(message.contains(lastMessage.get(player)) || lastMessage.get(player).contains(message)) {
            ChatUtils.chat(player, "&cYou cannot say the same message twice!");
            return false;
        }

        lastMessage.put(player, message);
        return true;
    }

    public void removePlayer(ProxiedPlayer player) {
        lastMessage.remove(player);
    }
}