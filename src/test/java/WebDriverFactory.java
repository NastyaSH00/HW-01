import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;


public class WebDriverFactory {
    private static Logger logger = LogManager.getLogger(WebDriverFactory.class);

    public static WebDriver getDriver(String browserName, String strategy) {
        PageLoadStrategy pageLoadStrategy;

        switch (strategy) {
            case "none":
                pageLoadStrategy = PageLoadStrategy.NONE;
                logger.info("Стратегия - " + strategy);
                break;
            case "eager":
                pageLoadStrategy = PageLoadStrategy.EAGER;
                logger.info("Стратегия - " + strategy);
                break;
            default:
                pageLoadStrategy = PageLoadStrategy.NORMAL;
                logger.info("Стратегия по умолчанию normal");
                break;
        }


        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                logger.info("Драйвер Google Chrome");

                ChromeOptions optionsChrome = new ChromeOptions();
                optionsChrome.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, false);
                optionsChrome.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                optionsChrome.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
                optionsChrome.setAcceptInsecureCerts(false);
                optionsChrome.addArguments("--start-maximized");
                optionsChrome.addArguments("--incognito");
                optionsChrome.setPageLoadStrategy(pageLoadStrategy);

                return new ChromeDriver(optionsChrome);

            case "firefox" :
                WebDriverManager.firefoxdriver().setup();
                logger.info("Драйвер Mozilla Firefox");

                FirefoxOptions optionsFirefox = new FirefoxOptions();
                //optionsFirefox.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                optionsFirefox.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
                optionsFirefox.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, false);
                optionsFirefox.setAcceptInsecureCerts(false);
                optionsFirefox.setPageLoadStrategy(pageLoadStrategy);
                optionsFirefox.addArguments("-private");

              FirefoxDriver firefoxDriver = new FirefoxDriver(optionsFirefox);
              firefoxDriver.manage().window().fullscreen();

               return firefoxDriver;

            default:
                throw new RuntimeException("Incorrect browser name");
        }
    }
}
