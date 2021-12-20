package com.browserstack.run_first_test.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;

public class Utils {
    public static void scroll(AppiumDriver driver, int fromX, int fromY, int toX, int toY) {
//        TouchAction touchAction = new TouchAction(driver);
////        touchAction.longPress(PointOption.point(fromX, fromY)).moveTo(PointOption.point(toX, toY)).release().perform();
        new TouchAction(driver).longPress(PointOption.point(fromX, fromY)).moveTo(PointOption.point(toX, toY)).release().perform();
    }

    public static void tapByCoordinates(AppiumDriver driver, int x,  int y) {
        new TouchAction(driver)
                .tap(PointOption.point(x,y))
                .waitAction(waitOptions(ofMillis(250))).perform();
    }
}
