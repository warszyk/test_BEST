package apiTC;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import logging.ExtentManager;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestBaseAPI {

    protected ExtentReports extentReports;
    protected ExtentTest testLogger;

    @Parameters({"baseUri", "basePath"})
    @BeforeSuite
    public void setupConfiguration(String baseUri, String basePath) {
        RestAssured.baseURI = baseUri;
        RestAssured.basePath = basePath;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @BeforeSuite
    public void prepareHtmlReportFile() {
        extentReports = ExtentManager.createExtentReportFile("RegressionApiTests");
    }

    @AfterMethod(alwaysRun = true)
    public void onFailure(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE) {
            testLogger.log(Status.FAIL, result.getThrowable().toString());
        }
    }

    @AfterSuite
    public void closeExtentManager(){
        ExtentManager.closeReport(extentReports);
    }
}