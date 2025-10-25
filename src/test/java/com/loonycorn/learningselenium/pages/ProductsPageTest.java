package com.loonycorn.learningselenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductsPageTest {
    private final WebDriver driver;

    public ProductsPageTest(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isPageOpened() {

        return driver.getCurrentUrl().contains("inventory.html");
    }

    public void navigateToProductPage(String productName) {
        WebElement productLink = driver.findElement(By.linkText(productName));
        productLink.click();
    }

    public void navigateToCart() {
        WebElement cartButton = driver.findElement(By.cssSelector(".shopping_cart_link"));
        cartButton.click();
    }
}
