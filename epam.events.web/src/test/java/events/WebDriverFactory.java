package events;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverFactory {
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);
    public enum WebDriverName {
        CHROME,
        FIREFOX,
        REMOTE
    }

    public static WebDriver create(String webDriverName, String options) {
        WebDriverName driverName = WebDriverName.valueOf(webDriverName.toUpperCase());
        WebDriver driver = null;
        String[] browserOptions = new String[0];
        if (options != null)  browserOptions = options.split(" ");

        switch (driverName) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(browserOptions);
                driver = new ChromeDriver(chromeOptions);
                logger.info("ChromeDriver is started");
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(browserOptions);
                driver = new FirefoxDriver(firefoxOptions);
                logger.info("FirefoxDriver is started");
                break;
            case REMOTE:
                ChromeOptions remoteChromeOptions = new ChromeOptions();
                remoteChromeOptions.addArguments(browserOptions);
                remoteChromeOptions.setCapability("user-agent", "chrome");
                remoteChromeOptions.setCapability("version", "92.0.4515");
                remoteChromeOptions.setCapability("platform", Platform.WIN10);
                try {
                    driver = new RemoteWebDriver(new URL("http://192.168.88.250:4444/wd/hub"), remoteChromeOptions);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                break;
            default:
                logger.error("{} WebDriver isn't implement", driverName);
        }

        return driver;
    }
}
