package org.ike.integrate.controller;

import org.ike.integrate.entity.Account;
import org.ike.integrate.service.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertLargeWithBulk() throws Exception {
        // Given
        List<Account> mockAccounts = Arrays.asList(
                new Account("1", "user1", null, true, new BigDecimal("1.00")),
                new Account("2", "user2", null, false, new BigDecimal("2.00"))
        );

        when(accountService.insertBatchWithBulk(anyList())).thenReturn(true);

        // When
        boolean result = accountController.insertLargeWithBulk(2);

        // Then
        verify(accountService, times(1)).insertBatchWithBulk(anyList());
        Assert.assertTrue("The result should be true when the operation is successful", result);
    }

    @Test(expected = Exception.class)
    public void testInsertLargeWithBulkException() throws Exception {
        // Given
        when(accountService.insertBatchWithBulk(anyList())).thenThrow(new RuntimeException("Database error"));

        // When
        accountController.insertLargeWithBulk(2);

        // Then
        // Expected exception
    }

    @Test
    public void testInsertLargeWithBulkWithZeroCount() throws Exception {
        // Given
        when(accountService.insertBatchWithBulk(anyList())).thenReturn(true);

        // When
        boolean result = accountController.insertLargeWithBulk(0);

        // Then
        verify(accountService, never()).insertBatchWithBulk(anyList());
        Assert.assertTrue("The result should be true when no accounts are inserted", result);
    }
}
