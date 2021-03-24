package uiTC;

import browserFactory.BrowserFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import logging.ExtentManager;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class TestBaseUI extends BrowserFactory {

    protected static ExtentReports extentReports;
    protected ExtentTest testLogger;

    protected String login;
    protected String password;
    protected String baseURL;

    @Parameters({"login", "password"})
    @BeforeSuite
    public void getCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameters({"baseUrl"})
    @BeforeSuite
    public void getUrl(String baseURL) {
        this.baseURL = baseURL;
    }

    @BeforeSuite
    public void prepareHtmlReportFile() {
        extentReports = ExtentManager.createExtentReportFile("RegressionUiTests");
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