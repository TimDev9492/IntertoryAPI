package me.timwastaken.intertoryapi;

import me.timwastaken.intertoryapi.factories.IntertoryFactory;
import me.timwastaken.intertoryapi.factories.ProductionFactory;
import org.bukkit.plugin.java.JavaPlugin;

public final class IntertoryAPI extends JavaPlugin {
    private static IntertoryAPI self = null;
    private final IntertoryFactory factory = new ProductionFactory();

    @Override
    public void onEnable() {
        // Plugin startup logic
        self = this;
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
}
