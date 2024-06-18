package com.qa.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExtentReporter {

    private static ExtentReports extentReport;

    public static ExtentReports generateExtentReport() {
        if (extentReport == null) {
            // Load properties file
            Properties props = new Properties();
            try {
                FileInputStream configStream = new FileInputStream("report.properties");
                props.load(configStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Create a Spark reporter
            String reportPath = System.getProperty("user.dir") + "/reports/index.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            // Customize the report
            sparkReporter.config().setDocumentTitle(props.getProperty("documentTitle", "Automation Report"));
            sparkReporter.config().setReportName(props.getProperty("reportName", "Test Report"));
            sparkReporter.config().setTheme(Theme.valueOf(props.getProperty("theme", "DARK")));

            extentReport = new ExtentReports();
            extentReport.attachReporter(sparkReporter);

            // Adding system information
            extentReport.setSystemInfo("Operating System", System.getProperty("os.name"));
            extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReport.setSystemInfo("User Name", System.getProperty("user.name"));
            extentReport.setSystemInfo("Environment", props.getProperty("environment", "QA"));
            extentReport.setSystemInfo("Browser", props.getProperty("browser", "Chrome"));
        }
        return extentReport;
    }
}
