package me.timwastaken.intertoryapi.inventories.items;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class IntertoryItem {
    public abstract ItemStack getStack();

    public abstract void process(InventoryClickEvent event);
}
