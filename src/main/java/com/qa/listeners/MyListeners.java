package com.qa.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.utils.ExtentReporter;
import com.qa.utils.EmailUtils;

import java.io.File;
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

        // Generate Excel file with timestamp
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String excelFileName = "ExtentReports_" + timeStamp + ".xlsx";
        String excelFilePath = "C:/Users/Hello/eclipse-workspace/test/test-output/" + excelFileName;

        // Assuming the report is hosted at this URL
        String reportURL = "file:///C:/Users/Hello/eclipse-workspace/test/test-output/ExtentReports/extentReport.html"; // Adjust as needed
        
        // Create Excel file
        createExcelFile(excelFilePath, reportURL);

        // Send email with the Excel attachment
        sendEmailWithExcelAttachment(excelFilePath);
    }

    private void createExcelFile(String filePath, String reportURL) {
        // You can use Apache POI or any other library to create Excel files
        // Here's a simple example using Apache POI to create an Excel file
        // Adjust this code based on your requirements
        
        // TODO: Implement logic to create Excel file
        // For now, let's just create an empty file
        try {
            File file = new File(filePath);
            boolean created = file.createNewFile();
            if (created) {
                System.out.println("Excel file created: " + filePath);
            } else {
                System.out.println("Failed to create Excel file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEmailWithExcelAttachment(String filePath) {
        String subject = "Automation Test Report";
        String body = "Hello,\n\nPlease find the automation test report attached.";

        // Call EmailUtils to send email with attachment
        EmailUtils.sendEmailWithAttachment(subject, body, filePath);
    }
}

