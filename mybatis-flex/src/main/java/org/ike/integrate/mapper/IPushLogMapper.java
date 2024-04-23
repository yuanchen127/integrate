package org.ike.integrate.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.ike.integrate.entity.PushLog;

import java.util.Set;

@Mapper
public interface IPushLogMapper extends BaseMapper<PushLog> {

    @Select("select distinct declare_clazz from tb_record")
    public Set<String> listClass();
}
