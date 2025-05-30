package com.weather_app.selenium_tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class RemoveCityFromFavorites {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            deleteUserFavoriteAsAdmin(driver, wait);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
    
    private static void deleteUserFavoriteAsAdmin(WebDriver driver, WebDriverWait wait) throws InterruptedException {
        System.out.println("\nStep 2: Logging in as admin to delete user's favorite...");
        
        System.out.println("Opening admin login page...");
        driver.get("http://localhost:3000/admin/sign-in");

        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        WebElement passwordInput = driver.findElement(By.name("password"));
        emailInput.sendKeys("admin@gmail.com");
        passwordInput.sendKeys("admin");

        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        System.out.println("Waiting for admin dashboard...");
        wait.until(ExpectedConditions.urlContains("/admin/dashboard"));
        
        System.out.println("Navigating to Favorites view...");
        WebElement favoritesLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(), 'Favorites') or contains(text(), 'Favorite Cities')]/ancestor::a")));
        favoritesLink.click();
        
        System.out.println("Waiting for favorites table to load...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[contains(text(), 'Favorites View')]")));
        Thread.sleep(2000); 
        
        System.out.println("Getting the first row in the favorites table...");
        List<WebElement> rows = driver.findElements(By.xpath("//tbody/tr"));
        
        if (rows.size() > 0) {
            WebElement firstRow = rows.get(0);
            
            WebElement customerCell = firstRow.findElement(By.xpath("./td[1]"));
            WebElement favoritesCell = firstRow.findElement(By.xpath("./td[2]"));
            
            System.out.println("Selected row for customer: " + customerCell.getText());
            System.out.println("Favorites: " + favoritesCell.getText());
            
            if (!favoritesCell.getText().contains("No favorite cities")) {
                WebElement deleteButton = firstRow.findElement(By.xpath(".//button[@title='Delete']"));
                System.out.println("Clicking delete button to remove favorite...");
                deleteButton.click();
                
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@role='dialog']")));
                Thread.sleep(1000); 
                
                WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@role='dialog']//button[contains(@class, 'confirmButton') or position()=2]"))); 
                System.out.println("Confirming deletion...");
                confirmButton.click();
                
                Thread.sleep(2000);
                System.out.println("Success: Admin deleted favorite city from the first row.");
            } else {
                System.out.println("The first row has no favorites to delete.");
            }
        } else {
            System.out.println("No rows found in the favorites table.");
        }
        Thread.sleep(3000);
    }
}
