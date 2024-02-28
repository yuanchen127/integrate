package org.ike.integrate.slot.transdata;

public interface TransDataService {

    public <T> boolean trans(AbstractTransParam<T> function);
}
