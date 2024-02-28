package com.example.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;
    private static final String USERNAME_LOCATOR = "//*[@id=\"user-name\"]";
    private static final String PASSWORD_LOCATOR = "//*[@id=\"password\"]";
    private static final String BUTTON_LOCATOR = "//*[@id=\"login-button\"]";
    private static final String MESSAGE_LOCATOR = "//h3[@data-test=\"error\"]";

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        userNameInput = driver.findElement(By.xpath(USERNAME_LOCATOR));
        passwordInput = driver.findElement(By.xpath(PASSWORD_LOCATOR));
        button = driver.findElement(By.xpath(BUTTON_LOCATOR));
    }

    private WebElement userNameInput;
    private WebElement passwordInput;
    private WebElement button;
    private WebElement message;

    public void login(String username, String password) {
        userNameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        button.click();
    }

    public boolean checkMessage(String str) {
        message = driver.findElement(By.xpath(MESSAGE_LOCATOR));
        return (message.getText().contains(str));
    }
}
