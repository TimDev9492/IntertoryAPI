package me.timwastaken.intertoryapi.common;

import me.timwastaken.intertoryapi.IntertoryAPI;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class UniqueHolder implements InventoryHolder {
    private final Identifiable ident;

    public UniqueHolder() {
        this.ident = IntertoryAPI.getInstance().getFactory().createIdentifiable();
    }

    public Identifiable getIdentifiable() {
        return ident;
    }

    @Override
    public Inventory getInventory() {
        throw new UnsupportedOperationException("This inventory holder is used for comparing inventories only!");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof UniqueHolder other) {
            return other.getIdentifiable().equals(this.ident);
        }
        return false;
    }
}
