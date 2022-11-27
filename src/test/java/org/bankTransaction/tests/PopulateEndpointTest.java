package org.bankTransaction.tests;

import org.bankTransaction.reporting.Reporter;
import org.bankTransaction.utils.tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PopulateEndpointTest extends BaseTest {


    @Test
    public void populateEndpointTest() {
        Reporter.info("Checking that the 10 non duplicated bank transactions were created.");
        Assert.assertEquals(createAndPostFakeTransactions(), 10,"The bank transactions weren't created.");
    }

}
