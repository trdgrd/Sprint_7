package ru.praktikum.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.praktikum.Config;

import static io.restassured.RestAssured.given;

public abstract class BaseHttpClient {

    private RequestSpecification baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Config.BASE_URI)
                .addHeader("Content-Type", "application/json")
                .setRelaxedHTTPSValidation()
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new ErrorLoggingFilter())
                .build();
    }

    protected Response getRequest(String path) {
        return given()
                .spec(baseRequestSpec())
                .get(path);
    }

    protected Response postRequest(String path, Object body) {
        return given()
                .spec(baseRequestSpec())
                .body(body)
                .post(path);
    }

    protected Response postRequest(String path, String... params) {
        return given()
                .spec(baseRequestSpec())
                .body(params)
                .post(path);
    }

    protected Response deleteRequest(String path) {
        return given()
                .spec(baseRequestSpec())
                .delete(path);
    }

}