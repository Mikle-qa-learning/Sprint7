package ru.yandex.practicum.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class Base {

    public Response doPostRequest(String url, Object requestBody, String contentType) {
        return given(baseRequest(contentType))
                .body(requestBody)
                .when()
                .post(url);
    }

    public Response doGetRequest(String url) {
        return given(baseRequest())
                .get(url);
    }

    public Response doGetRequest(String url, Map<String, Object> queryParams) {
        return given(baseRequest())
                .queryParams(queryParams)
                .when()
                .get(url);
    }

    public Response doDeleteRequest(String url) {
        return given(baseRequest())
                .delete(url);
    }

    public Response doPutRequest(String url, Map<String, Object> queryParams) {
        return given(baseRequest())
                .queryParams(queryParams)
                .when()
                .put(url);
    }

    private RequestSpecification baseRequest(String... headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setRelaxedHTTPSValidation();

        if (headers.length > 0 && headers[0] != null) {
            builder.addHeader("Content-type", headers[0]);
        }

        return builder.build();
    }
}
