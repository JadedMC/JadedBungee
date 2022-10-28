package net.jadedmc.jadedbungee.features.chat.filter.filters;

import net.jadedmc.jadedbungee.features.chat.filter.Filter;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;

public class TooFastFilter extends Filter {
    private final Map<ProxiedPlayer, Long> lastMessage = new HashMap<>();

    public TooFastFilter() {
        setFalseMessage(false);
    }

    public boolean passesFilter(ProxiedPlayer player, String message) {
        if(player.hasPermission("jadedbungee.bypass.timefilter")) {
            return true;
        }

        if(lastMessage.get(player) == null) {
            lastMessage.put(player, System.currentTimeMillis());
            return true;
        }

        if(System.currentTimeMillis() < lastMessage.get(player) + 1500) {
            ChatUtils.chat(player, "&cYou are chatting too fast!");
            return false;
        }



        lastMessage.put(player, System.currentTimeMillis());
        return true;
    }

    public void removePlayer(ProxiedPlayer player) {
        lastMessage.remove(player);
    }
}