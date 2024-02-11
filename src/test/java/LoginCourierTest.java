import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.pojo.Courier;
import steps.CourierSteps;

public class LoginCourierTest {

    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;
    private ValidatableResponse response;
    private int courierId;

    @Before
    public void setUp() {
        courier = new Courier(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomNumeric(10),
                RandomStringUtils.randomAlphabetic(10)
        );

        ValidatableResponse response = courierSteps.createCourier(courier);
        courierSteps.checkCourierCreated(response);
    }

    @After
    public void tearDown() {
        courierId = courierSteps.getCourierId(courier);
        courierSteps.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Проверка успешного логина курьера с логином и паролем")
    public void loginCourierWithLoginAndPasswordTest() {
        response = courierSteps.loginCourier(courier);
        courierSteps.checkCourierLoggedIn(response);
    }

    @Test
    @DisplayName("Проверка невозможности логина курьера с невалидным паролем")
    public void loginCourierWithInvalidPasswordTest() {
        Courier courierWithInvalidPassword = new Courier(courier.getLogin(), RandomStringUtils.randomNumeric(10));
        response = courierSteps.loginCourier(courierWithInvalidPassword);
        courierSteps.checkCourierWithInvalidCredentialsNotLoggedIn(response);
    }

    @Test
    @DisplayName("Проверка невозможности логина курьера без логина")
    public void loginCourierWithoutLoginTest() {
        Courier courierWithoutLogin = new Courier("", courier.getPassword());
        response = courierSteps.loginCourier(courierWithoutLogin);
        courierSteps.checkCourierWithoutRequiredFieldNotLoggedIn(response);
    }

    @Test
    @DisplayName("Проверка невозможности логина курьера без пароля")
    public void loginCourierWithoutPasswordTest() {
        Courier courierWithoutPassword = new Courier(courier.getLogin(), "");
        response = courierSteps.loginCourier(courierWithoutPassword);
        courierSteps.checkCourierWithoutRequiredFieldNotLoggedIn(response);
    }

}