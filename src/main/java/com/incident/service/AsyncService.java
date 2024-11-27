package com.incident.service;  

import com.incident.model.Incident;

import java.util.List;  
import java.util.concurrent.CompletableFuture;  

public interface AsyncService {  
    CompletableFuture<Incident> processIncidentAsync(Incident incident);
    CompletableFuture<List<Incident>> batchProcessIncidents(List<Incident> incidents);  
    CompletableFuture<Void> sendNotification(String message);  
}