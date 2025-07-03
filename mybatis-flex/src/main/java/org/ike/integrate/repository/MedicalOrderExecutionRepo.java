package org.ike.integrate.repository;

public interface MedicalOrderExecutionRepo {

	/**
	 * 批量保存MedicalOrderExecution列表
	 */
	boolean saveBatchMedicalOrderExecution();

	boolean saveBatchWithJDBC();

	boolean saveBatchWithCOPY();

	//删除所有数据
	boolean deleteAll();
}
