package me.timwastaken.intertoryapi.inventories;

import me.timwastaken.intertoryapi.common.Vector2;
import me.timwastaken.intertoryapi.inventories.items.IntertoryItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScrollableSection extends IntertorySection {
    private Vector2<Integer> scrollOffset;
    private final Vector2<Integer> virtualSize;

    public ScrollableSection(int width, int height, int virtualWidth, int virtualHeight) {
        this(new Vector2<>(width, height), new Vector2<>(virtualWidth, virtualHeight));
    }

    public ScrollableSection(Vector2<Integer> size, Vector2<Integer> virtualSize) {
        super(size);
        this.slots = new ArrayList<>(virtualSize.getX() * virtualSize.getY());
        for (int i = 0; i < virtualSize.getX() * virtualSize.getY(); i++) this.slots.add(i);
        this.virtualSize = virtualSize;
        this.scrollOffset = new Vector2<>(0, 0);
    }

    @Override
    protected Map<Vector2<Integer>, IntertoryItem> getView() {
        Map<Vector2<Integer>, IntertoryItem> view = new HashMap<>();
        for (Map.Entry<Vector2<Integer>, IntertoryItem> ownedItem : this.ownedItems.entrySet()) {
            Vector2<Integer> rawSlot = ownedItem.getKey();
            int actualX = rawSlot.getX() - this.scrollOffset.getX();
            int actualY = rawSlot.getY() - this.scrollOffset.getY();
            if (actualX < 0 || actualX >= this.size.getX()) continue;
            if (actualY < 0 || actualY >= this.size.getY()) continue;
            view.put(new Vector2<>(actualX, actualY), ownedItem.getValue());
        }
        return view;
    }

    public boolean scroll(Vector2<Integer> offset) {
        int nextScrollX = this.scrollOffset.getX() + offset.getX();
        int nextScrollY = this.scrollOffset.getY() + offset.getY();
        if (nextScrollX < 0 || nextScrollY < 0) return false;
        if (nextScrollX > this.virtualSize.getX() - this.size.getX()) return false;
        if (nextScrollY > this.virtualSize.getY() - this.size.getY()) return false;
        this.scrollOffset = new Vector2<>(nextScrollX, nextScrollY);
        return true;
    }

    public boolean scroll(int offsetX, int offsetY) {
        return this.scroll(new Vector2<>(offsetX, offsetY));
    }

    public Vector2<Integer> getVirtualSize() {
        return virtualSize;
    }
}
