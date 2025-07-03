package org.ike.integrate.controller;

import org.ike.integrate.repository.MedicalOrderExecutionRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/medical/order")
public class MedicalOrderExecutionController {

	@Resource
	private MedicalOrderExecutionRepo medicalOrderExecutionRepo;

	@PostMapping("save/batch")
	public boolean saveBatchMedicalOrderExecution() {
		//删除数据
		medicalOrderExecutionRepo.deleteAll();
		return medicalOrderExecutionRepo.saveBatchMedicalOrderExecution();
	}

	@PostMapping("save/batch/jdbc")
	public boolean saveBatchWithJDBC() {
		//删除数据
		medicalOrderExecutionRepo.deleteAll();
		return medicalOrderExecutionRepo.saveBatchWithJDBC();
	}

	@PostMapping("save/batch/copy")
	public boolean saveBatchWithCOPY() {
		//删除数据
		medicalOrderExecutionRepo.deleteAll();
		return medicalOrderExecutionRepo.saveBatchWithCOPY();
	}

}
