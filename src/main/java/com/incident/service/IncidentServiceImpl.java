package com.incident.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.incident.dao.IncidentDao;
import com.incident.exception.IncidentNotFoundException;
import com.incident.model.Incident;
import jakarta.annotation.Resource;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@CacheConfig(cacheNames = "incidents")
public class IncidentServiceImpl extends ServiceImpl<IncidentDao, Incident> implements IncidentService {

    @Resource
    private CacheManager cacheManager;
    @Resource
    private IncidentDao incidentDao;

    @Override
    @CachePut(key = "#result.id")
    public Incident createIncident(Incident incident) {
        incident.setCreatedAt(LocalDateTime.now());
        incident.setUpdatedAt(LocalDateTime.now());
        incidentDao.insert(incident);
        // 删除缓存
        clearCache(String.valueOf(incident.getId()));
        return incident;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteIncident(int id) {
        Incident in = incidentDao.selectById(id);
        if (in == null) {
            throw new IncidentNotFoundException("Incident not found with id: " + id);
        }
        incidentDao.deleteById(id);
    }

    @Override
    @CachePut(key = "#id")
    public Incident modifyIncident(int id, Incident incident) {
        // 删除缓存
        clearCache(String.valueOf(id));
//        cache.evict(key);
        Incident in = incidentDao.selectById(id);
        if (in == null) {
            throw new IncidentNotFoundException("Incident not found with id: " + id);
        }

        // 更新数据库记录
        in.setTitle(incident.getTitle());
        in.setDescription(incident.getDescription());
        in.setStatus(incident.getStatus());
        in.setPriority(incident.getPriority());
        in.setUpdatedAt(LocalDateTime.now());

        incidentDao.updateById(in);  // 直接更新该对象
        // 删除缓存
        clearCache(String.valueOf(id));

        // 更新缓存，返回数据库中最新的对象
        return in;
    }

    @Override
    @Cacheable
    public List<Incident> getAllIncidents() {
        return new ArrayList<>(incidentDao.selectList(null));
    }

    @Override
    @Cacheable(key = "#id")
    public Incident getIncidentById(int id) {
        Incident in = incidentDao.selectById(id);
        if (in == null) {
            throw new IncidentNotFoundException("Incident not found with id: " + id);
        }
        return incidentDao.selectById(id);
    }

    public void clearCache(String key) {
        Cache cache = cacheManager.getCache("incidents");  // 获取缓存
        if (cache != null) {
            cache.evict(key);  // 手动清除缓存
        }
    }

}