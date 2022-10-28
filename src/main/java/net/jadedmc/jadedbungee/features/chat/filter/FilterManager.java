package net.jadedmc.jadedbungee.features.chat.filter;

import net.jadedmc.jadedbungee.features.chat.filter.filters.RepeatMessageFilter;
import net.jadedmc.jadedbungee.features.chat.filter.filters.TooFastFilter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

public class FilterManager {
    private final List<Filter> filters = new ArrayList<>();

    public FilterManager() {
        filters.add(new TooFastFilter());
        filters.add(new RepeatMessageFilter());
    }

    public boolean passesFilter(ProxiedPlayer player, String message) {
        for(Filter filter: filters) {
            if(!filter.passesFilter(player, message)) {
                return false;
            }
        }

        return true;
    }

    public void removePlayer(ProxiedPlayer player) {
        for(Filter filter: filters) {
            filter.removePlayer(player);
        }
    }
}