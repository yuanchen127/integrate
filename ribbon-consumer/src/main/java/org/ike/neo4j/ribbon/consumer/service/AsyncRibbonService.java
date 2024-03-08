package org.ike.neo4j.ribbon.consumer.service;

import org.ike.neo4j.ribbon.consumer.common.AsyncResponse;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

public interface AsyncRibbonService {

    public CompletableFuture<AsyncResponse> test(int count, Date start) throws InterruptedException;
}
