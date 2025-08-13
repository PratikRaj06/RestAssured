package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReport.html");
            spark.config().setReportName("API Automation Report");
            spark.config().setDocumentTitle("REST Assured Test Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester 1", "Pratik Raj");
            extent.setSystemInfo("Tester 2", "Tunir Chakraborty");
            extent.setSystemInfo("Tester 3", "Bapi Paswan");
            extent.setSystemInfo("Tester 4", "Sourin Dutta");
            extent.setSystemInfo("Tester 5", "Madhusudan Roy");
        }
        return extent;
    }
}
