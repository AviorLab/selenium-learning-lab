package com.loonycorn.learningselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class WebDriverTest {

    @Test
    public void navigateToPageUsingChrome() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://google.com");
        driver.quit();
    }

    @Test
    public void navigateToPageUsingFirefox() {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://google.com");
        driver.quit();

    }
}
