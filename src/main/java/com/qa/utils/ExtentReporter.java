package com.qa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {

    private static ExtentReports extentReport;
    private static String reportFileName;

    public static ExtentReports generateExtentReport() {
        if (extentReport == null) {
            reportFileName = "index_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".html";
            String reportPath = System.getProperty("user.dir") + "/reports/" + reportFileName;
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

    public static String getReportFileName() {
        return reportFileName;
    }
}
