package net.jadedmc.jadedbungee.features.messaging.commands;

import io.netty.util.internal.StringUtil;
import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

/**
 * This class runs the reply command, which is used to reply to a private message from another player.
 */
public class ReplyCMD extends Command {
    private JadedBungee plugin;

    /**
     * Creates the command /reply with no permission and alias /r.
     * @param plugin Instance of the plugin.
     */
    public ReplyCMD(JadedBungee plugin) {
        super("reply", "", "r");
        this.plugin = plugin;
    }

    /**
     * This is the code that runs when the command is sent.
     * @param sender The player (or console) that sent the command.
     * @param args The arguments of the command.
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        // Only players should be able to message each other.
        if(!(sender instanceof ProxiedPlayer player)) {
            return;
        }

        // Make sure they're using the command properly.
        if(args.length < 1) {
            ChatUtils.chat(player, "&c&lUsage &8» &c/r [message]");
            return;
        }

        // Get the target using the Message Manager.
        ProxiedPlayer target = plugin.messageManager().getReplyTarget(player);

        // Make sure there is a target.
        if(target == null) {
            ChatUtils.chat(player, "&c&lError &8» &cYou have no one to reply to!");
            return;
        }

        // Gets the message from the arguments by taking the args and turning it into a list.
        String message = StringUtil.join(" ", Arrays.asList(args)).toString();

        // Gets the message format from the config and sends it to the sender.
        String toMessage = plugin.settingsManager().getConfig().getString("toMessage")
                .replace("%sender%", player.getName())
                .replace("%receiver%", target.getName())
                .replace("%message%", message);
        ChatUtils.chat(player, toMessage);

        // Gets the message format from the config and sends it to the receiver.
        String fromMessage = plugin.settingsManager().getConfig().getString("fromMessage")
                .replace("%sender%", player.getName())
                .replace("%receiver%", target.getName())
                .replace("%message%", message);
        ChatUtils.chat(target, fromMessage);

        // Sends message to all staff online using social spy.
        String spyMessage = plugin.settingsManager().getConfig().getString("spyMessage")
                .replace("%sender%", player.getName())
                .replace("%receiver%", target.getName())
                .replace("%message%", message);

        for(ProxiedPlayer stalker : plugin.messageManager().getSpying()) {
            ChatUtils.chat(stalker, spyMessage);
        }

        // Creates a conversation between the two players so /reply works.
        plugin.messageManager().setReplyTarget(player, target);

        // Logs the message
        plugin.messageManager().log("PRIVATE", player, "To " + target.getName() + ": " + message, false);
    }
}