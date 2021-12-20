package com.browserstack.run_first_test.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewLetterPage {
    private AppiumDriver driver;

    @AndroidFindBy(id = "ru.yandex.mail:id/copy_edit_text")
    private AndroidElement addressField;

    @AndroidFindBy(id = "ru.yandex.mail:id/attach_file")
    private AndroidElement attachButton;

    @AndroidFindBy(id = "ru.yandex.mail:id/menu_send")
    private AndroidElement sendButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Файл с телефона']")
    private AndroidElement pickFileButton;

    @AndroidFindBy(id = "ru.yandex.mail:id/item_dismiss")
    private AndroidElement addFileButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Кому']")
    private AndroidElement addressLabel;

    public NewLetterPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void clickAddressLabel() {
        addressLabel.click();
    }

    public String getAddressValue() {
        return addressField.getText();
    }

    public void clickSendBtn() {
        sendButton.click();
    }

    public void clickAttachButton() {
        attachButton.click();
    }

    public void clickPickFileBtn() {
        pickFileButton.click();
    }

    public void clickAddFileBtn() {
        addFileButton.click();
    }

    public void typeAddress(String address) {
        addressField.setValue(address);
    }

    public void waitForSendButton(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(sendButton));
    }

    public void waitForAttachButton(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(attachButton));
    }

    public void waitForAddressField(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(addressField));
    }

    public void waitForPickFileBtn(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(pickFileButton));
    }

    public void waitForAddFileBtn(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(addFileButton));
    }
}
