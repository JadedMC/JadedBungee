package net.jadedmc.jadedbungee.features.chat.channels;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.Channel;
import net.jadedmc.jadedbungee.player.CustomPlayer;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class StaffChannel extends Channel {
    private final JadedBungee plugin;

    public StaffChannel(JadedBungee plugin) {
        super("STAFF");

        this.plugin = plugin;
    }

    @Override
    public boolean chat(ProxiedPlayer player, String message) {
        CustomPlayer customPlayer = plugin.customPlayerManager().getPlayer(player);

        String rank  = customPlayer.getRank().toString();
        rank = rank.substring(0,1).toUpperCase() + rank.substring(1).toLowerCase();

        for(ProxiedPlayer viewer : ProxyServer.getInstance().getPlayers()) {
            if(viewer.hasPermission("staff.chat")) {
                ChatUtils.chat(viewer, "&4&l(&c&lStaff&4&l) &c&l" + rank + " &f" + player.getDisplayName() + " &8>> &c" + message);
            }
        }

        return true;
    }
}
