package org.ike.neo4j.ribbon.consumer.common;

import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTest {

    private Unit unit = new Unit();

    @Test
    public void unit1() {
        assertFalse(unit.unit1());
    }

    @Test
    public void unit2() {
        assertEquals(1, unit.unit2());
    }
}