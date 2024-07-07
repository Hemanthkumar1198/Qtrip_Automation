package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
// import org.openqa.selenium.By;
// import org.openqa.selenium.support.ui.ExpectedConditions;
// import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    RemoteWebDriver driver;
    @FindBy(name ="email")
    private WebElement emailAddressField;
    @FindBy(name ="password")
    private WebElement passwordField;
    @FindBy(xpath ="//button[text()='Login to QTrip']")
    private WebElement logintButton;
    @FindBy(xpath = "//div[contains(@class, 'login register')]")
    private WebElement logoutButton;

    public LoginPage(RemoteWebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(ajaxFactory, this);
    }

    public void LoginToApp(String userName, String passWord) throws InterruptedException {
        // emailAddressField.clear();
        // emailAddressField.sendKeys(userName);
        // Thread.sleep(2000);
        // passwordField.clear();
        // passwordField.sendKeys(passWord);
        // logintButton.click();
        // Thread.sleep(3000);
        // if(logintButton.isDisplayed()){
        //     return true;
        // }
        // else{
        //     return false;
        // }
        SeleniumWrapper.wrapperEnterText(emailAddressField, userName);

        SeleniumWrapper.wrapperEnterText(passwordField, passWord);

        SeleniumWrapper.wrapperClick(driver, logintButton);     
    }

    

}
