package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class WindowManagementTest {

    private static final String WINDOW_SITE = "https://the-internet.herokuapp.com/windows";
    private static final String SCROLL_SITE = "https://the-internet.herokuapp.com/infinite_scroll";

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }    @Test
    public void testWindowSizeAndPosition() {
        driver.get(WINDOW_SITE);

        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().setSize(new Dimension(800, 600));

        String windowOneHandle = driver.getWindowHandle();

        delay();

        driver.switchTo().newWindow(WindowType.WINDOW);

        String windowTwoHandle = driver.getWindowHandle();

        driver.get(SCROLL_SITE);

        driver.manage().window().setSize(new Dimension(1000, 375));
        driver.manage().window().setPosition(new Point(0, 250));

        driver.switchTo().window(windowOneHandle);

        Dimension windowOneSize = driver.manage().window().getSize();
        Assert.assertEquals(windowOneSize.getWidth(), 800);
        Assert.assertEquals(windowOneSize.getHeight(), 600);

        Point windowOnePosition = driver.manage().window().getPosition();
        System.out.println("Window One Position: " + windowOnePosition);

        driver.switchTo().window(windowTwoHandle);

        Dimension windowTwoSize = driver.manage().window().getSize();
        Assert.assertEquals(windowTwoSize.getWidth(), 1000);
        Assert.assertEquals(windowTwoSize.getHeight(), 375);

        Point windowTwoPosition = driver.manage().window().getPosition();
        System.out.println("Window Two Position: " + windowTwoPosition);

        delay();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
