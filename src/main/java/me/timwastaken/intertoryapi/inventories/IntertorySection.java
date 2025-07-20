package me.timwastaken.intertoryapi.inventories;

import me.timwastaken.intertoryapi.common.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntertorySection {
    private final List<IntertorySection> children;
    private final Map<Integer, IntertoryItem> ownedItems;
    private Vector2<Integer> position;
    private Vector2<Integer> size;

    public IntertorySection(int width, int height) {
        this(new Vector2<>(width, height));
    }

    public IntertorySection(Vector2<Integer> size) {
        this.position = new Vector2<>(0, 0);
        this.size = size;
        this.children = new ArrayList<>();
        this.ownedItems = new HashMap<>();
    }

    /**
     * Returns a map of all the items this section and all of its
     * children own.
     *
     * @return A map of all the items this section and its children own.
     */
    private Map<Integer, IntertoryItem> getItems(IntertorySection parent) {
        Map<Integer, IntertoryItem> items = new HashMap<>();
        for (Map.Entry<Integer, IntertoryItem> ownedItem : this.ownedItems.entrySet()) {
            int slot = ownedItem.getKey();
            int parentSlot = parent != null ?
        }
    }

    public Map<Integer, IntertoryItem> getItems() {
        return this.getItems(null);
    }

    public Vector2<Integer> getPosition() {
        return position;
    }

    public Vector2<Integer> getSize() {
        return size;
    }
}
