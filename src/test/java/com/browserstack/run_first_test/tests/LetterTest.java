package com.browserstack.run_first_test.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
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

public class LetterTest {
    private DesiredCapabilities capabilities;
    private AppiumDriver driver;
    private LoginPage loginPage;
    private MailPage mailPage;
    private NewLetterPage newLetterPage;

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
//        resetApp();

        loginPage = new LoginPage(driver);
        mailPage = new MailPage(driver);
        newLetterPage = new NewLetterPage(driver);
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
    public void sendLetter() throws InterruptedException {
        //open mail
        loginPage.waitForOpenMailBtn(30);
        loginPage.clickOpenMailButton();

        //click new letter
        mailPage.waitForNewLetterBtn(10);

        Integer expectedCount = Integer.parseInt(mailPage.getCounterValue()) + 1;

        mailPage.clickNewLetterBtn();

        //type address

        //permissions
//        if (driver.findElementById("com.android.permissioncontroller:id/permission_allow_button") != null)
//            driver.findElementById("com.android.permissioncontroller:id/permission_allow_button").click();
        setPermission(true);

        newLetterPage.waitForAddressField(10);
        newLetterPage.typeAddress("rraf-alla@yandex.ru");
        newLetterPage.clickAddressLabel();
        newLetterPage.clickAddressLabel();

        //attach file
        newLetterPage.clickAttachButton();

        //permissions
//        if (driver.findElementById("com.android.permissioncontroller:id/permission_deny_button") != null)
//            driver.findElementById("com.android.permissioncontroller:id/permission_deny_button").click();
//        if (driver.findElementById("com.android.permissioncontroller:id/permission_allow_button") != null)
//            driver.findElementById("com.android.permissioncontroller:id/permission_allow_button").click();
        setPermission(false);
        setPermission(true);

        newLetterPage.waitForPickFileBtn(15);
        newLetterPage.clickPickFileBtn();
        driver.findElementByXPath("//android.widget.TextView[@text='screenshot_45 (1).png']").click();
        newLetterPage.clickAddFileBtn();
        //send letter
        newLetterPage.clickSendBtn();

        //assert by letter count

        //hide rating popup
        mailPage.waitForRating(10);
        Utils.tapByCoordinates(driver, 300, 700);

//        new WebDriverWait(driver, 15).until(ExpectedConditions.textToBe(By.id("ru.yandex.mail:id/counter"), expectedCount.toString()));
        mailPage.waitForCounterChange(15, expectedCount);
        String newCount = mailPage.getCounterValue();
        Assert.assertEquals(expectedCount.toString(), newCount);
    }

    public void setPermission(boolean givePerm) {
        AndroidElement denyBtn = (AndroidElement) driver.findElementById("com.android.permissioncontroller:id/permission_deny_button");

        if (denyBtn != null) {
            if (givePerm)
                driver.findElementById("com.android.permissioncontroller:id/permission_allow_button").click();
            else
                denyBtn.click();
        }
    }

    @After
    public void teardown() {
        if (driver != null) driver.quit();
    }

    private void resetApp() {
        driver.resetApp();
    }
}
