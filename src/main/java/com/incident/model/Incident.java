package com.incident.model;  


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.time.LocalDateTime;  

@Data  
public class Incident {  
    private int id;

    private String title;  

    private String description;  
    
    private String status;  
    private String priority;
    @TableField(value = "createdAt")
    private LocalDateTime createdAt;
    @TableField(value = "updatedAt")
    private LocalDateTime updatedAt;  
}