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

public class MyListeners extends BaseTest implements ITestListener {
    private ExtentReports extentReport;
    private ExtentTest extentTest;
    private final String reportTitle = "Automation Test Report";

    private final String smtpUsername = System.getenv("SMTP_USERNAME");
    private final String smtpPassword = System.getenv("SMTP_PASSWORD");

    @Override
    public void onStart(ITestContext context) {
        extentReport = ExtentReporter.getExtentReport(); // Ensure the method name is correct
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getName();
        extentTest = extentReport.createTest(testName);
        extentTest.log(Status.INFO, testName + " started executing");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName();
        extentTest.log(Status.PASS, testName + " executed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        Throwable throwable = result.getThrowable();

        extentTest.log(Status.FAIL, testName + " got failed");
        extentTest.log(Status.FAIL, throwable);

        try {
            failed(result.getMethod().getMethodName());
        } catch (Exception e) {
            extentTest.log(Status.WARNING, "Error in capturing failure details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getName();
        extentTest.log(Status.SKIP, testName + " got skipped");
        extentTest.log(Status.SKIP, result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        String githubPagesURL = "https://sirisha-vemparala.github.io/test4-reports/reports";
        String reportURL = githubPagesURL + "/index.html";

        String[] recipients = {"sirishavemparala12@gmail.com", "prathyusha@keyutech.com", "geetha@keyutech.com", "mounika11195@gmail.com"};
        sendEmailWithReportURL(reportURL, timeStamp, recipients);
    }

    private void sendEmailWithReportURL(String reportURL, String timeStamp, String[] recipients) {
        String subject = "Automation Test Report";
        String body = "Hello MyPursu Team,\n\nPlease find the " + reportTitle + " generated at " + timeStamp + " at:\n" + reportURL + "\n\nRegards,\nYour Automation Team";

        if (smtpUsername != null && smtpPassword != null) {
            try {
                EmailUtils.sendEmailWithReportURL(subject, body, smtpUsername, smtpPassword, recipients);
                System.out.println("Email sent successfully to " + String.join(", ", recipients));
            } catch (Exception e) {
                System.err.println("Failed to send email: " + e.getMessage());
            }
        } else {
            System.out.println("SMTP credentials (SMTP_USERNAME and SMTP_PASSWORD) are not set in environment variables.");
        }
    }
}
