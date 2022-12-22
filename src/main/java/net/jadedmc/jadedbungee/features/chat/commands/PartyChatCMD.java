package net.jadedmc.jadedbungee.features.chat.commands;

import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyChatCMD extends Command {
    private final JadedBungee plugin;

    public PartyChatCMD(JadedBungee plugin) {
        super("partychat", "", "pc");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!(sender instanceof ProxiedPlayer player)) {
            return;
        }

        if(plugin.partyManager().getParty(player) == null) {
            ChatUtils.chat(player, "&c&lError &8» &cYou are not in a party!");
            return;
        }

        if(plugin.channelManager().getChannel(player).getName().equalsIgnoreCase("PARTY")) {
            plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("GLOBAL"));
            ChatUtils.chat(player, "&a&lChat &8» &aChannel set to &7" + plugin.channelManager().getChannel("GLOBAL").getName() + "&a.");
        }
        else {
            plugin.channelManager().setChannel(player, plugin.channelManager().getChannel("PARTY"));
            ChatUtils.chat(player, "&a&lChat &8» &aChannel set to &7" + plugin.channelManager().getChannel("PARTY").getName() + "&a.");
        }
    }
}