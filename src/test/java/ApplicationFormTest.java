import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.nio.channels.Selector;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ApplicationFormTest {

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test

    void shouldRegistrationAccount() {
        String planDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");

        $$("[data-test-id='city'] input").find(Condition.visible).setValue("Красноярск");
        $("[type='tel']").press(Keys.chord(Keys.CONTROL, Keys.BACK_SPACE))
                .setValue(planDate);
        $$("[name='name']").find(Condition.visible).setValue("Алексеев Алексей");
        $$("[name='phone']").find(Condition.visible).setValue("+70123456789");
        $(".checkbox__box").click();
        $$("button").find(Condition.text("Забронировать")).click();
        $$("[data-test-id='notification']")
                .find(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planDate), Duration.ofSeconds(15));
//        $(Selectors.withText("Встреча успешно забронирована на " + planDate)).should(Condition.visible, Duration.ofSeconds(25)); - обьясните пожалуйста почему не находил по тексту?
    }
}
