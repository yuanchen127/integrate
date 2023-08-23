package org.ike.integrate.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

@RunWith(PowerMockRunner.class)
@PrepareForTest(StringUtils.class)
public class StringUtilsTest {

    @Test
    public void test() {
        PowerMockito.mockStatic(StringUtils.class);
        String expected = "abc";
        StringUtils.isEmpty(expected);
        PowerMockito.verifyStatic(StringUtils.class);
        ArgumentCaptor<StringUtils> argumentCaptor = ArgumentCaptor.forClass(StringUtils.class);
        StringUtils.isEmpty(argumentCaptor.capture());
        Assert.assertEquals("not equals", argumentCaptor.getValue(), expected);
    }

    public static void main(String[] args) {
        Calendar inst = Calendar.getInstance();
        inst.setTime(new Date());
//        inst.set(Calendar.HOUR_OF_DAY, 12);
        Date date = new Date(inst.getTimeInMillis());
        System.out.printf("date: ",date);
    }

}
