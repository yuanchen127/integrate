package org.ike.integrate.controller;

import org.ike.integrate.slot.common.SlotService;
import org.ike.integrate.mapper.IPushLogMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("record")
public class RecordController {

    @Resource(name = "recordService")
    private SlotService slotService;

    @Resource
    private IPushLogMapper iPushLogMapper;

    @Resource(name = "recordParamWithName")
    private SlotService slotServiceWithName;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public boolean record(@PathVariable("id") int id) {
//        slotService.record();
        slotService.pushData(new HashMap<String, String>(1) {{
            put("INT", String.valueOf(id));
        }});
        return true;
    }

    @PostMapping(value = "name/{id}")
    public boolean recordWithServiceName(@PathVariable("id") int id) {
        slotServiceWithName.pushData(new HashMap<String, String>(){{
            put("count", String.valueOf(id));
            put("serviceName", "true");
        }});
        return false;
    }

    @PostMapping(value = "push/{id}")
    public boolean rePushData(@PathVariable("id") Long id) {


        return false;
    }
}
