package com.example.resttemplatecpdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
public class DemoController {

    private final Logger logger = LoggerFactory.getLogger(DemoController.class);

    /**
     * simple executor for the helloz calls
     */
    private final ExecutorService executor = Executors.newFixedThreadPool(15);

    private final RestTemplate restTemplate;

    private final String serverHostname;

    public DemoController(RestTemplate restTemplate, @Value("${backend.server.host:localhost}") String serverHostname) {
        this.restTemplate = restTemplate;
        this.serverHostname = serverHostname;
    }

    /**
     * Each request results in a call to the server app.
     */
    @GetMapping("/hello")
    public String getHello() {
        logger.info("Calling server 1 time");
        long start = System.nanoTime();
        String response = restTemplate.getForObject("http://" + serverHostname + ":8081/hello", String.class);
        logger.info("Returning response after {}: {}", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start), response);
        return response;
    }

    /**
     * Added this to test the handling of multiple threads making requests to the server
     */
    @GetMapping("/helloz")
    public String getHelloz() {
        logger.info("Calling server 10 times");

        Collection<Future<?>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SayHi task = new SayHi(restTemplate);
            Future<?> submit = executor.submit(task);
            tasks.add(submit);
        }
        logger.info("Waiting for all tasks to complete");
        while (tasks.stream().anyMatch(t -> !t.isDone())) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        logger.info("Getting results");
        for (Future<?> result : tasks) {
            try {
                result.get();
            } catch (Exception e) {
                logger.error("Error", e);
            }
        }
        logger.info("Done");
        return "hello";
    }


    private static class SayHi implements Runnable {

        private final Logger logger = LoggerFactory.getLogger(SayHi.class);
        private final RestTemplate restTemplate;

        private SayHi(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        @Override
        public void run() {

            long start = System.nanoTime();
            String response = restTemplate.getForObject("http://resttemplate-cp-demo-server:8081/hello", String.class);
            logger.info("Returning response after {}: {}", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start), response);
        }
    }
}
