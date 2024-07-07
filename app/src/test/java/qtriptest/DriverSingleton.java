package qtriptest;

import java.net.MalformedURLException;
//import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.remote.BrowserType;
// import org.openqa.selenium.remote.DesiredCapabilities;
// import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {
    WebDriver driver;

    public static DriverSingleton instanceOfSingleton;
    private DriverSingleton() throws MalformedURLException{
        // final DesiredCapabilities capabilities = new DesiredCapabilities();
        // capabilities.setBrowserName(BrowserType.CHROME);
        // driver = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static DriverSingleton getInstanceOfSingletonBrowserClass() throws MalformedURLException{
        if(instanceOfSingleton == null){
            instanceOfSingleton = new DriverSingleton();
        }
            return instanceOfSingleton;
    }

    public WebDriver getDriver(){
        return driver;
    }
}