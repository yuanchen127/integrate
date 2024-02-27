package org.ike.integrate.controller;

import org.ike.integrate.common.transdata.AbstractTransParam;
import org.ike.integrate.common.transdata.TransDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/trans/param")
public class TransParamController {

    @Resource
    private TransDataService transDataService;

    @GetMapping("/str/{param}")
    public boolean transString(@PathVariable("param") String param) {
        int init = 10;

        return transDataService.trans(new AbstractTransParam<Integer>() {
            @Override
            public Integer generateParam() {
                return init + param.length();
            }
        });
    }

}
