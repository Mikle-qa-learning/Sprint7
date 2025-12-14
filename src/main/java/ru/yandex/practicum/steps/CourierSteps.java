package ru.yandex.practicum.steps;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.yandex.practicum.models.Courier;

import static io.restassured.RestAssured.given;

public class CourierSteps {


    public ValidatableResponse creatingFullFieldCourier(Courier courier){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();


    }


    public ValidatableResponse creatingRequiredFieldCourier(Courier courier){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();

    }


    public ValidatableResponse creatingWithoutLoginCourier(Courier courier){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }


    public ValidatableResponse creatingWithoutPasswordCourier(Courier courier){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }


    public ValidatableResponse creatingWithEmptyLoginCourier(Courier courier){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }


    public ValidatableResponse creatingWithEmptyPasswordCourier(Courier courier){
        return given()
                .baseUri("https://qa-scooter.praktikum-services.ru/")
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

}
