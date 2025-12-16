package ru.yandex.practicum.tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import ru.yandex.practicum.tests.steps.OrderSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class CreateOrderTests extends OrderSteps {
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private final List<String> color;
    private Integer trackId;

    public CreateOrderTests(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] initParamsForTest() {
        return new Object[][] {
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @Before
    @Step("Подготовка тестовых данных")
    public void prepareTestData() {
        this.firstName = "testName";
        this.lastName = "testLastName";
        this.address = "Москва, Тестовая ул., д. 123";
        this.phone = "+7 (901) 234-56-78";
        this.rentTime = "3";
        this.deliveryDate = "2023-07-24";
        this.comment = "Some comment";
    }

    @After
    @Step("Очистка данных после теста")
    public void clearAfterTests() {
        if (trackId == null) return;

        Response deleteResponse = deleteOrder(trackId);
        verifyStatusCode(deleteResponse, 200);
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrderIsSuccess() {
        Allure.parameter("Цвет самоката", color);

        Response response = createOrder(firstName, lastName, address, phone,
                rentTime, deliveryDate, comment, color);

        verifyStatusCode(response, 201);
        verifyTrackIdNotNull(response);

        this.trackId = getTrackId(response);
    }
}
