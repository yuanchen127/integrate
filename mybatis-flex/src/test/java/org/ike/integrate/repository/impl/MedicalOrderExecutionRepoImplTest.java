package org.ike.integrate.repository.impl;


import org.ike.integrate.entity.MedicalOrderExecution;
import org.ike.integrate.mapper.MedicalOrderExecutionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class MedicalOrderExecutionRepoImplTest {

    @Mock
    private MedicalOrderExecutionMapper medicalOrderExecutionMapper;

    @InjectMocks
    private MedicalOrderExecutionRepoImpl medicalOrderExecutionRepo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveBatchMedicalOrderExecution_InsertBatchReturnsPositiveNumber_ReturnsTrue() {
        // 准备
        List<MedicalOrderExecution> list = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            MedicalOrderExecution var = new MedicalOrderExecution();
            var.setId(i);
            list.add(var);
        }
        when(medicalOrderExecutionMapper.insertBatch(list)).thenReturn(2000);

        boolean result = medicalOrderExecutionRepo.saveBatchMedicalOrderExecution();

        assertTrue(result);
    }

    @Test
    public void saveBatchMedicalOrderExecution_InsertBatchReturnsZeroOrNegativeNumber_ReturnsFalse() {
        // 准备
        List<MedicalOrderExecution> list = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            MedicalOrderExecution var = new MedicalOrderExecution();
            var.setId(i);
            list.add(var);
        }
        when(medicalOrderExecutionMapper.insertBatch(list)).thenReturn(0);

        boolean result = medicalOrderExecutionRepo.saveBatchMedicalOrderExecution();

        assertFalse(result);
    }
}
