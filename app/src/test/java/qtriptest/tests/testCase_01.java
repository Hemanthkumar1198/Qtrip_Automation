package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ExtentReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

public class testCase_01 {

    static WebDriver driver;
    public static String lastGeneratedEmail;
    public static ExtentReports report;
    public static ExtentTest test;

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s | %s | %s  | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        
        ExtentReportSingleton rpt1 =  ExtentReportSingleton.getInstaceOfReportSingleton();
        report = rpt1.getReport();

        DriverSingleton dsTestCase01 = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = dsTestCase01.getDriver();
        logStatus("driver", "initializing driver", "Success");
    }

    @Test(description = "Verify User Registration then login and logout", priority = 1,
            groups = {"Login Flow"}, dataProvider = "data-provider", dataProviderClass = DP.class,
            enabled = true)
    public static void TestCase01(String UserName, String passWord) {
        Boolean status;
        try {
            logStatus("Navigate to RegPage", "Started", "Success");
            HomePage home = new HomePage(driver);
            test = report.startTest("TestCase01");
            home.navigatetoRegisterPage();
            Assert.assertTrue(
                    driver.getCurrentUrl()
                            .equals("https://qtripdynamic-qa-frontend.vercel.app/pages/register/"),
                    "Success");
            logStatus("Navigate to RegPage", "Stopped", "Success");

            logStatus("Registartion Execution", "Started", "Success");
            RegisterPage register = new RegisterPage(driver);
            status = register.registerUser(UserName, passWord, passWord, true);
            if(status){
                test.log(LogStatus.PASS, "User registration is successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User registration is Unsuccessful");
            }
            Thread.sleep(2000);
            Assert.assertTrue(driver.getCurrentUrl()
                    .equals("https://qtripdynamic-qa-frontend.vercel.app/pages/login"), "Success");
            logStatus("Registartion Execution", "Stopped", "Success");

            lastGeneratedEmail = register.user_email;
            logStatus("Login Execution", "Started", "Success");
            LoginPage login = new LoginPage(driver);
            Thread.sleep(2000);
            login.LoginToApp(lastGeneratedEmail, passWord);
            logStatus("Login Execution", "Stopped", "Success");
            
            logStatus("Logout Execution", "Started", "Success");
            status = home.isUserLoggedin();
            if(home.isUserLoggedin()){
                test.log(LogStatus.PASS, "User Login is successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User registration is Unsuccessful");
            }
            Assert.assertTrue(status, "Login is unsuccessful");
            logStatus("Logout Execution", "Stopped", "Success");

        //     WebElement actuaElement =
        //             driver.findElement(By.xpath("//div[contains(@class,'login')]"));
        //     String actualText = actuaElement.getText();
        //     String expectedText = "Logout";
        //     Assert.assertEquals(actualText, expectedText, "Success");
        //     logStatus("Login Execution", "Stopped", "Success");

            home.logout();
            status = home.isUserLoggedOut();
            if(home.isUserLoggedOut()){
                test.log(LogStatus.PASS, "User logout is Successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User Logout is Unsuccessful");
            }
            Assert.assertTrue(status, "User logout is Unsuccessful");

            test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "TestCase01");

        } catch (Exception e) {
            logStatus("Page Test", "TestCase 01 Validation", "Failed");
           // e.printStackTrace();
        }
    }
}
