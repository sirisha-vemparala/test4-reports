package com.qa.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.utils.EmailUtils;
import com.qa.utils.ExtentReporter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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

    private void uploadReportToGitHub(String filePath, String fileName) {
        try {
            File file = new File(filePath);
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String encodedContent = Base64.getEncoder().encodeToString(fileContent);

            String url = "https://api.github.com/repos/" + repoOwner + "/" + repoName + "/contents/" + fileName;
            String jsonBody = String.format("{\"message\": \"Add report file %s\", \"content\": \"%s\"}", fileName, encodedContent);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost uploadFile = new HttpPost(url);

            uploadFile.setHeader("Authorization", "token " + githubToken);
            uploadFile.setHeader("Content-Type", "application/json");
            uploadFile.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                String responseString = EntityUtils.toString(response.getEntity());

                if (response.getStatusLine().getStatusCode() == 201) {
                    System.out.println("Report uploaded successfully: " + responseString);
                } else {
                    System.err.println("Failed to upload report: " + responseString);
                }
            }

            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
