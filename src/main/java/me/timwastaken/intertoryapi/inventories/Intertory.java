package me.timwastaken.intertoryapi.inventories;

import me.timwastaken.intertoryapi.common.UniqueHolder;
import me.timwastaken.intertoryapi.common.Vector2;
import me.timwastaken.intertoryapi.inventories.items.IntertoryItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Intertory {
    private final String title;
    private final UniqueHolder holder;
    private final IntertorySection content;
    private Map<Integer, IntertoryItem> cache;
    private Inventory instance = null;

    public Intertory(String title, IntertorySection section) {
        this.title = title;
        this.content = section;
        this.holder = new UniqueHolder();
        this.cache = section.getItems();
        this.updateInventory(this.cache);
    }

    public Inventory getInventory() {
        return this.instance;
    }

    private void updateInventory(Map<Integer, IntertoryItem> items) {
        if (this.instance == null)
            this.instance = Bukkit.createInventory(
                    holder,
                    content.getSize().getX() * content.getSize().getY(),
                    this.title
            );

        for (int slot : this.content.getSlots()) {
            this.instance.setItem(slot, Optional.ofNullable(items.get(slot)).map(IntertoryItem::getStack).orElse(null));
        }
    }

    public boolean representsInventory(Inventory inv) {
        return Objects.equals(inv.getHolder(), this.holder);
    }

    public void process(InventoryClickEvent event, Intertory environment) {
        IntertoryItem item = this.cache.get(event.getSlot());
        if (item != null) item.process(event, environment);
        this.updateItemCache();
        this.updateInventory(this.cache);
    }

    public IntertorySection getOwningSection(IntertoryItem item) {
        return this.content.getOwningSection(item);
    }

    public void updateItemCache() {
        this.cache = this.content.getItems();
    }

    public void openFor(Player p) {
        p.openInventory(this.getInventory());
    }
}
