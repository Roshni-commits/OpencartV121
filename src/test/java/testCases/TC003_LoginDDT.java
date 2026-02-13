package testCases;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testbase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class,groups="Datadriven")
    public void verify_loginDDT(String email, String pwd, String exp) {
        logger.info("********** Starting TC_003_LoginDDT **********");
        logger.info("Testing login with Email: " + email + " | Password: " + pwd + " | Expected: " + exp);

        try {
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickLogin();

            LoginPage lp = new LoginPage(driver);
            lp.setEmail(email);
            lp.setPassword(pwd);
            lp.ClickLogin();

            MyAccountPage macc = new MyAccountPage(driver);

            // Explicit wait for My Account page
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            boolean targetPage = false;
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[contains(text(),'My Account')]")));
                targetPage = true;
                logger.info("My Account page is visible after login.");
            } catch (Exception e) {
                targetPage = false;
                logger.error("My Account page NOT found: " + e.getMessage());
            }

            // Assertions based on Expected column
            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage) {
                    logger.info("Login succeeded as expected for VALID credentials: " + email);
                    macc.clickLogout();
                    Assert.assertTrue(true, "Login passed as expected.");
                } else {
                    logger.error("Login FAILED for VALID credentials: " + email + " / " + pwd);
                    Assert.fail("Login FAILED for VALID credentials: " + email + " / " + pwd);
                }
            } else if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage) {
                    logger.error("Login SUCCEEDED with INVALID credentials: " + email + " / " + pwd);
                    macc.clickLogout();
                    Assert.fail("Login SUCCEEDED with INVALID credentials: " + email + " / " + pwd);
                } else {
                    logger.info("Login failed as expected for INVALID credentials: " + email);
                    Assert.assertTrue(true, "Login failed as expected for invalid credentials.");
                }
            } else {
                logger.warn("Unexpected value in 'exp': " + exp);
                Assert.fail("Invalid 'exp' value in DataProvider: " + exp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception occurred during test execution: " + e.getMessage());
        }

        logger.info("********** Finished TC_003_LoginDDT **********");
    }
}



