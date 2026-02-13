package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage extends BasePage {
	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmailAddress;
	
	
	@FindBy(xpath="//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement btnLogin;
	
	public void setEmail(String email) {
	    driver.switchTo().defaultContent();   // ✅ fix NoSuchFrameException

	    new WebDriverWait(driver, Duration.ofSeconds(10))
	            .until(ExpectedConditions.visibilityOf(txtEmailAddress));

	    txtEmailAddress.clear();
	    txtEmailAddress.sendKeys(email);
	}

	public void setPassword(String pwd) {
	    new WebDriverWait(driver, Duration.ofSeconds(10))
	            .until(ExpectedConditions.visibilityOf(txtPassword));

	    txtPassword.clear();
	    txtPassword.sendKeys(pwd);
	}

	public void ClickLogin() {
		btnLogin.click();
	}
	 
	 
	
}
