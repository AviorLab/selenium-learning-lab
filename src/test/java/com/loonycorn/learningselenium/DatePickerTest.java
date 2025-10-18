package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class DatePickerTest {

    private static final String SITE = "https://jqueryui.com/datepicker/";

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
    public void simplePickDateTest() {
        driver.get(SITE);
        driver.switchTo().frame(0);

        WebElement datePickerInput = driver.findElement(By.id("datepicker"));
        datePickerInput.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-datepicker-div")));

        delay();

        WebElement dateCell = driver.findElement(By.xpath("//a[text()='24']"));
        dateCell.click();

        String selectedDate = datePickerInput.getAttribute("value");
        System.out.println("Selected date: " + selectedDate);

        delay();
    }


    @Test
    public void pickDateFromPreviousMonthTest() {
        driver.get(SITE);
        driver.switchTo().frame(0);

        WebElement datePickerInput = driver.findElement(By.id("datepicker"));
        datePickerInput.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-datepicker-div")));

        delay();

        WebElement prevMonthButton = driver.findElement(By.xpath("//span[text()='Prev']"));
        prevMonthButton.click();

        WebElement dateCell = driver.findElement(By.xpath("//a[text()='10']"));
        dateCell.click();

        String selectedDate = datePickerInput.getAttribute("value");
        System.out.println("Selected date: " + selectedDate);

        delay();
    }


    @Test
    public void pickDateInJanuaryTest() {
        driver.get(SITE);
        driver.switchTo().frame(0);

        WebElement datePickerInput = driver.findElement(By.id("datepicker"));
        datePickerInput.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ui-datepicker-div")));

        delay();

        WebElement prevMonthButton;
        while (!driver.findElement(By.className("ui-datepicker-month")).getText().equals("January")) {
            prevMonthButton = driver.findElement(By.xpath("//a[@title='Prev']"));
            prevMonthButton.click();
            delay();
        }

        WebElement dateCell = driver.findElement(By.xpath("//a[text()='10']"));
        dateCell.click();

        String selectedDate = datePickerInput.getAttribute("value");
        System.out.println("Selected date: " + selectedDate);

        delay();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
