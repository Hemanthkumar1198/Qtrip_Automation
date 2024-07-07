package qtriptest;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumWrapper {

    public static boolean wrapperEnterText(WebElement textBox, String inputText) {
        try {
            textBox.clear();
            textBox.sendKeys(inputText);
            return true;

        } catch (Exception exe) {
            return false;
        }
    }

    public static boolean wrapperClick(WebDriver driver, WebElement element)
            throws InterruptedException {
        if (element.isDisplayed()) {
            if (element.isEnabled()) {
                JavascriptExecutor js = ((JavascriptExecutor) driver);
                js.executeScript("arguments[0].scrollIntoView(true);", element);
                element.click();
                Thread.sleep(3000);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean wrapperNavigate(WebDriver driver, String url){
        try{
            if(driver.getCurrentUrl().equals(url)){
                return true;
            }else{
                driver.get(url);
                return driver.getCurrentUrl() == url;
            }
        }catch(Exception e){
            return false;            
        }
    }

    public static String captureScreenshot(WebDriver driver) throws IOException {
        File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File Dest = new File(System.getProperty("user.dir")+ "/QTripImages/"+ System.currentTimeMillis() + ".png");
        FileUtils.copyFile(srcFile, Dest);
        String errfilePath = Dest.getAbsolutePath();
        return errfilePath;
        }
}
