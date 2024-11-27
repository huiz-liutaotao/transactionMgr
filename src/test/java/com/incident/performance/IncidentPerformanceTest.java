// test/java/com/incident/performance/IncidentPerformanceTest.java  
package com.incident.performance;  

import com.incident.model.Incident;  
import com.incident.service.IncidentService;  
import org.junit.jupiter.api.Tag;  
import org.junit.jupiter.api.Test;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.test.context.SpringBootTest;  
import org.springframework.test.context.ActiveProfiles;  

import java.util.ArrayList;  
import java.util.List;  
import java.util.concurrent.CompletableFuture;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Tag("performance")
class IncidentPerformanceTest {

    @Autowired
    private IncidentService incidentService;

    @Test
    void performStressTest() {
        int numberOfThreads = 5;
        int requestsPerThread = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfThreads; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                for (int j = 0; j < requestsPerThread; j++) {
                    try {
                        Incident incident = new Incident();
                        incident.setTitle("Performance Test Incident " + j);
                        incident.setDescription("Created during performance test");
                        incident.setStatus("OPEN");
                        incident.setPriority("LOW");
                        incidentService.createIncident(incident);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();

        try {
            // 等待所有任务完成或超时（使用Thread.sleep替代TimeUnit）
            Thread.sleep(5000); // 等待5秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        long duration = System.currentTimeMillis() - startTime;  
        System.out.println("Performance test completed in " + duration + " ms");  
        assertTrue(duration < 30000, "Performance test took too long");  
    }  
}