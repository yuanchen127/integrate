package org.ike.integrate.slot.common;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private long current;

    private long size;

    private long total;

    private List<T> data;

}
