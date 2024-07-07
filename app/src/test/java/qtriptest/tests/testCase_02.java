package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ExtentReportSingleton;
import qtriptest.SeleniumWrapper;
import qtriptest.pages.HomePage;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
// import org.testng.annotations.BeforeTest;
// import org.testng.annotations.Test;
// import org.testng.asserts.Assertion;
import org.testng.Assert;
import org.testng.annotations.*;

public class testCase_02 {

    static WebDriver driver;
    static ExtentReports report;
    static ExtentTest test; 
    
    public static void logStatus(String type, String message, String status){
        System.out.println(String.format("%s | %s | %s  | %s", 
        String.valueOf(java.time.LocalDateTime.now()), type, message, status));
    }

    @BeforeTest(alwaysRun = true)
    public static void createDriver() throws MalformedURLException{
    ExtentReportSingleton rpt2 =  ExtentReportSingleton.getInstaceOfReportSingleton();
    report = rpt2.getReport(); 

    DriverSingleton dsTestCase02 = DriverSingleton.getInstanceOfSingletonBrowserClass();
    driver = dsTestCase02.getDriver();
    logStatus("driver", "initializing driver", "Success");
    }

    @Test(description = "Search and Filter flow",priority = 2,
     groups={"Search and Filter"}, dataProvider ="data-provider", 
     dataProviderClass = DP.class, enabled = true)
    public static void TestCase02(String city, String categoryValue, String durationValue, String expectedFilterResults, String expectedUnFilteredResults){
        boolean status;
        try{

            String cityValue = city.toLowerCase();
            logStatus("Search", "Started", "Success");
            test = report.startTest("TestCase02");
            HomePage home = new HomePage(driver);
            home.navigateToHome();

            home.search(cityValue);
            status = home.isCityFound();              
            if(status){
                //logStatus("Search", "City found", "Success");
                test.log(LogStatus.PASS, "Search of City is Successful");
            }else{
                //logStatus("Search", "City not found", "Fail");
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "Search of City is Unsuccessful");
            }
  
            status = home.selectTheCity();
            if(status){
                // logStatus("Search", "selection of city", "Success");
                // Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city="+cityValue+""), "Success");
                test.log(LogStatus.PASS, "City selection is Successful");
            }else{
                //logStatus("Search", "selection of city", "Fail");
                test.log(LogStatus.FAIL, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "Selection of City is Unsuccessful");
                Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city="+cityValue+""), "Selection of City is not successful");
            }
           
            // home.search(city);
            // status = home.isNoCityFoundMessageDisplayed();             
            // if(status){
            //     logStatus("Search", "No City found", "Success");
            // }else{
            //     logStatus("Search", "No City found", "Fail");
            // }
            // logStatus("Search", "Started, city found", "Success");

            logStatus("Filtering", "Category-Started", "Success");
            home.filterByCategory(categoryValue);
            Thread.sleep(3000);        
            logStatus("Filtering", "Category-Stopped", "Success");
            
            logStatus("Filtering", "Duration-Started", "Success");
            home.filterByDuration(durationValue );
            Thread.sleep(3000);
            logStatus("Filtering", "Duration-Stopped", "Success");
            int actualCount = home.countOfSearchResultsAfterFilter();
            int expectedCount = Integer.parseInt(expectedFilterResults);
            Assert.assertEquals(actualCount, expectedCount, "Count is matching");        
             
            home.resettingFilters();
            int actualCountAfterClearing = home.countOfSearchResultsAfterFilter();
            int expectedCountAfterClearing = Integer.parseInt(expectedUnFilteredResults);
            Assert.assertEquals(actualCountAfterClearing, expectedCountAfterClearing, "Count is matching");
            
            test.log(LogStatus.PASS, test.addScreenCapture(SeleniumWrapper.captureScreenshot(driver)), "TestCase02");
        }catch(Exception e){
            logStatus("Page Test", "TestCase 02 Validation", "Failed");
            e.printStackTrace();
        }    
    }
}
