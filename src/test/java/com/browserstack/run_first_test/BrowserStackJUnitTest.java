package com.browserstack.run_first_test;

import java.net.URL;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.FileReader;

import com.browserstack.run_first_test.pages.InboxPage;
import com.browserstack.run_first_test.pages.LoginPage;
import com.browserstack.run_first_test.utils.UserInfo;
import io.appium.java_client.AppiumDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BrowserStackJUnitTest {
    public AndroidDriver<AndroidElement> driver;
    private LoginPage loginPage;
    private InboxPage inboxPage;

    private static JSONObject config;

    @Parameter(value = 0)
    public int taskID;

    @Parameters
    public static Iterable<? extends Object> data() throws Exception {
        List<Integer> taskIDs = new ArrayList<Integer>();

        JSONParser parser = new JSONParser();
        config = (JSONObject) parser.parse(new FileReader("src/test/resources/com/browserstack/run_first_test/first.conf.json"));
        int envs = ((JSONArray) config.get("environments")).size();

        for (int i = 0; i < envs; i++) {
            taskIDs.add(i);
        }

        return taskIDs;
    }

    @Before
    public void setUp() throws Exception {
        JSONArray envs = (JSONArray) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(taskID);
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("username");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("access_key");
        }

        String app = System.getenv("BROWSERSTACK_APP_ID");
        if (app != null && !app.isEmpty()) {
            capabilities.setCapability("app", app);
        }

        driver = new AndroidDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void test() throws Exception {
        //mail choose
        loginPage.waitForYandexOption(10);
        loginPage.clickYandex();

        //login
        loginPage.waitForLoginField(20);
        loginPage.typeLogin(UserInfo.login);

        //next
        loginPage.clickNextButton();

        //password
        loginPage.waitForPasswordField(20);
        loginPage.typePassword(UserInfo.password);

        //next
        loginPage.clickNextButton();
        List<AndroidElement> list = driver.findElementsByClassName("//*[@class=android.widget.Button]");
        list.get(0).click();
        loginPage.waitForOpenMailBtn(20);

        //open mail
        loginPage.clickOpenMailButton();
        inboxPage.waitForHeader(20);

        //check value
        String header = inboxPage.getHeaderText();
        Assert.assertEquals("Входящие", header);
    }

    @After
    public void tearDown() throws Exception {
        // Invoke driver.quit() to indicate that the test is completed. 
        // Otherwise, it will appear as timed out on BrowserStack.
        driver.quit();
    }
}
