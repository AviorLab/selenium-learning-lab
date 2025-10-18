package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.function.Function;

public class WaitTest {

    private static final String SITE = "https://testpages.eviltester.com/styled/index.html";

    private WebDriver driver;

    private long startTime;
    private double duration;

    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        startTime = System.currentTimeMillis();
    }

    private Function<WebDriver, WebElement> buttonBecomesClickable(By locator) {
        return driver -> {
            WebElement element = driver.findElement(locator);
            String disabled = element.getAttribute("disabled");
            return disabled == null || !disabled.equals("true") ? element : null;
        };
    }

    @Test
    public void buttonClickTest() {
        driver.get(SITE);

        WebElement dynamicButtonsLinkEl = driver.findElement(
                By.linkText("Dynamic Buttons Challenge 02"));
        dynamicButtonsLinkEl.click();

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class);

        WebElement startButtonEl = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("button00")));
        startButtonEl.click();

        WebElement oneButtonEl = wait.until(
                buttonBecomesClickable(By.id("button01")));
        oneButtonEl.click();

        WebElement twoButtonEl = wait.until(
                buttonBecomesClickable(By.id("button02")));
        twoButtonEl.click();

        WebElement threeButtonEl = wait.until(
                buttonBecomesClickable(By.id("button03")));
        threeButtonEl.click();

        WebElement allButtonsClickedMessageEl = driver.findElement(By.id("buttonmessage"));
        String message = allButtonsClickedMessageEl.getText();

        Assert.assertEquals(message, "All Buttons Clicked",
                "Message after clicking all buttons is incorrect.");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        long endTime = System.currentTimeMillis();
        duration = (endTime - startTime) / 1000.0;
        System.out.println("Total time taken: " + duration + " seconds");
    }
}
