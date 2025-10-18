package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class IFramesTest {

    private static final String SITE =
            "file:///Users/vitthalsrinivasan/IdeaProjects/learning-selenium/src/test/java/templates/nested_iframes.html";

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
    }

    @Test
    public void testNumberOfIFrames() {
        driver.get(SITE);

        int numberOfIFrames = countNestedIframes(driver.findElement(By.tagName("body")));
        Assert.assertEquals(numberOfIFrames, 3,
                "Number of iframes is not as expected");
    }

    private int countNestedIframes(WebElement element) {
        int count = 0;

        for (WebElement iframe : element.findElements(By.tagName("iframe"))) {
            count++;
            driver.switchTo().frame(iframe);

            count += countNestedIframes(driver.findElement(By.tagName("body")));
            driver.switchTo().parentFrame();
        }

        return count;
    }


    @Test
    public void testCopyH1FromIframe1ToTextArea() {
        driver.get(SITE);

        driver.switchTo().frame("iframe1");

        WebElement h1Element = driver.findElement(By.tagName("h1"));
        String h1Text = h1Element.getText();

        System.out.println("iFrame1 heading: " + h1Text);

        driver.switchTo().defaultContent();

        WebElement textarea = driver.findElement(By.tagName("textarea"));
        textarea.clear();
        textarea.sendKeys(h1Text);

        delay();
        Assert.assertEquals(textarea.getAttribute("value"), h1Text,
                "Text in textarea does not match h1 text from iframe1");
    }

    @Test
    public void testCopyH1FromIframe2ToTextArea() {
        driver.get(SITE);

        driver.switchTo().frame("iframe1");
        driver.switchTo().frame("iframe2");

        WebElement h1Element = driver.findElement(By.tagName("h1"));
        String h1Text = h1Element.getText();

        System.out.println("iFrame2 heading: " + h1Text);

        driver.switchTo().defaultContent();

        WebElement textarea = driver.findElement(By.tagName("textarea"));
        textarea.clear();
        textarea.sendKeys(h1Text);

        delay();

        Assert.assertEquals(textarea.getAttribute("value"), h1Text,
                "Text in textarea does not match h1 text from iframe2");
    }

    @Test
    public void testCopyH1FromIframe3ToTextArea() {
        driver.get(SITE);

        driver.switchTo().frame("iframe1");
        driver.switchTo().frame("iframe2");
        driver.switchTo().frame("iframe3");

        WebElement h1Element = driver.findElement(By.tagName("h1"));
        String h1Text = h1Element.getText();

        System.out.println("iFrame3 heading: " + h1Text);

        driver.switchTo().defaultContent();

        WebElement textarea = driver.findElement(By.tagName("textarea"));
        textarea.clear();
        textarea.sendKeys(h1Text);

        delay();

        Assert.assertEquals(textarea.getAttribute("value"), h1Text,
                "Text in textarea does not match h1 text from iframe3");
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
