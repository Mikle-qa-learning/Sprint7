package ru.yandex.practicum.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.After;
import org.junit.Test;
import ru.yandex.practicum.factories.CourierFactory;
import ru.yandex.practicum.models.Courier;
import ru.yandex.practicum.steps.CourierSteps;
import ru.yandex.practicum.steps.LoginCourierSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTests {

    private final CourierFactory courierFactory = new CourierFactory();


    @Test
    public void shouldLoginCourier(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier fullFieldCourier = courierFactory.createFullFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingFullFieldCourier(fullFieldCourier);
        LoginCourierSteps loginCourierSteps = new LoginCourierSteps();
        loginCourierSteps.loginCourier(fullFieldCourier)
                .statusCode(200);

    }

    @Test
    public void loginCourierWithRequiredField(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier requiredFieldCourier = courierFactory.createRequiredFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingRequiredFieldCourier(requiredFieldCourier);
        LoginCourierSteps loginCourierSteps = new LoginCourierSteps();
        loginCourierSteps.loginCourier(requiredFieldCourier)
                .statusCode(200);
    }

    @Test
    public void loginWithWrongLoginShouldReturnError() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Courier requiredFieldCourier = courierFactory.createRequiredFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingFullFieldCourier(requiredFieldCourier);

        Courier wrongLoginCourier = courierFactory.createWithWrongLoginCourier(requiredFieldCourier);

        LoginCourierSteps loginCourierSteps = new LoginCourierSteps();
        loginCourierSteps.loginCourier(wrongLoginCourier)
                .statusCode(404);

    }

    @Test
    public void loginWithWrongPasswordShouldReturnError(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        Courier requiredFieldCourier = courierFactory.createRequiredFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingFullFieldCourier(requiredFieldCourier);

        Courier wrongPasswordCourier = courierFactory.createWithWrongPasswordCourier(requiredFieldCourier);

        LoginCourierSteps loginCourierSteps = new LoginCourierSteps();
        loginCourierSteps.loginCourier(wrongPasswordCourier)
                .statusCode(404);
    }

    @Test
    public void loginWithoutLoginShouldReturnError(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier requiredFieldCourier = courierFactory.createRequiredFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingRequiredFieldCourier(requiredFieldCourier);

        Courier withoutLoginWithExistentPasswordCourier = courierFactory.createWithoutLoginWithExistentPasswordCourier(requiredFieldCourier);

        LoginCourierSteps loginCourierSteps = new LoginCourierSteps();
        loginCourierSteps.loginCourier(withoutLoginWithExistentPasswordCourier)
                .statusCode(400);

    }

    @Test
    public void loginWithoutPasswordShouldReturnError(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier requiredFieldCourier = courierFactory.createRequiredFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingRequiredFieldCourier(requiredFieldCourier);

        Courier withoutPasswordWithExistentLoginCourier = courierFactory.createWithoutPasswordWithExistentLoginCourier(requiredFieldCourier);

        LoginCourierSteps loginCourierSteps = new LoginCourierSteps();
        loginCourierSteps.loginCourier(withoutPasswordWithExistentLoginCourier)
                .statusCode(400);
    }

    @Test
    public void loginWithNonExistentCourierShouldReturnError(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
       Courier nonExistentCourier = courierFactory.createRequiredFieldCourier();
        LoginCourierSteps loginCourierSteps = new LoginCourierSteps();

        loginCourierSteps.loginCourier(nonExistentCourier)
                .statusCode(404);
    }

    @Test
    public void loginCourierShouldReturnCorrectBody(){
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        Courier requiredFieldCourier = courierFactory.createRequiredFieldCourier();
        CourierSteps courierSteps = new CourierSteps();
        courierSteps.creatingRequiredFieldCourier(requiredFieldCourier);
        LoginCourierSteps loginCourierSteps = new LoginCourierSteps();
        loginCourierSteps.loginCourier(requiredFieldCourier)
                .body("id", notNullValue());
    }

    @After
    public void tearDown(){
        LoginCourierSteps loginCourierSteps = new LoginCourierSteps();
        loginCourierSteps.deleteCourier(Courier)
                .statusCode(200);
    }
}
