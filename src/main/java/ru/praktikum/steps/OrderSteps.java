package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import ru.praktikum.client.OrderClient;
import ru.praktikum.pojo.Order;

import static org.hamcrest.Matchers.*;

public class OrderSteps {

    private OrderClient orderClient = new OrderClient();

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return orderClient.create(order).then();
    }

    @Step("Проверка, что заказ создан")
    public ValidatableResponse checkOrderCreated(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_CREATED)
                .body("track", is(notNullValue()));
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return orderClient.get().then();
    }

    @Step("Проверка, что список заказов получен")
    public ValidatableResponse checkOrderListReceived(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_OK)
                .body("orders", hasSize(greaterThan(0)));
    }

}