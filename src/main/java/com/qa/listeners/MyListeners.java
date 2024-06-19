package com.qa.listeners;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.utils.ExtentReporter;
import com.qa.utils.EmailUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyListeners implements ITestListener {
    ExtentReports extentReport;
    ExtentTest extentTest;

    @Override
    public void onStart(ITestContext context) {
        extentReport = ExtentReporter.generateExtentReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getName();
        extentTest = extentReport.createTest(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName();
        extentTest.log(Status.PASS, testName + " executed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        extentTest.log(Status.FAIL, testName + " got failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getName();
        extentTest.log(Status.SKIP, testName + " got skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();

        // Get the current timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // GitHub Pages URL where reports are hosted
        String githubPagesURL = "https://sirisha-vemparala.github.io/automation-reports";

        // URL of the report
        String reportURL = githubPagesURL + "/extentReport_" + timeStamp + ".html";

        // Send email with the report URL
        sendEmailWithReportURL(reportURL);
    }

    private void sendEmailWithReportURL(String reportURL) {
        String subject = "Automation Test Report";
        String body = "Hello,\n\nPlease find the automation test report at:\n" + reportURL + "\n\nRegards,\nYour Automation Team";

        // Call EmailUtils to send email with the report URL
        EmailUtils.sendEmailWithReportURL(subject, body);
    }
}
