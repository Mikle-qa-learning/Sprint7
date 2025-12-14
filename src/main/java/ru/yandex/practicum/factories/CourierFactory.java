package ru.yandex.practicum.factories;

import io.qameta.allure.Step;
import net.datafaker.Faker;
import ru.yandex.practicum.models.Courier;

public class CourierFactory {

    private final Faker faker = new Faker();

    @Step("Создание курьера с логином, паролем, именем")
    public Courier createFullFieldCourier(){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setPassword(faker.text().text(10));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    @Step("Создание курьера с логином и паролем")
    public Courier createRequiredFieldCourier() {
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setPassword(faker.text().text(10));
        return courier;
    }

    @Step("Создание курьера без логина")
    public Courier createWithoutLoginCourier(){
        Courier courier = new Courier();
        courier.setPassword(faker.text().text(10));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    @Step("Создание курьера без пароля")
    public Courier createWithoutPasswordCourier(){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    @Step("Создание курьера с пустым логином")
    public Courier createWithEmptyLoginCourier(){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(0));
        courier.setPassword(faker.text().text(10));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    @Step("Создание курьера с пустым паролем")
    public Courier createWithEmptyPasswordCourier(){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setPassword(faker.text().text(0));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    @Step("Создание курьера с существующим логином")
    public Courier createWithExistingLoginCourier(String login) {
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(faker.text().text(10));
        courier.setFirstName(faker.name().firstName());
        return courier;
    }

    @Step("Создание курьера с неверным логином")
    public Courier createWithWrongLoginCourier(Courier requiredFieldCourier){
        Courier courier = new Courier();
        courier.setLogin(faker.text().text(8));
        courier.setPassword(requiredFieldCourier.getPassword());
        return  courier;
    }

    @Step("Создание курьера с неверным паролем")
    public Courier createWithWrongPasswordCourier(Courier requiredFieldCourier){
        Courier courier = new Courier();
        courier.setLogin(requiredFieldCourier.getLogin());
        courier.setPassword(faker.text().text(10));
        return  courier;
    }

    @Step("Создание курьера без логина с существующим паролем")
    public Courier createWithoutLoginWithExistentPasswordCourier(Courier requiredFieldCourier){
        Courier courier = new Courier();
        courier.setPassword(requiredFieldCourier.getPassword());
        return courier;
    }

    @Step("Создание курьера с существующим логином")
    public Courier createWithoutPasswordWithExistentLoginCourier(Courier requiredFieldCourier){
        Courier courier = new Courier();
        courier.setLogin(requiredFieldCourier.getLogin());
        return courier;
    }

    @Step("Создание двух идентичных курьеров")
    public Courier createTwoIdenticalCourier() {
        Courier courier = new Courier();
        String login = faker.text().text(8);
        String password = faker.text().text(10);
        String firstName = faker.name().firstName();
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }
}
