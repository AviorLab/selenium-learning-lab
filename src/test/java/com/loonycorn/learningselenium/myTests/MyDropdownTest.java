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

public class MyDropdownTest {

    private static final String SITE = "https://testautomationpractice.blogspot.com/";
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }


    @Test
    public void myDropdownTest() {
        driver.get("https://testautomationpractice.blogspot.com/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement box = wait.until(ExpectedConditions.elementToBeClickable(By.id("comboBox")));
        box.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdown")));

        List<WebElement> options = driver.findElements(By.cssSelector("#dropdown .option"));
        Assert.assertTrue(!options.isEmpty(), "No options in custom dropdown");

        String wanted = "Item 100";
        WebElement wantedOpt = options.stream()
                .filter(o -> o.getText().trim().equals(wanted))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Option not found: " + wanted));
        wantedOpt.click();

        // מאמתים שהבחירה נכנסה לשדה (הטקסט של ה-input)
        String actual = driver.findElement(By.id("comboBox")).getAttribute("value");
        Assert.assertEquals(actual, wanted, "Incorrect default option in subcategory dropdown");
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
