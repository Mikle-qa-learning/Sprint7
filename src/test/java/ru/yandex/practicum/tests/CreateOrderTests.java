package ru.yandex.practicum.tests;

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

    // Параметры теста
    private final Order order;

    // Конструктор с одним параметром
    public CreateOrderTests(Order order) {
        this.order = order;
    }

    // Простой вариант данных - только заказы
    @Parameterized.Parameters
    public static Collection<Object[]> testData() {
        OrderFactory factory = new OrderFactory();

        return Arrays.asList(new Object[][] {
                {factory.createOrderWithColorBlack()},
                {factory.createOrderWithColorGray()},
                {factory.createOrderWithBothColors()},
                {factory.createOrderWithoutColors()}
        });
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
                // Игнорируем
            }
        }
    }

    @Test
    public void createOrderWithDifferentColors() {

        ValidatableResponse response = orderSteps.createOrder(order);
        response.statusCode(201);
        trackId = response.extract().path("track");


    }
}