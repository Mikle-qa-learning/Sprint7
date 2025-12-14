package ru.yandex.practicum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ListOrdersSteps {

    public ValidatableResponse gettingListOrders(){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/orders")
                .then();

    }
}
