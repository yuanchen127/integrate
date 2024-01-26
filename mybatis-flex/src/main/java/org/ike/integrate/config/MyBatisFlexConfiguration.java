package org.ike.integrate.config;

import com.mybatisflex.core.audit.AuditManager;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;

@Configuration
public class MyBatisFlexConfiguration {

    private static final Logger logger = LoggerFactory.getLogger("mybatis-flex-sql");

    public MyBatisFlexConfiguration() {
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector((auditMessage) ->{
            logger.info("{}, {}, {}ms", auditMessage.getQuery(), auditMessage.getQueryParams(), auditMessage.getElapsedTime());
        });
    }

    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties props = new Properties();
        props.put("MySQL", "mysql");
        props.put("PostgreSQL", "pg");
        props.put("Oracle", "oracle");
        props.put("DB2", "d2");
        props.put("SQL Server", "sqlserver");
        databaseIdProvider.setProperties(props);
        return databaseIdProvider;
    }

}
