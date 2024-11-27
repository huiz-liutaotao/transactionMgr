package com.incident.config;  

import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.scheduling.annotation.EnableAsync;  
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;  

import java.util.concurrent.Executor;  
import java.util.concurrent.ThreadPoolExecutor;  

@Configuration  
@EnableAsync  
public class ThreadPoolConfig {  
    
    @Bean("taskExecutor")  
    public Executor taskExecutor() {  
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();  
        // 核心线程数  
        executor.setCorePoolSize(10);  
        // 最大线程数  
        executor.setMaxPoolSize(20);  
        // 队列容量  
        executor.setQueueCapacity(200);  
        // 线程名前缀  
        executor.setThreadNamePrefix("taskExecutor-");  
        // 线程存活时间  
        executor.setKeepAliveSeconds(60);  
        // 拒绝策略  
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  
        // 等待所有任务完成后再关闭线程池  
        executor.setWaitForTasksToCompleteOnShutdown(true);  
        executor.initialize();  
        return executor;  
    }  

    @Bean("asyncServiceExecutor")  
    public Executor asyncServiceExecutor() {  
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();  
        executor.setCorePoolSize(5);  
        executor.setMaxPoolSize(10);  
        executor.setQueueCapacity(100);  
        executor.setThreadNamePrefix("async-service-");  
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  
        executor.initialize();  
        return executor;  
    }  
}