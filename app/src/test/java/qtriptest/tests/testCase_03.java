package qtriptest.tests;

//import net.bytebuddy.agent.builder.AgentBuilder.Default.BootstrapInjectionStrategy.Disabled;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ExtentReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_03 {

    static RemoteWebDriver driver;
    static String lastGeneratedEmail;
    static ExtentReports report;
    static ExtentTest test; 

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s | %s | %s  | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {
        
        ExtentReportSingleton rpt3 =  ExtentReportSingleton.getInstaceOfReportSingleton();
        report = rpt3.getReport();

        DriverSingleton dsTestCase03 = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = dsTestCase03.geDriver();
        logStatus("driver", "initializing driver", "Success");
    }

    @Test(description = "Booking and Cancellation Flow", priority = 3,
            groups = {"Booking and Cancellation Flow"}, dataProvider = "data-provider",
            dataProviderClass = DP.class, enabled = true)
    public static void TestCase03(String username, String password, String city,
            String adventureName, String guestName, String reservationDate, String countOfPpl) {
        boolean status;
        String cityValue = city.toLowerCase();
        try {
            logStatus("Navigate to RegPage", "Started", "Success");
            test = report.startTest("TestCase03"); 
            HomePage home = new HomePage(driver);
            AdventurePage adventurePage = new AdventurePage(driver);
            RegisterPage register = new RegisterPage(driver);
            AdventureDetailsPage adventureDetailPage = new AdventureDetailsPage(driver);
            home.navigatetoRegisterPage();
            Assert.assertTrue(
                    driver.getCurrentUrl()
                            .equals("https://qtripdynamic-qa-frontend.vercel.app/pages/register/"),
                    "Not the Registration Page");
            logStatus("Navigate to RegPage", "Stopped", "Success");

            logStatus("Registartion Execution", "Started", "Success");
            status = register.registerUser(username, password, password, true);
            Thread.sleep(3000);
            if(status){
                test.log(LogStatus.PASS, "User Registration is Successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User Registration is Unsuccessful");
            }
            Assert.assertTrue(driver.getCurrentUrl()
                    .equals("https://qtripdynamic-qa-frontend.vercel.app/pages/login"), "Resigration is Unsuccessful");
            logStatus("Registartion Execution", "Stopped", "Success");

            lastGeneratedEmail = register.user_email;
            logStatus("Login Execution", "Started", "Success");
            LoginPage login = new LoginPage(driver);
            Thread.sleep(3000);

            login.LoginToApp(lastGeneratedEmail, password);
            status = home.isUserLoggedin();
            if(status){
                test.log(LogStatus.PASS, "User Login is successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User registration is Unsuccessful");
            }
            Assert.assertTrue(status, "Login is unsuccessful");

            logStatus("Search City", "Started", "Success");
            home.search(cityValue);
            status = home.isCityFound();
            if(status){
                test.log(LogStatus.PASS, "Autocomplet of City is Successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "Autocomplet of City is Unsuccessful");
            }

            status = home.selectTheCity();
            if(status){
                test.log(LogStatus.PASS, "Selection of City is Successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "Selection of City is Unsuccessful");
            }

            adventurePage.searchAdventure(adventureName);
            logStatus("Adventure", "Started", "Success");
            status = adventurePage.selectAdventure();
            if(status){
                test.log(LogStatus.PASS, "Selection of Adventure is Successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "Selection of Adventure is Unsuccessful");
            }
            logStatus("Adventure", "Stopped", "Success");
            Assert.assertTrue(driver.getCurrentUrl().contains(
                    "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/detail/?adventure"),
                    "Not matching");

            logStatus("Reservation", "started", "Success");
            status =adventureDetailPage.reserveAdventure(guestName, reservationDate, countOfPpl);
            if(status){
                test.log(LogStatus.PASS, "Reservation is Successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "Reservation is Unsuccessful");
            }
            logStatus("Reservation", "stopped", "Success");
            
            String actualText = adventureDetailPage.reserveHeaderText();
            String expectedText =
                    "Greetings! Reservation for this adventure is successful. (Click here to view your reservations)";
            Assert.assertEquals(actualText, expectedText, "Did not reserve the adventure");

            HistoryPage historyPage = new HistoryPage(driver);
            status = historyPage.clickReservationLink();
            if(status){
                test.log(LogStatus.PASS, "User navigated to Reservation details Page");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User is not navigated to Reservation details Page");
            }
            logStatus("Reservation", "stopped", "Success");
            
            logStatus("Cancel Reservation", "Started", "Success");
            historyPage.cancelReservation();
            logStatus("Cancel Reservation", "Started", "Success");

            logStatus("Page Refresh", "Started", "Success");
            historyPage.refreshPage();
            logStatus("Page Refresh", "Stopped", "Success");

            logStatus("Transaction Id ", "is visible", "Success");
            status = historyPage.noReservationMessage();
            if(status){
                test.log(LogStatus.PASS, "No reservation message is displayed");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "No reservation message is not displayed");
            }

            test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "TestCase03");
            
            home.logout();
            status = home.isUserLoggedOut();
            if(status){
                test.log(LogStatus.PASS, "User log out Successfully");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User log out Unsuccessfully");
            }
            logStatus("logged", "out", "Success");

        } catch (Exception e) {
            logStatus("Page Test", "TestCase 03 Validation", "Failed");
            e.printStackTrace();
        }
    }
}
