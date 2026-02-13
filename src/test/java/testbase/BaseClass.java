package testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass
    @Parameters({"browser"})
    public void setup(String br) {

        try {

            // Load config.properties
            p = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            p.load(fis);

            logger = LoggerFactory.getLogger(this.getClass());
            logger.info("===== Browser setup started =====");

            String executionEnv = p.getProperty("execution_env");

            // =========================
            // REMOTE (Docker Grid)
            // =========================
            if (executionEnv.equalsIgnoreCase("remote")) {

                if (br.equalsIgnoreCase("chrome")) {

                    ChromeOptions options = new ChromeOptions();
                    driver = new RemoteWebDriver(
                            new URL("http://localhost:4444"), options);

                } else if (br.equalsIgnoreCase("firefox")) {

                    FirefoxOptions options = new FirefoxOptions();
                    driver = new RemoteWebDriver(
                            new URL("http://localhost:4444"), options);

                } else {
                    throw new RuntimeException("Remote does not support: " + br);
                }
            }

            // =========================
            // LOCAL Execution
            // =========================
            else if (executionEnv.equalsIgnoreCase("local")) {

                switch (br.toLowerCase()) {

                    case "chrome":
                        driver = new ChromeDriver();
                        break;

                    case "firefox":
                        driver = new FirefoxDriver();
                        break;

                    case "edge":
                        driver = new EdgeDriver();
                        break;

                    case "safari":
                        driver = new SafariDriver();
                        break;

                    default:
                        throw new RuntimeException("Invalid browser: " + br);
                }
            }

            else {
                throw new RuntimeException("Invalid execution_env in config.properties");
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.manage().window().maximize();

            driver.get(p.getProperty("appURL"));
            logger.info("Navigated to: " + p.getProperty("appURL"));

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Setup failed: " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }

    // Utilities
    public String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomAlphaNumeric() {
        return RandomStringUtils.randomAlphabetic(3)
                + RandomStringUtils.randomNumeric(3);
    }

    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());

        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);

        Path screenshotPath = Paths.get(System.getProperty("user.dir"), "screenshots");
        screenshotPath.toFile().mkdirs();

        Path targetPath = screenshotPath.resolve(tname + "_" + timeStamp + ".png");
        File targetFile = targetPath.toFile();

        sourceFile.renameTo(targetFile);

        return targetFile.getAbsolutePath();
    }
}


