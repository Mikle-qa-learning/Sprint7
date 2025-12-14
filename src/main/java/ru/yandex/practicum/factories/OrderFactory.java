package ru.yandex.practicum.factories;

import net.datafaker.Faker;
import ru.yandex.practicum.models.Order;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderFactory {

    private final Faker faker = new Faker();

    public Order createOrderWithColorBlack(){
        Order order = new Order();
        order.setFirstName(faker.name().firstName());
        order.setLastName(faker.name().lastName());
        order.setAddress(faker.address().secondaryAddress());
        order.setMetroStation(faker.number().numberBetween(1, 10));
        order.setPhone(faker.phoneNumber().phoneNumber());
        order.setRentTime(faker.number().numberBetween(1, 24));
        order.setDeliveryDate(String.valueOf(faker.date().future(30, TimeUnit.DAYS)));
        order.setComment(faker.text().text(20));;
        order.setColor(List.of("BLACK"));
        return order;
    }

    public Order createOrderWithColorGray() {
        Order order = new Order();
        order.setFirstName(faker.name().firstName());
        order.setLastName(faker.name().lastName());
        order.setAddress(faker.address().secondaryAddress());
        order.setMetroStation(faker.number().numberBetween(1, 10));
        order.setPhone(faker.phoneNumber().phoneNumber());
        order.setRentTime(faker.number().numberBetween(1, 24));
        order.setDeliveryDate(String.valueOf(faker.date().future(30, TimeUnit.DAYS)));
        order.setComment(faker.text().text(20));;
        order.setColor(List.of("GRAY"));
        return order;
    }

    public Order createOrderWithBothColors() {
        Order order = new Order();
        order.setFirstName(faker.name().firstName());
        order.setLastName(faker.name().lastName());
        order.setAddress(faker.address().secondaryAddress());
        order.setMetroStation(faker.number().numberBetween(1, 10));
        order.setPhone(faker.phoneNumber().phoneNumber());
        order.setRentTime(faker.number().numberBetween(1, 24));
        order.setDeliveryDate(String.valueOf(faker.date().future(30, TimeUnit.DAYS)));
        order.setComment(faker.text().text(20));
        order.setColor(List.of("BLACK", "GRAY"));
        return order;
    }

    public Order createOrderWithoutColors() {
        Order order = new Order();
        order.setFirstName(faker.name().firstName());
        order.setLastName(faker.name().lastName());
        order.setAddress(faker.address().secondaryAddress());
        order.setMetroStation(faker.number().numberBetween(1, 10));
        order.setPhone(faker.phoneNumber().phoneNumber());
        order.setRentTime(faker.number().numberBetween(1, 24));
        order.setDeliveryDate(String.valueOf(faker.date().future(30, TimeUnit.DAYS)));
        order.setComment(faker.text().text(20));
        order.setColor(null);
        return order;
    }

}
