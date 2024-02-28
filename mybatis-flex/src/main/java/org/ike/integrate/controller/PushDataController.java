package org.ike.integrate.controller;

import org.ike.integrate.service.PushDataService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("push")
public class PushDataController {

    @Resource
    private PushDataService pushDataService;

    @PostMapping("/{id}")
    public boolean push(@PathVariable("id") Long id) {
        return pushDataService.pushByLog(id);
    }
}
