package org.ike.integrate.config;

import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisFlexCustomConfiguration implements MyBatisFlexCustomizer {
    @Override
    public void customize(FlexGlobalConfig globalConfig) {

        IntegrateMessageFactory creator = new IntegrateMessageFactory();

        IntegrateMessageReporter reporter = new IntegrateMessageReporter();

        AuditManager.setAuditEnable(true);
        AuditManager.setMessageFactory(creator);
        AuditManager.setMessageReporter(reporter);


    }
}
