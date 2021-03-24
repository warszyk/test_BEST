package pageObjects;

import com.aventstack.extentreports.ExtentTest;
import logging.ExtentManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends BasePage{

    @FindBy(id="LoginForm:username")
    WebElement username;

    @FindBy(id="LoginForm:password")
    WebElement password;

    @FindBy(id="LoginForm:loginCmdLink")
    WebElement loginBtn;

    public LoginPage(WebDriver driver, ExtentTest extentTest){
        super(driver, extentTest);
    }

    @Override
    protected boolean isAt() {
        Assert.assertTrue(driver.getCurrentUrl().contains("noAuth/login"),"Page has wrong URL address");
        return true;
    }

    public MainPage logIn(String login, String pass){
        waitForVisibilityOf(username);
        sendText(username, login,"login");
        sendText(password, pass,"password");
        click(loginBtn, "login button");
        return new MainPage(driver, extentTest);
    }
}