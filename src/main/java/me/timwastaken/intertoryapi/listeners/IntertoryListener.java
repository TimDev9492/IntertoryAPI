package me.timwastaken.intertoryapi.listeners;

import me.timwastaken.intertoryapi.inventories.Intertory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class IntertoryListener implements Listener {
    private final List<Intertory> intertories;

    public IntertoryListener() {
        this.intertories = new ArrayList<>();
    }

    public void registerIntertory(Intertory intertory) {
        this.intertories.add(intertory);
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent event) {
        for (Intertory intertory : this.intertories) {
            if (intertory.representsInventory(event.getInventory())) {
                intertory.process(event);
            }
        }
    }
}
