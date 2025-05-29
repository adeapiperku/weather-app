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

public class SearchCitiesTest {

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

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

            // Locate search input
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='text']")));

            String city = "Paris";
            System.out.println("Searching city: " + city);

            // Clear input (use JS to be sure)
            js.executeScript("arguments[0].value = '';", searchInput);

            // Type city name
            searchInput.sendKeys(city);

            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space(translate(text(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ'))='SEARCH']")));

            searchButton.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.MuiCardContent-root")));

            List<WebElement> cityResults = driver.findElements(By.xpath("//*[contains(text(),'" + city + "')]"));
            if (cityResults.size() > 0) {
                System.out.println("Results found for city: " + city);
            } else {
                System.err.println("No results found for city: " + city);
            }

            Thread.sleep(3000); // wait a bit to see results

        } catch (Exception e) {
            System.err.println("Error during test: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}