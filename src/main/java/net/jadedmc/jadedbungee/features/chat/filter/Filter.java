package net.jadedmc.jadedbungee.features.chat.filter;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public abstract class Filter {
    private boolean falseMessage;

    public Filter() {
        falseMessage = true;
    }

    public void setFalseMessage(boolean falseMessage) {
        this.falseMessage = falseMessage;
    }

    public boolean showFalseMessage() {
        return falseMessage;
    }

    public abstract boolean passesFilter(ProxiedPlayer player, String message);

    public void removePlayer(ProxiedPlayer player) {}
}