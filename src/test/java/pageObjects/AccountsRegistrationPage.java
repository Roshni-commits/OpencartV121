package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountsRegistrationPage {
    
    WebDriver driver;
    WebDriverWait wait;
    
    public AccountsRegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }
    
    // Input Fields
    @FindBy(id = "input-firstname")
    WebElement txtFirstName;
    
    @FindBy(id = "input-lastname")
    WebElement txtLastName;
    
    @FindBy(id = "input-email")
    WebElement txtEmail;
    
    @FindBy(id = "input-telephone")
    WebElement txtTelephone;
    
    @FindBy(id = "input-password")
    WebElement txtPassword;
    
    @FindBy(id = "input-confirm")
    WebElement txtConfirmPassword;
    
    // Continue Button
    @FindBy(xpath = "//input[@type='submit' and @value='Continue']")
    WebElement btnContinue;
    
    // Confirmation Message
    @FindBy(css = "#content h1")
    WebElement confirmationMessage;
    
    // ==================== Setter Methods ====================
    
    public void setFirstName(String fname) {
        wait.until(ExpectedConditions.visibilityOf(txtFirstName));
        txtFirstName.clear();
        txtFirstName.sendKeys(fname);
    }
    
    public void setLastName(String lname) {
        wait.until(ExpectedConditions.visibilityOf(txtLastName));
        txtLastName.clear();
        txtLastName.sendKeys(lname);
    }
    
    public void setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(txtEmail));
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }
    
    public void setTelephone(String telephone) {
        wait.until(ExpectedConditions.visibilityOf(txtTelephone));
        txtTelephone.clear();
        txtTelephone.sendKeys(telephone);
    }
    
    public void setPassword(String pwd) {
        wait.until(ExpectedConditions.visibilityOf(txtPassword));
        txtPassword.clear();
        txtPassword.sendKeys(pwd);
    }
    
    public void setConfirmPassword(String pwd) {
        wait.until(ExpectedConditions.visibilityOf(txtConfirmPassword));
        txtConfirmPassword.clear();
        txtConfirmPassword.sendKeys(pwd);
    }
    
    // ==================== Privacy Policy Checkbox ====================
    
    public void setPrivacyPolicy() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // Wait for checkbox to be present using name="agree"
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("agree")));
        
        // Scroll to checkbox
        js.executeScript("arguments[0].scrollIntoView(true);", checkbox);
        
        // Small pause for Safari
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        // Use JavaScript to click - most reliable for Safari
        js.executeScript("arguments[0].click();", checkbox);
        
        // Small pause after click
        try { Thread.sleep(300); } catch (InterruptedException e) {}
    }
    
    // ==================== Continue Button ====================
    
    public void clickContinue() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        wait.until(ExpectedConditions.elementToBeClickable(btnContinue));
        
        // Scroll to button
        js.executeScript("arguments[0].scrollIntoView(true);", btnContinue);
        
        // Small pause for Safari
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        // Click using JavaScript
        js.executeScript("arguments[0].click();", btnContinue);
    }
    
    // ==================== Confirmation Message ====================
    
    public String getConfirmationMsg() {
        try {
            wait.until(ExpectedConditions.visibilityOf(confirmationMessage));
            return confirmationMessage.getText();
        } catch (Exception e) {
            try {
                WebElement h1 = driver.findElement(By.tagName("h1"));
                return h1.getText();
            } catch (Exception e2) {
                return "NO MESSAGE FOUND";
            }
        }
    }
}