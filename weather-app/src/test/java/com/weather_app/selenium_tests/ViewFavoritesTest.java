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

public class ViewFavoritesTest {

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

            System.out.println("Navigating to profile page...");
            WebElement profileLink = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/client/profile')]")));
            profileLink.click();

            System.out.println("Waiting for profile page to load...");
            wait.until(ExpectedConditions.urlContains("/client/profile"));

            System.out.println("Clicking 'View Favorite Cities' button...");
            WebElement viewFavoritesButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(., 'View Favorite Cities')]")));
            viewFavoritesButton.click();

            System.out.println("Waiting for favorites dialog to appear...");
            WebElement dialogTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[text()='Favorite Cities']")));

            System.out.println("Checking for favorite cities...");
            List<WebElement> favoriteItems = driver.findElements(
                    By.xpath("//div[@role='dialog']//ul/li"));

            if (favoriteItems.size() > 0) {
                System.out.println("Found " + favoriteItems.size() + " favorite cities:");
                for (WebElement item : favoriteItems) {
                    System.out.println("- " + item.getText());
                }
            } else {
                WebElement noFavorites = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@role='dialog']//*[contains(text(), 'No favorite cities added')]")));
                if (noFavorites.isDisplayed()) {
                    System.out.println("No favorite cities added.");
                } else {
                    System.err.println("Dialog opened but no content found.");
                }
            }
            
            System.out.println("Closing the favorites dialog...");
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='dialog']//div[contains(@class, 'MuiDialogActions')]//button")));
            closeButton.click();
            wait.until(ExpectedConditions.invisibilityOf(dialogTitle));
            System.out.println("Test completed successfully.");

            Thread.sleep(2000);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
