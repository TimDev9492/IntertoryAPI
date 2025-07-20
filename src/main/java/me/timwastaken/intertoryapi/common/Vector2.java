package me.timwastaken.intertoryapi.common;

import me.timwastaken.intertoryapi.IntertoryAPI;

import java.util.UUID;

public class Vector2<T> {
    private final Identifiable ident;
    private final T x;
    private final T y;

    public Vector2(T x, T y) {
        this.ident = IntertoryAPI.getInstance().getFactory().createIdentifiable();
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode() {
        return this.ident.hashCode();
    }
}
