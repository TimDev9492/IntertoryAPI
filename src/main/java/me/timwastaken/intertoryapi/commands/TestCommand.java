package me.timwastaken.intertoryapi.commands;

import me.timwastaken.intertoryapi.inventories.Intertory;
import me.timwastaken.intertoryapi.inventories.IntertorySection;
import me.timwastaken.intertoryapi.inventories.items.IntertoryItem;
import me.timwastaken.intertoryapi.inventories.items.Items;
import me.timwastaken.intertoryapi.utils.IntertoryBuilder;
import me.timwastaken.intertoryapi.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
                Items.RangeSelect playTimeSelect = new Items.RangeSelect(
                        Material.CLOCK,
                        String.format(
                                "%s%sPlaytime",
                                ChatColor.GOLD,
                                ChatColor.BOLD
                        ),
                        "The amount of time the game will run for (in minutes)",
                        60, 1, Integer.MAX_VALUE,   // initial value, min, max
                        1,                                        // small increment
                        10                                          // large increment
                );
                Items.RangeSelect numberOfSkipsSelect = new Items.RangeSelect(
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
                );
                Items.RangeSelect backpackSpaceSelect = new Items.RangeSelect(
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
                );

                Intertory gameConfigIntertory = new IntertoryBuilder(9, 4)
                        .addSection(0, 0, new IntertoryBuilder(9, 3)
                                .withItem(2, 1, playTimeSelect)
                                .withItem(4, 1, numberOfSkipsSelect)
                                .withItem(6, 1, backpackSpaceSelect)
                                .withBackground(Material.GRAY_STAINED_GLASS_PANE)
                                .getSection()
                        )
                        .addSection(0, 3, new IntertoryBuilder(9, 1)
                                .withItem(0, 0, new Items.Button(
                                        new ItemBuilder(Material.TNT)
                                                .name(String.format(
                                                        "%s%sCancel",
                                                        ChatColor.RED,
                                                        ChatColor.BOLD
                                                ))
                                                .build(),
                                        () -> {     // the action to be performed
                                            p.closeInventory();
                                            p.sendMessage(String.format(
                                                    "%sGame configuration canceled.",
                                                    ChatColor.RED
                                            ));
                                            return false;    // whether the action was successful
                                        }
                                ))
                                .withItem(4, 0, new Items.ToggleState(
                                        Material.WRITABLE_BOOK,
                                        String.format(
                                                "%s%sSave config?",
                                                ChatColor.YELLOW,
                                                ChatColor.BOLD
                                        ),
                                        "Save the configuration for the next time",
                                        false
                                ))
                                .withItem(8, 0, new Items.Button(
                                        new ItemBuilder(Material.TIPPED_ARROW)
                                                .name(String.format(
                                                        "%s%sStart",
                                                        ChatColor.GREEN,
                                                        ChatColor.BOLD
                                                ))
                                                .build(),
                                        () -> {     // the action to be performed
                                            p.closeInventory();
                                            p.sendMessage(String.format(
                                                    "%sStarting the game with these configured values:",
                                                    ChatColor.GREEN
                                            ));
                                            p.sendMessage(String.format(
                                                    "%sGame time: %s%d minutes",
                                                    ChatColor.BLUE,
                                                    ChatColor.GRAY,
                                                    playTimeSelect.getValue()
                                            ));
                                            p.sendMessage(String.format(
                                                    "%sNumber of skips: %s%d",
                                                    ChatColor.BLUE,
                                                    ChatColor.GRAY,
                                                    numberOfSkipsSelect.getValue()
                                            ));
                                            p.sendMessage(String.format(
                                                    "%sBackpack size: %s%d slots",
                                                    ChatColor.BLUE,
                                                    ChatColor.GRAY,
                                                    backpackSpaceSelect.getValue() * 9
                                            ));
                                            return true;    // whether the action was successful
                                        }
                                ))
                                .withBackground(Material.BLACK_STAINED_GLASS_PANE)
                                .getSection()
                        )
                        .getIntertory(String.format(
                                "%sConfigure game settings",
                                ChatColor.DARK_GRAY
                        ));

                IntertoryItem stick = new Items.Placeholder(new ItemStack(Material.STICK));
                IntertoryItem stone = new Items.Placeholder(new ItemStack(Material.STONE));
                IntertoryItem paper = new Items.Placeholder(new ItemStack(Material.PAPER));

                IntertorySection stickSection = new IntertoryBuilder(3, 3)
                        .withItem(1, 1, stick)
                        .withBackground(Material.BROWN_STAINED_GLASS_PANE)
                        .getSection();
                IntertorySection stoneSection = new IntertoryBuilder(3, 3)
                        .withItem(1, 1, stone)
                        .withBackground(Material.GRAY_STAINED_GLASS_PANE)
                        .getSection();
                IntertorySection paperSection = new IntertoryBuilder(3, 3)
                        .withItem(1, 1, paper)
                        .withBackground(Material.WHITE_STAINED_GLASS_PANE)
                        .getSection();

                Intertory intertory = new IntertoryBuilder(9, 3)
                        .addSection(0, 0, stickSection)
                        .addSection(3, 0, stoneSection)
                        .addSection(6, 0, paperSection)
                        .getIntertory("My Simple Intertory");

                gameConfigIntertory.openFor(p);
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
