package net.jadedmc.jadedbungee.commands.misc;

import net.jadedmc.jadedbungee.JadedBungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class LobbyCMD extends Command {
    private final JadedBungee plugin;

    public LobbyCMD(JadedBungee plugin) {
        super("lobby", "", "hub");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer player)) {
            return;
        }

        player.connect(plugin.getProxy().getServerInfo("lobby"));
    }
}