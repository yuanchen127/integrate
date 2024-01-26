package org.ike.integrate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.common.annotations.RecordLog;
import org.ike.integrate.service.RecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {

//    @RecordLog
    public boolean record() {
        log.info("recorded");
        return false;
    }

    @RecordLog
    @Override
    public boolean record(Map<String, String> param) {
        log.info("trans param");
        return true;
    }

}
