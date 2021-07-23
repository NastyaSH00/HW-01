import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class TestDriverConfiguration {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(TestDriverConfiguration.class);
    String browser = System.getProperty("browser", "chrome");
    String strategy = System.getProperty("strategy", "normal");

    @BeforeEach
    public void setUp() {
        logger.info("Браузер - " + browser);
        driver = WebDriverFactory.getDriver(browser, strategy);
        logger.info("Драйвер стартовал!");
    }

    @Test
    public void openPage() {
        driver.get("https://www.dns-shop.ru/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String title = driver.getTitle();
        logger.info("Заголовок: " + title);
        String currentUrl = driver.getCurrentUrl();
        logger.info("Текущий url: " + currentUrl);

        //поиск элементов и нажания на кнопки/ссылки
        String buttonOk = "/html/body/header/div[2]/div/ul[1]/li[1]/div/div[2]/a[1]";
        WebElement elementButtonOk = driver.findElement(By.xpath(buttonOk));
        logger.info("Нажата кнопка: \"" + elementButtonOk.getText() + "\"");
        elementButtonOk.click();
        String linkHouseholdAppliances = "//*[@id=\"homepage-desktop-menu-wrap\"]/div/div[1]/div/a";
        WebElement elementlinkHouseholdAppliances = driver.findElement(By.xpath(linkHouseholdAppliances));
        logger.info("Нажата кнопка: \"" + elementlinkHouseholdAppliances.getText() + "\"");
        elementlinkHouseholdAppliances.click();


        // поиск и вывод категорий в логи
        logger.info("Список категорий:");
        WebElement allElementsCategories = driver.findElement(By.xpath("//div[@class='subcategory']"));
        List<WebElement> elementsCategories = allElementsCategories.findElements(By.className("subcategory__title"));
        for(int i = 0; i < elementsCategories.size(); i++){
            logger.info(elementsCategories.get(i).getText());
        }

        //ожидание
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //вывод куки
        logger.info("Куки:");
        Set<Cookie> cookies = driver.manage().getCookies();
        for(Cookie cookie : cookies) {
            logger.info(String.format("Domain: %s", cookie.getDomain()));
            logger.info(String.format("Expiry: %s", cookie.getExpiry()));
            logger.info(String.format("Name: %s", cookie.getName()));
            logger.info(String.format("Path: %s", cookie.getPath()));
            logger.info(String.format("Value: %s", cookie.getValue()));
            logger.info("--------------------------------------");
        }
    }


    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен!");
        }
    }
}