package com.browserstack.run_first_test.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.browserstack.run_first_test.pages.*;
import com.browserstack.run_first_test.utils.*;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ThemeTest {
    private DesiredCapabilities capabilities;
    private AppiumDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;
    private SettingsPage settingsPage;

    @Before
    public void setUp() {
        switch (Capabilities.platformName) {
            case "iOS": {
                setIOSCapabilities();
                break;
            }
            case "Android": {
                setAndroidCapabilities();
                break;
            }
            default: {
                throw new RuntimeException("Incorrect platform");
            }
        }
        resetApp();

        loginPage = new LoginPage(driver);
        mailPage = new MailPage(driver);
        settingsPage = new SettingsPage(driver);
    }

    private void setIOSCapabilities() {
        this.capabilities = new DesiredCapabilities();
        JSONObject appiumJson = JSONService.readJsonFromFile(this.getClass().getClassLoader().getResource("iosSim.json").getPath());
        JSONObject caps = JSONService.getCapabilities(appiumJson);
        caps.keySet().forEach(keyStr -> this.capabilities.setCapability(keyStr, caps.get(keyStr)));
        try {
            this.driver = new IOSDriver<MobileElement>(new URL(JSONService.getUrl(appiumJson)), this.capabilities);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    private void setAndroidCapabilities() {
        this.capabilities = new DesiredCapabilities();
//        JSONObject appiumJson = utils.JSONService.readJsonFromFile(this.getClass().getClassLoader().getResource("capabilities/androidSim.json").getPath());
        JSONObject appiumJson = JSONService.readJsonFromFile("src/test/resources/capabilities/androidSim.json");
        JSONObject caps = JSONService.getCapabilities(appiumJson);
        caps.keySet().forEach(keyStr -> this.capabilities.setCapability(keyStr, caps.get(keyStr)));
        try {
            this.driver = new AndroidDriver<>(new URL(JSONService.getUrl(appiumJson)), this.capabilities);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void changeTheme() {
        //open mail
        loginPage.waitForOpenMailBtn(30);
        loginPage.clickOpenMailButton();

        //open menu
        mailPage.waitForMenuBtn(10);
        mailPage.clickMenuBtn();

        //click settings
        mailPage.waitForSettingsButton(5);
        mailPage.clickSettingsBtn();

        //click theme and choose dark
        settingsPage.waitForThemeValue(10);
        settingsPage.clickThemeLabel();
        settingsPage.clickDarkThemeOption();

        //get theme name
        settingsPage.waitForThemeValue(10);
        String result = settingsPage.getThemeValue();

        Assert.assertEquals("Темная", result);
    }

    @After
    public void teardown() {
        if (driver != null) driver.quit();
    }

    private void resetApp() {
        driver.resetApp();
    }
}
