package com.example.test;

import com.example.pageobject.InventoryPage;
import com.example.pageobject.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SwagLabsTest { // (2.3) Create the test class and realize 3 tests, using Page Objects;
    private static WebDriver driver;
    public String gridUrl = "http://192.168.0.102:4444";
    public String baseUrl = "https://www.saucedemo.com/v1";
    @BeforeTest
    public void startBrowser() {
        driver = new ChromeDriver();
    }

    @AfterTest
    public void stopBrowser() throws InterruptedException {
        Thread.sleep(5000);
        driver.quit();
    }

    // CHECK SUCCESS LOGIN
    @Test (testName = "TC1_GC_StandardUser")
    public void SuccessLoginStandardUser() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions(); // (2.4.a) Create new instance for GC browser;
        driver = new RemoteWebDriver(new URL(gridUrl), options); // (2.4.b) Initialize driver on Se Grid using GC options;
        driver.get(baseUrl);
        var loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().endsWith("inventory.html"));
        var inventoryPage = new InventoryPage(driver);
        Assert.assertEquals(inventoryPage.getNumberOfInventoryItems(), 6);
    }

    // CHECK THE ERROR MESSAGE (AFTER UNSUCCESS LOGIN OF THE LOCKED USER) CONTAINS CORRESPONDING TEXT
    @Test (testName = "TC2_FF_LockedUser")
    public void UnSuccessLoginLockedUser() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions(); // (2.4.a) Create new instance for FF browser;
        driver = new RemoteWebDriver(new URL(gridUrl), options); // (2.4.b) Initialize driver on Se Grid using FF options;
        var errMsg = "this user has been locked out";
        driver.get(baseUrl);
        var loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.checkMessage(errMsg), "Error message must contain: " + errMsg);
    }

    // CHECK THAT POSSIBLE TO SEE BROKEN LINKS
    @Test (testName = "TC3_FF_ProblemUser")
    public void ShouldSeeBrokenLinks() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions(); // (2.4.a) Create new instance for FF browser;
        driver = new RemoteWebDriver(new URL(gridUrl), options); // (2.4.b) Initialize driver on Se Grid using FF options;
        driver.get(baseUrl);
        var loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().endsWith("inventory.html"));
        var inventoryPage = new InventoryPage(driver);
        List<String> images = inventoryPage.getImageUrls();
    }
}
