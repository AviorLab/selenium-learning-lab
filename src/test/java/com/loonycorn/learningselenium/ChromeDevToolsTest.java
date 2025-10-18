package com.loonycorn.learningselenium;


import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v140.network.Network;
import org.openqa.selenium.devtools.v140.network.model.Request;
import org.openqa.selenium.devtools.v140.network.model.Response;
import org.openqa.selenium.devtools.v140.security.Security;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChromeDevToolsTest {

    private static final String SITE = "https://www.saucedemo.com/";
    private WebDriver driver;
    private DevTools devTools;

    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
        devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
    }

//    @Test
//    public void testCaptureRequestTraffic() {
//        List<Request> capturedRequests = new CopyOnWriteArrayList<>();
//
//        devTools.send(Network.enable(
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty()
//        ));
//
//
//        devTools.addListener(Network.requestWillBeSent(), event -> {
//            capturedRequests.add(event.getRequest());
//        });
//
//        driver.get(SITE);
//
//        Assert.assertFalse(capturedRequests.isEmpty(), "No requests were captured.");
//
//        for (Request request : capturedRequests) {
//            System.out.println("Request URL: " + request.getUrl());
//            System.out.println("Request Method: " + request.getMethod());
//            System.out.println("----------------------------------------------");
//        }
//    }


//    public void testBlockImageLoading() {
//        // שלב 1: הפעלת DevTools והגדרת חסימה לפני טעינת האתר
//        devTools.send(Network.enable(
//                Optional.empty(),
//                Optional.empty(),
//                Optional.empty()
//        ));
//        devTools.send(Network.setBlockedURLs(
//                ImmutableList.of("*.jpg", "*.jpeg", "*.png")
//        ));
//        // שלב 2: כניסה לאתר
//        driver.get(SITE);
//
//        // שלב 3: מילוי טופס התחברות
//        WebElement usernameField = driver.findElement(By.id("user-name"));
//        WebElement passwordField = driver.findElement(By.id("password"));
//        WebElement loginButton = driver.findElement(By.id("login-button"));
//
//        usernameField.clear();
//        usernameField.sendKeys("standard_user");
//
//        passwordField.clear();
//        passwordField.sendKeys("secret_sauce");
//
//        loginButton.click();
//
//        // שלב 4: השהייה קלה לצורך תצפית
//        delay(5000);
//    }
//
//    @Test
//    public void testCaptureConsoleLogs() {
//        // שלב 1: כניסה לאתר
//        driver.get(SITE);
//
//        // שלב 2: איתור אלמנטים בטופס ההתחברות
//        WebElement usernameField = driver.findElement(By.id("user-name"));
//        WebElement passwordField = driver.findElement(By.id("password"));
//        WebElement loginButton = driver.findElement(By.id("login-button"));
//
//        // שלב 3: הכנסת פרטים שגויים כדי ליצור שגיאה בלוגים
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("wrong_password");
//        loginButton.click();
//
//        // שלב 4: השהייה כדי לאפשר לאירועים להתרחש
//        delay(20000);
//
//        // שלב 5: שליפת הלוגים מהדפדפן
//        LogEntries entries = driver.manage().logs().get(LogType.BROWSER);
//        List<LogEntry> logList = entries.getAll();
//
//        System.out.println("📜 מספר לוגים שנמצאו: " + logList.size());
//        System.out.println("============================================");
//
//        // שלב 6: הדפסת כל לוג שנמצא
//        for (LogEntry entry : logList) {
//            System.out.println("Level: " + entry.getLevel());
//            System.out.println("Message: " + entry.getMessage());
//            System.out.println("Timestamp: " + entry.getTimestamp());
//            System.out.println("--------------------------------------------");
//        }
//
//        // שלב 7: בדיקה אם קיימת שגיאה מסוג SEVERE
//        boolean hasError = logList.stream()
//                .anyMatch(entry -> entry.getLevel().getName().equals("SEVERE"));
//
//        Assertions.assertFalse(hasError, "נמצאו שגיאות בקונסול של הדפדפן!");
//    }

//    @Test
//    public void testBlockCSSResources() {
//        // שלב 1: הפעלת DevTools והגדרת חסימה לפני טעינת האתר
//        devTools.send(Network.enable(
//                Optional.of(100000000L), // buffer size for capturing events
//                Optional.empty(),
//                Optional.empty()
//        ));
//        devTools.send(Network.setBlockedURLs(
//                ImmutableList.of("*.css") // חסימת כל קובצי ה־CSS
//        ));
//
//        // שלב 2: טעינת האתר
//        driver.get(SITE);
//
//        // שלב 3: התחברות רגילה
//        WebElement usernameField = driver.findElement(By.id("user-name"));
//        WebElement passwordField = driver.findElement(By.id("password"));
//        WebElement loginButton = driver.findElement(By.id("login-button"));
//
//        usernameField.clear();
//        usernameField.sendKeys("standard_user");
//
//        passwordField.clear();
//        passwordField.sendKeys("secret_sauce");
//
//        loginButton.click();
//
//        delay(5000);
//
//        // שלב 4: ריענון הדף כדי שהחסימה תיכנס לתוקף
//        driver.navigate().refresh();
//        delay(5000);
//
//        // שלב 5: גלילה עם JavaScript כדי לבדוק את טעינת האלמנטים
//        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//
//        // גלול עד אמצע הדף
//        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
//        delay(5000);
//
//        // גלול עד הסוף למטה
//        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
//        delay(5000);
//
//        // שלב 6: Assertion בסיסי לוודא שהעמוד נטען למרות היעדר ה־CSS
//        String pageSource = driver.getPageSource();
//        Assertions.assertTrue(pageSource.contains("Products") || pageSource.length() > 0,
//                "העמוד לא נטען כראוי ללא קבצי CSS!");
//    }

    @Test
    public void testHandlingInsecureWebsites() {
        // שלב 1: הפעלת מודול האבטחה של DevTools
        devTools.send(Security.enable());

        // שלב 2: התעלמות משגיאות תעודת SSL
        devTools.send(Security.setIgnoreCertificateErrors(true));

        // שלב 3: ניסיון גישה לאתר עם תעודה שפג תוקפה
        driver.get("https://expired.badssl.com/");

        // השהייה קלה כדי לראות את טעינת האתר
        delay(5000);

        // שלב 4: בדיקה שהאתר נטען כראוי
        String actualTitle = driver.getTitle();
        Assert.assertTrue(
                actualTitle.equals("expired.badssl.com"),
                "The title does not equal 'expired.badssl.com'."
        );
    }

    private static void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
