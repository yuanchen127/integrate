package org.ike.integrate.controller;

import org.ike.integrate.slot.common.SlotBeanInfo;
import org.ike.integrate.slot.common.SlotContext;
import org.ike.integrate.slot.common.SlotRecordPoint;
import org.ike.integrate.slot.controller.SlotRecordController;
import org.ike.integrate.slot.repository.SlotRecordRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SlotRecordControllerTest {

    @Mock
    private SlotRecordRepo slotRecordRepo;

    @Mock
    private SlotContext slotContext;

    @InjectMocks
    private SlotRecordController slotRecordController;

    private Serializable mockId;
    private SlotRecordPoint mockRecordPoint;
    private SlotBeanInfo mockBeanInfo;

    @BeforeEach
    public void setUp() {
        mockId = "123"; // 假设的ID，根据实际情况可能需要更改
        mockRecordPoint = new SlotRecordPoint();
        mockRecordPoint.setBeanClassName("com.example.MyClass");
        mockRecordPoint.setParam("{\"data\":\"exampleData\"}");

        mockBeanInfo = Mockito.mock(SlotBeanInfo.class);
        try {
            // 假设pushData方法存在，并且接受一个Object类型的参数
            when(mockBeanInfo.getParamClass()).thenReturn((Class) Object.class);
            when(mockBeanInfo.getDeclareClazz().getMethod("pushData", Object.class)).thenReturn(Object.class.getMethod("toString"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        when(slotRecordRepo.getRecord(any(Serializable.class))).thenReturn(mockRecordPoint);
        when(slotContext.getSlotBeanInfo(any(String.class))).thenReturn(mockBeanInfo);
    }

    @Test
    public void testRePushParam() {
        // Arrange: 已经在@BeforeEach中进行了初始化

        // Act
        boolean result = slotRecordController.retry(mockId);

        // Assert
        verify(slotRecordRepo, times(1)).getRecord(mockId); // 确认是否调用了slotRecordRepo的getRecord方法
        verify(slotContext, times(1)).getSlotBeanInfo(mockRecordPoint.getBeanClassName()); // 确认是否调用了slotContext的getSlotBeanInfo方法
        verify(slotRecordRepo, times(1)).deleteRecord(mockId); // 确认是否调用了slotRecordRepo的deleteRecord方法

        // 对于方法是否成功执行的断言，需要根据实际情况来决定断言的内容
        // 例如这里我们断言result为true，因为我们的模拟环境中方法执行是成功的
        assertTrue(result);
    }
}
