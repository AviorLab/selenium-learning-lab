package com.loonycorn.learningselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;


public class BlogTest {

    @Test
    public void chromeOptionsTest() {
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("stable");
        WebDriver driver = new ChromeDriver(options);
        options.addArguments("--start-maximized", "--incognito");

        driver.get("https://omayo.blogspot.com/");
        delay(5000);
        driver.quit();
    }

   @Test
//    public void chromeOptionsTest() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized", "--incognito");
//        WebDriver driver = new ChromeDriver(options);
//
//        driver.get("https://omayo.blogspot.com/");
//        delay(5000);
//        driver.quit();
//    }

    private void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
