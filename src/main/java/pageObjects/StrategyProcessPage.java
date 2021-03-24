package pageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class StrategyProcessPage extends BasePage{

    @FindBy(xpath="//tbody[@id='strategyPackagesForm:packageListTable_data']/tr[@role='row'][1]")
    WebElement firstPackage;

    public StrategyProcessPage(WebDriver driver, ExtentTest extentTest){
        super(driver, extentTest);
    }

    @Override
    protected boolean isAt() {
        Assert.assertTrue(driver.getCurrentUrl().contains("strategyProcess"),"Page has wrong URL address");
        return true;
    }

    public StrategyProcessPackagePage chooseFirstPackage(){
        click(firstPackage, "first package on the list.");
        return new StrategyProcessPackagePage(driver, extentTest);
    }

    public boolean isAtLeastOnePackage(){
        try {
            waitForVisibilityOf(firstPackage);
        } catch (TimeoutException ex){
            return false;
        }
        return true;
    }
}