package net.jadedmc.jadedbungee.features.party;

import net.jadedmc.jadedbungee.JadedBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all existing party.
 */
public class PartyManager {
    private final JadedBungee plugin;
    private final List<Party> parties = new ArrayList<>();

    /**
     * Creates the party manager.
     * @param plugin Instance of the plugin.
     */
    public PartyManager(JadedBungee plugin) {
        this.plugin = plugin;
    }

    /**
     * Create a new party.
     * @param leader Leader of the party,
     * @return Party that was created.
     */
    public Party createParty(ProxiedPlayer leader) {
        Party party = new Party(plugin, leader);
        parties.add(party);
        return party;
    }

    /**
     * Disbands an active party.
     * @param party Party to disband.
     */
    public void disbandParty(Party party) {
        getParties().remove(party);
    }

    /**
     * Get the party a player is in.
     * Returns null if not in a party.
     * @param player Player to get party of.
     * @return Party the player is in.
     */
    public Party getParty(ProxiedPlayer player) {
        for(Party party : getParties()) {
            if(party.getMembers().contains(player)) {
                return party;
            }
        }

        return null;
    }

    /**
     * Get all current parties.
     * @return All current parties.
     */
    public List<Party> getParties() {
        return parties;
    }
}