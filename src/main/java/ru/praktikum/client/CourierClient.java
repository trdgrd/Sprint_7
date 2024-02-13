package ru.praktikum.client;

import io.restassured.response.Response;
import ru.praktikum.pojo.Courier;

public class CourierClient extends BaseHttpClient {

    private static final String BASE_PATH = "api/v1/courier/";

    public Response create(Courier courier) {
        return postRequest(BASE_PATH, courier);
    }

    public Response login(Courier courier) {
        return postRequest(BASE_PATH + "login/", courier);
    }

    public Response delete(int id) {
        return deleteRequest(BASE_PATH + id);
    }

}