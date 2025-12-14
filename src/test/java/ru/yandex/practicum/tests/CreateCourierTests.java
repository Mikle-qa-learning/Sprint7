package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.factories.CourierFactory;
import ru.yandex.practicum.models.Courier;
import ru.yandex.practicum.steps.CourierSteps;
import ru.yandex.practicum.steps.LoginCourierSteps;

import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTests {

    private final CourierFactory courierFactory = new CourierFactory();
    private final CourierSteps courierSteps = new CourierSteps();
    private final LoginCourierSteps loginCourierSteps = new LoginCourierSteps();

    private Courier createdCourier;

    @Test
    @DisplayName("Тест: успешное создание курьера с параметрами логин, пароль, имя")
    public void shouldCreateCourier() {
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Тест: ошибка при создании двух идентичных курьеров")
    public void creatingTwoIdenticalCourierError() {
        createdCourier = courierFactory.createTwoIdenticalCourier();
        courierSteps.creatingFullFieldCourier(createdCourier);
        courierSteps.creatingFullFieldCourier(createdCourier)
                .statusCode(409);
    }

    @Test
    @DisplayName("Тест: успешное создание курьера с обязательными параметрами логин, пароль")
    public void shouldCreateRequiredFieldCourier() {
        createdCourier = courierFactory.createRequiredFieldCourier();
        courierSteps.creatingRequiredFieldCourier(createdCourier)
                .statusCode(201);
    }

    @Test
    @DisplayName("Тест: запрос на создание курьера возвращает корректный код ответа ")
    public void requestCreatingCourierReturnCorrectResponseCode() {
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier)
                .statusCode(201);
    }

    @Test
    @DisplayName("Тест: запрос на создание курьера возвращает корректное тело ответа")
    public void requestCreatingCourierReturnCorrectBody() {
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Тест: ошибка при создании курьера без логина")
    public void creatingWithoutLoginCourierError() {
        Courier withoutLoginCourier = courierFactory.createWithoutLoginCourier();
        courierSteps.creatingWithoutLoginCourier(withoutLoginCourier)
                .statusCode(400);
    }

    @Test
    @DisplayName("Тест: ошибка при создании курьера без пароля")
    public void creatingWithoutPasswordCourierError() {
        Courier withoutPasswordCourier = courierFactory.createWithoutPasswordCourier();
        courierSteps.creatingWithoutPasswordCourier(withoutPasswordCourier)
                .statusCode(400);
    }

    @Test
    @DisplayName("Тест: ошибка при создании курьера с пустым логином")
    public void creatingWithEmptyLoginCourierError() {
        Courier withEmptyLoginCourier = courierFactory.createWithEmptyLoginCourier();
        courierSteps.creatingWithEmptyLoginCourier(withEmptyLoginCourier)
                .statusCode(400);
    }

    @Test
    @DisplayName("Тест: ошибка при создании курьера с пустым паролем ")
    public void creatingWithEmptyPasswordCourierError() {
        Courier withEmptyPasswordCourier = courierFactory.createWithEmptyPasswordCourier();
        courierSteps.creatingWithEmptyPasswordCourier(withEmptyPasswordCourier)
                .statusCode(400);
    }

    @Test
    @DisplayName("Тест: ошибка при создании курьера с существующим логином")
    public void creatingWithExistingLoginCourierError() {
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier)
                .statusCode(201);

        Courier secondCourier = courierFactory.createWithExistingLoginCourier(createdCourier.getLogin());
        courierSteps.creatingFullFieldCourier(secondCourier)
                .statusCode(409);
    }

    @After
    public void tearDown() {
        if (createdCourier != null && createdCourier.getLogin() != null && createdCourier.getPassword() != null) {
            try {
                Integer courierId = loginCourierSteps.loginCourier(createdCourier)
                        .extract()
                        .path("id");

                if (courierId != null) {
                    Courier courierToDelete = new Courier();
                    courierToDelete.setId(courierId);
                    loginCourierSteps.deleteCourier(courierToDelete);
                }
            } catch (Exception e) {

            }
        }
        createdCourier = null;
    }
}
