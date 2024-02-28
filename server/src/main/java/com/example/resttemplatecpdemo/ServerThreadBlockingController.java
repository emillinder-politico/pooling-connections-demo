package com.example.resttemplatecpdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is a very simple controller that keeps a count and simulates a big chunk of work.
 */
@RestController
public class ServerThreadBlockingController {

    private final Logger logger = LoggerFactory.getLogger(ServerThreadBlockingController.class);

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    @GetMapping("/hello")
    public String getHello() throws InterruptedException {
        int count = COUNTER.incrementAndGet();
        logger.info("Received request {}", count);
        Thread.sleep(4000L);
        logger.info("Returning response {}", count);
        return "Hello";
    }

}
