package ru.yandex.practicum.tests;

import io.restassured.response.Response;
import ru.yandex.practicum.models.Order;


import java.util.HashMap;
import java.util.Map;

public class OrderBase extends Base {

    public Response createOrder(Order order) {
        return doPostRequest(
                Url.HOST_NAME + Url.CREATE_ORDER,
                order,
                "application/json"
        );
    }

    public Response deleteOrder(Integer trackId) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("track", trackId);

        return doPutRequest(
                Url.HOST_NAME + Url.DELETE_ORDER,
                queryParams
        );
    }

    public Response getOrdersList() {
        return doGetRequest(Url.HOST_NAME + Url.GET_ORDERS_LIST);
    }
}