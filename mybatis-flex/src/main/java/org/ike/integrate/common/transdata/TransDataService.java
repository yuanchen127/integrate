package org.ike.integrate.common.transdata;

import java.util.function.Supplier;

public interface TransDataService {

    public <T> boolean trans(AbstractTransParam<T> function);
}
