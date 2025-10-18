package com.loonycorn.learningselenium.myTests;

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

public class MyFirstTests {

    private WebDriver driver;
    private static final String SITE = "https://courses.ultimateqa.com";

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
    public void loginTest() throws InterruptedException {

       driver.get(SITE);

        WebElement  loginButtonEl = driver.findElement(By.cssSelector("a[href='/users/sign_in']"));
        Assert.assertTrue(loginButtonEl.isEnabled());
        Assert.assertTrue(loginButtonEl.isDisplayed());

        Assert.assertEquals(loginButtonEl.getText(), "Sign In");
        loginButtonEl.click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://courses.ultimateqa.com/users/sign_in");
        WebElement  creatNewAccountEl = driver.findElement(By.cssSelector("a[href='/users/sign_up']"));

        Assert.assertTrue(creatNewAccountEl.isEnabled());
        Assert.assertTrue(creatNewAccountEl.isDisplayed());

        Assert.assertEquals(creatNewAccountEl.getText(), "Create a new account");
        creatNewAccountEl.click();

        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(7));
        wait.until(ExpectedConditions.urlToBe("https://courses.ultimateqa.com/users/sign_up"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.sign-up__container")));
         wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"user[first_name]\"]")));

        WebElement usernameInputEl = driver.findElement(By.xpath("//*[@id=\"user[first_name]\"]"));
        WebElement userLestNameInputEl = driver.findElement(RelativeLocator.with(By.tagName("input")).toRightOf(usernameInputEl));
        WebElement userEmailInputEl = driver.findElement(RelativeLocator.with(By.tagName("input")).below(userLestNameInputEl));

        WebElement passwordInputEl = driver.findElement(RelativeLocator.with(By.tagName("input")).below(userEmailInputEl));
        WebElement checkbox1El = driver.findElement(By.xpath("//*[@id=\"user[terms]\"]"));

        usernameInputEl.sendKeys("Avior");
        userLestNameInputEl.sendKeys("Kasay");
        userEmailInputEl.sendKeys("aviork20@gmail.com");
        passwordInputEl.sendKeys("password123");

        Assert.assertFalse(checkbox1El.isSelected());
        checkbox1El.click();
        Assert.assertTrue(checkbox1El.isSelected());
        WebElement signUpBtn = driver.findElement(By.cssSelector("button[data-callback='onSubmit']"));


        wait.until(ExpectedConditions.elementToBeClickable(signUpBtn));
        Assert.assertTrue(signUpBtn.isEnabled());
        signUpBtn.click();
        wait.until(ExpectedConditions.urlToBe("https://courses.ultimateqa.com/collections"));

    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    }















