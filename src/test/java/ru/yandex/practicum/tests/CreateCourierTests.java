package ru.yandex.practicum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import ru.yandex.practicum.tests.steps.CourierSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;


public class CreateCourierTests {
    private CourierSteps courierSteps;
    private String login;
    private String password;
    private String firstName;

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        courierSteps = new CourierSteps();
        this.login = "courier_" + UUID.randomUUID();
        this.password = "pass_" + UUID.randomUUID();
        this.firstName = "name_" + UUID.randomUUID();
    }

    @After
    @Step("Очистка данных после теста")
    public void cleanAfterTests() {
        if (!courierSteps.isCourierCreated()) {
            return;
        }

        Response loginResponse = courierSteps.loginCourier(login, password);
        Integer idCourier = courierSteps.getIdCourier(loginResponse);

        if (idCourier != null) {
            courierSteps.deleteCourier(idCourier);
        }

        courierSteps.resetCreatedFlag();
    }

    @Test
    @DisplayName("Создание нового курьера")
    public void createNewCourierIsSuccess() {
        Response response = courierSteps.createCourier(login, password, firstName);

        if (courierSteps.verifyCreationSuccess(response, 201)) {
            courierSteps.markAsCreated();
        }

        courierSteps.verifyStatusCode(response, 201);
        courierSteps.verifyResponseMessage(response, "ok", true);
    }

    @Test
    @DisplayName("Создание двух одинаковых курьеров")
    public void createSameCouriersError() {
        Response response = courierSteps.createCourier(login, password, firstName);

        if (courierSteps.verifyCreationSuccess(response, 201)) {
            courierSteps.markAsCreated();
        }

        courierSteps.verifyStatusCode(response, 201);
        courierSteps.verifyResponseMessage(response, "ok", true);

        response = courierSteps.createCourier(login, password, firstName);

        courierSteps.verifyStatusCode(response, 409);
    }

    @Test
    @DisplayName("Создание нового курьера без входных данных")
    public void createCourierMissingLoginError() {
        Response response = courierSteps.createCourier("", password, firstName);

        courierSteps.verifyStatusCode(response, 400);
        courierSteps.verifyResponseMessage(response, "message",
                "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без входных данных")
    public void createCourierMissingPasswordError() {
        Response response = courierSteps.createCourier(login, "", firstName);

        courierSteps.verifyStatusCode(response, 400);
        courierSteps.verifyResponseMessage(response, "message",
                "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без логина")
    public void createCourierMissingLoginParamIsFailed() {
        Response response = courierSteps.createCourier("", password, firstName);

        courierSteps.verifyStatusCode(response, 400);
        courierSteps.verifyResponseMessage(response, "message",
                "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание нового курьера без пароля")
    public void createCourierMissingPasswordParamIsFailed() {
        Response response = courierSteps.createCourier(login, "", firstName);

        courierSteps.verifyStatusCode(response, 400);
        courierSteps.verifyResponseMessage(response, "message",
                "Недостаточно данных для создания учетной записи");
    }

    @Test
    @DisplayName("Создание курьера с существующим логином (разные пароль и имя)")
    public void creatingWithExistingLoginButDifferentData() {
        Response firstResponse = courierSteps.createCourier(login, password, firstName);

        courierSteps.verifyStatusCode(firstResponse, 201);
        courierSteps.verifyResponseMessage(firstResponse, "ok", true);
        courierSteps.markAsCreated();


        String differentPassword = "different_" + UUID.randomUUID();
        String differentFirstName = "different_" + UUID.randomUUID();

        Response secondResponse = courierSteps.createCourier(login,differentPassword, differentFirstName
        );

        courierSteps.verifyStatusCode(secondResponse, 409);

    }
}
