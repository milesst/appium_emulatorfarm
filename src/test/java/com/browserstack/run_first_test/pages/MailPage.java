package com.browserstack.run_first_test.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MailPage {
    private AppiumDriver driver;

    @AndroidFindBy(id = "ru.yandex.mail:id/counter")
    private AndroidElement counter;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc=\"Открыть меню\"]")
    private AndroidElement menuButton;

    @AndroidFindBy(xpath = "//android.widget.RelativeLayout[contains(@content-desc, \"Папка Входящие\")]")
    private AndroidElement inboxButton;

    @AndroidFindBy(xpath = "//android.widget.RelativeLayout[contains(@content-desc, \"Папка Отправленные\")]")
    private AndroidElement outboxButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Настройки']")
    private AndroidElement settingsBtn;

//    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.view.ViewGroup/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.FrameLayout[2]/android.widget.LinearLayout/android.view.ViewGroup/android.widget.TextView")
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, \"Входящие\") or contains(@text, \"Отправленные\")]")
    private AndroidElement header;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Выход']")
    private AndroidElement exitBtn;

    @AndroidFindBy(id = "ru.yandex.mail:id/fab")
    private AndroidElement newLetterBtn;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='ru.yandex.mail:id/sender']")
    private AndroidElement sender;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='ru.yandex.mail:id/date_time']")
    private AndroidElement time;

    @AndroidFindBy(id = "ru.yandex.mail:id/delete")
    private AndroidElement deleteLetterBtn;

    @AndroidFindBy(xpath = "(//android.widget.ImageView[@content-desc=\"Выбрать письмо\"])[1]")
    private AndroidElement letterAvatar;

    public MailPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickLetterAvatar() { letterAvatar.click(); }

    public String getHeaderText() {
        return header.getText();
    }

    public String getSender() {
        return sender.getText();
    }

    public String getCounterValue() {
        return counter.getText();
    }

    public Point getCoordsOfSender() {
        return sender.getLocation();
    }

    public Point getCoordsOfTime() { return time.getCenter();}

    public void clickNewLetterBtn() {
        newLetterBtn.click();
    }

    public void clickDeleteLetterBtn() { deleteLetterBtn.click(); }

    public void clickMenuBtn() {
        menuButton.click();
    }

    public void clickInboxBtn() {
        inboxButton.click();
    }

    public void clickOutboxBtn() {
        outboxButton.click();
    }

    public void clickSettingsBtn() {
        settingsBtn.click();
    }

    public void clickExitBtn() {
        exitBtn.click();
    }

    public void waitForDeleteBtn(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(deleteLetterBtn));
    }

    public void waitForHeader(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(header));
    }

    public void waitForMenuBtn(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(menuButton));
    }

    public void waitForCounter(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(counter));
    }

    public void waitForInboxButton(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(inboxButton));
    }

    public void waitForOutboxButton(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(outboxButton));
    }

    public void waitForSettingsButton(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(settingsBtn));
    }

    public void waitForExitBtn(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(exitBtn));
    }

    public void waitForNewLetterBtn(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(newLetterBtn));
    }

    public void waitForLetterSender(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(sender));
    }

    public void waitForCounterChange(int timeInSeconds, Integer expectedCounter) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.textToBePresentInElement(counter, expectedCounter.toString()));
    }

    public void waitForRating(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(driver.findElementById("ru.yandex.mail:id/new_smartrate_rating_bar")));
    }
}
