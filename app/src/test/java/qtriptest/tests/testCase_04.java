package qtriptest.tests;

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

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_04 {

    static WebDriver driver;
    static String lastGeneratedEmail;
    static ExtentReports report;
    static ExtentTest test;

    public static void logStatus(String type, String message, String status) {
        System.out.println(String.format("%s | %s | %s  | %s",
                String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException {

        ExtentReportSingleton rpt4 =  ExtentReportSingleton.getInstaceOfReportSingleton();
        report = rpt4.getReport(); 

        DriverSingleton dsTestCase04 = DriverSingleton.getInstanceOfSingletonBrowserClass();
        driver = dsTestCase04.getDriver();
        logStatus("driver", "initializing driver", "Success");
    }

    @Test(description = "Reliability Flow", priority = 4, groups = {"Booking History Flow"},
            dataProvider = "data-provider", dataProviderClass = DP.class, enabled = true)
    public static void TestCase04(String username, String password, String dataSet[]) {
        boolean status;
        test = report.startTest("TestCase04");
        HomePage home = new HomePage(driver);
        AdventurePage adventurePage = new AdventurePage(driver);
        RegisterPage register = new RegisterPage(driver);
        HistoryPage historyPage = new HistoryPage(driver);
        AdventureDetailsPage adventureDetailPage = new AdventureDetailsPage(driver);
        try {

            logStatus("Navigate to RegPage", "Started", "Success");
            home.navigatetoRegisterPage();
            Assert.assertTrue(
                    driver.getCurrentUrl()
                            .equals("https://qtripdynamic-qa-frontend.vercel.app/pages/register/"),
                    "Success");
            logStatus("Navigate to RegPage", "Stopped", "Success");

            logStatus("Registartion Execution", "Started", "Success");
            status = register.registerUser(username, password, password, true);
            if(status){
                test.log(LogStatus.PASS, "User registration is successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User registration is Unsuccessful");
            }
            Assert.assertTrue(driver.getCurrentUrl()
                    .equals("https://qtripdynamic-qa-frontend.vercel.app/pages/login"), "Success");
            logStatus("Registartion Execution", "Stopped", "Success");

            lastGeneratedEmail = register.user_email;
            logStatus("Login Execution", "Started", "Success");
            LoginPage login = new LoginPage(driver);
            Thread.sleep(3000);

            login.LoginToApp(lastGeneratedEmail, password);
            status = home.isUserLoggedin();
            if(home.isUserLoggedin()){
                test.log(LogStatus.PASS, "User Login is successful");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User registration is Unsuccessful");
            }

            int actualRevservationCount = dataSet.length;
            for (int i = 0; i < dataSet.length; i++) {
                String arrayString = dataSet[i].toString();
                String[] dataArray = arrayString.split(";");
                String city = dataArray[0];
                String adventureName = dataArray[1];
                String guestName = dataArray[2];
                String reservationDate = dataArray[3];
                String countOfPpl = dataArray[4];
                logStatus("Search City", "Started", "Success");

                home.search(city);
                status = home.isCityFound();
                if (status) {
                    logStatus("City", "found" + " " + city + "", "Success");
                    test.log(LogStatus.PASS, "Search of City is successful");
                } else {
                    logStatus("City", "not found" + city + "", "Fail");
                    test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "Search of City is not successful");
                }

                String cityValuePassed = home.autocompleteSearch();
                if (cityValuePassed.contains(city)) {
                    logStatus("Actucal City", "matching with Expected City" + " " + city + "",
                            "Success");
                } else {
                    logStatus("Actucal City", "matching with Expected City" + " " + city + "",
                            "Fail");
                }

                status = home.selectTheCity();
                if (status) {
                    test.log(LogStatus.PASS, "Selection of City is successful");
                    logStatus("City", "selected", "Success");
                } else {
                    test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "Selection of City is Unsuccessful");
                    logStatus("City", "not selected", "Fail");
                }

                adventurePage.searchAdventure(adventureName);
                String acutalAdventure = adventurePage.adventureValueText();
                if (acutalAdventure.contains(adventureName)) {
                    logStatus("Adventure Name", "matching", "Success");
                } else {
                    logStatus("Adventure Name", "not matching", "Fail");
                }

                logStatus("Adventure", "Started", "Success");
                adventurePage.selectAdventure();
                logStatus("Adventure", "Stopped" + " " + adventureName + "", "Success");
                Assert.assertTrue(driver.getCurrentUrl().contains(
                        "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/detail/?adventure"),
                        "Not matching");
                Thread.sleep(3000);

                logStatus("Reservation", "started", "Success");
                status = adventureDetailPage.reserveAdventure(guestName, reservationDate, countOfPpl);
                Thread.sleep(3000);
                if(status)
                if(status){
                    test.log(LogStatus.PASS, "Reservation is Successful");
                }
                else{
                    test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "Reservation is Unsuccessful");
                }
                logStatus("Reservation",
                        "stopped" + " " + guestName + " " + reservationDate + " " + countOfPpl + "",
                        "Success");
                Assert.assertTrue(driver.getCurrentUrl().contains(
                        "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/detail/?adventure"),
                        "Not matching");

                String actualText = adventureDetailPage.reserveHeaderText();
                String expectedText =
                        "Greetings! Reservation for this adventure is successful. (Click here to view your reservations)";
                Assert.assertEquals(actualText, expectedText, "Did not reserve the adventure");
                Thread.sleep(5000);

                adventureDetailPage.navigateToHome();
                Assert.assertTrue(
                        driver.getCurrentUrl()
                                .equals("https://qtripdynamic-qa-frontend.vercel.app/"),
                        "Not navigated to Home Page");
                Thread.sleep(3000);
            }
            logStatus("Checking Reservation Data", "Started", "Success");
            historyPage.clickReservationLink();
            logStatus("Checking Reservation Data", "Stopped", "Success");

            logStatus("Checking Reservation rows", "Stopped", "Success");
            int expectedReservationCount = historyPage.checkReservationData();
            Assert.assertEquals(actualRevservationCount, expectedReservationCount,
                    "No of reservation Mismatch");
            logStatus("Checking Reservation rows", "Stopped", "Success");

            test.log(LogStatus.INFO, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "TestCase04");

            home.logout();
            status = home.isUserLoggedOut();
            if(status){
                test.log(LogStatus.PASS, "User logged out Successfully");
            }
            else{
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "User log out Unsuccessful");
            }
            logStatus("log", "out is", "Success");

        } catch (Exception e) {
            logStatus("Page Test", "TestCase 04 Validation", "Failed");
            e.printStackTrace();
        }
    }

    @AfterTest(enabled = true)
    public static void quitDriver() throws MalformedURLException {
        driver.quit();
        report.flush();
        report.endTest(test);
        logStatus("Logout", "from the application", "success" );
    }
}
