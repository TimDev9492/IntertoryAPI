package me.timwastaken.intertoryapi.factories;

import me.timwastaken.intertoryapi.common.Identifiable;
import me.timwastaken.intertoryapi.inventories.items.ItemAction;
import me.timwastaken.intertoryapi.utils.IntertorySound;

import java.util.Map;

public abstract class IntertoryFactory {
    protected Map<ItemAction, IntertorySound> soundMap;

    public abstract Identifiable createIdentifiable();

    public void bindSound(ItemAction action, IntertorySound sound) {
        this.soundMap.put(action, sound);
    }
    public IntertorySound getSound(ItemAction action) {
        if (this.soundMap == null || !this.soundMap.containsKey(action)) throw new UnsupportedOperationException(String.format(
                "%s has no sound!",
                action
        ));
        return this.soundMap.get(action);
    }
}
