package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testbase.BaseClass;

public class ExtentReportManager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private String repName;

    @Override
    public void onStart(ITestContext testContext) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            repName = "Test-Report-" + timeStamp + ".html";

            // Create reports folder if missing
            Path reportDir = Paths.get(System.getProperty("user.dir"), "reports");
            reportDir.toFile().mkdirs();

            sparkReporter = new ExtentSparkReporter(reportDir.resolve(repName).toString());
            sparkReporter.config().setDocumentTitle("Opencart Automation Report");
            sparkReporter.config().setReportName("Opencart Functional Testing");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // System info
            extent.setSystemInfo("Application", "Opencart");
            extent.setSystemInfo("Module", "Admin");
            extent.setSystemInfo("Sub Module", "Customers");
            extent.setSystemInfo("User Name", System.getProperty("user.name"));
            extent.setSystemInfo("Environment", "QA");

            String os = testContext.getCurrentXmlTest().getParameter("OS");
            extent.setSystemInfo("Operating System", os);

            String browser = testContext.getCurrentXmlTest().getParameter("browser");
            extent.setSystemInfo("Browser", browser);

            List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
            if (!includedGroups.isEmpty()) {
                extent.setSystemInfo("Groups", includedGroups.toString());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName() + " : " + result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, result.getName() + " executed successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, result.getName() + " failed");
        extentTest.get().log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            extentTest.get().addScreenCaptureFromPath(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, result.getName() + " skipped");
        if(result.getThrowable() != null) {
            extentTest.get().log(Status.INFO, result.getThrowable().getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();
        Path reportPath = Paths.get(System.getProperty("user.dir"), "reports", repName);
        File extentReport = reportPath.toFile();
        try {
            if(Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(extentReport.toURI());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onTestFailedWithTimeout(ITestResult result) { onTestFailure(result); }
}


