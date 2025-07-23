# IntertoryAPI

A Minecraft Bukkit/Spigot library plugin for creating interactive inventory GUIs (Intertories).

## Installation

Add the IntertoryAPI JAR to your plugin's dependencies and include it in your `plugin.yml`:

```yaml
depend: [IntertoryAPI]
```

### Build system

You can import the library using your preferred build system by using the JitPack maven repository
(Replace `TAG` with your required version from [Tags](https://github.com/TimDev9492/IntertoryAPI/tags))

#### Maven

**Step 1.** Add the JitPack repository to your `pom.xml`
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```
**Step 2.** Add the dependency
```xml
<dependency>
  <groupId>com.github.TimDev9492</groupId>
  <artifactId>IntertoryAPI</artifactId>
  <version>TAG</version>
</dependency>
```

#### Gradle

**Step 1.** Add the JitPack repository to your `pom.xml`
```gradle
repositories {
  mavenCentral()
  maven { url 'https://jitpack.io' }
}
```
**Step 2.** Add the dependency
```gradle
dependencies {
  implementation 'com.github.TimDev9492:IntertoryAPI:TAG'
}
```

## Getting started

An `Intertory` (**Inter**active Inven**tory**) is made up of (optionally nested) sections that contain `IntertoryItem`.

To build an `Intertory`, one can use the `IntertoryBuilder` class.

An example of a simple `Intertory` would look like this:
```java
IntertoryItem stick = new Items.Placeholder(new ItemStack(Material.STICK));
IntertoryItem stone = new Items.Placeholder(new ItemStack(Material.STONE));
IntertoryItem paper = new Items.Placeholder(new ItemStack(Material.PAPER));

Intertory intertory = new IntertoryBuilder(
        9,                      // 9 slots in width
        3,                      // 3 slots in height
)
        .withItem(2, 1, stick)  // add stick at position (2, 1)
        .withItem(4, 1, stone)  // add stone at position (4, 1)
        .withItem(6, 1, paper)  // add paper at position (6, 1)
        .getIntertory("My Simple Intertory");

intertory.openFor(player);
```

You can also compose an `Intertory` of multiple sections
```java
IntertoryItem stick = new Items.Placeholder(new ItemStack(Material.STICK));
IntertoryItem stone = new Items.Placeholder(new ItemStack(Material.STONE));
IntertoryItem paper = new Items.Placeholder(new ItemStack(Material.PAPER));

IntertorySection stickSection = new IntertoryBuilder(3, 3)
        .withItem(1, 1, stick)
        .withBackground(Material.BROWN_STAINED_GLASS_PANE)
        .getSection();
IntertorySection stoneSection = new IntertoryBuilder(3, 3)
        .withItem(1, 1, stone)
        .withBackground(Material.GRAY_STAINED_GLASS_PANE)
        .getSection();
IntertorySection paperSection = new IntertoryBuilder(3, 3)
        .withItem(1, 1, paper)
        .withBackground(Material.WHITE_STAINED_GLASS_PANE)
        .getSection();

Intertory intertory = new IntertoryBuilder(9, 3)
        .addSection(0, 0, stickSection)
        .addSection(3, 0, stoneSection)
        .addSection(6, 0, paperSection)
        .getIntertory("Intertory Sections");

intertory.openFor(player);
```

## API Reference

### The `IntertoryItem` class

An `IntertoryItem` is basically just a wrapper around a normal `ItemStack`, however it does provide some
additional functionality.

There are multiple prebuilt `IntertoryItem` classes which you can use all within the `Items` class.

- `Items.Placeholder` represents a placeholder item that cancels every click interaction (This is espacially useful
for background items)
- `Items.ToggleState` represents a simple toggle button that can be enabled or disabled
- `Items.Button` represents a button that triggers an action when it is clicked
- `Items.RangeSelect` represents an item that can be used to select an integer value from a specified range

All of the items mentioned above (except for `Items.Placeholder`) internally inherit from the more generalized version
`Items.ItemWithState<T>` which represents an item that reacts to its internal state when clicked. You can inherit
from this class to create a more advanced `IntertoryItem`.

## Usage

Below, you can find an `Intertory` which uses all of the `IntertoryItem` types above, as reference:
```java
Items.RangeSelect playTimeSelect = new Items.RangeSelect(
        Material.CLOCK,
        String.format(
                "%s%sPlaytime",
                ChatColor.GOLD,
                ChatColor.BOLD
        ),
        "The amount of time the game will run for (in minutes)",
        60, 1, Integer.MAX_VALUE,   // initial value, min, max
        1,                          // small increment
        10                          // large increment
);
Items.RangeSelect numberOfSkipsSelect = new Items.RangeSelect(
        Material.BARRIER,
        String.format(
                "%s%sSkips",
                ChatColor.GOLD,
                ChatColor.BOLD
        ),
        "The number of skips every player has",
        5, 0, Integer.MAX_VALUE,
        1,
        5,
        true
);
Items.RangeSelect backpackSpaceSelect = new Items.RangeSelect(
        Material.ENDER_CHEST,
        String.format(
                "%s%sBackpack",
                ChatColor.GOLD,
                ChatColor.BOLD
        ),
        "The amount of rows of backpack space " +
                "(3x for normal chest, 6x for double chest, ...)",
        3, 1, 6,
        1,
        2,
        true
);

Intertory gameConfigIntertory = new IntertoryBuilder(9, 4)
        .addSection(0, 0, new IntertoryBuilder(9, 3)
                .withItem(2, 1, playTimeSelect)
                .withItem(4, 1, numberOfSkipsSelect)
                .withItem(6, 1, backpackSpaceSelect)
                .withBackground(Material.GRAY_STAINED_GLASS_PANE)
                .getSection()
        )
        .addSection(0, 3, new IntertoryBuilder(9, 1)
                .withItem(0, 0, new Items.Button(
                        new ItemBuilder(Material.TNT)
                                .name(String.format(
                                        "%s%sCancel",
                                        ChatColor.RED,
                                        ChatColor.BOLD
                                ))
                                .build(),
                        // the action to be performed
                        () -> {
                            p.closeInventory();
                            p.sendMessage(String.format(
                                    "%sGame configuration canceled.",
                                    ChatColor.RED
                            ));
                            // whether the action was successful
                            return false;
                        }
                ))
                .withItem(4, 0, new Items.ToggleState(
                        Material.WRITABLE_BOOK,
                        String.format(
                                "%s%sSave config?",
                                ChatColor.YELLOW,
                                ChatColor.BOLD
                        ),
                        "Save the configuration for the next time",
                        false
                ))
                .withItem(8, 0, new Items.Button(
                        new ItemBuilder(Material.TIPPED_ARROW)
                                .name(String.format(
                                        "%s%sStart",
                                        ChatColor.GREEN,
                                        ChatColor.BOLD
                                ))
                                .build(),
                        () -> {
                            p.closeInventory();
                            p.sendMessage(String.format(
                                    "%sStarting the game with these configured values:",
                                    ChatColor.GREEN
                            ));
                            p.sendMessage(String.format(
                                    "%sGame time: %s%d minutes",
                                    ChatColor.BLUE,
                                    ChatColor.GRAY,
                                    playTimeSelect.getValue()
                            ));
                            p.sendMessage(String.format(
                                    "%sNumber of skips: %s%d",
                                    ChatColor.BLUE,
                                    ChatColor.GRAY,
                                    numberOfSkipsSelect.getValue()
                            ));
                            p.sendMessage(String.format(
                                    "%sBackpack size: %s%d slots",
                                    ChatColor.BLUE,
                                    ChatColor.GRAY,
                                    backpackSpaceSelect.getValue() * 9
                            ));
                            return true;
                        }
                ))
                .withBackground(Material.BLACK_STAINED_GLASS_PANE)
                .getSection()
        )
        .getIntertory(String.format(
                "%sConfigure game settings",
                ChatColor.DARK_GRAY
        ));

gameConfigIntertory.openFor(player);
```
