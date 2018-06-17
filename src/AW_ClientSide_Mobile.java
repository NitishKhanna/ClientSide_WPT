/**
 * Created by Nitish on 6/17/2018.
 */
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AW_ClientSide_Mobile {
    public static final String USERNAME = "nitishkhanna2";
    public static final String AUTOMATE_KEY = "9H1L4dZyyxU9Bqj4Wf6v";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
    public static final int pageTimeOut = 5;
    private static void waitForPageLoad (WebDriver driver, int pageLoadTimeout) {
        driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
    }
    public static void main(String[] args) throws Exception {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browserstack.local", "true");

        caps.setCapability("browserName", "android");
        caps.setCapability("device", "Samsung Galaxy S8");
        caps.setCapability("realMobile", "true");
        caps.setCapability("os_version", "7.0");

//        caps.setCapability("browserName", "iPhone");
//        caps.setCapability("device", "iPhone 7 Plus");
//        caps.setCapability("realMobile", "true");
//        caps.setCapability("os_version", "10.3");

        WebDriver driver = new RemoteWebDriver(new URL(URL), caps);
        driver.get("https://www.arrow.com/");
        waitForPageLoad(driver, pageTimeOut);

        ClientSide_Metrics cw = new ClientSide_Metrics(driver);
        cw.calculateAllMetrices();

        WebElement searchTextBox = driver.findElement(By.name("q"));
        searchTextBox.sendKeys("Thyristor");
        searchTextBox.submit();
        waitForPageLoad(driver, pageTimeOut);

        cw.calculateAllMetrices();

        int searchResultsNumber = driver.findElements(By.cssSelector("span.SearchResults-productName")).size();
        driver.findElements(By.cssSelector("span.SearchResults-productName")).get(searchResultsNumber-1).click();
        waitForPageLoad(driver, pageTimeOut);

        cw.calculateAllMetrices();
        driver.quit();

    }
}

