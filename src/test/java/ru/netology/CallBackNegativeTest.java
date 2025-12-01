package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallBackNegativeTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
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
        driver = null;
    }

    @Test
    void shouldSubmitNegativeReqWhenNameOnlySpaces() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("                 ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79811234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenNameContainsLatinLetters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Kate Golubeva");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79811234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenNameContainsNumbers() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("111111111111");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79811234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenNameContainsSpecialCharacters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("$#@@#%^^^");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79811234567");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenPhoneOnlySpaces() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Екатерина Голубева");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("         ");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenPhoneMoreTwelveCharacters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Екатерина Голубева");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7111111111111111111111");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenPhoneWithoutPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Екатерина Голубева");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89117115611");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenPhoneWithBracketsAndDashes() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Екатерина Голубева");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7(911)711-56-11");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenPhoneContainsSpecialCharacters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Екатерина Голубева");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("%#$#$#%#%%");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenPhoneLessTwelveCharacters() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Екатерина Голубева");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7911");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldSubmitNegativeReqWhenAgreementNotClicked() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Екатерина Голубева");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79117115611");
        driver.findElement(By.cssSelector("[data-test-id='agreement']"));
        driver.findElement(By.cssSelector("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
}

