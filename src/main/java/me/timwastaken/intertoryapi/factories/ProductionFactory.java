package me.timwastaken.intertoryapi.factories;

import me.timwastaken.intertoryapi.common.Identifiable;
import me.timwastaken.intertoryapi.common.UUIDIdentifiable;
import me.timwastaken.intertoryapi.inventories.items.ItemAction;
import me.timwastaken.intertoryapi.utils.IntertorySound;
import org.bukkit.Sound;

import java.util.*;

public class ProductionFactory extends IntertoryFactory {
    private final Set<UUID> generatedUUIDs;

    public ProductionFactory() {
        this.generatedUUIDs = new HashSet<>();
        this.soundMap = new HashMap<>(Map.of(
                ItemAction.GENERIC_SUCCESS, IntertorySound.SUCCESS,
                ItemAction.GENERIC_FAIL, IntertorySound.FAIL,
                ItemAction.VALUE_OUT_OF_BOUNDS, IntertorySound.createCustomSound(Sound.BLOCK_NOTE_BLOCK_BASEDRUM, 1f, 0.5f),
                ItemAction.SCROLL_SUCCESS, IntertorySound.createCustomSound(Sound.ITEM_ARMOR_EQUIP_LEATHER, 1f, 1f),
                ItemAction.TOGGLE_ENABLED, IntertorySound.createCustomSound(Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1f, 1f),
                ItemAction.TOGGLE_DISABLED, IntertorySound.createCustomSound(Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1f, 1f),
                ItemAction.SMALL_INCREMENT, IntertorySound.createCustomSound(Sound.BLOCK_POWDER_SNOW_PLACE, 1f, 1f),
                ItemAction.LARGE_INCREMENT, IntertorySound.createCustomSound(Sound.ENTITY_ENDER_DRAGON_FLAP, 1f, 1f),
                ItemAction.SMALL_DECREMENT, IntertorySound.createCustomSound(Sound.BLOCK_WOOL_BREAK, 1f, 1f),
                ItemAction.LARGE_DECREMENT, IntertorySound.createCustomSound(Sound.BLOCK_WOOD_BREAK, 1f, 0.5f)
        ));
    }

    @Override
    public Identifiable createIdentifiable() {
        UUID next;
        do {
            next = UUID.randomUUID();
        } while (this.generatedUUIDs.contains(next));
        this.generatedUUIDs.add(next);
        return new UUIDIdentifiable(next);
    }
}
