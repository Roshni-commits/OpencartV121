package testCases;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import pageObjects.AccountsRegistrationPage;
import pageObjects.HomePage;
import testbase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

    @Test(groups = {"Regression","Master"})
    public void verify_account_registration() {

        logger.info("************************************************************");
        logger.info("******* Starting TC001_AccountRegistrationTest *******");
        logger.info("************************************************************");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {

            // Step 1: Navigate to Registration page
            HomePage hp = new HomePage(driver);
            hp.clickMyAccount();
            hp.clickRegister();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-firstname")));
            logger.info("Registration page loaded successfully");

            // Step 2: Fill Registration Form
            AccountsRegistrationPage regpage = new AccountsRegistrationPage(driver);

            String firstName = "John";
            String lastName = "Doe";
            String email = "user" + System.currentTimeMillis() + "@gmail.com";
            String telephone = "9876543210";   // Safe 10-digit number
            String password = "Test@1234";     // Safe valid password

            regpage.setFirstName(firstName);
            regpage.setLastName(lastName);
            regpage.setEmail(email);
            regpage.setTelephone(telephone);
            regpage.setPassword(password);
            regpage.setConfirmPassword(password);
            regpage.setPrivacyPolicy();

            logger.info("Form filled successfully");
            logger.info("Generated Email: " + email);

            // Step 3: Click Continue
            regpage.clickContinue();
            logger.info("Clicked Continue button");

            // Step 4: Wait for success page
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(text(),'Your Account Has Been Created')]")));

            String pageHeading = regpage.getConfirmationMsg();
            logger.info("Page heading: " + pageHeading);

            // Step 5: Assertion
            Assert.assertEquals(
                    pageHeading.trim(),
                    "Your Account Has Been Created!",
                    "Registration failed!"
            );

            logger.info("*********************************************");
            logger.info("TEST PASSED - Account created successfully!");
            logger.info("*********************************************");

        } catch (Exception e) {

            logger.error("TEST FAILED - Registration did not complete");

            try {
                String alertText = driver.findElement(By.cssSelector(".alert-danger")).getText();
                logger.error("Error message on page: " + alertText);
            } catch (Exception ex) {
                logger.error("No alert-danger found on page.");
            }

            try {
                String fieldError = driver.findElement(By.cssSelector(".text-danger")).getText();
                logger.error("Field validation error: " + fieldError);
            } catch (Exception ex) {
                logger.error("No field-level error found.");
            }

            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.info("************************************************************");
        logger.info("******* Finished TC001_AccountRegistrationTest *******");
        logger.info("************************************************************");
    }
}




