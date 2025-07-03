package org.ike.integrate.controller;

import org.ike.integrate.entity.ModelCheckInfo;
import org.ike.integrate.req.ModelCheckReq;
import org.ike.integrate.service.CheckModelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController()
@RequestMapping("/model/check")
public class CheckModelController {

	@Resource
	CheckModelService checkModelService;

	@PostMapping("list")
	public List<ModelCheckInfo> listCheckInfo(@RequestBody ModelCheckReq req) {
		return checkModelService.getModelAnnotationValue(req.getPath());
	}
}
