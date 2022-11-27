package org.bankTransaction.tests;

import org.bankTransaction.reporting.Reporter;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.bankTransaction.utils.tests.BaseTest;



public class CheckForDuplicatedEmailsTest extends BaseTest {

    @Test
    public void checkForDuplicatedEmailsTest() {
        Reporter.info("Checking for duplicated emails in the available transactions in the endpoint...");
        Assert.assertTrue(checkForDuplicatedEmails(), "There are duplicated emails in the endpoint.");
    }

}
