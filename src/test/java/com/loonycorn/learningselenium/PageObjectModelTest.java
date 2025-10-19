package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class PageObjectModelTest {

    private WebDriver driver;
    private static final String SITE = "https://www.saucedemo.com/";

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
    public void testLogin() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");

        delay();

        loginButton.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login failed!");

        delay();
    }

    @Test(dependsOnMethods = "testLogin")
    public void testAddBackpackToCart() {
        WebElement backpackLink = driver.findElement(By.linkText("Sauce Labs Backpack"));
        backpackLink.click();

        WebElement addToCartButton = driver.findElement(By.cssSelector(".btn_inventory"));
        addToCartButton.click();

        WebElement cartButton = driver.findElement(By.cssSelector(".btn_inventory"));
        Assert.assertEquals(cartButton.getText(), "Remove", "Button text did not change");

        delay();

        driver.navigate().back();
    }

    @Test(dependsOnMethods = "testAddBackpackToCart")
    public void testAddFleeceJacketToCart() {
        WebElement fleeceJacketLink = driver.findElement(By.linkText("Sauce Labs Fleece Jacket"));
        fleeceJacketLink.click();

        WebElement addToCartButton = driver.findElement(By.cssSelector(".btn_inventory"));
        addToCartButton.click();

        WebElement cartButton = driver.findElement(By.cssSelector(".btn_inventory"));
        Assert.assertEquals(cartButton.getText(), "Remove", "Button text did not change");

        delay();

        driver.navigate().back();
    }

    @Test(dependsOnMethods = {"testAddBackpackToCart", "testAddFleeceJacketToCart"})
    public void testCart() {
        WebElement checkoutButton = driver.findElement(By.cssSelector(".shopping_cart_link"));
        checkoutButton.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("cart.html"), "Cart page not loaded");

        WebElement backpackNameInCart = driver.findElement(By.xpath("//div[@class='inventory_item_name'][text()='Sauce Labs Backpack']"));
        Assert.assertNotNull(backpackNameInCart, "Sauce Labs Backpack not found in the cart");

        WebElement fleeceJacketNameInCart = driver.findElement(By.xpath("//div[@class='inventory_item_name'][text()='Sauce Labs Fleece Jacket']"));
        Assert.assertNotNull(fleeceJacketNameInCart, "Sauce Labs Fleece Jacket not found in the cart");

        WebElement cartItemCount = driver.findElement(By.cssSelector(".shopping_cart_badge"));
        Assert.assertEquals(cartItemCount.getText(), "2", "Incorrect number of items in the cart");

        WebElement continueButton = driver.findElement(By.cssSelector(".btn_action"));
        Assert.assertEquals(continueButton.getText(), "Checkout", "Incorrect button text on the checkout page");

        delay();
    }

    @Test(dependsOnMethods = "testCart")
    public void testCheckout() {
        WebElement checkoutButton = driver.findElement(By.cssSelector(".checkout_button"));
        checkoutButton.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"), "Checkout page not loaded");

        WebElement firstNameField = driver.findElement(By.id("first-name"));
        WebElement lastNameField = driver.findElement(By.id("last-name"));
        WebElement zipCodeField = driver.findElement(By.id("postal-code"));

        firstNameField.sendKeys("Peter");
        lastNameField.sendKeys("Hank");
        zipCodeField.sendKeys("12345");

        Assert.assertEquals(firstNameField.getAttribute("value"), "Peter", "First name field value is incorrect");
        Assert.assertEquals(lastNameField.getAttribute("value"), "Hank", "Last name field value is incorrect");
        Assert.assertEquals(zipCodeField.getAttribute("value"), "12345", "Zip code field value is incorrect");

        delay();

        WebElement continueCheckoutButton = driver.findElement(By.cssSelector(".cart_button"));
        continueCheckoutButton.click();

        delay();
    }

    @Test(dependsOnMethods = "testCheckout")
    public void testFinalCheckout() {
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"), "Checkout page not loaded");

        WebElement paymentInfoValue = driver.findElement(By.cssSelector(".summary_value_label[data-test='payment-info-value']"));
        Assert.assertEquals(paymentInfoValue.getText(), "SauceCard #31337");

        WebElement shippingInfoValue = driver.findElement(By.cssSelector(".summary_value_label[data-test='shipping-info-value']"));
        Assert.assertEquals(shippingInfoValue.getText(), "Free Pony Express Delivery!");

        WebElement totalLabel = driver.findElement(By.cssSelector(".summary_total_label[data-test='total-label']"));
        Assert.assertEquals(totalLabel.getText(), "Total: $86.38");

        delay();

        WebElement finishButton = driver.findElement(By.id("finish"));
        finishButton.click();

        delay();
    }

    @Test(dependsOnMethods = "testFinalCheckout")
    public void testOrderCompletion() {
        WebElement header = driver.findElement(By.cssSelector("[data-test='complete-header']"));
        Assert.assertEquals(header.getText(), "Thank you for your order!");

        WebElement text = driver.findElement(By.cssSelector("[data-test='complete-text']"));
        Assert.assertEquals(text.getText(), "Your order has been dispatched, and will arrive just as fast as the pony can get there!");

        delay();
    }


    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}