package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class LinksTest {

    private static final String SITE =
            "file:///Users/vitthalsrinivasan/IdeaProjects/learning-selenium/src/test/java/templates/broken_links.html";

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
    public void testTotalNumberOfLinks() {
        driver.get(SITE);

        List<WebElement> links = driver.findElements(By.tagName("a"));
        int actualCount = links.size();

        Assert.assertEquals(actualCount, 10,
                "Total number of links does not match expected count");
    }

    @Test
    public void testBrokenLinks() {
        driver.get(SITE);

        List<WebElement> links = driver.findElements(By.tagName("a"));

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            if (url != null && !url.isEmpty()) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    connection.setConnectTimeout(10000);
                    connection.connect();

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        System.out.println("Link is working fine: " + url);
                    } else {
                        System.out.println("Broken link found: " + url);
                    }
                } catch (Exception e) {
                    System.out.println("Exception occurred while checking link: " + url);
                    e.printStackTrace();
                }
            }
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
