package com.incident.config;  

import lombok.extern.slf4j.Slf4j;  
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;  

import jakarta.annotation.Resource;  
import java.util.HashMap;  
import java.util.Map;  

@Slf4j  
@RestController  
@RequestMapping("/thread-pool")  
public class ThreadPoolMonitor {  

    @Resource(name = "taskExecutor")  
    private ThreadPoolTaskExecutor taskExecutor;  

    @Resource(name = "asyncServiceExecutor")  
    private ThreadPoolTaskExecutor asyncServiceExecutor;  

    @GetMapping("/status")  
    public Map<String, Object> getThreadPoolStatus() {  
        Map<String, Object> status = new HashMap<>();  
        
        // 监控taskExecutor  
        Map<String, Object> taskExecutorStatus = new HashMap<>();  
        taskExecutorStatus.put("activeCount", taskExecutor.getActiveCount());  
        taskExecutorStatus.put("corePoolSize", taskExecutor.getCorePoolSize());  
        taskExecutorStatus.put("maxPoolSize", taskExecutor.getMaxPoolSize());  
        taskExecutorStatus.put("queueSize", taskExecutor.getThreadPoolExecutor().getQueue().size());  
        
        // 监控asyncServiceExecutor  
        Map<String, Object> asyncServiceStatus = new HashMap<>();  
        asyncServiceStatus.put("activeCount", asyncServiceExecutor.getActiveCount());  
        asyncServiceStatus.put("corePoolSize", asyncServiceExecutor.getCorePoolSize());  
        asyncServiceStatus.put("maxPoolSize", asyncServiceExecutor.getMaxPoolSize());  
        asyncServiceStatus.put("queueSize", asyncServiceExecutor.getThreadPoolExecutor().getQueue().size());  

        status.put("taskExecutor", taskExecutorStatus);  
        status.put("asyncServiceExecutor", asyncServiceStatus);  
        
        return status;  
    }  
}