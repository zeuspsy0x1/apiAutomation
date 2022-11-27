package org.bankTransaction.tests;

import org.bankTransaction.reporting.Reporter;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.bankTransaction.utils.tests.BaseTest;


public class UpdateAccountNumberTest extends BaseTest {

    private int transactionId = 1;

    @Test
    public void updateAccountNumberTest() {
        Reporter.info("Sending the new accountNumber to the bank transaction number " + transactionId + " and checking if it was updated.");
        Assert.assertEquals(updateAccountNumber(transactionId), 200, "The accountNumber didn't change.");
    }
}








