package pageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public abstract class BasePage {

    protected WebDriver driver;
    protected ExtentTest extentTest;

    public BasePage(WebDriver driver, ExtentTest extentTest) {
        this.driver = driver;
        this.extentTest = extentTest;
        PageFactory.initElements(this.driver, this);
        Assert.assertTrue(isAt());
        extentTest.log(Status.PASS, "'" + this.getClass().getSimpleName() + "' is open");
    }

    protected abstract boolean isAt();

    public void waitForVisibilityOf(WebElement element) {
        new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element, String elementDescription) {
        extentTest.log(Status.INFO,"Click " + elementDescription);
        element.click();
        extentTest.log(Status.PASS,"Element was clicked");
    }

    public void sendText(WebElement element, String text, String elementDescription) {
        extentTest.log(Status.INFO,"Enter text '" + text + "' to " + elementDescription);
        element.sendKeys(text);
        extentTest.log(Status.PASS,"Text was entered");
    }

    public void waitUntilElementIsNotPresent(WebElement element, String elementDescription){
        extentTest.log(Status.INFO,"Waiting until element: " + elementDescription + " is not present on the page.");
        new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return !element.isDisplayed();
                } catch (NoSuchElementException | StaleElementReferenceException e) {
                    return true;
                }
            }
        });
        extentTest.log(Status.PASS,"Element is not present on the page.");
    }
}