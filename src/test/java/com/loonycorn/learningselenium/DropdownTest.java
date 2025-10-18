package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class DropdownTest {

    private static final String SITE =
            "file:///Users/vitthalsrinivasan/IdeaProjects/learning-selenium/src/test/java/templates/dynamic_dropdown.html";

    private static final String MULTISELECT_SITE =
            "file:///Users/vitthalsrinivasan/IdeaProjects/learning-selenium/src/test/java/templates/dynamic_multiselect.html";

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
    public void initialStateDropdownTests() {
        driver.get(SITE);

        WebElement categoryDropdown = driver.findElement(By.id("categoryDropdown"));

        Assert.assertTrue(categoryDropdown.isDisplayed(), "Category dropdown is not displayed");

        Select categorySelect = new Select(categoryDropdown);

        Assert.assertEquals(categorySelect.getFirstSelectedOption().getText(),
                "-- Select a Category --",
                "Incorrect default option in category dropdown");

        WebElement subcategoryDropdown = driver.findElement(By.id("subcategoryDropdown"));

        Assert.assertFalse(subcategoryDropdown.isEnabled(),
                "Subcategory dropdown should not be enabled");

        delay();
    }

    @Test
    public void testDynamicSubcategories() {
        driver.get(SITE);

        WebElement categoryDropdown = driver.findElement(By.id("categoryDropdown"));

        Select categorySelect = new Select(categoryDropdown);
        categorySelect.selectByVisibleText("Electronics");

        Assert.assertEquals(categorySelect.getFirstSelectedOption().getText(),
                "Electronics",
                "Incorrect default option in category dropdown");

        WebElement subcategoryDropdown = driver.findElement(By.id("subcategoryDropdown"));
        Select subcategorySelect = new Select(subcategoryDropdown);

        Assert.assertTrue(subcategoryDropdown.isEnabled(),
                "Subcategory dropdown should be enabled");

        Assert.assertEquals(subcategorySelect.getOptions().size(), 5,
                "Incorrect number of options in subcategory dropdown");

        subcategorySelect.selectByIndex(2);

        Assert.assertEquals(subcategorySelect.getFirstSelectedOption().getText(),
                "Smartphones",
                "Incorrect default option in subcategory dropdown");

        delay();
    }


    @Test
    public void testDynamicMultiselect() {
        driver.get(MULTISELECT_SITE);

        WebElement categoryDropdown = driver.findElement(By.id("categoryDropdown"));

        Select categorySelect = new Select(categoryDropdown);
        categorySelect.selectByVisibleText("Books");

        Assert.assertEquals(categorySelect.getFirstSelectedOption().getText(),
                "Books",
                "Incorrect default option in category dropdown");

        WebElement subcategoryDropdown = driver.findElement(By.id("subcategoryDropdown"));
        Select subcategorySelect = new Select(subcategoryDropdown);

        Assert.assertTrue(subcategoryDropdown.isEnabled(),
                "Subcategory dropdown should be enabled");

        Assert.assertEquals(subcategorySelect.getOptions().size(), 5,
                "Incorrect number of options in subcategory dropdown");

        subcategorySelect.selectByVisibleText("Fiction");
        subcategorySelect.selectByVisibleText("Non-Fiction");

        List<WebElement> selectedOptions = subcategorySelect.getAllSelectedOptions();

        Assert.assertEquals(selectedOptions.size(), 2,
                "Incorrect number of selected subcategories");
        Assert.assertTrue(selectedOptions.stream().anyMatch(option -> option.getText().equals("Fiction")),
                "Fiction is not selected");
        Assert.assertTrue(selectedOptions.stream().anyMatch(option -> option.getText().equals("Non-Fiction")),
                "Non-Fiction is not selected");

        delay();
    }


    @Test
    public void testDynamicMultiselectDeselect() {
        driver.get(MULTISELECT_SITE);

        WebElement categoryDropdown = driver.findElement(By.id("categoryDropdown"));

        Select categorySelect = new Select(categoryDropdown);
        categorySelect.selectByVisibleText("Books");

        WebElement subcategoryDropdown = driver.findElement(By.id("subcategoryDropdown"));
        Select subcategorySelect = new Select(subcategoryDropdown);

        subcategorySelect.selectByVisibleText("Fiction");
        subcategorySelect.selectByVisibleText("Non-Fiction");
        subcategorySelect.selectByVisibleText("Comics");

        delay();

        Assert.assertEquals(subcategorySelect.getAllSelectedOptions().size(), 3,
                "Incorrect number of selected subcategories");

        subcategorySelect.deselectByVisibleText("Fiction");

        List<WebElement> selectedOptions = subcategorySelect.getAllSelectedOptions();

        Assert.assertEquals(selectedOptions.size(), 2,
                "Incorrect number of selected subcategories");

        Assert.assertTrue(selectedOptions.stream().anyMatch(option -> option.getText().equals("Comics")),
                "Comics is not selected");
        Assert.assertTrue(selectedOptions.stream().anyMatch(option -> option.getText().equals("Non-Fiction")),
                "Non-Fiction is not selected");

        delay();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
