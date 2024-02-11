package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import ru.praktikum.client.CourierClient;
import ru.praktikum.pojo.Courier;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierSteps {

    private CourierClient courierClient = new CourierClient();

    @Step
    public ValidatableResponse createCourier(Courier courier) {
        return courierClient.create(courier).then();
    }

    @Step
    public ValidatableResponse checkCourierCreated(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Step
    public ValidatableResponse checkCourierWithoutRequiredFieldNotCreated(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Step
    public ValidatableResponse checkExistedCourierNotCreated(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Step
    public ValidatableResponse loginCourier(Courier courier) {
        return courierClient.login(courier).then();
    }

    @Step
    public ValidatableResponse checkCourierLoggedIn(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(notNullValue()));
    }

    @Step
    public int getCourierId(Courier courier) {
        return loginCourier(courier)
                .extract()
                .path("id");
    }

    @Step
    public ValidatableResponse checkCourierWithInvalidCredentialsNotLoggedIn(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Step
    public ValidatableResponse checkCourierWithoutRequiredFieldNotLoggedIn(ValidatableResponse response) {
        return response
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Step
    public ValidatableResponse deleteCourier(int id) {
        ValidatableResponse response = courierClient.delete(id).then();
        return response
                .statusCode(HttpStatus.SC_OK)
                .body("ok", is(true));
    }

}