package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends BasePage {
	public MyAccountPage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//h2[text()='My Account']")  //My Account heading page
	WebElement msgHeading;
	
	@FindBy(xpath="//div[@class='list-group']//a[text()='Logout']")
	WebElement lnkLogout;
	
	public boolean IsMyAccountPageExists() {
	    try {
	        new WebDriverWait(driver, Duration.ofSeconds(10))
	                .until(ExpectedConditions.visibilityOf(msgHeading));
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
	
	public void clickLogout()
	{
		lnkLogout.click();
	}

	
	

}
