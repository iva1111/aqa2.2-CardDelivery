package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        }


    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        Configuration.browser = "chrome";
        open("http://localhost:9999");
    }

    @Test
    public void shouldCardDeliveryWithCssSelectors() {
        $("[data-test-id=city] .input__control").setValue("Пермь");
        LocalDate today = LocalDate.now();
        LocalDate dayOfMeeting = today.plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedDate = dayOfMeeting.format(formatter);
        $("[data-test-id=date] .input__control").sendKeys(Keys.CONTROL + "A");
        $("[data-test-id=date] .input__control").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] .input__control").setValue(formattedDate);
        $("[data-test-id=name] .input__control").setValue("Иванова Анна");
        $("[data-test-id=phone] .input__control").setValue("+79021111111");
        $("div form fieldset label").click();
        $(Selectors.byText("Забронировать")).click();
        $(Selectors.withText("Успешно")).shouldBe(visible, Duration.ofSeconds(15));
        $("div.notification__content").shouldHave(exactText("Встреча успешно забронирована на " + formattedDate));

    }
}