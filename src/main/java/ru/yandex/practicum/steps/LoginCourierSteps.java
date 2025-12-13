package ru.yandex.practicum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.models.Courier;

import static io.restassured.RestAssured.given;

public class LoginCourierSteps {

    public ValidatableResponse loginCourier(Courier courier){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse deleteCourier(Courier courier){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .delete("/api/v1/courier/")
                .then();
    }
}
