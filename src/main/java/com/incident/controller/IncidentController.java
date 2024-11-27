package com.incident.controller;  

import com.incident.model.Incident;
import com.incident.service.AsyncService;
import com.incident.service.IncidentService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController  
@RequestMapping("/api/incidents")  
@RequiredArgsConstructor  
@CrossOrigin(origins = "http://localhost:3000")  
public class IncidentController {  

    @Resource
    private IncidentService incidentService;

    @Resource
    private AsyncService asyncService;

    @PostMapping  
    public ResponseEntity<Incident> createIncident(@Valid @RequestBody Incident incident) {
        return ResponseEntity.ok(incidentService.createIncident(incident));  
    }  

    @DeleteMapping("/{id}")  
    public ResponseEntity<Void> deleteIncident(@PathVariable int id) {
        incidentService.deleteIncident(id);  
        return ResponseEntity.noContent().build();  
    }  

    @PutMapping("/{id}")  
    public ResponseEntity<Incident> modifyIncident(  
            @PathVariable int id,
            @Valid @RequestBody Incident incident) {  
        return ResponseEntity.ok(incidentService.modifyIncident(id, incident));  
    }  

    @GetMapping  
    public ResponseEntity<List<Incident>> getAllIncidents() {  
        return ResponseEntity.ok(incidentService.getAllIncidents());  
    }  

    @GetMapping("/{id}")  
    public ResponseEntity<Incident> getIncidentById(@PathVariable int id) {
        return ResponseEntity.ok(incidentService.getIncidentById(id));  
    }

    @PostMapping("/process")
    public Incident processIncident(@RequestBody Incident incident) {
        try {
            CompletableFuture<Incident> future = asyncService.processIncidentAsync(incident);
            // 等待处理完成并返回结果
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("处理事件时发生错误", e);
        }
    }

    @PostMapping("/batch")
    public List<Incident> batchProcess(@RequestBody List<Incident> incidents) {
        try {
            CompletableFuture<List<Incident>> future = asyncService.batchProcessIncidents(incidents);
            // 同时发送通知
            CompletableFuture<Void> notificationFuture = asyncService.sendNotification("开始批量处理事件");

            // 等待所有操作完成
            CompletableFuture.allOf(future, notificationFuture).join();

            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("批量处理事件时发生错误", e);
        }
    }
}