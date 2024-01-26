package org.ike.common;

import java.util.function.Supplier;

public class InvokeMethod {

     public static void invoke(Supplier supplier) {
         supplier.get();
     }

//    public static void main(String[] args) {
//        invoke(()->{
//            return InvokeTest.tt(false);
//        });
//    }
}
