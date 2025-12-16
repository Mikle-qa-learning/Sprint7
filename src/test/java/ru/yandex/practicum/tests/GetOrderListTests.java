package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import ru.yandex.practicum.tests.steps.OrderSteps;

public class GetOrderListTests extends OrderSteps {

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList() {
        Response response = getOrdersList();

        verifyStatusCode(response, 200);
        verifyOrdersListNotEmpty(response);
    }
}
