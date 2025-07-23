package me.timwastaken.intertoryapi.inventories.items;

import me.timwastaken.intertoryapi.inventories.Intertory;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public abstract class IntertoryItem {
    public abstract ItemStack getStack();

    public abstract void process(InventoryClickEvent event, Intertory environment);

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getStack());
    }
}
