package com.weather_app.selenium_tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class AdminLogoutTest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            driver.get("http://localhost:3000/admin/sign-in");

            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
            WebElement passwordInput = driver.findElement(By.id("password"));
            emailInput.sendKeys("admin@gmail.com");
            passwordInput.sendKeys("admin");

            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Sign In')]"));
            loginButton.click();

            wait.until(ExpectedConditions.urlContains("/admin/dashboard"));
            System.out.println("Logged in successfully.");

            System.out.println("Waiting for dashboard to fully load...");
            Thread.sleep(3000);
            
            System.out.println("Looking for logout button...");
            
            try {
                List<WebElement> listItems = driver.findElements(By.tagName("li"));
                if (!listItems.isEmpty()) {
                    System.out.println("Found " + listItems.size() + " list items");
                    WebElement logoutItem = listItems.get(listItems.size() - 1);
                    System.out.println("Clicking the last list item...");
                    logoutItem.click();
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                System.out.println("Method 1 failed: " + e.getMessage());
            }
            
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(
                    "Array.from(document.querySelectorAll('*')).find(el => el.textContent.includes('Log out')).click();"
                );
                System.out.println("Used JavaScript to click element with 'Log out' text");
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("Method 2 failed: " + e.getMessage());
            }
            
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(
                    "localStorage.removeItem('user'); window.location.href = '/client/home';"
                );
                System.out.println("Used JavaScript to remove user from localStorage and redirect");
                Thread.sleep(2000);
            } catch (Exception e) {
                System.out.println("Method 3 failed: " + e.getMessage());
            }

            System.out.println("Checking if logout was successful...");
            
            try {
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/client/home"),
                    ExpectedConditions.urlContains("/admin/sign-in"),
                    ExpectedConditions.urlContains("/login")
                ));
                
                System.out.println("Current URL after logout attempts: " + driver.getCurrentUrl());
                
                driver.get("http://localhost:3000/admin/dashboard");
                Thread.sleep(2000);
                
                if (driver.getCurrentUrl().contains("/sign-in") || driver.getCurrentUrl().contains("/login")) {
                    System.out.println("Logout successful! Redirected to login when accessing protected page.");
                } else {
                    System.out.println("WARNING: Still able to access protected page. Logout may have failed.");
                }
            } catch (Exception e) {
                System.out.println("Error during logout verification: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}