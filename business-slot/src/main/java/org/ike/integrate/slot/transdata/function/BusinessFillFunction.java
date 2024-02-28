package org.ike.integrate.slot.transdata.function;

@FunctionalInterface
public interface BusinessFillFunction<T> {
    public T apply();
}
