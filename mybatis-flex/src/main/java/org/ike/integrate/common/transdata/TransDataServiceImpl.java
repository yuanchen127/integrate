package org.ike.integrate.common.transdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransDataServiceImpl implements TransDataService {

    private boolean enable = false;

    @Override
    public <T> boolean trans(AbstractTransParam<T> function) {
        if (!enable) {
            return false;
        }

        T param = function.generateParam();
        log.warn("param:{}", param);

        //todo 数据传输方案实现：MQ或者三方接口调用
        return true;
    }
}
