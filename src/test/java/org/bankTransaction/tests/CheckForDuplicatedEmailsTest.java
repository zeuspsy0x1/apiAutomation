package org.bankTransaction.tests;

import org.bankTransaction.reporting.Reporter;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.bankTransaction.utils.tests.BaseTest;

/**
 * @author Zeus Vargas
 * A class that contains the test method <checkForDuplicatedEmailsTest> and is executed from the Suite.xml file.
 */
public class CheckForDuplicatedEmailsTest extends BaseTest {

    /**
     * Makes a get request to fetch all the <BankTransactionData> objects from the api and then checks if
     * there are duplicated emails in the response or not.
     */
    @Test
    public void checkForDuplicatedEmailsTest() {
        Reporter.info("Checking for duplicated emails in the available transactions in the endpoint...");
        Assert.assertTrue(checkForDuplicatedEmails(), "There are duplicated emails in the endpoint.");
    }

}
