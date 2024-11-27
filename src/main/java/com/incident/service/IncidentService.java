package com.incident.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.incident.model.Incident;
import java.util.List;  

public interface IncidentService extends IService<Incident> {
    Incident createIncident(Incident incident);
    void deleteIncident(int id);
    Incident modifyIncident(int id, Incident incident);
    List<Incident> getAllIncidents();
    Incident getIncidentById(int id);
}