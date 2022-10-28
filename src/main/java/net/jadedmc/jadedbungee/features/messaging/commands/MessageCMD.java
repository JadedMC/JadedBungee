package net.jadedmc.jadedbungee.features.messaging.commands;

import io.netty.util.internal.StringUtil;
import net.jadedmc.jadedbungee.JadedBungee;
import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

/**
 * This class runs the message command, which is used to send a private message to another player.
 */
public class MessageCMD extends Command {
    private final JadedBungee plugin;

    /**
     * Creates the /message command with no permissions and the following aliases:
     * - /msg
     * - /m
     * - /tell
     * - /t
     * - /whipser
     * - /w
     * @param plugin Instance of the plugin.
     */
    public MessageCMD(JadedBungee plugin) {
        super("message", "", "msg","m","tell","t","whisper","w");
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
        if(args.length < 2) {
            ChatUtils.chat(player, "&c&lUsage &8» &c/msg [player] [message]");
            return;
        }

        // Gets the target player.
        ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);

        // Make sure they're online.
        if(target == null) {
            ChatUtils.chat(player, "&c&lError &8» &cThat player isn't online!");
            return;
        }

        // Make sure the target isn't the player.
        if(target.equals(player)) {
            ChatUtils.chat(player, "&c&lError &8» &cYou cannot message yourself!");
            return;
        }

        // Gets the message from the arguments by creating a new array ignoring the username and turning it into a list.
        String message = StringUtil.join(" ", Arrays.asList(Arrays.copyOfRange(args, 1, args.length))).toString();

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
        plugin.channelManager().log("PRIVATE", player, "To " + target.getName() + ": " + message);
    }
}