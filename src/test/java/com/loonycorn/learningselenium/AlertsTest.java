package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class AlertsTest {

    private static final String SITE =
            "https://testpages.eviltester.com/styled/alerts/alert-test.html";

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


    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    public void testAlert() {
        driver.get(SITE);

        delay();

        driver.findElement(By.id("alertexamples")).click();

        delay();

        boolean isAlertPresent = isAlertPresent();
        Assert.assertTrue(isAlertPresent, "Alert not present");
    }

    @Test
    public void testGetAlertTextAndAccept() {
        driver.get(SITE);
        driver.findElement(By.id("alertexamples")).click();

        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();
        System.out.println("Alert text: " + alertText);

        Assert.assertEquals(alertText, "I am an alert box!",
                "Alert text mismatch");

        delay();

        alert.accept();

        Assert.assertEquals(driver.findElement(By.id("alertexplanation")).getText(),
                "You triggered and handled the alert dialog");

//        alert.accept();

        delay();
    }

    @Test
    public void testGetAlertTextAndDismiss() {
        driver.get(SITE);
        driver.findElement(By.id("alertexamples")).click();

        Alert alert = driver.switchTo().alert();

        String alertText = alert.getText();
        System.out.println("Alert text: " + alertText);

        Assert.assertEquals(alertText, "I am an alert box!",
                "Alert text mismatch");

        delay();

        alert.dismiss();

        Assert.assertEquals(driver.findElement(By.id("alertexplanation")).getText(),
                "You triggered and handled the alert dialog");

        delay();
    }


    @Test
    public void testWaitForAlert() {
        driver.get(SITE);
        driver.findElement(By.id("alertexamples")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();
        System.out.println("Alert text: " + alertText);

        Assert.assertEquals(alertText, "I am an alert box!",
                "Alert text mismatch");

        delay();

        alert.dismiss();

        Assert.assertEquals(driver.findElement(By.id("alertexplanation")).getText(),
                "You triggered and handled the alert dialog");

        delay();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
