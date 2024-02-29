package com.example.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage { // (2.1) Create the test class and implement Page Object for the items' inventory;
    private WebDriver driver;
    private static final String INVENTORY_ITEMS_LOCATOR = "//div[@id='inventory_container']//div[@class='inventory_item']";
    private static final String INVENTORY_IMAGES_LOCATOR = "//div[@class='inventory_item']//img[@class='inventory_item_img']";

    private List<WebElement> inventoryItems;
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        inventoryItems = driver.findElements(By.xpath(INVENTORY_ITEMS_LOCATOR));
    }

    public int getNumberOfInventoryItems() {
        return inventoryItems.size();
    }

    public List<String> getImageUrls() {
        List<String> urls = new ArrayList<>();
        List<WebElement> items = driver.findElements(By.xpath(INVENTORY_IMAGES_LOCATOR));
        for (WebElement elem: items) {
            urls.add(elem.getAttribute("src"));
        }
        return urls;
    }
}