package org.bankTransaction.tests;

import org.bankTransaction.reporting.Reporter;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.bankTransaction.utils.tests.BaseTest;

/**
 * @author Zeus Vargas
 * A class that contains the test method <updateAccountNumberTest> and is executed from the Suite.xml file.
 */
public class UpdateAccountNumberTest extends BaseTest {

    private int transactionId = 1;

    /**
     * It passes de <transactionId> of the transaction that needs to change its accountNumber to a method that
     * creates a fake accountNumber and then makes a put request to the api to edit that information.
     */
    @Test
    public void updateAccountNumberTest() {
        Reporter.info("Sending the new accountNumber to the bank transaction number " + transactionId + " and checking if it was updated.");
        Assert.assertEquals(updateAccountNumber(transactionId), 200, "The accountNumber didn't change.");

    }
}








