package com.browserstack.run_first_test.tests;

import com.browserstack.run_first_test.pages.*;
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

public class MailTest {
    private DesiredCapabilities capabilities;
    private AppiumDriver driver;
    private InboxPage inboxPage;
    private LoginPage loginPage;
    private OutboxPage outboxPage;

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
        inboxPage = new InboxPage(driver);
        outboxPage = new OutboxPage(driver);
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
//    @Before
//    public void setUp() throws MalformedURLException, URISyntaxException {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("deviceName", Capabilities.deviceName);
//        capabilities.setCapability("platformName", Capabilities.platformName);
//        capabilities.setCapability("platformVersion", Capabilities.platformVersion);
//        capabilities.setCapability("udid", Capabilities.udid);
//        capabilities.setCapability("appPackage", Capabilities.appPackage);
//        capabilities.setCapability("appActivity", Capabilities.appActivity);
//
//        URL resource = getClass().getClassLoader().getResource("yandex-mail-8-4-1.apk");
//        capabilities.setCapability("app", resource.toURI());
//
//        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
//        loginPage = new LoginPage(driver);
//        inboxPage = new InboxPage(driver);
//        outboxPage = new OutboxPage(driver);
//    }

    @Test
    public void goToInbox() {
        //open mail
        loginPage.waitForOpenMailBtn(30);
        loginPage.clickOpenMailButton();

        //open menu
        inboxPage.waitForMenuBtn(10);
        inboxPage.clickMenuBtn();

        //click inbox
        inboxPage.waitForInboxButton(10);
        inboxPage.clickInboxBtn();

        String result = inboxPage.getHeaderText();
        Assert.assertEquals("Входящие", result);

        //open menu and click outbox
        inboxPage.waitForMenuBtn(10);
        inboxPage.clickMenuBtn();

        inboxPage.waitForOutboxButton(10);
        inboxPage.clickOutboxBtn();

        //open and close menu
        outboxPage.waitForMenuBtn(15);
        outboxPage.clickMenuBtn();
        outboxPage.waitForInboxButton(5);
        Utils.scroll(driver, 350, 600, 10, 600);

        //get header text
        result = outboxPage.getHeaderText();
        Assert.assertEquals("Отправленные", result);
    }

    @After
    public void teardown() {
        if (driver != null) driver.quit();
    }

    private void resetApp() {
        driver.resetApp();
    }
}
