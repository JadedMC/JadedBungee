package net.jadedmc.jadedbungee.player;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class CustomPlayer {
    private Rank rank = Rank.DEFAULT;

    public CustomPlayer(ProxiedPlayer player) {

    }

    public Rank getRank() {
        return rank;
    }

}
