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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class UserProfileTest {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            System.out.println("Opening user login page...");
            driver.get("http://localhost:3000/client/sign-in"); // Change to your login page URL

            // Login as user
            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("email"))); // Adjust if different
            WebElement passwordInput = driver.findElement(By.id("password")); // Adjust if different

            System.out.println("Entering user credentials...");
            emailInput.sendKeys("user@gmail.com"); // Replace with valid test user email
            passwordInput.sendKeys("admin");  // Replace with valid test user password

            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Sign In')]"));
            loginButton.click();

            System.out.println("Waiting for user dashboard...");
            wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/dashboard"),
                ExpectedConditions.urlContains("/home")
            ));
            
            // Navigate directly to profile page
            System.out.println("Redirecting to profile page...");
            driver.get("http://localhost:3000/client/profile");

            // Wait for profile page content to load by checking header "User Profile"
            System.out.println("Waiting for profile page to load...");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'User Profile')]")));

            // Verify user full name is displayed in avatar section
            WebElement fullNameElement = driver.findElement(By.xpath("//div[contains(@class,'avatarSection')]//h6"));
            String fullName = fullNameElement.getText();
            System.out.println("User full name displayed: " + fullName);

            if (!fullName.isEmpty()) {
                System.out.println("Success: Profile page displayed correctly.");
            } else {
                System.out.println("Error: User full name not found on profile page.");
            }

            Thread.sleep(3000); // Pause to see result visually if running manually

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            
            // Take screenshot on failure
            try {
                File screenshot = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
                String screenshotPath = System.getProperty("user.dir") + "/profile-test-error-" + System.currentTimeMillis() + ".png";
                Files.copy(
                    screenshot.toPath(),
                    new File(screenshotPath).toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                );
                System.out.println("Screenshot saved to: " + screenshotPath);
            } catch (Exception screenshotError) {
                System.err.println("Failed to capture screenshot: " + screenshotError.getMessage());
            }
        } finally {
            System.out.println("Test completed. Closing browser...");
            driver.quit();
        }
    }
}
