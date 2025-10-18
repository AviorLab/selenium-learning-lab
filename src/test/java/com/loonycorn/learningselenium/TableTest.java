package com.loonycorn.learningselenium;

import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class TableTest {

    private static final String SITE =
            "file:///Users/avior/IdeaProjects/learning-selenium/src/test/java/templates/nested_table.html";

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
    public void testNumberOfRows() {
        driver.get(SITE);

        List<WebElement> outerTableRowsEl = driver.findElements(
                By.xpath("//table[@id='employeeTable']/tbody/tr"));

        Assert.assertEquals(outerTableRowsEl.size(), 2,
                "Incorrect number of rows in the outer table");

        System.out.println("Outer table rows: " + outerTableRowsEl);

        delay();
    }

    @Test
    public void testFirstRowData() {
        driver.get(SITE);

        WebElement firstRowEl = driver.findElement(
                By.xpath("//table[@id='employeeTable']/tbody/tr[1]"));
        List<WebElement> firstRowCellsEl = firstRowEl.findElements(
                By.xpath(".//td"));

        Assert.assertEquals(firstRowCellsEl.get(0).getText(), "John Doe",
                "Name in the first row doesn't match");
        Assert.assertEquals(firstRowCellsEl.get(1).getText(), "Manager",
                "Position in the first row doesn't match");
    }

    @Test
    public void testNestedTableRowsData() {
        driver.get(SITE);

        WebElement nestedTableEl = driver.findElement(By.xpath(
                "//*[@id=\"employeeTable\"]/tbody/tr[1]/td[3]/table"));

        List<WebElement> nestedRowsEl = nestedTableEl.findElements(By.tagName("tr"));

        Assert.assertEquals(nestedRowsEl.get(0).getText(), "Email john.doe@example.com",
                "Email in the first row nested table doesn't match");
        Assert.assertEquals(nestedRowsEl.get(1).getText(), "Phone 123-456-7890",
                "Phone in the first row nested table doesn't match");
        Assert.assertEquals(nestedRowsEl.get(2).getText(), "Address 123 Main St, City",
                "Address in the first row nested table doesn't match");

        nestedTableEl = driver.findElement(By.xpath(
                "//*[@id=\"employeeTable\"]/tbody/tr[2]/td[3]/table"));

        nestedRowsEl = nestedTableEl.findElements(By.tagName("tr"));

        Assert.assertEquals(nestedRowsEl.get(0).getText(), "Email jane.smith@example.com",
                "Email in the second row nested table doesn't match");
        Assert.assertEquals(nestedRowsEl.get(1).getText(), "Phone 987-654-3210",
                "Phone in the second row nested table doesn't match");
        Assert.assertEquals(nestedRowsEl.get(2).getText(), "Address 456 Park Ave, Town",
                "Address in the second row nested table doesn't match");

    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
