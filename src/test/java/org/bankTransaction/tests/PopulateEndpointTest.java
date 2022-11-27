package org.bankTransaction.tests;

import org.bankTransaction.reporting.Reporter;
import org.bankTransaction.utils.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Zeus Vargas
 * A class that contains the test method <populateEndpointTest> and is executed from the Suite.xml file.
 */
public class PopulateEndpointTest extends BaseTest {

    /**
     * Uses the POJO class <BankTransactionData> to create fake data objects and post 10 of them to the api as json.
     * It actually creates 11, but only posts 10 because one of them has a duplicated email.
     */
    @Test
    public void populateEndpointTest() {
        Reporter.info("Checking that the 10 non duplicated bank transactions were created.");
        Assert.assertEquals(createAndPostFakeTransactions(), 10,"The bank transactions weren't created.");

    }

}
