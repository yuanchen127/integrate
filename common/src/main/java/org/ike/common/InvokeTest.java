package org.ike.common;

public class InvokeTest {

    private static boolean isInvoke = false;

    public static boolean tt() {
        return isInvoke;
    }

    public static boolean tt(boolean bol) {
        return bol;
    }
}
