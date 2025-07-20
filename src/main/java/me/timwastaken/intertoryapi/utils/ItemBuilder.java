package me.timwastaken.intertoryapi.utils;

import me.timwastaken.intertoryapi.IntertoryAPI;
import me.timwastaken.intertoryapi.common.PersistentRecord;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemBuilder {
    private final Material material;
    private int amount = 1;
    private Integer maxStackSize = null;
    private String displayName = null;
    private final List<PersistentRecord<?>> persistentRecords = new ArrayList<>();
    private final List<String> lore = new ArrayList<>();
    private Consumer<ItemMeta> metaModifier = meta -> {};

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public ItemBuilder amount(int amount) {
        return this.amount(amount, false);
    }

    public ItemBuilder amount(int amount, boolean increaseMaxStackSize) {
        if (increaseMaxStackSize) this.maxStackSize(amount);
        this.amount = amount;
        return this;
    }

    public ItemBuilder maxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
        return this;
    }

    public ItemBuilder name(String name) {
        this.displayName = name;
        return this;
    }

    public <T> ItemBuilder persistentData(String key, PersistentDataType<T, T> type, T value) {
        NamespacedKey namespacedKey = new NamespacedKey(IntertoryAPI.getInstance(), key);
        this.persistentRecords.add(new PersistentRecord<T>(namespacedKey, type, value));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore.clear();
        this.lore.addAll(lore);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        this.lore.add(line);
        return this;
    }

    public ItemBuilder addLoreLines(List<String> lines) {
        this.lore.addAll(lines);
        return this;
    }

    public ItemBuilder modifyMeta(Consumer<ItemMeta> consumer) {
        this.metaModifier = this.metaModifier.andThen(consumer);
        return this;
    }

    public ItemStack build() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        if (maxStackSize != null) {
            meta.setMaxStackSize(maxStackSize);
        }

        if (displayName != null) {
            meta.setDisplayName(displayName);
        }

        for (PersistentRecord<?> persistentRecord : this.persistentRecords) {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            setData(container, persistentRecord);
        }

        if (!lore.isEmpty()) {
            meta.setLore(new ArrayList<>(lore));
        }

        metaModifier.accept(meta);
        item.setItemMeta(meta);

        return item;
    }

    private <T> void setData(PersistentDataContainer container, PersistentRecord<T> record) {
        container.set(record.key(), record.type(), record.value());
    }
}

