package com.qa.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.utils.EmailUtils;
import com.qa.utils.ExtentReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyListeners implements ITestListener {
    private ExtentReports extentReport;
    private ExtentTest extentTest;
    private String reportTitle = "Automation Test Report"; // Default report title
    
    // Fetch SMTP_USERNAME and SMTP_PASSWORD from environment variables
    private final String smtpUsername = System.getenv("SMTP_USERNAME");
    private final String smtpPassword = System.getenv("SMTP_PASSWORD");

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
        String githubPagesURL = "https://sirisha-vemparala.github.io/test4-reports/test-output/ExtentReports";

        // URL of the report
        String reportURL = githubPagesURL + "/extentReport.html";

        // Send email with the report URL to multiple recipients
        String[] recipients = {"sirishavemparala12@gmail.com", "prathyushavemparala335@gmail.com"}; // Replace with actual recipient emails
        sendEmailWithReportURL(reportURL, timeStamp, recipients);
    }

    private void sendEmailWithReportURL(String reportURL, String timeStamp, String[] recipients) {
        String subject = "Automation Test Report";
        String body = "Hello,\n\nPlease find the " + reportTitle + " generated at " + timeStamp + " at:\n" + reportURL + "\n\nRegards,\nYour Automation Team";

        // Check if SMTP credentials are available
        if (smtpUsername != null && smtpPassword != null) {
            // Call EmailUtils to send email with the report URL and SMTP credentials
            EmailUtils.sendEmailWithReportURL(subject, body, smtpUsername, smtpPassword, recipients);
        } else {
            System.out.println("SMTP credentials (SMTP_USERNAME and SMTP_PASSWORD) are not set in environment variables.");
        }
    }
}
