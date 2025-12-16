package ru.yandex.practicum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.tests.steps.CourierSteps;

import java.util.UUID;

public class LoginCourierTests extends CourierSteps {
    private String login;
    private String password;
    private String firstName;

    @Before
    @Step("Подготовка данных для тестирования")
    public void prepareTestData() {
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();

        Response createResponse = createCourier(login, password, firstName);

        if (!verifyCreationSuccess(createResponse, 201)) {
            throw new RuntimeException("Не удалось создать курьера для тестирования логина");
        }
    }

    @After
    @Step("Очистка данных после теста")
    public void clearAfterTests() {
        Response loginResponse = loginCourier(login, password);

        if (loginResponse.getStatusCode() != 200) {
            return;
        }

        Integer idCourier = getIdCourier(loginResponse);
        if (idCourier == null) {
            return;
        }

        deleteCourier(idCourier);
    }


    @Test
    @DisplayName("Логин курьера ")
    public void loginCourierIsSuccess() {
        Response response = loginCourier(login, password);

        verifyStatusCode(response, 200);
        verifyCourierIdNotNull(response);
    }

    @Test
    @DisplayName("Логин курьера с пустыми параметрами")
    public void loginCourierMissingAllParamsIsFailed() {
        Response response = loginCourier("", "");

        verifyStatusCode(response, 400);
        verifyResponseMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Логин курьера без логина")
    public void loginCourierMissingLoginParamIsFailed() {
        Response response = loginCourier("", password);

        verifyStatusCode(response, 400);
        verifyResponseMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Логин курьера без пароля")
    public void loginCourierMissingPasswordParamIsFailed() {
        Response response = loginCourier(login, "");

        verifyStatusCode(response, 400);
        verifyResponseMessage(response, "message", "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Логин курьера с некорректным логином")
    public void loginCourierIncorrectLoginParamIsFailed() {
        Response response = loginCourier(login + "1", password);

        verifyStatusCode(response, 404);
        verifyResponseMessage(response, "message", "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Логин курьера с некорректным паролем")
    public void loginCourierIncorrectPasswordParamIsFailed() {
        Response response = loginCourier(login, password + "1");

        verifyStatusCode(response, 404);
        verifyResponseMessage(response, "message", "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Логин несуществующего курьера")
    public void loginNonExistentCourierIsFailed() {
        String nonExistentLogin = "non_existent_" + UUID.randomUUID();
        String nonExistentPassword = "non_existent_" + UUID.randomUUID();

        Response response = loginCourier(nonExistentLogin, nonExistentPassword);

        verifyStatusCode(response, 404);
        verifyResponseMessage(response, "message", "Учетная запись не найдена");
    }
}