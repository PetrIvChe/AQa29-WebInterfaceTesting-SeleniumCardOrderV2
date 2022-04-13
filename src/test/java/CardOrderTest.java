
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.bonigarcia.wdm.WebDriverManager;


public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpDriver() {
        System.setProperty("webdriver.chrome.driver", "/driver/win/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }


    @BeforeEach
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void shouldSendForm() {
        //driver.get("http://localhost:9999");
        //driver.findElement(By.className("input__control")).sendKeys("Иоган Кристиан");
        //driver.findElement(By.className("input__box")).sendKeys("+79874574582");
//
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иоган Кристиан");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79874574582");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWhenHyphenInName() {
        //driver.get("http://localhost:9999");

//        List<WebElement> textFields = driver.findElements(By.className("input__control"));
//        textFields.get(0).sendKeys("Смирнова-Петрова Анна-Мария");
//        textFields.get(1).sendKeys("+99999999999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Смирнова-Петрова Анна-Мария");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79874574582");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendFormWhenNameIsEmpty() {
        //driver.get("http://localhost:9999");

//        List<WebElement> textFields = driver.findElements(By.className("input__control"));
//        textFields.get(0).sendKeys("");
//        textFields.get(1).sendKeys("+99999999999");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79874574582");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        //  String actual = driver.findElement(By.className("input__sub")).getText();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub ")).getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendAlertMessageWhenNameOnEnglish() {
        //driver.get("http://localhost:9999");

//        List<WebElement> textFields = driver.findElements(By.className("input__control"));
//        textFields.get(0).sendKeys("Smith James");
//        textFields.get(1).sendKeys("+00000000000");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Smith James");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79874574582");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub ")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendAlertMessageWhenPhoneNumberIsEmpty() {
        //driver.get("http://localhost:9999");
//        List<WebElement> textFields = driver.findElements(By.className("input__control"));
//        textFields.get(0).sendKeys("Иванов Сергей");
//        textFields.get(1).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Сергей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub ")).getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendAlertMessageWhenPhoneNumberIsMore12Symbols() {
        //driver.get("http://localhost:9999");
//        List<WebElement> textFields = driver.findElements(By.className("input__control"));
//        textFields.get(0).sendKeys("Иванов Сергей");
//        textFields.get(1).sendKeys("+373777156325");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Сергей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+373777156325");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub ")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendAlertMessageWhenPhoneNumberWithoutPlus() {
        //driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Сергей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("373777156325");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub ")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendAlertMessageWhenPhoneNumberWithPlusInMiddle() {
        //driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Сергей");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("3737+77156325");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub ")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldSendAlertFormWhenNonCheckOnAgreementCheckbox() {
        //driver.get("http://localhost:9999");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Иоган Кристиан");
        textFields.get(1).sendKeys("+79874574582");
        driver.findElement(By.className("checkbox__box")).isSelected();
        driver.findElement(By.tagName("button")).click();
        String actual = driver.getCurrentUrl();
        String expected = "http://localhost:9999/";
        assertEquals(expected, actual);
    }


}
