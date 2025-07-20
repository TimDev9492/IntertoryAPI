package me.timwastaken.intertoryapi.common;

public abstract class Identifiable {
    @Override
    public abstract int hashCode();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Identifiable that = (Identifiable) obj;
        return this.specificEquals(that);
    }

    public abstract boolean specificEquals(Identifiable other);
}
