package com.bekey.echemeris.app;

import com.bekey.echemeris.app.dto.UserTransaction;
import com.bekey.echemeris.app.service.CountService;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.net.URI;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TestApplication {

    public static final int COUNT_THREADS_COUNT = 2;

    public static void main(String[] args) throws Exception {
        CountService countService = new CountService();

        URI fileUri = TestApplication.class.getClassLoader().getResource("input.txt").toURI();

        ConcurrentLinkedQueue<UserTransaction> queue = new ConcurrentLinkedQueue<>();

        try (CSVReader reader = new CSVReader(new FileReader(fileUri.getPath()))) {
            String[] record;
            while ((record = reader.readNext()) != null) {
                queue.add(new UserTransaction(Integer.parseInt(record[0]),
                        record[1],
                        Double.parseDouble(record[2])));
            }
        }

        countService.countAndPrint(queue, COUNT_THREADS_COUNT);
    }

}
