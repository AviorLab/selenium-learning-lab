
package com.loonycorn.learningselenium;
import com.loonycorn.learningselenium.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

public class AdminLoginCookiesTests {

    private WebDriver driver;
    private static final String SITE = "https://testpages.eviltester.com/styled/cookies/adminlogin.html";

    @BeforeTest
    public void setUp() {
        driver = DriverFactory.createDriver(DriverFactory.BrowserType.CHROME);
    }


    private static void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void testLogin() {
        driver.get(SITE);

        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login"));

        // הכנסת פרטי ההתחברות
        usernameInput.sendKeys("Admin");
        passwordInput.sendKeys("AdminPass");

        delay();

        loginButton.click();

        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://testpages.eviltester.com/styled/cookies/adminview.html",
                "Login failed"
        );

        // בדיקת ה-cookie של ההתחברות
        Cookie loginCookie = driver.manage().getCookieNamed("loggedin");
        Assert.assertNotNull(loginCookie);
        Assert.assertEquals(loginCookie.getValue(), "Admin");

        System.out.println("✅ Login cookie after login: " + loginCookie);

        delay();
    }

    @Test
    public void testLoginLogoutBySettingCookies() {
        driver.get(SITE);

        // מאמת שהעמוד הראשוני הוא עמוד ההתחברות
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://testpages.eviltester.com/styled/cookies/adminlogin.html"
        );

        // יצירת Cookie שמדמה התחברות
        Cookie newCookie = new Cookie("loggedin", "Admin");
        driver.manage().addCookie(newCookie);

        delay();
        driver.navigate().refresh();

        // מאמת שההתחברות הצליחה לפי ה-URL
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://testpages.eviltester.com/styled/cookies/adminview.html",
                "Login failed"
        );

        Cookie loginCookie = driver.manage().getCookieNamed("loggedin");
        Assert.assertNotNull(loginCookie);
        Assert.assertEquals(loginCookie.getValue(), "Admin");

        System.out.println("✅ Login cookie added manually: " + loginCookie);

        delay();

        // מוחק את הקוקי ומרענן כדי לבדוק התנתקות
        driver.manage().deleteCookieNamed("loggedin");
        driver.navigate().refresh();

        // מאמת שחזרנו לעמוד ההתחברות
        Assert.assertEquals(
                driver.getCurrentUrl(),
                "https://testpages.eviltester.com/styled/cookies/adminlogin.html"
        );

        System.out.println("✅ Cookie deleted and returned to login page.");

        delay();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}