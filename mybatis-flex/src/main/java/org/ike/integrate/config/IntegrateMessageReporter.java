package org.ike.integrate.config;

import com.mybatisflex.core.audit.AuditMessage;
import com.mybatisflex.core.audit.MessageReporter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class IntegrateMessageReporter implements MessageReporter {
    @Override
    public void sendMessages(List<AuditMessage> messages) {
        for (AuditMessage message : messages) {
            log.warn(">>>>>>Sql Audit: " + message.toString());
        }
    }
}
