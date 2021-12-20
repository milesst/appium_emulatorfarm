package com.browserstack.run_first_test.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SettingsPage {
    private AppiumDriver driver;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Системная') or contains(@text, 'Темная')]")
    private AndroidElement themeValue;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Тема']")
    private AndroidElement themeLabel;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Очистить кэш']")
    private AndroidElement clearCacheButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='ОЧИСТИТЬ']")
    private AndroidElement confirmClearCacheBtn;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[13]/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.TextView")
    private AndroidElement cacheSizeLabel;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Темная') and contains(@resource-id, 'ru.yandex.mail:id/pref_popup_menu_item')]")
    private AndroidElement darkThemeOption;

    public SettingsPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getCacheSize() { return cacheSizeLabel.getText(); }

    public void clickClearCacheBtn() { clearCacheButton.click(); }

    public void clickConfirmClearCacheBtn() { confirmClearCacheBtn.click(); }

    public void clickThemeLabel() { themeLabel.click(); }

    public void clickDarkThemeOption() { darkThemeOption.click(); }

    public void waitForClearCacheBtn(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(clearCacheButton));
    }

    public String getThemeValue() {
        return themeValue.getText();
    }

    public void waitForThemeValue(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(themeValue));
    }

    public void waitForCacheLabel(int timeInSeconds) {
        new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.visibilityOf(cacheSizeLabel));
    }
}
