import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.pojo.Courier;
import ru.praktikum.steps.CourierSteps;

public class CreateCourierTest {

    private CourierSteps courierSteps = new CourierSteps();
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courier = new Courier(
                RandomStringUtils.randomAlphabetic(10),
                RandomStringUtils.randomNumeric(10),
                RandomStringUtils.randomAlphabetic(10)
        );
    }

    @Test
    @DisplayName("Проверка успешного создания курьера с логином и паролем")
    public void createCourierWithLoginAndPasswordTest() {
        ValidatableResponse response = courierSteps.createCourier(courier);
        courierSteps.checkCourierCreated(response);

        response = courierSteps.loginCourier(courier);
        courierSteps.checkCourierLoggedIn(response);

        courierId = courierSteps.getCourierId(courier);
        courierSteps.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Проверка невозможности создания курьера без логина")
    public void createCourierWithoutLoginTest() {
        Courier courierWithoutLogin = new Courier();
        courierWithoutLogin.setPassword(RandomStringUtils.randomNumeric(10));
        ValidatableResponse response = courierSteps.createCourier(courierWithoutLogin);
        courierSteps.checkCourierWithoutRequiredFieldNotCreated(response);
    }

    @Test
    @DisplayName("Проверка невозможности создания курьера без пароля")
    public void createCourierWithoutPasswordTest() {
        Courier courierWithoutPassword = new Courier(courier.getLogin());
        ValidatableResponse response = courierSteps.createCourier(courierWithoutPassword);
        courierSteps.checkCourierWithoutRequiredFieldNotCreated(response);
    }

    @Test
    @DisplayName("Проверка невозможности создания курьеров с одинаковым логином")
    public void createIdenticalCouriersTest() {
        ValidatableResponse response = courierSteps.createCourier(courier);
        courierSteps.checkCourierCreated(response);

        Courier duplicateCourier = new Courier(courier.getLogin(), courier.getPassword());
        response = courierSteps.createCourier(duplicateCourier);
        courierSteps.checkExistedCourierNotCreated(response);

        courierId = courierSteps.getCourierId(courier);
        courierSteps.deleteCourier(courierId);
    }

}