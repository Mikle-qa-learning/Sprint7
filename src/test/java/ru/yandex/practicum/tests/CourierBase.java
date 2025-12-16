package ru.yandex.practicum.tests;

import ru.yandex.practicum.models.Courier;
import io.restassured.response.Response;

public class CourierBase extends Base {

    public Response createCourier(Courier courier) {
        return doPostRequest(
                Url.HOST_NAME + Url.CREATE_COURIER,
                courier,
                "application/json"
        );
    }

    public Response loginCourier(Courier courier) {
        return doPostRequest(
                Url.HOST_NAME + Url.LOGIN_COURIER,
                courier,
                "application/json"
        );
    }

    public Response deleteCourier(Integer idCourier) {
        return doDeleteRequest(Url.HOST_NAME + Url.DELETE_COURIER + idCourier);
    }
}