package ru.yandex.practicum.tests.steps;

import ru.yandex.practicum.models.Courier;
import ru.yandex.practicum.models.CourierResponse;
import ru.yandex.practicum.tests.CourierBase;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

public class CourierSteps {

    private final CourierBase courierBase = new CourierBase();
    private boolean isCreated = false;

    @Step("Создание курьера")
    public Response createCourier(String login, String password, String firstName) {
        return courierBase.createCourier(new Courier(login, password, firstName));
    }

    @Step("Логин курьера")
    public Response loginCourier(String login, String password) {
        return courierBase.loginCourier(new Courier(login, password));
    }

    @Step("Получение ID курьера")
    public Integer getIdCourier(Response response) {
        try {
            return response.body().as(CourierResponse.class).getId();
        } catch (Exception e) {
            return null;
        }
    }

    @Step("Удаление курьера")
    public Response deleteCourier(Integer idCourier) {
        return courierBase.deleteCourier(idCourier);
    }

    @Step("Проверка кода ответа")
    public void verifyStatusCode(Response response, int expectedCode) {
        Allure.addAttachment("Статус ответа", response.getStatusLine());
        response.then().statusCode(expectedCode);
    }

    @Step("Проверка тела ответа")
    public void verifyResponseMessage(Response response, String path, Object expectedValue) {
        Allure.addAttachment("Тело ответа", response.getBody().asString());
        response.then().assertThat().body(path, equalTo(expectedValue));
    }

    @Step("Проверка тела ответа на наличие id")
    public void verifyCourierIdNotNull(Response response) {
        Allure.addAttachment("Тело ответа", response.getBody().asString());
        response.then().assertThat().body("id", notNullValue());
    }

    @Step("Проверка успешного создания курьера")
    public boolean verifyCreationSuccess(Response response, int expectedCode) {
        return response.getStatusCode() == expectedCode;
    }

    @Step("Курьер помечен как созданный")
    public void markAsCreated() {
        this.isCreated = true;
    }

    @Step("Сброс создания курьера")
    public void resetCreatedFlag() {
        this.isCreated = false;
    }

    public boolean isCourierCreated() {
        return this.isCreated;
    }
}
