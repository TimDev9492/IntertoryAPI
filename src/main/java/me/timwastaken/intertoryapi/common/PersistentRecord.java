package me.timwastaken.intertoryapi.common;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public record PersistentRecord<T>(NamespacedKey key, PersistentDataType<T, T> type, T value) {
}
