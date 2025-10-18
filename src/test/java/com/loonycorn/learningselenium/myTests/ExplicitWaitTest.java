package com.loonycorn.learningselenium.myTests;
import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.function.Function;


public class ExplicitWaitTest {

    private static final  String SITE1 = "https://testpages.eviltester.com/styled/index.html";
    private static final  String SITE2 = "https://the-internet.herokuapp.com/dynamic_loading";

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }


    @Test
    public void ExplicitWait(){

        driver.get(SITE1);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Expanding Div via CSS"))).click();
        By hiddenLink = By.cssSelector("a[href='expandeddiv.html']");
        Assert.assertTrue(driver.findElements(hiddenLink).isEmpty()
                        || !driver.findElement(hiddenLink).isDisplayed(),
                "Link should be hidden before hover");

        WebElement noticMeEl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".noticeme")));
        new Actions(driver).moveToElement(noticMeEl).perform();

        wait.until(ExpectedConditions.elementToBeClickable(hiddenLink)).click();

        wait.until(ExpectedConditions.urlContains("/styled/expandeddiv.html"));
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"),"You clicked the link in the Expanding div"));

        By backToExpanding = By.cssSelector("a[href='expandingdiv.html']");
        wait.until(ExpectedConditions.elementToBeClickable(backToExpanding)).click();

    }

@Test
    public  void DynamicallyLoadedPage1 (){

        driver.get(SITE2);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Example 1: Element on page that is hidden"))).click();

        wait.until(ExpectedConditions.textToBe(By.tagName("h3"),"Dynamically Loaded Page Elements"));

        By hiddenText = By.xpath("//*[@id=\"finish\"]/h4");
        Assert.assertTrue(driver.findElements(hiddenText).isEmpty()
                        || !driver.findElement(hiddenText).isDisplayed(),
                "Link should be hidden before hover");

        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("button"))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loading")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(hiddenText, "Hello World!"));

    }




    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
