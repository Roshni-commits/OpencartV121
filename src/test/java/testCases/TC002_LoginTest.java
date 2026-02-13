package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testbase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	@Test(groups= {"Sanity","Master"})
	
	public void verify_login()
	{
		logger.info("**************Starting TC_002_LoginTest******");
		try
		{
		//HomePage
		 HomePage hp=new HomePage(driver);
		 hp.clickMyAccount();
		 hp.clickLogin();
		 
		 //LoginPage
		 LoginPage lp=new LoginPage(driver);
		 lp.setEmail(p.getProperty("email"));
		 lp.setPassword(p.getProperty("password"));
		 lp.ClickLogin();
		 
		 //MyAccount
		 MyAccountPage macc=new MyAccountPage(driver);
		 boolean targetPage=macc.IsMyAccountPageExists();
		 
		 Assert.assertTrue(targetPage);  //Assert.assertEquals(targetpage,true,"login failed");
		 
	}
	catch(Exception e)
	{
		e.printStackTrace();
		Assert.fail(e.getMessage()); 
		 
	}
		logger.info("*************Finished TC_002_LoginTest *********");
	
	}
}
