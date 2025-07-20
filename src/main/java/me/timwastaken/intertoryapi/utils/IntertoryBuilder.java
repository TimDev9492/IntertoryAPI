package me.timwastaken.intertoryapi.utils;

import me.timwastaken.intertoryapi.IntertoryAPI;
import me.timwastaken.intertoryapi.common.Vector2;
import me.timwastaken.intertoryapi.inventories.Intertory;
import me.timwastaken.intertoryapi.inventories.IntertorySection;
import me.timwastaken.intertoryapi.inventories.items.IntertoryItem;

public class IntertoryBuilder {
    private final IntertorySection mainSection;
    private final String title;

    public IntertoryBuilder(int width, int height, String title) {
        Vector2<Integer> size = new Vector2<>(width, height);
        this.mainSection = new IntertorySection(size);
        this.title = title;
    }

    public IntertoryBuilder withItem(int x, int y, IntertoryItem item) {
        this.mainSection.setItem(new Vector2<>(x, y), item);
        return this;
    }

    public IntertoryBuilder addSection(int x, int y, IntertorySection section) {
        this.mainSection.appendChildAt(section, new Vector2<>(x, y));
        return this;
    }

    public IntertorySection getSection() {
        return this.mainSection;
    }

    public Intertory getIntertory() {
        Intertory intertory = new Intertory(this.title, this.mainSection);
        IntertoryAPI.getInstance().registerIntertory(intertory);
        return intertory;
    }
}
