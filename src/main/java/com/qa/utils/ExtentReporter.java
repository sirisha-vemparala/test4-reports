package com.qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {

    private static ExtentReports extentReport;

    public static ExtentReports generateExtentReport() {
        if (extentReport == null) {
            
            String reportPath = System.getProperty("user.dir") + "/reports/index.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

           
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Test Report");
            sparkReporter.config().setTheme(Theme.DARK);

            extentReport = new ExtentReports();
            extentReport.attachReporter(sparkReporter);

            
            extentReport.setSystemInfo("Operating System", System.getProperty("os.name"));
            extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReport.setSystemInfo("User Name", System.getProperty("user.name"));
            extentReport.setSystemInfo("Environment", "QA");
            extentReport.setSystemInfo("Browser", "Chrome");
        }
        return extentReport;
    }
}
