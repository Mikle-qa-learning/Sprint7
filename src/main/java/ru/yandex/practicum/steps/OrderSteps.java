package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.models.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    public ValidatableResponse createOrder(Order order){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Удаление заказа")
    public ValidatableResponse cancelOrder(Integer trackId) {
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .queryParam("track", trackId)
                .when()
                .put("/api/v1/orders/cancel")
                .then();
    }
}
