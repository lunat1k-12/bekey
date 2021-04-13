package com.bekey.echemeris.app.service;

import com.bekey.echemeris.app.dto.UserTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

public class CountService {

    public void countAndPrint(Queue<UserTransaction> queue, int threadsCount) throws ExecutionException, InterruptedException {
        ConcurrentHashMap<String, Double> countResult = new ConcurrentHashMap<>();
        ConcurrentLinkedQueue<UserTransaction> concurrentQueue = new ConcurrentLinkedQueue<>(queue);

        List<CompletableFuture<Void>> processThreads = new ArrayList<>();
        for (int i = 0; i < threadsCount; i++) {
            processThreads.add(CompletableFuture.runAsync(() -> processCount(concurrentQueue, countResult)));
        }

        CompletableFuture.allOf(processThreads.toArray(new CompletableFuture[0]))
                .thenRunAsync(() -> this.printResult(countResult))
                .get();
    }

    private void processCount(ConcurrentLinkedQueue<UserTransaction> queue, ConcurrentHashMap<String, Double> countResult) {
        UserTransaction tran = queue.poll();
        while (tran != null) {
            countResult.merge(tran.getName(), tran.getAmount(), Double::sum);
            tran = queue.poll();
        }
    }

    private void printResult(Map<String, Double> count) {
        count.forEach((name, totalBalance) -> System.out.println(name + ": " + totalBalance));
    }
}
