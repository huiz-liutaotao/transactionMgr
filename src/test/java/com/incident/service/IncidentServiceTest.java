package com.incident.service;

import com.incident.exception.IncidentNotFoundException;  
import com.incident.model.Incident;  
import org.junit.jupiter.api.BeforeEach;  
import org.junit.jupiter.api.Test;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.test.context.SpringBootTest;  
import org.springframework.test.context.ActiveProfiles;  

import static org.junit.jupiter.api.Assertions.*;  

@SpringBootTest  
@ActiveProfiles("test")  
class IncidentServiceTest {  

    @Autowired  
    private IncidentService incidentService;  

    private Incident testIncident;  

    @BeforeEach  
    void setUp() {  
        testIncident = new Incident();  
        testIncident.setTitle("Test Incident");  
        testIncident.setDescription("Test Description");  
        testIncident.setStatus("OPEN");  
        testIncident.setPriority("HIGH");  
    }  

    @Test  
    void whenCreateIncident_thenIncidentIsCreated() {  
        Incident created = incidentService.createIncident(testIncident);  
        
        assertNotNull(created);  
        assertNotNull(created.getId());  
        assertEquals("Test Incident", created.getTitle());  
        assertEquals("Test Description", created.getDescription());  
        assertEquals("OPEN", created.getStatus());  
        assertEquals("HIGH", created.getPriority());  
    }  

    @Test  
    void whenGetNonExistentIncident_thenThrowException() {  
        int nonExistentId = 1;
        
        assertThrows(IncidentNotFoundException.class, () ->   
            incidentService.getIncidentById(nonExistentId)  
        );  
    }  

    @Test  
    void whenModifyIncident_thenIncidentIsUpdated() {  
        // Create an incident first  
        Incident created = incidentService.createIncident(testIncident);  
        assertNotNull(created.getId());  

        // Modify the incident  
        created.setTitle("Updated Title");  
        created.setStatus("IN_PROGRESS");  
        
        Incident updated = incidentService.modifyIncident(created.getId(), created);  
        
        assertNotNull(updated);  
        assertEquals("Updated Title", updated.getTitle());  
        assertEquals("IN_PROGRESS", updated.getStatus());  
    }  

    @Test  
    void whenDeleteIncident_thenIncidentIsRemoved() {  
        // Create an incident first  
        Incident created = incidentService.createIncident(testIncident);  
        int id = created.getId();
        assertNotNull(id);  

        // Delete the incident  
        incidentService.deleteIncident(id);  

        // Verify it's deleted  
        assertThrows(IncidentNotFoundException.class, () ->   
            incidentService.getIncidentById(id)  
        );  
    }  
}