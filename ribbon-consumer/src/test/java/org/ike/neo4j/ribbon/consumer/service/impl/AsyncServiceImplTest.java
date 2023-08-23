package org.ike.neo4j.ribbon.consumer.service.impl;

import org.ike.neo4j.ribbon.consumer.common.AsyncResponse;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncServiceImplTest {

    @Test
    public void test1() throws InterruptedException, ExecutionException {
        AsyncServiceImpl var = Mockito.mock(AsyncServiceImpl.class);
//        CompletableFuture<AsyncResponse> response = var.test(1, new Date());
//        assertEquals(1, response.get().getCount());
    }

    @Test
    public void testCreate() {
//        AsyncServiceImpl var = new AsyncServiceImpl();
//        assertTrue(var instanceof AsyncServiceImpl);
    }
}