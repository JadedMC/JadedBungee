package net.jadedmc.jadedbungee.features.chat.commands;

import io.netty.util.internal.StringUtil;
import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.features.chat.Channel;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

/**
 * Creates the /chat command, which can be used to send messages to different channels.
 */
public class ChatCMD extends Command {
    private final JadedBungee plugin;

    /**
     * Creates the /chat command with aliases "channel", "c".
     * @param plugin Instance of the plugin.
     */
    public ChatCMD(JadedBungee plugin) {
        super("chat", "", "channel","c");
        this.plugin = plugin;
    }

    /**
     * This is the code that runs when the command is sent.
     * @param sender The player (or console) that sent the command.
     * @param args The arguments of the command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        // Only players should be able to use chat channels.
        if(!(sender instanceof ProxiedPlayer player)) {
            return;
        }

        // Make sure they're using the command properly.
        if(args.length < 1) {
            ChatUtils.chat(player, "&c&lUsage &8» &c/chat [channel] <message>");
            return;
        }

        // Makes sure the channel exists.
        if(plugin.channelManager().getChannel(args[0]) == null) {
            ChatUtils.chat(player, "&c&lError &8» &cThat channel does not exist!");
            return;
        }

        Channel channel = plugin.channelManager().getChannel(args[0]);

        // Makes sure the player has access to the channel.
        if(!channel.canUse(player)) {
            ChatUtils.chat(player, "&c&lError &8» &cYou do not have access to that channel.");
            return;
        }

        // Checks if the channel should be toggled or used.
        if(args.length == 1) {
            // Toggles the channel being used.
            plugin.channelManager().setChannel(player, plugin.channelManager().getChannel(args[0]));
            ChatUtils.chat(player, "&a&lChat &8» &aChannel set to &7" + channel.getName() + "&a.");
        }
        else {
            // Gets the message from the arguments by creating a new array ignoring the username and turning it into a list.
            String message = StringUtil.join(" ", Arrays.asList(Arrays.copyOfRange(args, 1, args.length))).toString();
            plugin.channelManager().getChannel(args[0]).chat(player, message);
        }
    }
}