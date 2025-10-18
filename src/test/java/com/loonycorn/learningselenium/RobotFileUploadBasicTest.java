package com.loonycorn.learningselenium;
import com.loonycorn.learningselenium.utils.DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RobotFileUploadBasicTest {

    private WebDriver driver;
    private static final String SITE = "https://the-internet.herokuapp.com/upload";
    private Path fileToUpload;

    @BeforeTest
    public void setUp() throws Exception {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        // נכין קובץ טקסט פשוט להעלאה (כדי שלא תתעסק בנתיבים)
        fileToUpload = Paths.get(System.getProperty("user.home"), "robot-upload.txt");
        if (!Files.exists(fileToUpload)) {
            Files.writeString(fileToUpload, "hello from Robot");
        }
    }



    private static void delay(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    @Test
    public void testFileUploadRobotGUI() throws Exception {
        driver.get(SITE);

        driver.findElement(By.id("file-upload")).click();
        delay(700);

        System.out.println("Uploading file from: " + fileToUpload.toAbsolutePath());


        // 2) ROBOT בסיסי: מדביק נתיב מלא לשדה "File name" ולוחץ Enter
        StringSelection pathSel = new StringSelection(fileToUpload.toAbsolutePath().toString());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(pathSel, null);

        Robot robot = new Robot();
        robot.setAutoDelay(40);

        // Ctrl+V (הדבקה לשדה שם הקובץ)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Enter לאישור הבחירה
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        delay(600);

        // 3) Submit
        driver.findElement(By.id("file-submit")).click();

        // 4) אימות בסיסי שהקובץ הועלה
        String shown = driver.findElement(By.id("uploaded-files")).getText().trim();
        Assert.assertEquals(shown, fileToUpload.getFileName().toString());
        System.out.println("✅ Uploaded via Robot (GUI): " + shown);
    }



    @AfterTest
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}