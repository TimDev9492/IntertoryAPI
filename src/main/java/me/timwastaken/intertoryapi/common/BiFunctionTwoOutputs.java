package me.timwastaken.intertoryapi.common;

import java.util.Map;

@FunctionalInterface
public interface BiFunctionTwoOutputs<T, U, V, W> {
    Map.Entry<V, W> apply(T t, U u);
}
