package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.UUID;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class RegisterPage {
    WebDriver driver;
    @FindBy(id ="floatingInput")
    private WebElement userNameField;
    @FindBy(name ="password")
    private WebElement passwordField;
    @FindBy(name ="confirmpassword")
    private WebElement confirmPasswordField;
    @FindBy(xpath ="//button[text()='Register Now']")
    private WebElement registerNowButton;
    String emailAddress =" ";
    public String user_email =" "; 
    
    public RegisterPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajaxFactory, this);
    }

    public boolean registerUser(String userName, String password, String confirmPassword, boolean makeUserNameDynamic) throws InterruptedException{
        if(makeUserNameDynamic){
            emailAddress = userName.split("@")[0]+UUID.randomUUID().toString() + "@" + userName.split("@")[1];
        }else{
            emailAddress = userName;
        }
        // userNameField.sendKeys(emailAddress);
        // Thread.sleep(2000);

        // passwordField.sendKeys(password);
        // Thread.sleep(2000);

        // confirmPasswordField.sendKeys(confirmPassword);
        // Thread.sleep(2000);

        // registerNowButton.click();
        // Thread.sleep(3000);
        // WebDriverWait wait = new WebDriverWait(driver, 5);
        // wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Login to QTrip']")));
        // this.user_email = emailAddress;

        SeleniumWrapper.wrapperEnterText(userNameField, emailAddress);

        SeleniumWrapper.wrapperEnterText(passwordField, password);

        SeleniumWrapper.wrapperEnterText(confirmPasswordField, confirmPassword);

        SeleniumWrapper.wrapperClick(driver, registerNowButton);

        Thread.sleep(2000);

        this.user_email = emailAddress;

        return this.driver.getCurrentUrl().endsWith("/login");
    }
}
