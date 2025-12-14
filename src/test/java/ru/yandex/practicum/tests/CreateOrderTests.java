package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.factories.OrderFactory;
import ru.yandex.practicum.models.Order;
import ru.yandex.practicum.steps.OrderSteps;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CreateOrderTests {

    private final OrderSteps orderSteps = new OrderSteps();
    private Integer trackId;
    private final Order order;
    private final String testName;

    public CreateOrderTests(Order order, String testName) {
        this.order = order;
        this.testName = testName;
    }

    @Parameterized.Parameters(name = "{1}")
    public static Collection<Object[]> testData() {
        OrderFactory factory = new OrderFactory();

        return Arrays.asList(new Object[][] {
                {factory.createOrderWithColorBlack(), "Создание заказа с цветом BLACK"},
                {factory.createOrderWithColorGray(), "Создание заказа с цветом GRAY"},
                {factory.createOrderWithBothColors(), "Создание заказа с обоими цветами"},
                {factory.createOrderWithoutColors(), "Создание заказа без указания цвета"}
        });
    }

    @Test
    @DisplayName("Параметризованный тест создания заказа")
    public void createOrderWithDifferentColors() {
        io.qameta.allure.Allure.parameter("Вариант теста", testName);
        ValidatableResponse response = orderSteps.createOrder(order);
        response.statusCode(201);
        trackId = response.extract().path("track");
    }

    @Before
    public void setUp() {
        RestAssured.reset();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @After
    public void tearDown() {
        if (trackId != null) {
            try {
                orderSteps.cancelOrder(trackId).statusCode(200);
            } catch (Exception e) {
            }
        }
    }
}