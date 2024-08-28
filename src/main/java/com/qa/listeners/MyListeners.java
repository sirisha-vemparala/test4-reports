package com.qa.listeners;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private final String reportTitle = "Automation Test Report";

    private final String smtpUsername = System.getenv("SMTP_USERNAME");
    private final String smtpPassword = System.getenv("SMTP_PASSWORD");
    private final String githubToken = System.getenv("GITHUB_TOKEN");
    private final String repoOwner = "sirisha-vemparala";
    private final String repoName = "test4-reports";
    private final String githubPagesBaseUrl = "https://sirisha-vemparala.github.io/test4-reports";

    private String reportFilePath;
    private String reportFileName;

    @Override
    public void onStart(ITestContext context) {
        extentReport = ExtentReporter.getExtentReport();
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

        // Retrieve the generated report path from ExtentReports
        reportFilePath = ExtentReporter.getReportFilePath();
        reportFileName = reportFilePath.substring(reportFilePath.lastIndexOf("/") + 1);

        uploadReportToGitHub(reportFilePath, reportFileName);

        // Update reportURL to GitHub Pages URL
        String reportURL = githubPagesBaseUrl + "/reports/" + reportFileName;

        String[] recipients = {"sirishavemparala12@gmail.com"};
        sendEmailWithReportURL(reportURL, new Date(), recipients);
    }

    private void sendEmailWithReportURL(String reportURL, Date timeStamp, String[] recipients) {
        String subject = "Automation Test Report";
        String body = "Hello MyPursu Team,\n\nPlease find the " + reportTitle + " generated at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeStamp) + " at:\n" + reportURL + "\n\nRegards,\nYour Automation Team";

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

    private void uploadReportToGitHub(String reportFilePath, String reportFileName) {
        if (githubToken != null) {
            try {
                byte[] reportBytes = Files.readAllBytes(Paths.get(reportFilePath));
                String encodedReport = Base64.getEncoder().encodeToString(reportBytes);
                String githubApiUrl = "https://api.github.com/repos/" + repoOwner + "/" + repoName + "/contents/reports/" + reportFileName;

                Map<String, String> jsonPayloadMap = new HashMap<>();
                jsonPayloadMap.put("message", "Upload test report");
                jsonPayloadMap.put("content", encodedReport);

                String jsonPayload = new com.google.gson.Gson().toJson(jsonPayloadMap);

                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new java.net.URL(githubApiUrl).openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Authorization", "Bearer " + githubToken);
                connection.setRequestProperty("Content-Type", "application/json");
                try (java.io.OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonPayload.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = connection.getResponseCode();
                if (responseCode == 201 || responseCode == 200) {
                    System.out.println("Report uploaded successfully to GitHub.");
                } else {
                    System.err.println("Failed to upload report to GitHub. Response code: " + responseCode);
                }
            } catch (IOException e) {
                System.err.println("Failed to upload report to GitHub: " + e.getMessage());
            }
        } else {
            System.out.println("GitHub token (GITHUB_TOKEN) is not set in environment variables.");
        }
    }
}