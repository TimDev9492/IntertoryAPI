package me.timwastaken.intertoryapi.common;

import java.util.UUID;

public class UUIDIdentifiable extends Identifiable {
    private final UUID uuid;

    public UUIDIdentifiable(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean specificEquals(Identifiable other) {
        if (other instanceof UUIDIdentifiable otherUUID) {
            return this.uuid.equals(otherUUID.getUUID());
        }
        return false;
    }
}
