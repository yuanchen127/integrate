package org.ike.integrate.mapper;

import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ike.integrate.entity.TableColumnSchema;

import java.util.List;
import java.util.Map;

@Mapper
public interface BinaryTruncationFieldMapper extends BaseMapper<TableColumnSchema> {

    @Select("select COLUMN_NAME as 'columnName', DATA_TYPE as 'dateType', CHARACTER_MAXIMUM_LENGTH as 'maxCharLength' from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME = #{tableName}")
    List<Map<String, Object>> listTableColumnSchema(@Param("tableName") String tableName);
}
