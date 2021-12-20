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

public class DeleteLetterTest {
    private DesiredCapabilities capabilities;
    private AppiumDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;

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
    public void deleteLastLetter() throws InterruptedException {
        //open mail
        loginPage.waitForOpenMailBtn(30);
        loginPage.clickOpenMailButton();

        //get current letter count and compute expected value
        mailPage.waitForCounter(20);
        String oldCounter = mailPage.getCounterValue();
        Integer expectedCounter = Integer.parseInt(oldCounter) - 1;

        //swipe left
//        Point letterCoords = mailPage.getCoordsOfLetter();
//        Utils.scroll(driver, mailPage.getCoordsOfTime().x, mailPage.getCoordsOfTime().y, mailPage.getCoordsOfTime().x-200, mailPage.getCoordsOfTime().y);
//        Utils.scroll(driver, 580, 600, 180, 600);

        //select letter
        mailPage.clickLetterAvatar();

        //click delete
        mailPage.waitForDeleteBtn(5);
        mailPage.clickDeleteLetterBtn();

        //hide rating popup
        mailPage.waitForRating(10);
        Utils.tapByCoordinates(driver, 300, 700);

        mailPage.waitForCounterChange(15, expectedCounter);
        String newCount = mailPage.getCounterValue();

        Assert.assertEquals(expectedCounter.toString(), newCount);
    }

    @After
    public void teardown() {
        if (driver != null) driver.quit();
    }

    private void resetApp() {
        driver.resetApp();
    }
}
