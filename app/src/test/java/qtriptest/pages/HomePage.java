package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

    String url = "https://qtripdynamic-qa-frontend.vercel.app/";
    WebDriver driver;

    /* Page Factory desgn pattern */
    String city;
    @FindBy(xpath ="//a[text()='Register']")
    private WebElement registerButton;
    @FindBy(xpath = "//div[text()='Logout']")
    private WebElement logoutbtn;
    @FindBy(xpath="//li[contains(@class,'active auth')]//a[text()='Login Here']")
    private WebElement loginHereBtn;
    @FindBy(id ="autocomplete")
    private WebElement searchField;
    @FindBy(xpath ="//div[@class='container']//ul//li")
    private WebElement autoCompleteResults;
    @FindBy(xpath ="//ul[@id='results']//h5")
    private WebElement noSearchResults;
    @FindBy(xpath ="//div[@class='activity-card']//img[@class='img-responsive']")
    private List<WebElement> searchResultsBeforeFilter;
    @FindBy(name="duration")
    private WebElement durationFilter;
    @FindBy(id="category-select")
    private WebElement categoryFilter;
    @FindBy(xpath="//div[@class='activity-card']//img[@class='img-responsive']")
    private List<WebElement> searchResultsAfterFilter;
    @FindBy(xpath ="//select[@id='duration-select']/../div[@class='ms-3']")
    private WebElement durationClear;
    @FindBy(xpath ="//select[@id='category-select']/../div[@class='ms-3']")
    private WebElement categoryClear;

    /* Constructor  to initialize the driver, webElements and wait for the webElements to locate */
    public HomePage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 20);
        PageFactory.initElements(ajaxFactory, this); 
    }

    public void navigatetoRegisterPage() throws InterruptedException{
        // if(driver.getCurrentUrl()!= url){
        //     driver.get(url);
        // }
        // registerButton.click();
        SeleniumWrapper.wrapperNavigate(driver, url);
        SeleniumWrapper.wrapperClick(driver, registerButton);
    }

    public boolean isUserLoggedin() throws InterruptedException{
        try{
            Thread.sleep(2000);
            return logoutbtn.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }

    public void logout() throws InterruptedException{
        //logoutbtn.click();
        SeleniumWrapper.wrapperClick(driver, logoutbtn);
    }

    public boolean isUserLoggedOut() throws InterruptedException{
        try{
            Thread.sleep(4000);
            return loginHereBtn.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }

    public void navigateToHome() throws InterruptedException{
        // if(driver.getCurrentUrl()!=url)
        // driver.get(url);
        SeleniumWrapper.wrapperNavigate(driver, url);
        //SeleniumWrapper.wrapperClick(driver, registerButton);
    }

    public void search(String city) throws InterruptedException {
        // searchField.clear();
        // searchField.sendKeys(city); /* search for the city */
        // WebDriverWait wait = new WebDriverWait(driver, 10);
        // wait.until(ExpectedConditions.or(ExpectedConditions.textToBePresentInElement(autoCompleteResults, city),
        // ExpectedConditions.textToBePresentInElement(noSearchResults, "No City found")));
        // Thread.sleep(5000);

        // Actions actions = new Actions(driver);
        
        // actions.click(searchField).sendKeys(city);
        // actions.build().perform();
        Thread.sleep(2000);   
        SeleniumWrapper.wrapperClick(driver, searchField);
        SeleniumWrapper.wrapperEnterText(searchField, city);
        Thread.sleep(3000);
        /* have to verify the city name using contains method*/
    }

    public boolean isNoCityFoundMessageDisplayed(){
        noSearchResults.isDisplayed();    
        return true;
    }

    public boolean isCityFound(){
        autoCompleteResults.isDisplayed();
        return true;
    }

    public String autocompleteSearch(){
        String expectedCityText = autoCompleteResults.getText();
        System.out.println("autocomplete Search method "+expectedCityText);
        return expectedCityText;
    }

    public boolean selectTheCity() throws InterruptedException{
       SeleniumWrapper.wrapperClick(driver, autoCompleteResults);
        //autoCompleteResults.click();
        Thread.sleep(3000);
        return true;
    }

    /* counting the no of records before applying filter */
    public int countOfSearchResultsBeforeFilter(){
        int count =0;
        List<WebElement> resultsData = searchResultsBeforeFilter;
        count = resultsData.size();
        return count;
    }

    /* counting the no of records after applying filter */
    public int countOfSearchResultsAfterFilter(){
        int count =0;
        List<WebElement> resultsData = searchResultsBeforeFilter;
        count = resultsData.size();
        return count;
    }

       /* Filtering by Category field */
    public void filterByCategory(String categoryValue) throws InterruptedException{
        Select category = new Select(categoryFilter);
        List<WebElement> allValuesofCategory = category.getOptions();
        for(WebElement categoryValues : allValuesofCategory ){
            if(categoryValues.getText().equals(categoryValue)){
                SeleniumWrapper.wrapperClick(driver, categoryValues);
               // categoryValues.click();
        	 }
        }
    }

    /* Filtering by Duration of Hours field */
    public void filterByDuration(String filterValue) throws InterruptedException{
        Select duration = new Select(durationFilter);
         List<WebElement> allValuesofDuration = duration.getOptions();
        for(WebElement durationValues : allValuesofDuration ){
        	if(durationValues.getText().equals(filterValue)){
        		//durationValues.click();
               SeleniumWrapper.wrapperClick(driver, durationValues);
           	 }
        }
    }

    public void resettingFilters() throws InterruptedException{
        SeleniumWrapper.wrapperClick(driver, durationClear);
        SeleniumWrapper.wrapperClick(driver, categoryClear);
        // durationClear.click();
        // categoryClear.click();
    }
}
