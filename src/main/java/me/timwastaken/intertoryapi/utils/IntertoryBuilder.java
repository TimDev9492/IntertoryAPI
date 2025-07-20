package me.timwastaken.intertoryapi.utils;

import me.timwastaken.intertoryapi.IntertoryAPI;
import me.timwastaken.intertoryapi.common.Vector2;
import me.timwastaken.intertoryapi.inventories.Intertory;
import me.timwastaken.intertoryapi.inventories.IntertorySection;
import me.timwastaken.intertoryapi.inventories.items.IntertoryItem;
import me.timwastaken.intertoryapi.inventories.items.Items;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class IntertoryBuilder {
    private final IntertorySection mainSection;

    public IntertoryBuilder(int width, int height) {
        Vector2<Integer> size = new Vector2<>(width, height);
        this.mainSection = new IntertorySection(size);
    }

    public IntertoryBuilder withItem(int x, int y, IntertoryItem item) {
        this.mainSection.setItem(new Vector2<>(x, y), item);
        return this;
    }

    public IntertoryBuilder withBackground(Material itemType) {
        return this.withBackground(new ItemBuilder(itemType).name(" ").build());
    }

    public IntertoryBuilder withBackground(ItemStack backgroundStack) {
        this.mainSection.setBackgroundItem(new Items.Placeholder(backgroundStack));
        return this;
    }

    public IntertoryBuilder addSection(int x, int y, IntertorySection section) {
        this.mainSection.appendChildAt(section, new Vector2<>(x, y));
        return this;
    }

    public IntertorySection getSection() {
        return this.mainSection;
    }

    public Intertory getIntertory(String title) {
        Intertory intertory = new Intertory(title, this.mainSection);
        IntertoryAPI.getInstance().registerIntertory(intertory);
        return intertory;
    }
}
