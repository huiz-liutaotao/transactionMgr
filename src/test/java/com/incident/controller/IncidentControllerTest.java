// test/java/com/incident/controller/IncidentControllerTest.java  
package com.incident.controller;  

import com.fasterxml.jackson.databind.ObjectMapper;  
import com.incident.model.Incident;  
import com.incident.service.IncidentService;  
import org.junit.jupiter.api.BeforeEach;  
import org.junit.jupiter.api.Test;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;  
import org.springframework.test.web.servlet.MockMvc;  

import java.util.Arrays;  
import java.util.UUID;  

import static org.mockito.ArgumentMatchers.any;  
import static org.mockito.Mockito.when;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  


@WebMvcTest(IncidentController.class)  
class IncidentControllerTest {  

    @Autowired  
    private MockMvc mockMvc;  

    @MockBean  
    private IncidentService incidentService;  

    @Autowired  
    private ObjectMapper objectMapper;  

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
    void whenCreateIncident_thenReturnCreatedIncident() throws Exception {  
        when(incidentService.createIncident(any(Incident.class)))  
            .thenReturn(testIncident);  

        mockMvc.perform(post("/api/incidents")  
                .contentType(MediaType.APPLICATION_JSON)  
                .content(objectMapper.writeValueAsString(testIncident)))  
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$.title").value("Test Incident"))  
                .andExpect(jsonPath("$.description").value("Test Description"));  
    }  

    @Test  
    void whenGetAllIncidents_thenReturnIncidentsList() throws Exception {  
        when(incidentService.getAllIncidents())  
            .thenReturn(Arrays.asList(testIncident));  

        mockMvc.perform(get("/api/incidents"))  
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$[0].title").value("Test Incident"))  
                .andExpect(jsonPath("$[0].description").value("Test Description"));  
    }  

    @Test  
    void whenGetIncidentById_thenReturnIncident() throws Exception {  
        when(incidentService.getIncidentById(testIncident.getId()))  
            .thenReturn(testIncident);  

        mockMvc.perform(get("/api/incidents/" + testIncident.getId()))  
                .andExpect(status().isOk())  
                .andExpect(jsonPath("$.title").value("Test Incident"));  
    }  

    @Test  
    void whenCreateInvalidIncident_thenReturn400() throws Exception {  
        Incident invalidIncident = new Incident();  
        // Missing required fields  

        mockMvc.perform(post("/api/incidents")  
                .contentType(MediaType.APPLICATION_JSON)  
                .content(objectMapper.writeValueAsString(invalidIncident)))  
                .andExpect(status().isBadRequest());  
    }  
}