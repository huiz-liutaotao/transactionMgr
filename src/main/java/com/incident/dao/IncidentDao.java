
package com.incident.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.incident.model.Incident;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IncidentDao extends BaseMapper<Incident> {
}
