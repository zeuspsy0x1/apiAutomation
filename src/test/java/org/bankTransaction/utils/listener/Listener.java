package org.bankTransaction.utils.listener;

import org.bankTransaction.reporting.Reporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * A class that prints messages in the console when a test starts, passes and fails.
 */
public class Listener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Reporter.info(" [[[* Test: " + result.getName() + " starting *]]] ");
        System.out.println();
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.info("Test: " + result.getName() + " [[[* PASSED *]]] ");
        System.out.println();
    }
    @Override
    public void onTestFailure(ITestResult result) {
        Reporter.error("Test: " + result.getName() + " [[[* FAILED *]]] ");
        System.out.println();
    }
    @Override
    public void onTestSkipped(ITestResult iTestResult) {
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }
    @Override
    public void onStart(ITestContext iTestContext) {
    }
    @Override
    public void onFinish(ITestContext iTestContext) {
    }
}
