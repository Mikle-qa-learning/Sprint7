package ru.yandex.practicum.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Test;
import ru.yandex.practicum.factories.CourierFactory;
import ru.yandex.practicum.models.Courier;
import ru.yandex.practicum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTests {

    private final CourierFactory courierFactory = new CourierFactory();

    @Test
    public void shouldCreateCourier(){
        Courier fullFieldCourier = courierFactory.createFullFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingFullFieldCourier(fullFieldCourier)
                .statusCode(201)
                .body("ok", is(true));
    }


    @Test
    public void creatingTwoIdenticalCourierError(){
        Courier fullFieldCourier = courierFactory.createFullFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingFullFieldCourier(fullFieldCourier);

        courierSteps.creatingFullFieldCourier(fullFieldCourier)
                .statusCode(409);

    }

    @Test
    public void shouldCreateRequiredFieldCourier(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier requiredFieldCourier = courierFactory.createRequiredFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingRequiredFieldCourier(requiredFieldCourier)
                .statusCode(201);
    }


    @Test
    public void requestCreatingCourierReturnCorrectResponseCode(){
        Courier fullFieldCourier = courierFactory.createFullFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingFullFieldCourier(fullFieldCourier)
                .statusCode(201);
    }

    @Test
    public void requestCreatingCourierReturnCorrectBody(){
        Courier fullFieldCourier = courierFactory.createFullFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingFullFieldCourier(fullFieldCourier)
                .body("ok", is(true));
    }

    @Test
    public void creatingWithoutLoginCourierError(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier withoutLoginCourier = courierFactory.createWithoutLoginCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingWithoutLoginCourier(withoutLoginCourier)
                .statusCode(400);

    }

    @Test
    public void creatingWithoutPasswordCourierError(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier withoutPasswordCourier = courierFactory.createWithoutPasswordCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingWithoutPasswordCourier(withoutPasswordCourier)
                .statusCode(400);
    }

    @Test
    public void creatingWithEmptyLoginCourierError(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier withEmptyLoginCourier = courierFactory.createWithEmptyLoginCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingWithEmptyLoginCourier(withEmptyLoginCourier)
                .statusCode(400);
    }

    @Test
    public void creatingWithEmptyPasswordCourierError(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier withEmptyPasswordCourier = courierFactory.createWithEmptyPasswordCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingWithEmptyPasswordCourier(withEmptyPasswordCourier)
                .statusCode(400);
    }

    @Test
    public void creatingWithExistingLoginCourierError() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        CourierSteps courierSteps = new CourierSteps();

        Courier firstCourier = courierFactory.createFullFieldCourier();
        courierSteps.creatingFullFieldCourier(firstCourier)
                .statusCode(201);

        Courier secondCourier = courierFactory.createWithExistingLoginCourier(firstCourier.getLogin());

        courierSteps.creatingFullFieldCourier(secondCourier)
                .statusCode(409);

    }
}
