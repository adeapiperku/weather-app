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

public class AdminViewFavoritesTest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            System.out.println("Opening admin login page...");
            driver.get("http://localhost:3000/admin/sign-in");

            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
            WebElement passwordInput = driver.findElement(By.id("password"));
            
            System.out.println("Entering admin credentials...");
            emailInput.sendKeys("admin@gmail.com");
            passwordInput.sendKeys("admin");

            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Sign In')]"));
            loginButton.click();

            System.out.println("Waiting for admin dashboard...");
            wait.until(ExpectedConditions.urlContains("/admin/dashboard"));
            
            System.out.println("Navigating to favorites view...");
            WebElement favoritesLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/admin/favorites') or contains(text(), 'Favorites')]")));
            favoritesLink.click();
            
            System.out.println("Waiting for favorites view to load...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(text(), 'Favorites View')]")));
            
            boolean customerColumnExists = driver.findElements(By.xpath("//th[contains(text(), 'Customer')]")).size() > 0;
            boolean citiesColumnExists = driver.findElements(By.xpath("//th[contains(text(), 'Favorite Cities')]")).size() > 0;
            
            if (customerColumnExists && citiesColumnExists) {
                System.out.println("Success: Favorites view table is displayed with correct columns.");
            } else {
                System.out.println("Error: Favorites view table does not have the expected columns.");
            }
            
            int rowCount = driver.findElements(By.xpath("//tbody/tr")).size();
            System.out.println("Found " + rowCount + " customer(s) with favorites data.");
            
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
