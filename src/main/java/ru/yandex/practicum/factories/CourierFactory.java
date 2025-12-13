package ru.yandex.practicum.factories;

import net.datafaker.Faker;
import ru.yandex.practicum.models.Courier;

public class CourierFactory {

    private final Faker faker = new Faker();


    public Courier createFullFieldCourier(){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setPassword(faker.text().text(10));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    public Courier createRequiredFieldCourier() {
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setPassword(faker.text().text(10));
        return courier;
    }

    public Courier createWithoutLoginCourier(){
        Courier courier = new Courier();
        courier.setPassword(faker.text().text(10));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    public Courier createWithoutPasswordCourier(){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    public Courier createWithEmptyLoginCourier(){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(0));
        courier.setPassword(faker.text().text(10));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    public Courier createWithEmptyPasswordCourier(){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setPassword(faker.text().text(0));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    public Courier createWithExistingLoginCourier(String login) {
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(faker.text().text(10));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    public Courier createWithWrongLoginCourier(Courier requiredFieldCourier){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setPassword(requiredFieldCourier.getPassword());
        return  courier;
    }

    public Courier createWithWrongPasswordCourier(Courier requiredFieldCourier){
        Courier courier = new Courier();
        courier.setLogin(requiredFieldCourier.getLogin());
        courier.setPassword(faker.text().text(10));
        return  courier;
    }

    public Courier createWithoutLoginWithExistentPasswordCourier(Courier requiredFieldCourier){
        Courier courier = new Courier();
        courier.setPassword(requiredFieldCourier.getPassword());
        return courier;
    }

    public Courier createWithoutPasswordWithExistentLoginCourier(Courier requiredFieldCourier){
        Courier courier = new Courier();
        courier.setLogin(requiredFieldCourier.getLogin());
        return courier;
    }
}
