package com.qa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {

    private static ExtentReports extentReport;
    private static String reportFilePath; // Add this line

    public static ExtentReports getExtentReport() {
        if (extentReport == null) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportFilePath = System.getProperty("user.dir") + "/reports/report_" + timeStamp + ".html"; // Update this line
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setReportName("Mypursu Automation Test Report");
            sparkReporter.config().setDocumentTitle("Mypursu Test Report");
            sparkReporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");

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

    // Add this method to retrieve the report file path
    public static String getReportFilePath() {
        return reportFilePath;
    }
}