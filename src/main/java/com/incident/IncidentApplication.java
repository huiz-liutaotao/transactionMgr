package com.incident;  

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;  
import org.springframework.cache.annotation.EnableCaching;  

@SpringBootApplication  
@EnableCaching
@MapperScan(basePackages = {"com.incident.dao"})
public class IncidentApplication {  
    public static void main(String[] args) {  
        SpringApplication.run(IncidentApplication.class, args);  
    }  
}