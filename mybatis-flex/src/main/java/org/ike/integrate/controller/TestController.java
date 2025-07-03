package org.ike.integrate.controller;

import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.req.Info;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping ("test")
public class TestController {

    @GetMapping("/{id}")
    public boolean testPathParam(Info info) throws Exception {
	    if (null == info || info.existNull()) {
//		    throw new Exception("参数丢失");
		    return false;
	    }
	    log.info(info.toString());
	    return true;
    }


}