import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import steps.OrderSteps;

public class OrderListTest {

    private OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Проверка возвращения списка существующих заказов")
    public void getOrderListTest() {
        ValidatableResponse response = orderSteps.getOrderList();
        orderSteps.checkOrderListReceived(response);
    }

}