
package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HistoryPage {
    
    WebDriver driver;
    
    String tableData;
    @FindBy(xpath ="//a[text()='Reservations']")
    private WebElement reservationLink;
    @FindBy(xpath ="//table[@class='table']//thead//tr/th")
    private List<WebElement> tableHeader;
    @FindBy(xpath ="//tbody[@id='reservation-table']//tr[1]/th")
    private WebElement transcationIdCol;
    @FindBy(xpath = "//tbody[@id='reservation-table']//tr[1]/td")
    private List<WebElement> tableRowData;
    @FindBy(xpath = "//tbody[@id='reservation-table']//tr")
    private List<WebElement> rowData;
    @FindBy(id ="no-reservation-banner")
    private WebElement noReserveMsg;
    

    public HistoryPage(WebDriver driver){
        this.driver = driver;
        AjaxElementLocatorFactory ajaxFactory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(ajaxFactory, this);
    }
   
    public boolean clickReservationLink() throws InterruptedException{
           SeleniumWrapper.wrapperClick(driver, reservationLink);
           // reservationLink.click();
            Thread.sleep(3000);
            return driver.getCurrentUrl().endsWith("/index.html");
    }

    public String transactionIdValue(){
        String transactionId = transcationIdCol.getText(); 
        return transactionId;
    }

    public int checkReservationData(){        
        for(WebElement allRowValues : rowData){
            System.out.println(allRowValues.getText());
        }
        return rowData.size();
    }

    public String allRowsData(){
        for(WebElement allRowValues : rowData){
           String tableData = allRowValues.getText();
        }
        return tableData;
    }

    public void cancelReservation() throws InterruptedException{
         /* print table headers & count */
        // System.out.println("Header Size"+tableHeader.size());
        // for(WebElement headerValue : tableHeader){
        //     System.out.println(headerValue.getText());
        // }

        /* print row data & count */
        // System.out.println("Row Size"+tableRowData.size());
        for(WebElement rowValues : tableRowData){
             if(rowValues.getText()=="Cancel"){
                    SeleniumWrapper.wrapperClick(driver, rowValues);
                   // rowValues.click();
                    Thread.sleep(3000);
             }
        }
    }

    public void refreshPage(){
        driver.navigate().refresh();
    }

    public boolean noReservationMessage(){
        if(noReserveMsg.isDisplayed()){
            return true;
        }else{
            return false;
        }
    }
}