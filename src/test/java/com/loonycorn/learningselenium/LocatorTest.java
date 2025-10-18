package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class LocatorTest {

    private WebDriver driver;

    private static final String SITE = "https://www.demoblaze.com/";
    private static final String CART = "https://www.demoblaze.com/cart.html";
    private static final String SITE2 = "https://www.saucedemo.com/";


    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }

    private static void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void idLocatorsTest() {
        driver.get(SITE);
        driver.get(CART);

        delay();

        WebElement logoEl = driver.findElement(By.id("nava"));
        System.out.println("Element text: " + logoEl.getText());

        Assert.assertTrue(logoEl.isDisplayed());
        Assert.assertTrue(logoEl.isEnabled());
        Assert.assertFalse(logoEl.isSelected());

        logoEl.click();

        delay();
        Assert.assertEquals(driver.getCurrentUrl(), SITE+"index.html");
         logoEl = driver.findElement(By.id("nava"));

        logoEl.click();
    }

    @Test
    public void classLocatorsTest() {
        driver.get(SITE);

        WebElement samsungGalaxyEl = driver.findElement(By.className("hrefch"));

        Assert.assertTrue(samsungGalaxyEl.isDisplayed());
        Assert.assertTrue(samsungGalaxyEl.isEnabled());

        samsungGalaxyEl.click();

        WebElement addToCartButtonEl = driver.findElement(By.className("btn-success"));

        addToCartButtonEl.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Wait for up to 10 seconds
        wait.until(ExpectedConditions.alertIsPresent());

        // We will learn more about alerts in a later learning path
        Alert alert = driver.switchTo().alert();
        alert.accept();

        WebElement cartEl = driver.findElement(By.id("cartur"));

        cartEl.click();

        WebElement cartItemEl = driver.findElement(By.className("success"));

        Assert.assertTrue(cartItemEl.isDisplayed());
        Assert.assertTrue(cartItemEl.isEnabled());
    }

    @Test
    public void tagNameLocatorTest() {
        driver.get(SITE);

        WebElement imgEl = driver.findElement(By.tagName("img"));
        String srcAttr = imgEl.getAttribute("src");

        Assert.assertNotNull(srcAttr);
        System.out.println("Image source: " + srcAttr);
    }

    @Test
    public void tagNameLocatorsTest1() {
        driver.get(SITE);

        List <WebElement> imgEls = driver.findElements(By.tagName("img"));

        for (WebElement imgEl : imgEls) {
            String srcAttr = imgEl.getAttribute("src");
            Assert.assertNotNull(srcAttr);
            System.out.println("Image source: " + srcAttr);
        }
    }

    @Test
    public void tagNameLocatorsTest2() {
        driver.get(SITE);

        List<WebElement> categoriesEls = driver.findElements(By.id("itemc"));

        for (WebElement categoryEl : categoriesEls) {
            System.out.println("------------------");
            System.out.println("Category clicked: " + categoryEl.getText());

            categoryEl.click();

            delay();

            WebElement containerEl = driver.findElement(By.id("tbodyid"));
            List<WebElement> linkEls = containerEl.findElements(By.tagName("a"));

            for (WebElement linkEl : linkEls) {
                String hrefAttr = linkEl.getAttribute("href");
                Assert.assertNotNull(hrefAttr);
                System.out.println("URL: " + hrefAttr);
            }
        }
    }

    @Test
    public void nameLocatorsTest() {
        driver.get(SITE);

        WebElement formEl = driver.findElement(By.name("frm"));

        Assert.assertTrue(formEl.isEnabled());
        Assert.assertTrue(formEl.isDisplayed());

        List<WebElement> buttonsEl = formEl.findElements(By.tagName("button"));

        // Make sure we have two buttons and both displayed
        Assert.assertEquals(buttonsEl.size(), 2);
        Assert.assertTrue(buttonsEl.get(0).isDisplayed());
        Assert.assertTrue(buttonsEl.get(1).isDisplayed());

        System.out.println("Button text: " + buttonsEl.get(0).getText());
        System.out.println("Button text: " + buttonsEl.get(1).getText());
    }

    @Test
    public void cssSelectorsLocatorsTest1() {
        driver.get(SITE2);

        // Select by .className selector
        WebElement loginEl = driver.findElement(By.cssSelector("div.login-box"));

        Assert.assertTrue(loginEl.isEnabled());
        Assert.assertTrue(loginEl.isDisplayed());

        // Select by #id (with or without tag name)
        WebElement usernameEl = loginEl.findElement(By.cssSelector("#user-name"));
        WebElement passwordEl = loginEl.findElement(By.cssSelector("input#password"));

        usernameEl.sendKeys("standard_user");
        passwordEl.sendKeys("secret_sauce");

        delay();

        WebElement submitButtonEl = loginEl.findElement(By.cssSelector(".submit-button"));
        submitButtonEl.submit();

        delay();

        Assert.assertEquals(driver.getCurrentUrl(), SITE2 + "inventory.html");
    }

    @Test
    public void cssSelectorsLocatorsTest2() {
        driver.get(SITE2);

        // "<tagname>[class='<class value>']"
        WebElement loginEl = driver.findElement(By.cssSelector("div[class='login-box']"));

        Assert.assertTrue(loginEl.isEnabled());
        Assert.assertTrue(loginEl.isDisplayed());

        // "<tagname>[id='<id value>']"
        WebElement usernameEl = loginEl.findElement(By.cssSelector("input[id='user-name']"));
        WebElement passwordEl = loginEl.findElement(By.cssSelector("input[id='password']"));

        usernameEl.sendKeys("standard_user");
        passwordEl.sendKeys("secret_sauce");

        delay();

        // "<tagname>[attr='<attr value>']"
        WebElement submitButtonEl = loginEl.findElement(By.cssSelector("input[data-test='login-button']"));
        submitButtonEl.submit();

        delay();

        Assert.assertEquals(driver.getCurrentUrl(), SITE2 + "inventory.html");

        // Add first product to cart
        WebElement addToCartBackpackEl = driver.findElement(By.cssSelector(
                "button.btn_inventory[name='add-to-cart-sauce-labs-backpack']"));
        addToCartBackpackEl.click();

        // Add second product to cart
        WebElement addToCartOnesieEl = driver.findElement(By.cssSelector(
                "button#add-to-cart-sauce-labs-onesie[data-test='add-to-cart-sauce-labs-onesie']"));
        addToCartOnesieEl.click();

        delay();

        // Click on the shopping cart link using the prefix of an attribute
        WebElement shoppingCartLinkEl = driver.findElement(By.cssSelector(
                "a[class^='shopping_cart']"));
        shoppingCartLinkEl.click();

        delay();

        // Click on the continue shopping button using the suffix of an attribute
        WebElement continueShoppingButtonEl = driver.findElement(By.cssSelector(
                "button[id$='continue-shopping']"));
        continueShoppingButtonEl.click();

        delay();
    }

    @Test
    public void xpathSelectorsLocatorsTest() {
        driver.get(SITE2);

        // "<tagname>[class='<class value>']"
        WebElement loginEl = driver.findElement(By.cssSelector("div[class='login-box']"));
        Assert.assertTrue(loginEl.isEnabled());
        Assert.assertTrue(loginEl.isDisplayed());

        // "<tagname>[id='<id value>']"
        WebElement usernameEl = loginEl.findElement(By.cssSelector("input[id='user-name']"));
        WebElement passwordEl = loginEl.findElement(By.cssSelector("input[id='password']"));

        usernameEl.sendKeys("standard_user");
        passwordEl.sendKeys("secret_sauce");

        delay();
        // "<tagname>[attr='<attr value>']"
        WebElement submitButtonEl = loginEl.findElement(By.cssSelector("input[data-test='login-button']"));
        submitButtonEl.submit();

        delay();
        Assert.assertEquals(driver.getCurrentUrl(), SITE2 + "inventory.html");
        WebElement addToCartBackpackLinkEl = driver.findElement(By.linkText("Sauce Labs Backpack"));
        addToCartBackpackLinkEl.click();

        delay();
        driver.navigate().back();
        WebElement addToCartOnesieLinkEl = driver.findElement(By.partialLinkText("Onesie"));
        addToCartOnesieLinkEl.click();

        delay();
        driver.navigate().back();


    }
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
