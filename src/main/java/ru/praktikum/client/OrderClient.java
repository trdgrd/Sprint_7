package ru.praktikum.client;

import io.restassured.response.Response;
import ru.praktikum.pojo.Order;

public class OrderClient extends BaseHttpClient {

    private static final String BASE_PATH = "api/v1/orders/";

    public Response get() {
        return getRequest(BASE_PATH);
    }

    public Response create(Order order) {
        return postRequest(BASE_PATH, order);
    }

}
