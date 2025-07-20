package me.timwastaken.intertoryapi.common;

public class SlotUtils {
    public static Vector2<Integer> toSlotXY(int slot, Vector2<Integer> size) {
        return new Vector2<>(slot % size.getX(), slot / size.getX());
    }

    public static int toSlot(Vector2<Integer> slotXY, Vector2<Integer> size) {
        return slotXY.getY() * size.getX() + slotXY.getX();
    }
}
