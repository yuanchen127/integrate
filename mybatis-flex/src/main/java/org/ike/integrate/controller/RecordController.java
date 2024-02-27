package org.ike.integrate.controller;

import org.ike.integrate.service.RecordService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("record")
public class RecordController {

    @Resource
    private RecordService recordService;

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public boolean record(@PathVariable("id") int id) {
        recordService.record();
        recordService.record(new HashMap<String, String>(1) {{
            put("INT", String.valueOf(id));
        }});
        return true;
    }
}
