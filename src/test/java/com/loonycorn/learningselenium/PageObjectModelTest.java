package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.pages.*;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class PageObjectModelTest {

    private static final String SITE = "https://www.saucedemo.com/";

    private static WebDriver driver;
    private LoginPageTest loginPage;
    private ProductsPageTest productsPage;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        loginPage = new LoginPageTest(driver);
        productsPage = new ProductsPageTest(driver);
        driver.get(SITE);
    }

    private static void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.Test
    @Test
    public void testLogin() {
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isPageOpened(), "Login failed!");
        delay();
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
