package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    
    WebDriver driver;
    WebDriverWait wait;
    
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }
    
    @FindBy(xpath = "//span[text()='My Account']")
    WebElement lnkMyAccount;
    
    @FindBy(linkText = "Register")
    WebElement lnkRegister;
    
    @FindBy(linkText = "Login")
    WebElement lnkLogin;
    
    public void clickMyAccount() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.elementToBeClickable(lnkMyAccount));
        js.executeScript("arguments[0].click();", lnkMyAccount);
    }
    
    public void clickRegister() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.elementToBeClickable(lnkRegister));
        js.executeScript("arguments[0].click();", lnkRegister);
    }
    
    public void clickLogin() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.elementToBeClickable(lnkLogin));
        js.executeScript("arguments[0].click();", lnkLogin);
    }
}
