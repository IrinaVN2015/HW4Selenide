package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;

public class OrderingCardDeliveryTest {


    private String getLocalDate(int days, String pattern) {
        LocalDate today = LocalDate.now();
        LocalDate futureDay = today.plusDays(days);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formatDate = futureDay.format(formatter);
        return formatDate;
    }


    @BeforeEach
    void setUp() {

        open("http://localhost:9999");
    }


    @Test
    void CorrectValueOnlyNameTest() {
        String newDate = getLocalDate(5, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Чита");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(newDate);
        $("[data-test-id=name] input").setValue("Петрова Мария");
        $("[data-test-id='phone'] input").setValue("+79315684565");
        $(".checkbox__box").click();
        $(".button").click();
        $(".notification__title").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + newDate), Duration.ofSeconds(15)).shouldBe(visible);
    }

}
