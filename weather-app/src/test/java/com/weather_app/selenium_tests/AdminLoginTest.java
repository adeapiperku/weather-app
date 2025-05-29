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

public class AdminLoginTest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            System.out.println("Opening customer login page...");
            driver.get("http://localhost:3000/admin/sign-in");

            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
            WebElement passwordInput = driver.findElement(By.id("password"));

            System.out.println("Entering customer credentials...");
            emailInput.sendKeys("admin@gmail.com"); 
            passwordInput.sendKeys("admin");

            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Sign In')]"));
            loginButton.click();

            System.out.println("Waiting for customer dashboard...");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("/customer/dashboard"),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Welcome')]"))
            ));

            System.out.println("Login successful! Customer dashboard is displayed.");

            Thread.sleep(3000); 

        } catch (Exception e) {
            System.err.println("Error during customer login test: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}