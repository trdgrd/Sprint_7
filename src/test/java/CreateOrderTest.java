import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.pojo.Order;
import ru.praktikum.steps.OrderSteps;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private OrderSteps orderSteps = new OrderSteps();

    private Order order;

    private final String[] colors;

    public CreateOrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Проверка успешного создания заказа с различными значениями поля 'цвет'")
    public void createOrderWithDifferentColorsTest() {
        order = new Order(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomAlphabetic(1),
                RandomStringUtils.randomAlphabetic(10),
                2,
                "2024-01-01",
                RandomStringUtils.randomNumeric(10),
                colors
        );

        ValidatableResponse response = orderSteps.createOrder(order);
        orderSteps.checkOrderCreated(response);
    }

}