package com.browserstack.run_first_test.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private AppiumDriver driver;

    @AndroidFindBy(id = "ru.yandex.mail:id/list_yandex")
    private AndroidElement yandexMailOption;

    @AndroidFindBy(id = "ru.yandex.mail:id/edit_login")
    private AndroidElement loginField;

    @AndroidFindBy(id = "ru.yandex.mail:id/button_next")
    private AndroidElement nextButton;

    @AndroidFindBy(id = "ru.yandex.mail:id/edit_password")
    private AndroidElement passwordField;

    @AndroidFindBy(id = "ru.yandex.mail:id/go_to_mail_button")
    private AndroidElement openMailButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Выберите почту']")
    private AndroidElement header;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getHeaderText() {
        return header.getText();
    }

    public void clickYandex() {
        yandexMailOption.click();
    }

    public void clickNextButton() {
        nextButton.click();
    }

    public void clickOpenMailButton() {
        openMailButton.click();
    }

    public void typeLogin(String login) {
        loginField.setValue(login);
    }

    public void typePassword(String password) {
        passwordField.setValue(password);
    }

    public void waitForYandexOption(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(yandexMailOption));
    }

    public void waitForLoginField(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(loginField));
    }

    public void waitForPasswordField(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(passwordField));
    }

    public void waitForOpenMailBtn(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(openMailButton));
    }

    public void waitForHeader(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(header));
    }
}
