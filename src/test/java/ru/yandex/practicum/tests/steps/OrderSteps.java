package ru.yandex.practicum.tests.steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.practicum.models.Order;
import ru.yandex.practicum.tests.OrderBase;
import ru.yandex.practicum.models.OrderResponse;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderSteps {

    private final OrderBase orderBase = new OrderBase();

    @Step("Создание заказа")
    public Response createOrder(String firstName, String lastName, String address, String phone,
                                String rentTime, String deliveryDate, String comment, List<String> color) { // Изменено
        return orderBase.createOrder(new Order(firstName, lastName, address, phone,
                rentTime, deliveryDate, comment, color)); // Изменено
    }

    @Step("Удаление заказа")
    public Response deleteOrder(Integer trackId) {
        return orderBase.deleteOrder(trackId);
    }

    @Step("Проверка кода ответа")
    public void verifyStatusCode(Response response, int expectedCode) {
        Allure.addAttachment("Ответ", response.getStatusLine());
        response.then().statusCode(expectedCode);
    }

    @Step("Проверка тела ответа")
    public void verifyTrackIdNotNull(Response response) {
        Allure.addAttachment("Ответ", response.getBody().asInputStream());
        response.then().assertThat().body("track", notNullValue());
    }

    @Step("Получение номера заказа")
    public Integer getTrackId(Response response) {
        return response.body().as(OrderResponse.class).getTrack();
    }

    @Step("Получение списка заказов")
    public Response getOrdersList() {
        return orderBase.getOrdersList();
    }

    @Step("Проверка тела ответа на наличие заказов")
    public void verifyOrdersListNotEmpty(Response response) {
        Allure.addAttachment("Список заказов", response.getBody().asInputStream());
        response.then().assertThat().body("orders", notNullValue());
    }

    @Step("Проверка тела ответа списка заказов")
    public void verifyResponseMessage(Response response, String path, Object expectedValue) {
        Allure.addAttachment("Тело ответа", response.getBody().asString());
        response.then().assertThat().body(path, org.hamcrest.core.IsEqual.equalTo(expectedValue));
    }
}
