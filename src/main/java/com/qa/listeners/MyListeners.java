package com.qa.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.utils.EmailUtils;
import com.qa.utils.ExtentReporter;

public class MyListeners extends BaseTest implements ITestListener {
    private ExtentReports extentReport;
    private ExtentTest extentTest;
    private String reportFileName;

    @Override
    public void onStart(ITestContext context) {
        extentReport = ExtentReporter.generateExtentReport();
        reportFileName = ExtentReporter.getReportFileName();
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentTest = extentReport.createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.log(Status.PASS, result.getName() + " executed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.log(Status.FAIL, result.getName() + " failed");
        try {
            captureScreenshot(result.getMethod().getMethodName());
            extentTest.addScreenCaptureFromPath(".\\screen\\" + result.getMethod().getMethodName() + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.log(Status.SKIP, result.getName() + " skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();
        sendReportByEmail();
    }

    private void sendReportByEmail() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String githubRepoUrl = "https://sirisha-vemparala.github.io/test4-reports/reports/";
        String reportURL = githubRepoUrl + reportFileName;

        String[] recipients = {"sirishavemparala12@gmail.com"};
        String subject = "Automation Test Report";
        String body = "Hello,\n\nPlease find the Automation Test Report generated at " + timeStamp + " at:\n" + reportURL + "\n\nRegards,\nYour Automation Team";

        String smtpUsername = System.getenv("SMTP_USERNAME");
        String smtpPassword = System.getenv("SMTP_PASSWORD");

        if (smtpUsername != null && smtpPassword != null) {
            try {
                EmailUtils.sendEmailWithReportURL(subject, body, smtpUsername, smtpPassword, recipients);
                System.out.println("Email sent successfully to " + String.join(", ", recipients));
            } catch (Exception e) {
                System.err.println("Failed to send email: " + e.getMessage());
            }
        } else {
            System.out.println("SMTP credentials are not set.");
        }
    }
}
