package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import ru.praktikum.client.OrderClient;
import ru.praktikum.pojo.Order;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class OrderSteps {

    private OrderClient orderClient = new OrderClient();

    @Step
    public ValidatableResponse createOrder(Order order) {
        return orderClient.create(order).then();
    }

    @Step
    public ValidatableResponse checkOrderCreated(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", is(notNullValue()));
    }

    @Step
    public ValidatableResponse getOrderList() {
        return orderClient.get().then();
    }

    @Step
    public ValidatableResponse checkOrderListReceived(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_OK)
                .body("orders", is(notNullValue()));
    }

}