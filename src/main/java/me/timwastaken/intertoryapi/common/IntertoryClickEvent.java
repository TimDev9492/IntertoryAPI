package me.timwastaken.intertoryapi.common;

import me.timwastaken.intertoryapi.inventories.Intertory;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;

public class IntertoryClickEvent extends InventoryClickEvent {
    private final Intertory intertory;

    public static IntertoryClickEvent from(InventoryClickEvent event, Intertory intertory) {
        return new IntertoryClickEvent(intertory, event.getView(), event.getSlotType(), event.getSlot(), event.getClick(), event.getAction());
    }

    public IntertoryClickEvent(Intertory intertory, InventoryView view, InventoryType.SlotType type, int slot, ClickType click, InventoryAction action) {
        super(view, type, slot, click, action);
        this.intertory = intertory;
    }

    public Intertory getIntertory() {
        return intertory;
    }
}
