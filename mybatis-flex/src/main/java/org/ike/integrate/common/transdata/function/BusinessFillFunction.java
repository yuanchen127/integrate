package org.ike.integrate.common.transdata.function;

@FunctionalInterface
public interface BusinessFillFunction<T> {
    public T apply();
}
