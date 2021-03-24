package pageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;

public class MainPage extends BasePage{

    public MainPage(WebDriver driver, ExtentTest extentTest){
        super(driver, extentTest);
    }

    @Override
    protected boolean isAt() {
        return true;
    }
}