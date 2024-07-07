package qtriptest.pages;

import qtriptest.SeleniumWrapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage {

    WebDriver driver;
    @FindBy(xpath = "//input[@name='name']")
    private WebElement guestNameField;
    @FindBy(xpath = "//input[@name='date']")
    private WebElement dateField;
    @FindBy(xpath = "//input[@name='person']")
    private WebElement numberOfPerson;
    @FindBy(xpath="//button[text()='Reserve']")
    private WebElement reserveButton;
    @FindBy(id ="reserved-banner")
    private WebElement reserverHeader;
    @FindBy(xpath ="//div[@id='navbarNavDropdown']//ul//li//a[text()='Home']")
    private WebElement homeButton;
  
    public AdventureDetailsPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(ajaxFactory, this); 
    }

    public boolean reserveAdventure(String guestName, String reserveDate, String countOfPerson){
        try{ 
            // guestNameField.clear();
            // guestNameField.sendKeys(guestName);
            // dateField.clear();
            // dateField.sendKeys(reserveDate);
            // numberOfPerson.clear();
            // numberOfPerson.sendKeys(countOfPerson);
            // Thread.sleep(3000);
            // reserveButton.click();
            // Thread.sleep(5000);
            SeleniumWrapper.wrapperEnterText(guestNameField, guestName);

            SeleniumWrapper.wrapperEnterText(dateField, reserveDate);

            SeleniumWrapper.wrapperEnterText(numberOfPerson, countOfPerson);

            SeleniumWrapper.wrapperClick(driver, reserveButton);
            Thread.sleep(3000);

            return true;

            }catch(Exception e){
                e.printStackTrace();
        }
        return true;
    }
        public String reserveHeaderText(){
            String reserveText = reserverHeader.getText();
            return reserveText;
        }

        public void navigateToHome() throws InterruptedException {
            SeleniumWrapper.wrapperClick(driver, homeButton);
            //homeButton.click();
            Thread.sleep(3000);
    }
}