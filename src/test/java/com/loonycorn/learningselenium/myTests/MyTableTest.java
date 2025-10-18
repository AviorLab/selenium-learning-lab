package com.loonycorn.learningselenium.myTests;

import org.openqa.selenium.WebDriver;
import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.WebDriverWait;

public class MyTableTest {

    private static final String SITE =
            "https://testautomationpractice.blogspot.com/";

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    @Test
    public void testNumberOfRows() {
        driver.get(SITE);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector("#rows tr"), 3));

        List<WebElement> rows = driver.findElements(By.cssSelector("#rows tr"));
            Assert.assertEquals(rows.size(), 4, "Incorrect number of rows in the outer table");
        System.out.println("Outer table rows: " + rows);

    }

    @Test
    public void testTableSymbolsAfterRender() {
        driver.get(SITE);
        driver.get("https://testautomationpractice.blogspot.com/"); // לדוגמה

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // המתן עד שיופיעו לפחות 4 שורות בטבלה
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector("#rows tr"), 3));

        // קח את כל התאים בעמודות הרלוונטיות (למשל CPU, Memory, Network)
        List<WebElement> cells = driver.findElements(By.cssSelector("#rows tr td"));

        for (WebElement cell : cells) {
            String text = cell.getText();

            Assert.assertFalse(text.isEmpty(), "Cell is empty!");
            boolean hasSymbol = text.contains("%")||text.contains("MB")||text.contains("Mbps")||text.contains("MB/s");

            if (text.matches(".*\\d.*")) Assert.assertTrue(hasSymbol, "Expected symbol missing in value" + text);
        }
    }
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
