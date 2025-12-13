package ru.yandex.practicum.tests;

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
        createdCourier = null; // Сбрасываем ссылку
    }

    @Test
    public void shouldCreateCourier() {
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void creatingTwoIdenticalCourierError() {
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier);

        courierSteps.creatingFullFieldCourier(createdCourier)
                .statusCode(409);
    }

    @Test
    public void shouldCreateRequiredFieldCourier() {
        createdCourier = courierFactory.createRequiredFieldCourier();
        courierSteps.creatingRequiredFieldCourier(createdCourier)
                .statusCode(201);
    }

    @Test
    public void requestCreatingCourierReturnCorrectResponseCode() {
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier)
                .statusCode(201);
    }

    @Test
    public void requestCreatingCourierReturnCorrectBody() {
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier)
                .body("ok", is(true));
    }

    @Test
    public void creatingWithoutLoginCourierError() {
        Courier withoutLoginCourier = courierFactory.createWithoutLoginCourier();
        courierSteps.creatingWithoutLoginCourier(withoutLoginCourier)
                .statusCode(400);
    }

    @Test
    public void creatingWithoutPasswordCourierError() {
        Courier withoutPasswordCourier = courierFactory.createWithoutPasswordCourier();
        courierSteps.creatingWithoutPasswordCourier(withoutPasswordCourier)
                .statusCode(400);
    }

    @Test
    public void creatingWithEmptyLoginCourierError() {
        Courier withEmptyLoginCourier = courierFactory.createWithEmptyLoginCourier();
        courierSteps.creatingWithEmptyLoginCourier(withEmptyLoginCourier)
                .statusCode(400);
    }

    @Test
    public void creatingWithEmptyPasswordCourierError() {
        Courier withEmptyPasswordCourier = courierFactory.createWithEmptyPasswordCourier();
        courierSteps.creatingWithEmptyPasswordCourier(withEmptyPasswordCourier)
                .statusCode(400);
    }

    @Test
    public void creatingWithExistingLoginCourierError() {
        createdCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(createdCourier)
                .statusCode(201);

        Courier secondCourier = courierFactory.createWithExistingLoginCourier(createdCourier.getLogin());
        courierSteps.creatingFullFieldCourier(secondCourier)
                .statusCode(409);
    }
}
