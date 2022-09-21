package net.jadedmc.jadedbungee.player;

import net.jadedmc.jadedbungee.utils.ChatUtils;
import net.md_5.bungee.api.ChatColor;

public enum Rank {
    OWNER("&c&lOwner", ChatColor.RED),
    ADMIN("", ChatColor.RED),
    MOD("", ChatColor.GOLD),
    TRIAL("", ChatColor.GOLD),
    BUILDER("", ChatColor.YELLOW),
    DEVELOPER("", ChatColor.YELLOW),
    JADED("", ChatColor.GREEN),
    SAPPHIRE("", ChatColor.BLUE),
    AMETHYST("", ChatColor.DARK_PURPLE),
    DEFAULT("", ChatColor.GRAY);

    private final String prefix;
    private final ChatColor rankColor;

    Rank(String prefix, ChatColor rankColor) {
        this.prefix = prefix;
        this.rankColor = rankColor;
    }

    public String getPrefix() {
        return ChatUtils.translate(prefix);
    }

    public ChatColor getRankColor() {
        return rankColor;
    }
}