package me.timwastaken.intertoryapi.inventories.items;

import me.timwastaken.intertoryapi.IntertoryAPI;
import me.timwastaken.intertoryapi.common.BiFunctionTwoOutputs;
import me.timwastaken.intertoryapi.common.IntertoryClickEvent;
import me.timwastaken.intertoryapi.common.StringUtils;
import me.timwastaken.intertoryapi.inventories.Intertory;
import me.timwastaken.intertoryapi.inventories.IntertorySection;
import me.timwastaken.intertoryapi.utils.IntertorySound;
import me.timwastaken.intertoryapi.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class Items {
    private static final Set<InventoryAction> ITEM_OUT_ACTIONS = new HashSet<>(Set.of(
            InventoryAction.PICKUP_ONE,
            InventoryAction.PICKUP_SOME,
            InventoryAction.PICKUP_ALL,
            InventoryAction.PICKUP_HALF,
            InventoryAction.COLLECT_TO_CURSOR,
            InventoryAction.DROP_ALL_SLOT,
            InventoryAction.MOVE_TO_OTHER_INVENTORY
    ));

    public static class NoItem extends IntertoryItem {
        @Override
        public ItemStack getStack() {
            return null;
        }

        @Override
        public void process(InventoryClickEvent event, Intertory environment) {
            // do nothing
        }
    }

    public static class FromItemStack extends IntertoryItem {
        private final ItemStack stack;

        public FromItemStack(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public ItemStack getStack() {
            return this.stack;
        }

        @Override
        public void process(InventoryClickEvent event, Intertory environment) {
            if (ITEM_OUT_ACTIONS.contains(event.getAction())) {
                // item leaves inventory
                IntertorySection section = environment.getOwningSection(this);
                section.removeItem(this);
            }
        }
    }

    public static class Placeholder extends IntertoryItem {
        private final ItemStack stack;

        public Placeholder(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public ItemStack getStack() {
            return this.stack;
        }

        @Override
        public void process(InventoryClickEvent event, Intertory environment) {
            event.setCancelled(true);
            IntertorySound.FAIL.playTo((Player) event.getWhoClicked());
        }
    }

    public static class ToggleState extends ItemWithState<Boolean> {
        public ToggleState(Material itemType, String title, String description, boolean enabled) {
            super(
                    enabled,
                    (isEnabled, type) -> new AbstractMap.SimpleEntry<>(
                            !isEnabled,
                            !isEnabled ? ItemAction.TOGGLE_ENABLED : ItemAction.TOGGLE_DISABLED
                    ),
                    (isEnabled) -> new ItemBuilder(itemType)
                            .name(title)
                            .addLoreLine(isEnabled ?
                                    String.format(
                                            "%s%s%s",
                                            ChatColor.GREEN,
                                            ChatColor.BOLD,
                                            "Enabled"
                                    ) :
                                    String.format(
                                            "%s%s%s",
                                            ChatColor.RED,
                                            ChatColor.BOLD,
                                            "Disabled"
                                    ))
                            .addLoreLine("")
                            .addLoreLine(String.format("%sClick to toggle", ChatColor.DARK_GRAY))
                            .build()
            );
        }
    }

    public static class RangeSelect extends ItemWithState<Integer> {
        public RangeSelect(
                Material itemType,
                String title,
                String description,
                int value,
                int min,
                int max,
                int smallStep,
                int bigStep
        ) {
            this(
                    itemType,
                    title,
                    description,
                    value,
                    min,
                    max,
                    smallStep,
                    bigStep,
                    false
            );
        }

        public RangeSelect(
                Material itemType,
                String title,
                String description,
                int value,
                int min,
                int max,
                int smallStep,
                int bigStep,
                boolean displayValueAsItemAmount
        ) {
            super(
                    value,
                    (currentValue, type) -> {
                        int nextValue;
                        boolean largeChange = type.isShiftClick();
                        int increment = largeChange ? bigStep : smallStep;
                        ItemAction action;
                        if (type.isLeftClick()) {
                            nextValue = currentValue + increment;
                            action = largeChange ? ItemAction.LARGE_INCREMENT : ItemAction.SMALL_INCREMENT;
                        } else if (type.isRightClick()) {
                            nextValue = currentValue - increment;
                            action = largeChange ? ItemAction.LARGE_DECREMENT : ItemAction.SMALL_DECREMENT;
                        } else {
                            return new AbstractMap.SimpleEntry<>(null, ItemAction.GENERIC_FAIL);
                        }
                        if (nextValue > max) return new AbstractMap.SimpleEntry<>(null, ItemAction.VALUE_OUT_OF_BOUNDS);
                        if (nextValue < min) return new AbstractMap.SimpleEntry<>(null, ItemAction.VALUE_OUT_OF_BOUNDS);
                        return new AbstractMap.SimpleEntry<>(nextValue, action);
                    },
                    (currentValue) -> {
                        List<String> descriptionLore = StringUtils.wrapText(description, 48)
                                .stream().map(
                                        line -> String.format("%s%s", ChatColor.GRAY, line)
                                ).toList();
                        return new ItemBuilder(itemType)
                                .name(title)
                                .amount(
                                        displayValueAsItemAmount ? Math.clamp(currentValue, 1, 99) : 1,
                                        true
                                )
                                .addLoreLine(String.format(
                                        "%sCurrent value: %s%d",
                                        ChatColor.GRAY,
                                        ChatColor.GOLD,
                                        currentValue
                                ))
                                .addLoreLine("")
                                .addLoreLines(descriptionLore)
                                .addLoreLine("")
                                .addLoreLine(String.format("%sLeft Click (+) | Right Click (-)", ChatColor.DARK_GRAY))
                                .build();
                    }
            );
        }

        public int getValue() {
            return super.getState();
        }
    }

    public static class ItemWithState<T> extends IntertoryItem {
        private T state;
        private final BiFunctionTwoOutputs<T, ClickType, T, ItemAction> stateTransform;
        private final Function<T, ItemStack> stateDisplay;

        public ItemWithState(
                T initialState,
                BiFunctionTwoOutputs<T, ClickType, T, ItemAction> stateTransform,
                Function<T, ItemStack> stateDisplay
        ) {
            this.state = initialState;
            this.stateTransform = stateTransform;
            this.stateDisplay = stateDisplay;
        }

        @Override
        public ItemStack getStack() {
            return this.stateDisplay.apply(this.state);
        }

        @Override
        public void process(InventoryClickEvent event, Intertory environment) {
            event.setCancelled(true);
            Map.Entry<T, ItemAction> result = this.stateTransform.apply(this.state, event.getClick());
            T next = result.getKey();
            ItemAction action = result.getValue();
            IntertoryAPI.getInstance().getFactory().getSound(action).playTo((Player) event.getWhoClicked());
            if (next != null)
                this.state = next;
        }

        public T getState() {
            return state;
        }
    }

    public static class Button extends IntertoryItem {
        private final ItemStack stack;
        private final Supplier<Boolean> action;

        public Button(ItemStack stack, Supplier<Boolean> action) {
            this.stack = stack;
            this.action = action;
        }

        @Override
        public ItemStack getStack() {
            return this.stack;
        }

        @Override
        public void process(InventoryClickEvent event, Intertory environment) {
            event.setCancelled(true);
            if (event.getClick().isLeftClick()) {
                boolean success = this.action.get();
                IntertorySound sound = IntertoryAPI.getInstance().getFactory().getSound(
                        success ? ItemAction.GENERIC_SUCCESS : ItemAction.GENERIC_FAIL
                );
                sound.playTo((Player) event.getWhoClicked());
            }
        }
    }
}
