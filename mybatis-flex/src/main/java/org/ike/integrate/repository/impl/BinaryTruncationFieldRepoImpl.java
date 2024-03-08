package org.ike.integrate.repository.impl;

import com.mybatisflex.core.query.QueryWrapper;
import org.ike.integrate.entity.Account;
import org.ike.integrate.entity.TableColumnSchema;
import org.ike.integrate.mapper.BinaryTruncationFieldMapper;
import org.ike.integrate.repository.BinaryTruncationFieldRepo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class BinaryTruncationFieldRepoImpl implements BinaryTruncationFieldRepo {
    @Resource
    private BinaryTruncationFieldMapper binaryTruncationFieldMapper;

    @Override
    public List<Map<String, Object>> listMap(Set<String> columns, String tableName) {
        return binaryTruncationFieldMapper.listTableColumnSchema(tableName);
    }

    @Override
    public List<TableColumnSchema> listTableColumnSchema(String tableName) {
        return  binaryTruncationFieldMapper.selectListByQuery(new QueryWrapper() {
            private static final long serialVersionUID = 4141665491894173110L;
            {
            eq("table_name", tableName);
        }});
    }
}
