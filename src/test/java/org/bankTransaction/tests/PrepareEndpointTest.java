package org.bankTransaction.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import org.bankTransaction.reporting.Reporter;
import org.bankTransaction.utils.tests.BaseTest;



public class PrepareEndpointTest extends BaseTest {

    @Test
    public void prepareEndpointTest() {

        Reporter.info("Fetching the Bank Transactions Data endpoint...");

        getAllEndpointTransactions();

        Assert.assertEquals(getStatusCodeOfGettingAllTransactions(), 200, "Fetching was unsuccessful.");
        Reporter.info("There are : " + getAvailableTransactionsInTheEndpoint().size() + " transactions available.");

        checkIfThereAreTransactionsAndDeleteThem();

        Reporter.info("Checking that there are 0 transactions in the endpoint...");
        Assert.assertEquals(getAllEndpointTransactions().size(), 0, "Deleting all transactions was unsuccessful.");

    }
}