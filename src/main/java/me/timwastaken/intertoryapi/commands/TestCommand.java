package me.timwastaken.intertoryapi.commands;

import me.timwastaken.intertoryapi.inventories.Intertory;
import me.timwastaken.intertoryapi.inventories.items.Items;
import me.timwastaken.intertoryapi.utils.IntertoryBuilder;
import me.timwastaken.intertoryapi.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            int slots = 27;
            if (args.length > 0) {
                try {
                    slots = Integer.parseInt(args[0]);
                } catch (NumberFormatException ex) {
                    p.sendMessage(ChatColor.RED + "Not a valid number!");
                    return false;
                }
            }
            try {
                Intertory sfibConfigIntertory = new IntertoryBuilder(9, 3, String.format(
                        "%sConfigure game settings",
                        ChatColor.DARK_GRAY
                ))
                        .withItem(0, 0, new Items.ToggleState(
                                Material.DIAMOND_SWORD,
                                String.format(
                                        "%s%s%s",
                                        ChatColor.GOLD,
                                        ChatColor.BOLD,
                                        "Friendly Fire"
                                ),
                                "Are players allowed to engage in PVP?",
                                true
                        ))
                        .withItem(2, 1, new Items.RangeSelect(
                                Material.CLOCK,
                                String.format(
                                        "%s%sPlaytime",
                                        ChatColor.GOLD,
                                        ChatColor.BOLD
                                ),
                                "The amount of time the game will run for (in minutes)",
                                60, 1, Integer.MAX_VALUE,
                                1,
                                10
                        ))
                        .withItem(4, 1, new Items.RangeSelect(
                                Material.BARRIER,
                                String.format(
                                        "%s%sSkips",
                                        ChatColor.GOLD,
                                        ChatColor.BOLD
                                ),
                                "The number of skips every player has",
                                5, 0, Integer.MAX_VALUE,
                                1,
                                5,
                                true
                        ))
                        .withItem(6, 1, new Items.RangeSelect(
                                Material.ENDER_CHEST,
                                String.format(
                                        "%s%sBackpack",
                                        ChatColor.GOLD,
                                        ChatColor.BOLD
                                ),
                                "The amount of rows of backpack space " +
                                        "(3x for normal chest, 6x for double chest, ...)",
                                3, 1, 6,
                                1,
                                2,
                                true
                        ))
                        .getIntertory();
                p.openInventory(sfibConfigIntertory.getInventory());
            } catch (IllegalArgumentException e) {
                p.sendMessage(ChatColor.RED + e.getMessage());
                return false;
            }
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
        return false;
    }
}
