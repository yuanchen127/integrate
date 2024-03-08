package org.ike.neo4j.ribbon.consumer.service.impl;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.ExecutionException;

public class AsyncRibbonServiceImplTest {

    @Test
    public void test1() throws InterruptedException, ExecutionException {
        AsyncRibbonServiceImpl var = Mockito.mock(AsyncRibbonServiceImpl.class);
//        CompletableFuture<AsyncResponse> response = var.test(1, new Date());
//        assertEquals(1, response.get().getCount());
    }

    @Test
    public void testCreate() {
//        AsyncRibbonServiceImpl var = new AsyncRibbonServiceImpl();
//        assertTrue(var instanceof AsyncRibbonServiceImpl);
    }
}