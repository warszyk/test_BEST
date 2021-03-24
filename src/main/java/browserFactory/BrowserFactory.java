package browserFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BrowserFactory {

    protected WebDriver driver;

    @BeforeTest
    public void setUpBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\lib\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterTest
    public void tearDown(){
        if (driver != null)
            driver.quit();
    }

    public void openPage(String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }
}