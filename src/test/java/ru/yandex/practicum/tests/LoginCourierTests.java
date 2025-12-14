package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.factories.CourierFactory;
import ru.yandex.practicum.models.Courier;
import ru.yandex.practicum.steps.CourierSteps;
import ru.yandex.practicum.steps.LoginCourierSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTests {

    private final CourierFactory courierFactory = new CourierFactory();
    private final CourierSteps courierSteps = new CourierSteps();
    private final LoginCourierSteps loginCourierSteps = new LoginCourierSteps();
    private Courier createdCourier;



    @Test
    @DisplayName("Тест: успешный логин курьера с параметрами логин, пароль, имя")
    public void shouldLoginCourier() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier);
        loginCourierSteps.loginCourier(createdCourier)
                .statusCode(200);
    }

    @Test
    @DisplayName("Тест: успешный логин курьера с обязательными параметрами логин, пароль")
    public void loginCourierWithRequiredField() {
        createdCourier = courierFactory.createRequiredFieldCourier();
        courierSteps.creatingRequiredFieldCourier(createdCourier);
        loginCourierSteps.loginCourier(createdCourier)
                .statusCode(200);
    }

    @Test
    @DisplayName("Тест: ошибка при попытке логина курьера с неверным логином")
    public void loginWithWrongLoginShouldReturnError() {
        createdCourier = courierFactory.createRequiredFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier);

        Courier wrongLoginCourier = courierFactory.createWithWrongLoginCourier(createdCourier);

        loginCourierSteps.loginCourier(wrongLoginCourier)
                .statusCode(404);
    }

    @Test
    @DisplayName("Тест: ошибка при попытке логина курьера с неверным паролем")
    public void loginWithWrongPasswordShouldReturnError() {
        createdCourier = courierFactory.createRequiredFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier);

        Courier wrongPasswordCourier = courierFactory.createWithWrongPasswordCourier(createdCourier);

        loginCourierSteps.loginCourier(wrongPasswordCourier)
                .statusCode(404);
    }

    @Test
    @DisplayName("Тест: ошибка при попытке логина курьера без логина")
    public void loginWithoutLoginShouldReturnError() {
        createdCourier = courierFactory.createRequiredFieldCourier();
        courierSteps.creatingRequiredFieldCourier(createdCourier);

        Courier withoutLoginWithExistentPasswordCourier = courierFactory.createWithoutLoginWithExistentPasswordCourier(createdCourier);

        loginCourierSteps.loginCourier(withoutLoginWithExistentPasswordCourier)
                .statusCode(400);
    }

    @Test
    @DisplayName("Тест: ошибка при попытке логина курьера без пароля")
    public void loginWithoutPasswordShouldReturnError() {
        createdCourier = courierFactory.createRequiredFieldCourier();
        courierSteps.creatingRequiredFieldCourier(createdCourier);

        Courier withoutPasswordWithExistentLoginCourier = courierFactory.createWithoutPasswordWithExistentLoginCourier(createdCourier);

        loginCourierSteps.loginCourier(withoutPasswordWithExistentLoginCourier)
                .statusCode(400);
    }

    @Test
    @DisplayName("Тест: ошибка при попытке логина несуществующего курьера")
    public void loginWithNonExistentCourierShouldReturnError() {
        Courier nonExistentCourier = courierFactory.createRequiredFieldCourier();
        loginCourierSteps.loginCourier(nonExistentCourier)
                .statusCode(404);
    }

    @Test
    @DisplayName("Тест: при успешном логине ответ возвращает корректное тело")
    public void loginCourierShouldReturnCorrectBody() {
        createdCourier = courierFactory.createRequiredFieldCourier();
        courierSteps.creatingRequiredFieldCourier(createdCourier);
        loginCourierSteps.loginCourier(createdCourier)
                .body("id", notNullValue());
    }

    @After
    public void tearDown() {
        if (createdCourier != null && createdCourier.getLogin() != null && createdCourier.getPassword() != null) {
            try {
                Integer courierId = loginCourierSteps.loginCourier(createdCourier)
                        .extract()
                        .path("id");

                if (courierId != null) {
                    Courier courierToDelete = new Courier();
                    courierToDelete.setId(courierId);
                    loginCourierSteps.deleteCourier(courierToDelete);
                }
            } catch (Exception e) {
            }
        }
        createdCourier = null;
    }
}
