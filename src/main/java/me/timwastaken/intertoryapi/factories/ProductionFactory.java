package me.timwastaken.intertoryapi.factories;

import me.timwastaken.intertoryapi.common.Identifiable;
import me.timwastaken.intertoryapi.common.UUIDIdentifiable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ProductionFactory extends IntertoryFactory {
    private final Set<UUID> generatedUUIDs;

    public ProductionFactory() {
        this.generatedUUIDs = new HashSet<>();
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
