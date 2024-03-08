package org.ike.integrate.repository;

import com.alibaba.fastjson.JSON;
import org.ike.integrate.entity.TableColumnSchema;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public interface BinaryTruncationFieldRepo {


    public List<Map<String, Object>> listMap(Set<String> columns, String tableName);

    List<TableColumnSchema> listTableColumnSchema(String tableName);

    default Map<String, TableColumnSchema> getTableColumnSchema(Set<String> columns, String tableName) {
//        List<Map<String, Object>> list = listMap(columns, tableName);
//        List<TableColumnSchema> tmpList = JSON.parseArray(JSON.toJSONString(list), TableColumnSchema.class);

        List<TableColumnSchema> tmpList = listTableColumnSchema(tableName);
        Map<String, TableColumnSchema> map = tmpList.stream().collect(Collectors.toMap(TableColumnSchema::getColumnName, k -> k, (k1, k2) -> k1));
        return map;
    }

}
