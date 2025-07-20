package me.timwastaken.intertoryapi.inventories;

import me.timwastaken.intertoryapi.common.SlotUtils;
import me.timwastaken.intertoryapi.common.Vector2;
import me.timwastaken.intertoryapi.inventories.items.IntertoryItem;
import me.timwastaken.intertoryapi.inventories.items.Items;

import java.util.*;

public class IntertorySection {
    private final List<IntertorySection> children;
    protected final Map<Vector2<Integer>, IntertoryItem> ownedItems;
    // the position of the child inside the parent
    private Vector2<Integer> position;
    protected final Vector2<Integer> size;
    protected List<Integer> slots;

    public IntertorySection(int width, int height) {
        this(new Vector2<>(width, height));
    }

    public IntertorySection(Vector2<Integer> size) {
        this.position = new Vector2<>(0, 0);
        this.size = size;
        this.slots = new ArrayList<>(size.getX() * size.getY());
        for (int i = 0; i < size.getX() * size.getY(); i++) this.slots.add(i);
        this.children = new ArrayList<>();
        this.ownedItems = new HashMap<>();
    }

    protected Map<Vector2<Integer>, IntertoryItem> getView() {
        return this.ownedItems;
    }

    /**
     * Returns a map of all the items this section and all of its
     * children own.
     *
     * @return A map of all the items this section and its children own.
     */
    private Map<Vector2<Integer>, IntertoryItem> getItems(IntertorySection parent) {
        Map<Vector2<Integer>, IntertoryItem> items = new HashMap<>();
        Map<Vector2<Integer>, IntertoryItem> selfAndChildItems = new HashMap<>(this.getView());
        for (IntertorySection child : this.children) {
            selfAndChildItems.putAll(child.getItems(this));
        }
        for (Map.Entry<Vector2<Integer>, IntertoryItem> ownedItem : selfAndChildItems.entrySet()) {
            Vector2<Integer> slot = ownedItem.getKey();
            Vector2<Integer> parentSlot;
            if (parent == null) parentSlot = slot;
            else {
                parentSlot = new Vector2<>(this.position.getX() + slot.getX(), this.position.getY() + slot.getY());
            }
            items.put(parentSlot, ownedItem.getValue());
        }
        return items;
    }

    public Map<Integer, IntertoryItem> getItems() {
        Map<Integer, IntertoryItem> items = new HashMap<>();
        Map<Vector2<Integer>, IntertoryItem> selfItems = this.getItems(null);
        for (Map.Entry<Vector2<Integer>, IntertoryItem> slotItems : selfItems.entrySet()) {
            Vector2<Integer> slotXY = slotItems.getKey();
            items.put(SlotUtils.toSlot(slotXY, this.size), slotItems.getValue());
        }
        return items;
    }

    public IntertoryItem setItem(Vector2<Integer> slot, IntertoryItem item) {
        return this.ownedItems.put(slot, item);
    }

    public void setBackgroundItem(IntertoryItem item) {
        for (int slot : this.getSlots()) {
            Vector2<Integer> slotXY = SlotUtils.toSlotXY(slot, this.size);
            if (!this.ownedItems.containsKey(slotXY)) {
                this.ownedItems.put(slotXY, item);
            }
        }
    }

    public void appendChildAt(IntertorySection section, Vector2<Integer> position) {
        section.setPosition(position);
        this.children.add(section);
    }

    public Vector2<Integer> getPosition() {
        return position;
    }

    public void setPosition(Vector2<Integer> position) {
        this.position = position;
    }

    public Vector2<Integer> getSize() {
        return size;
    }

    public List<Integer> getSlots() {
        return this.slots;
    }
}
