package com.example.test;

import com.example.pageobject.InventoryPage;
import com.example.pageobject.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class SwagLabsTest {

    private static WebDriver driver;
    public static final String baseUrl = "https://www.saucedemo.com/v1/";

    @BeforeTest
    public void startBrowser() {
        driver = new ChromeDriver();
    }

    @AfterTest
    public void stopBrowser() throws InterruptedException {
        Thread.sleep(5000);
        driver.close();
    }

    // CHECK SUCCESS LOGIN
    @Test
    public void SuccessLoginStandardUser() {
        var login = "standard_user";
        var password = "secret_sauce";
        driver.get(baseUrl);
        var loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().endsWith("inventory.html"));
        var inventoryPage = new InventoryPage(driver);
        Assert.assertEquals(inventoryPage.getNumberOfInventoryItems(), 6);
    }

    // CHECK THE ERROR MESSAGE (AFTER UNSUCCESS LOGIN OF THE LOCKED USER) CONTAINS CORRESPONDING TEXT
    @Test
    public void UnSuccessLoginLockedUser() {
        var errMsg = "this user has been locked out";
        driver.get(baseUrl);
        var loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.checkMessage(errMsg), "Error message must contain: " + errMsg);
    }

    // CHECK THAT POSSIBLE TO SEE BROKEN LINKS
    @Test
    public void ShouldSeeBrokenLinks() {
        driver.get(baseUrl);
        var loginPage = new LoginPage(driver);
        loginPage.login("problem_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().endsWith("inventory.html"));
        var inventoryPage = new InventoryPage(driver);
        List<String> images = inventoryPage.getImageUrls();
    }
}
