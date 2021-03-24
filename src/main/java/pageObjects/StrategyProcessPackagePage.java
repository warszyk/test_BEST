package pageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StrategyProcessPackagePage extends BasePage{

    @FindBy(css="div.ui-chkbox-box.ui-widget.ui-corner-all.ui-state-default")
    WebElement checkAllCheckbox;

    @FindBy(id="packageDetailsForm:confirmProcessBtn")
    WebElement confirmActionBtn;

    public StrategyProcessPackagePage(WebDriver driver, ExtentTest extentTest){
        super(driver, extentTest);
    }

    @Override
    protected boolean isAt() {
        return true;
    }

    public StrategyProcessPackagePage checkAllRecords(){
        waitForVisibilityOf(checkAllCheckbox);
        click(checkAllCheckbox, "check all packages - checkbox");
        return this;
    }

    public void clickConfirmActionBtn(){
        click(checkAllCheckbox, "confirm action - button");
    }

    public boolean waitUntilStrategyProcessPackagePageIsNotDisplayed(){
        try{
            waitUntilElementIsNotPresent(checkAllCheckbox, "check all packages - checkbox");
            waitUntilElementIsNotPresent(confirmActionBtn, "confirm action - button");
        } catch (TimeoutException e){
            return false;
        }
        return true;
    }
}