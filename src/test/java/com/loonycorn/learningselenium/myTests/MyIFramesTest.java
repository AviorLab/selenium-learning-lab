package com.loonycorn.learningselenium.myTests;
import org.openqa.selenium.WebDriver;
import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.WebDriverWait;


public class MyIFramesTest {
    private static final String SITE =
            "https://the-internet.herokuapp.com/nested_frames";

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }


    @Test
    public void testNumberOfIFrames()   {
        driver.get(SITE);

        int total = countFramesRecursively();
        Assert.assertEquals(total, 5, "Number of frames is not as expected");


    }

    private int countFramesRecursively() {

        int count = driver.findElements(By.tagName("frame")).size();

        for (WebElement f : driver.findElements(By.tagName("frame"))) {
            driver.switchTo().frame(f);
            count += countFramesRecursively();
            driver.switchTo().parentFrame();
        }
        return count;
    }

    @Test

    public void  testCopyTextFromMiddleFrame()  {

        driver.get(SITE);
        driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-middle");

        WebElement middleEl = driver.findElement(By.id("content"));
        String middleText = middleEl.getText();

        System.out.println("Middle heading: " + middleText);
        Assert.assertEquals( middleText,  "MIDDLE", "Text  does not match");

    }






    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
