package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;


public class RelativeLocatorTest {

    private WebDriver driver;
    private static final String SITE = "https://testpages.eviltester.com/styled/index.html";

    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void relativeLocatorsTest() {
        driver.get(SITE);

        WebElement basicFormLinkEl = driver.findElement(By.linkText("HTML Form Example"));

        basicFormLinkEl.click();

        delay();

        WebElement usernameInputEl = driver.findElement(By.name("username"));
        usernameInputEl.sendKeys("Bob Baker");

        WebElement passwordInputEl = driver.findElement(
                RelativeLocator.with(By.tagName("input")).below(usernameInputEl));
        passwordInputEl.sendKeys("password123");

        WebElement commentsInputEl = driver.findElement(
                RelativeLocator.with(By.tagName("textarea")).below(passwordInputEl));
        commentsInputEl.clear();
        commentsInputEl.sendKeys("Some comments here");

        delay();


        WebElement submitInputEl = driver.findElement(By.cssSelector("input[value='submit']"));

        submitInputEl.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("_valueusername")));

        WebElement usernameListItemEl = driver.findElement(By.id("_valueusername"));
        Assert.assertEquals(usernameListItemEl.getText(), "Bob Baker");

        WebElement passwordListItemEl = driver.findElement(
                RelativeLocator.with(By.tagName("li")).below(usernameListItemEl));
        Assert.assertEquals(passwordListItemEl.getText(), "password123");

        WebElement commentsListItemEl = driver.findElement(
                RelativeLocator.with(By.tagName("li")).below(passwordListItemEl));
        Assert.assertEquals(commentsListItemEl.getText(), "Some comments here");

        delay();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
