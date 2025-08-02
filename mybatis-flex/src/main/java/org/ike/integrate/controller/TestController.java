package org.ike.integrate.controller;

import lombok.extern.slf4j.Slf4j;
import org.ike.integrate.common.annotions.UpperCaseValue;
import org.ike.integrate.req.Info;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping ("test")
public class TestController {

    @GetMapping("/param/obj")
    public String testPathParam(Info info) throws Exception {
	    if (null == info || info.existNull()) {
//		    throw new Exception("参数丢失");
		    return info.getName();
	    }
	    log.info(info.toString());
	    return info.getName();
    }

	@PostMapping("param/case")
	public Info testBodyParam(@RequestBody Info info) throws Exception {
		log.debug("info:{}", info);
		return info;
	}

	@GetMapping("param")
	public String testParam(@UpperCaseValue String desc, @RequestParam @UpperCaseValue String name) {
		log.debug("desc:{}", desc);
		return desc + name;
	}

	@GetMapping("param/case/{name}")
	public String testParamUrl(@UpperCaseValue @PathVariable String name) {
		log.debug("name:{}", name);
		//打印name变量的内存地址
		log.debug("name identityHashCode:{}", System.identityHashCode(name));
		return name;
	}


}