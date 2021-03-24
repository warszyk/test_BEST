package uiTC;

import com.aventstack.extentreports.Status;
import logging.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.*;


public class TC_1_2_Test extends TestBaseUI {

    @BeforeTest
    public void setUpTestReporter(){
        testLogger = ExtentManager.startTest(extentReports,"TC.1.2 – Zakończenie procesu wysyłki masowej dokumentów");
    }

    @Parameters({"loginResource", "strategyProcessResource"})
    @Test
    public void endProcessOfDocumentationSending(String loginResource, String strategyProcessResource) {
        openPage(baseURL + loginResource);

        LoginPage loginPage = new LoginPage(driver, testLogger);
        loginPage.logIn(login, password);

        openPage(baseURL + strategyProcessResource);
        StrategyProcessPage strategyProcessPage = new StrategyProcessPage(driver, testLogger);
        Assert.assertTrue(strategyProcessPage.isAtLeastOnePackage(), "There is no packages on the page.");

        StrategyProcessPackagePage strategyProcessPackagePage = strategyProcessPage.chooseFirstPackage();
        strategyProcessPackagePage.checkAllRecords();
        strategyProcessPackagePage.clickConfirmActionBtn();
        Assert.assertTrue(strategyProcessPackagePage.waitUntilStrategyProcessPackagePageIsNotDisplayed(), "Packages are no longer displayed on the page.");
        testLogger.log(Status.PASS, "Packages have been confirmed correctly.");
    }
}