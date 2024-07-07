package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage {

    RemoteWebDriver driver;

    @FindBy(id="search-adventures")
    private WebElement searchAdventureField;
    @FindBy(xpath = "//div[@class='activity-card']//img[@class='img-responsive']")
    private WebElement searchResults;
    @FindBy(xpath="//div[@class='activity-card']//img[@class='img-responsive']/../div/div/h5")
    private WebElement adventureText;
    
    public AdventurePage(RemoteWebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(ajaxFactory, this); 
    }

    public void searchAdventure(String adventureValue) throws InterruptedException{
        SeleniumWrapper.wrapperEnterText(searchAdventureField, adventureValue);
        // searchAdventureField.clear();
        // searchAdventureField.sendKeys(adventureValue);
        // /* extract the value from the search results */
        // WebDriverWait wait = new WebDriverWait(driver, 10);
        // wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='activity-card']//img[@class='img-responsive']")));
        Thread.sleep(3000);
    }

    public String adventureValueText(){
        String actualAdventureText = adventureText.getText();
        return actualAdventureText;
    }

    public boolean selectAdventure() throws InterruptedException{
             Thread.sleep(5000);
            //searchResults.click();
    //     Thread.sleep(5000);

            SeleniumWrapper.wrapperClick(driver, searchResults);
            Thread.sleep(3000);
            return driver.getCurrentUrl().contains("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/detail/?adventure=");
    }
}