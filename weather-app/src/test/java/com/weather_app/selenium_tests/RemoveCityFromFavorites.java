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

public class RemoveCityFromFavorites {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            System.out.println("Opening login page...");
            driver.get("http://localhost:3000/client/sign-in");

            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
            WebElement passwordInput = driver.findElement(By.name("password"));
            emailInput.sendKeys("user@gmail.com");
            passwordInput.sendKeys("admin");

            WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
            loginButton.click();

            System.out.println("Waiting for home page...");
            wait.until(ExpectedConditions.urlContains("/client/home"));

            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));
            Thread.sleep(1500);

            System.out.println("Entering city name...");
            searchInput.clear();
            searchInput.sendKeys("London");
            
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'))='SEARCH']")));
            
            System.out.println("Clicking search button...");
            searchButton.click();

            System.out.println("Waiting for forecast results...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.MuiCardContent-root")));

            WebElement favoriteButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(), 'Add to Favorites') or contains(text(), 'Remove from Favorites')]")));
            
            if (favoriteButton.getText().contains("Add to Favorites")) {
                System.out.println("City is not in favorites. Adding to favorites first...");
                favoriteButton.click();
                
                System.out.println("Waiting for button text to change to 'Remove from Favorites'");
                wait.until(ExpectedConditions.textToBePresentInElementLocated(
                        By.xpath("//button[contains(text(), 'Remove from Favorites')]"),
                        "Remove from Favorites"));
                
                System.out.println("City was added to favorites.");
                
                favoriteButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[contains(text(), 'Remove from Favorites')]")));
            }
            System.out.println("Removing city from favorites...");
            favoriteButton.click();
            
            System.out.println("Waiting for button text to change to 'Add to Favorites'");
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.xpath("//button[contains(text(), 'Add to Favorites')]"),
                    "Add to Favorites"));
            
            System.out.println("Success: City was removed from favorites.");
            
            Thread.sleep(3000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
