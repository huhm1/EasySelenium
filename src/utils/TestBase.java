package utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import config.Config;

/**
 * @author haiming
 *
 */
public class TestBase{

    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports      extent;
    public static ExtentTest         report;

    public WebDriver                 driver;

    /**
     * Record test result in test report.
     *
     * @param nameOfTest
     * @param condition
     * @return condition
     */
    public Boolean reportAssert(final String nameOfTest, final Boolean condition){
        if(condition){
            TestBase.report.pass(nameOfTest + " passed!");
        }else{
            TestBase.report.fail(nameOfTest + " failed!");
        }

        return condition;
    }

    /**
     * Save the screenshot of the default WebDriver as file
     *
     * @param driver
     * @return URL HTML code
     *
     */
    public String saveScreenshot(){
        return this.saveScreenshot(this.driver);
    }

    /**
     * Save the screenshot of this WebDriver as a file
     *
     * @param driver
     * @return URL HTML code
     */
    public String saveScreenshot(final WebDriver driver){
        final TakesScreenshot scrShot = ((TakesScreenshot) driver);

        // Call getScreenshotAs method to create image file

        final File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String file = Config.reportFolder + LocalDateTime.now().format(formatter) + ".png";
        final File DestFile = new File(file);

        try{
            FileUtils.copyFile(SrcFile, DestFile);
            file = DestFile.getPath();
        }
        catch(final IOException e){

            e.printStackTrace();
        }

        try{
            TestBase.report.info(" Snapshot below: ").addScreenCaptureFromPath(file);
        }
        catch(final IOException e){

            e.printStackTrace();
        }
        return " <a href='" + file + "' data-featherlight='image'><img class='report-img' width='10%' "
                + "src='file:///" + file + "' /></a>";
    }

    /**
     * Save the screenshot of default WebDriver as Base64 code, No small image
     *
     * @return HTML code
     */
    public String saveBase64Screenshot(){
        return this.saveBase64Screenshot(this.driver, false);
    }

    /**
     * Save the screenshot of this WebDriver as Base64 code, No small image
     *
     * @param driver
     * @return HTML code
     */
    public String saveBase64Screenshot(final WebDriver driver){
        return this.saveBase64Screenshot(driver, false);
    }

    /**
     * Save the screenshot of this WebDriver as Base64 code
     *
     * @param driver
     *
     * @param imagIco
     *            with or without a small image as Ico
     * @return HTML code
     */
    public String saveBase64Screenshot(final WebDriver driver, final Boolean imagIco){
        final TakesScreenshot scrShot = ((TakesScreenshot) driver);

        // Call getScreenshotAs method to create image String

        final String imgSrc = " data:image/png;base64," + scrShot.getScreenshotAs(OutputType.BASE64);

        if(imagIco){
            return " <a href='" + imgSrc + "' data-featherlight='image'><img class='report-img' width='20%' " + "src='"
                    + imgSrc + "' /></a>";
        }else{
            return " <a href='" + imgSrc + "' data-featherlight='image'> Screenshot </a>";
        }
    }

    @BeforeSuite
    public void setUp(){
        if(TestBase.htmlReporter == null){
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            final String file = Config.reportFolder + "Report" + LocalDateTime.now().format(formatter) + ".html";
            TestBase.htmlReporter = new ExtentHtmlReporter(file);
            TestBase.extent = new ExtentReports();
            TestBase.extent.attachReporter(TestBase.htmlReporter);

            TestBase.extent.setSystemInfo("Project", Config.projectName);
            TestBase.extent.setSystemInfo("Host Name", "Test");
            TestBase.extent.setSystemInfo("Environment", "QA");

            TestBase.htmlReporter.config().setDocumentTitle("EXTENT report");
            TestBase.htmlReporter.config().setReportName(Config.projectName + " test report");
            TestBase.htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
            TestBase.htmlReporter.config().setTheme(Theme.STANDARD);

        }
    }

    /**
     * @param method
     */
    @BeforeMethod
    public void beforeMethod(final Method method){
        final String name = this.getClass().getSimpleName() + "." + method.getName();
        TestBase.report = TestBase.extent.createTest(name);
        TLog.pc("**************" + name + " Started***************");
    }

    @AfterMethod
    public void getResult(final ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            TestBase.report.log(
                    Status.FAIL,
                    MarkupHelper
                            .createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
            TestBase.report.fail("Failed!" + this.saveBase64Screenshot());
            TestBase.report.fail(result.getThrowable());
        }else if(result.getStatus() == ITestResult.SUCCESS){
            TestBase.report.log(
                    Status.PASS,
                    MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
        }else{
            TestBase.report.log(
                    Status.SKIP,
                    MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
            TestBase.report.skip(result.getThrowable());
        }
        TestBase.extent.flush();

        TLog.pc("**************" + result.getName() + " Finished***************");

        if(this.driver != null){
            this.driver.quit();
        }
    }

    @AfterSuite
    public void tearDown(){
        TestBase.extent.flush();

        if(this.driver != null){
            this.driver.quit();
        }
    }
}