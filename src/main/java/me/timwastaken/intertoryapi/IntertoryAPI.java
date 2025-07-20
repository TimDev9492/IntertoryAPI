package me.timwastaken.intertoryapi;

import me.timwastaken.intertoryapi.commands.TestCommand;
import me.timwastaken.intertoryapi.factories.IntertoryFactory;
import me.timwastaken.intertoryapi.factories.ProductionFactory;
import me.timwastaken.intertoryapi.inventories.Intertory;
import me.timwastaken.intertoryapi.inventories.items.ItemAction;
import me.timwastaken.intertoryapi.listeners.IntertoryListener;
import me.timwastaken.intertoryapi.utils.IntertorySound;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class IntertoryAPI extends JavaPlugin {
    private static IntertoryAPI self = null;
    private final IntertoryFactory factory = new ProductionFactory();
    private IntertoryListener listener;

    @Override
    public void onEnable() {
        // Plugin startup logic
        self = this;

        Objects.requireNonNull(this.getCommand("test")).setExecutor(new TestCommand());

        this.listener = new IntertoryListener();
        this.getServer().getPluginManager().registerEvents(this.listener, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public IntertoryFactory getFactory() {
        return factory;
    }

    public static IntertoryAPI getInstance() {
        return self;
    }

    public void registerIntertory(Intertory intertory) {
        this.listener.registerIntertory(intertory);
    }

    public void bindSound(ItemAction action, IntertorySound sound) {
        this.factory.bindSound(action, sound);
    }
}
