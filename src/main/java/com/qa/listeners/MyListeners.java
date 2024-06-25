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
    	ITestListener.super.onTestFailure(result);
   		String testName = result.getName();
   		try {
			failed(result.getMethod().getMethodName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());

        String githubPagesURL = "https://sirisha-vemparala.github.io/test4-reports/reports";
        String reportURL = githubPagesURL + "/index.html";

        String[] recipients = {"sirishavemparala12@gmail.com","prathyusha@keyutech.com","bharath@keyutech.com","geetha@keyutech.com","mounika11195@gmail.com"};
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