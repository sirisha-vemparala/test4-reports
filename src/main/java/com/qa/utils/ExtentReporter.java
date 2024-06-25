package com.qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {

    private static ExtentReports extentReport;

    // Synchronized method to ensure thread safety
    public synchronized static ExtentReports getExtentReport() {
        if (extentReport == null) {
            // Path to the report file
            String reportPath = System.getProperty("user.dir") + "/reports/index.html";

            // Create an instance of ExtentSparkReporter with the report path
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            // Set the configuration for the report
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setReportName("Mypursu Automation Test Report");
            sparkReporter.config().setDocumentTitle("Mypursu Test Report");
            sparkReporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
            sparkReporter.config().setCss(".r-img { width: 30px; height: 30px; }");

            // Create an instance of ExtentReports
            extentReport = new ExtentReports();
            // Attach the reporter to the ExtentReports instance
            extentReport.attachReporter(sparkReporter);

            // Set system info properties
            extentReport.setSystemInfo("Operating System", System.getProperty("os.name"));
            extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReport.setSystemInfo("User Name", System.getProperty("user.name"));
            extentReport.setSystemInfo("Environment", "QA");
            extentReport.setSystemInfo("Browser", "Chrome");
        }
        return extentReport;
    }
}
