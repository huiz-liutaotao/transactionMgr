package com.incident.service;

import com.incident.model.Incident;
import com.incident.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Async("taskExecutor")
    @Override
    public CompletableFuture<Incident> processIncidentAsync(Incident incident) {
        log.info("开始异步处理事件: {}", incident.getId());
        try {
            // 模拟耗时操作
            Thread.sleep(2000);
            // 处理逻辑
            incident.setStatus("PROCESSED");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("处理事件时发生错误", e);
        }
        return CompletableFuture.completedFuture(incident);
    }

    @Async("asyncServiceExecutor")
    @Override
    public CompletableFuture<List<Incident>> batchProcessIncidents(List<Incident> incidents) {
        log.info("开始批量处理事件，数量: {}", incidents.size());
        List<Incident> processedIncidents = new ArrayList<>();

        for (Incident incident : incidents) {
            try {
                // 模拟处理每个事件
                Thread.sleep(1000);
                incident.setStatus("BATCH_PROCESSED");
                processedIncidents.add(incident);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("批量处理事件时发生错误", e);
            }
        }

        return CompletableFuture.completedFuture(processedIncidents);
    }

    @Async("asyncServiceExecutor")
    @Override
    public CompletableFuture<Void> sendNotification(String message) {
        log.info("发送通知: {}", message);
        try {
            // 模拟发送通知
            Thread.sleep(1000);
        } catch (InterruptedException e) {  
            Thread.currentThread().interrupt();  
            log.error("发送通知时发生错误", e);  
        }  
        return CompletableFuture.completedFuture(null);  
    }  
}