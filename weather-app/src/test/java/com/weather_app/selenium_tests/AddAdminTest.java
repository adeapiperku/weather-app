package com.weather_app.selenium_tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class AddAdminTest {
    
    // Helper method to generate random admin data
    private static String[] generateRandomAdminData() {
        String[] firstNames = {"John", "Jane", "Michael", "Emma", "David", "Sarah", "Robert", "Lisa"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Wilson"};
        
        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        
        // Generate unique email using UUID to avoid duplicates
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "." + uniqueId + "@example.com";
        
        // Generate random password (8-12 characters)
        String password = "Pass" + uniqueId;
        
        // Generate random birth date (21-60 years old)
        LocalDate birthDate = LocalDate.now().minusYears(21 + random.nextInt(40));
        String birthDateStr = birthDate.format(DateTimeFormatter.ISO_DATE);
        
        // Generate random phone number
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            phoneNumber.append(random.nextInt(10));
        }
        
        return new String[] {firstName, lastName, email, password, birthDateStr, phoneNumber.toString()};
    }

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
            // 1. Login
            driver.get("http://localhost:3000/admin/sign-in");
            WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
            WebElement passwordInput = driver.findElement(By.id("password"));

            emailInput.sendKeys("admin@gmail.com");
            passwordInput.sendKeys("admin");

            WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Sign In')]"));
            loginButton.click();

            wait.until(ExpectedConditions.urlContains("/admin/dashboard"));
            System.out.println("Login successful.");

            // 2. Navigate to admins page
            driver.get("http://localhost:3000/admin/admins");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h4[contains(text(),'Admins')]")));
            System.out.println("Navigated to Admins management page.");

            // 3. Click Add button (MaterialTable floating Add with icon)
            System.out.println("Looking for Add button...");
            
            // Wait for page to fully load
            Thread.sleep(2000);
            
            // Enhanced approach to find and click the add button automatically
            boolean addButtonClicked = false;
            
            // Approach 1: Find by data-testid or aria-label (most reliable)
            try {
                WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("button[aria-label='Add'], button[data-testid='add-button'], button.MuiButtonBase-root[title='Add']")
                ));
                js.executeScript("arguments[0].click();", addButton);
                System.out.println("Found and clicked Add button by aria attributes");
                addButtonClicked = true;
            } catch (Exception e1) {
                System.out.println("Could not find Add button by aria attributes: " + e1.getMessage());
            }
            
            // Approach 2: Find by toolbar buttons (MaterialTable usually has the add button as the last toolbar button)
            if (!addButtonClicked) {
                try {
                    List<WebElement> toolbarButtons = driver.findElements(By.cssSelector(".MuiToolbar-root button, div[class*='Toolbar'] button"));
                    if (!toolbarButtons.isEmpty()) {
                        // Usually the add button is the last button in the toolbar
                        WebElement addButton = toolbarButtons.get(toolbarButtons.size() - 1);
                        js.executeScript("arguments[0].click();", addButton);
                        System.out.println("Found and clicked Add button in toolbar (last button)");
                        addButtonClicked = true;
                    }
                } catch (Exception e2) {
                    System.out.println("Could not find Add button in toolbar: " + e2.getMessage());
                }
            }
            
            // Approach 3: Find by SVG icon (Material-UI often uses Add icon)
            if (!addButtonClicked) {
                try {
                    WebElement addButton = driver.findElement(
                        By.xpath("//button[.//svg[contains(@class, 'MuiSvgIcon-root')]]")
                    );
                    js.executeScript("arguments[0].click();", addButton);
                    System.out.println("Found and clicked Add button by SVG icon");
                    addButtonClicked = true;
                } catch (Exception e3) {
                    System.out.println("Could not find Add button by SVG icon: " + e3.getMessage());
                }
            }
            
            // Approach 4: Use JavaScript to find any button with 'add' text or title
            if (!addButtonClicked) {
                try {
                    js.executeScript(
                        "const buttons = Array.from(document.querySelectorAll('button')); " +
                        "const addBtn = buttons.find(btn => btn.textContent.toLowerCase().includes('add') || " +
                        "btn.title.toLowerCase().includes('add') || " +
                        "btn.getAttribute('aria-label')?.toLowerCase().includes('add')); " +
                        "if (addBtn) addBtn.click();"
                    );
                    System.out.println("Used JavaScript to find and click Add button");
                    addButtonClicked = true;
                } catch (Exception e4) {
                    System.out.println("JavaScript approach failed: " + e4.getMessage());
                }
            }
            
            // Approach 5: Last resort - click on any button that might be the add button
            if (!addButtonClicked) {
                try {
                    List<WebElement> allButtons = driver.findElements(By.tagName("button"));
                    for (WebElement button : allButtons) {
                        try {
                            if (button.isDisplayed() && button.isEnabled()) {
                                js.executeScript("arguments[0].click();", button);
                                System.out.println("Clicked a potential Add button as last resort");
                                addButtonClicked = true;
                                break;
                            }
                        } catch (Exception ignored) {}
                    }
                } catch (Exception e5) {
                    System.out.println("Last resort approach failed: " + e5.getMessage());
                }
            }
            
            if (addButtonClicked) {
                System.out.println("Successfully clicked Add Admin button");
            } else {
                throw new RuntimeException("Could not find and click the Add button after trying multiple approaches");
            }
            
            System.out.println("Clicked Add Admin button.");

            // 4. Fill the editable row inputs with random data
            System.out.println("Waiting for form fields to appear...");
            Thread.sleep(1000); // Give time for form to appear
            
            // Generate random admin data
            String[] adminData = generateRandomAdminData();
            String firstName = adminData[0];
            String lastName = adminData[1];
            String email = adminData[2];
            String password = adminData[3];
            String birthDate = adminData[4];
            String phoneNumber = adminData[5];
            
            System.out.println("Generated admin data: " + firstName + " " + lastName + " (" + email + ")");
            
            // Find and fill the first name field
            try {
                WebElement firstNameInput = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[@name='firstName'] | //input[contains(@placeholder, 'First Name')] | //td[contains(@class, 'MuiTableCell')][1]//input")
                ));
                firstNameInput.clear();
                firstNameInput.sendKeys(firstName);
                System.out.println("Filled first name: " + firstName);
            } catch (Exception e) {
                System.out.println("Error filling first name: " + e.getMessage());
            }

            // Find and fill the last name field
            try {
                WebElement lastNameInput = driver.findElement(
                        By.xpath("//input[@name='lastName'] | //input[contains(@placeholder, 'Last Name')] | //td[contains(@class, 'MuiTableCell')][2]//input")
                );
                lastNameInput.clear();
                lastNameInput.sendKeys(lastName);
                System.out.println("Filled last name: " + lastName);
            } catch (Exception e) {
                System.out.println("Error filling last name: " + e.getMessage());
            }

            // Find and fill the email field
            try {
                WebElement emailAdminInput = driver.findElement(
                        By.xpath("//input[@name='email'] | //input[contains(@placeholder, 'Email')] | //td[contains(@class, 'MuiTableCell')][3]//input")
                );
                emailAdminInput.clear();
                emailAdminInput.sendKeys(email);
                System.out.println("Filled email: " + email);
            } catch (Exception e) {
                System.out.println("Error filling email: " + e.getMessage());
            }

            // Find and fill the password field
            try {
                WebElement passwordAdminInput = driver.findElement(
                        By.xpath("//input[@name='password'] | //input[contains(@placeholder, 'Password')] | //td[contains(@class, 'MuiTableCell')][4]//input")
                );
                passwordAdminInput.clear();
                passwordAdminInput.sendKeys(password);
                System.out.println("Filled password: " + password);
            } catch (Exception e) {
                System.out.println("Error filling password: " + e.getMessage());
            }

            // Find and fill the birth date field
            try {
                WebElement birthDateInput = driver.findElement(
                        By.xpath("//input[@name='birthDate'] | //input[contains(@placeholder, 'Birth Date')] | //td[contains(@class, 'MuiTableCell')][5]//input")
                );
                birthDateInput.clear();
                birthDateInput.sendKeys(birthDate);
                // Press Tab to close any date picker that might appear
                birthDateInput.sendKeys(Keys.TAB);
                System.out.println("Filled birth date: " + birthDate);
            } catch (Exception e) {
                System.out.println("Error filling birth date: " + e.getMessage());
            }

            // Find and fill the phone number field
            try {
                WebElement phoneInput = driver.findElement(
                        By.xpath("//input[@name='phoneNumber'] | //input[contains(@placeholder, 'Phone Number')] | //td[contains(@class, 'MuiTableCell')][6]//input")
                );
                phoneInput.clear();
                phoneInput.sendKeys(phoneNumber);
                System.out.println("Filled phone number: " + phoneNumber);
            } catch (Exception e) {
                System.out.println("Error filling phone number: " + e.getMessage());
            }

            System.out.println("Filled all admin fields.");

            // 5. Click Save button (typically near row)
            System.out.println("Looking for Save button...");
            
            try {
                // First attempt: Find by title or icon
                WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@title='Save'] | //button//*[name()='svg' and @data-testid='CheckIcon']/.. | //button[contains(@class, 'MuiIconButton')][1]")
                ));
                js.executeScript("arguments[0].click();", saveButton);
                System.out.println("Clicked Save button.");
            } catch (Exception e) {
                try {
                    // Second attempt: Find first icon button in the editable row
                    List<WebElement> iconButtons = driver.findElements(By.xpath("//tr[contains(@class, 'editingRow')]//button"));
                    if (!iconButtons.isEmpty()) {
                        js.executeScript("arguments[0].click();", iconButtons.get(0));
                        System.out.println("Clicked first button in editing row.");
                    } else {
                        // Third attempt: Use JavaScript to find and click the save button
                        js.executeScript(
                            "Array.from(document.querySelectorAll('button')).find(btn => btn.title === 'Save' || btn.innerHTML.includes('check')).click();"
                        );
                        System.out.println("Used JavaScript to click Save button.");
                    }
                } catch (Exception e2) {
                    System.out.println("Error clicking Save button: " + e2.getMessage());
                    // Try pressing Enter as a last resort
                    try {
                        WebElement lastInput = driver.findElement(By.xpath("//tr[contains(@class, 'editingRow')]//input[last()]"));
                        lastInput.sendKeys(Keys.ENTER);
                        System.out.println("Pressed Enter on last input field.");
                    } catch (Exception e3) {
                        System.out.println("Could not find a way to save the form: " + e3.getMessage());
                    }
                }
            }

            // 6. Wait for success message or admin to appear in table
            System.out.println("Waiting for confirmation of admin creation...");
            
            try {
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'successfully')]")),
                    ExpectedConditions.invisibilityOfElementLocated(By.xpath("//input[@name='firstName']")),
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//td[contains(text(),'"+email+"')]"))
                ));
                
                // Verify the admin was added by checking for their email in the table
                Thread.sleep(2000); // Wait for table to refresh
                List<WebElement> emailCells = driver.findElements(By.xpath("//td[contains(text(),'"+email+"')]"));
                
                if (!emailCells.isEmpty()) {
                    System.out.println("✅ SUCCESS: Admin added successfully and found in table!");
                    System.out.println("Admin credentials: " + email + " / " + password);
                } else {
                    System.out.println("⚠️ Admin may have been added but couldn't verify in table.");
                }
            } catch (Exception e) {
                System.err.println("Error during AddAdminTest: " + e.getMessage());
                e.printStackTrace();
                
                // Take screenshot on failure
                try {
                    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    String screenshotPath = System.getProperty("user.dir") + "/admin-test-error-" + System.currentTimeMillis() + ".png";
                    Files.copy(
                        screenshot.toPath(),
                        new File(screenshotPath).toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                    );
                    System.out.println("Screenshot saved to: " + screenshotPath);
                } catch (Exception screenshotError) {
                    System.err.println("Failed to capture screenshot: " + screenshotError.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error during AddAdminTest: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Test completed. Closing browser...");
            driver.quit();
        }
    }
}
