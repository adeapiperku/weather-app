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
import java.util.UUID;

public class CustomerRegisterTest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            System.out.println("Opening customer registration page...");
            driver.get("http://localhost:3000/client/sign-up");

            // Fill in the form
            wait.until(ExpectedConditions.elementToBeClickable(By.id("firstName"))).sendKeys("TestFirst");
            driver.findElement(By.id("lastName")).sendKeys("TestLast");

            // Generate a unique email for each test run
            String testEmail = "test_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
            driver.findElement(By.id("email")).sendKeys(testEmail);
            driver.findElement(By.id("password")).sendKeys("TestPassword123!");

            // Click the Sign Up button
            WebElement signUpButton = driver.findElement(By.xpath("//button[contains(text(), 'Sign Up')]"));
            signUpButton.click();

            // Wait for redirect to sign-in page
            wait.until(ExpectedConditions.urlContains("/client/sign-in"));

            System.out.println("Customer registration successful. Redirected to sign-in page.");

            Thread.sleep(3000); // Optional: to visually confirm before the browser closes

        } catch (Exception e) {
            System.err.println("Error during customer registration test: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
