package ru.yandex.practicum.tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Test;
import ru.yandex.practicum.steps.ListOrdersSteps;

import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOrdersTests {

  @Test
  public void shouldGetListOrders(){
      RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
      ListOrdersSteps listOrdersSteps = new ListOrdersSteps();
      listOrdersSteps.gettingListOrders()
              .statusCode(200)
              .body("orders", notNullValue());
  }
}
