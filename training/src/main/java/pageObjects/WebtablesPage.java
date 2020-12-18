package pageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class WebtablesPage {
 
	WebDriver driver;
	public WebtablesPage(WebDriver driver) {
		this.driver=driver;
	    PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using = "addNewRecordButton") 
	private WebElement addNewRecordBtn;
	
	@FindBy(how = How.XPATH, using = "(//*[contains(@id,'delete-record')])[1]") 
	private WebElement deleteBtnFirstRecord;
	
	@FindBy(how = How.XPATH, using = "(//*[contains(@id,'edit-record')])[1]") 
	private WebElement editBtnFirstRecord;
	
	@FindBy(how = How.ID, using = "firstName") 
	private WebElement firstName;
	
	@FindBy(how = How.ID, using = "lastName") 
	private WebElement lastName;
	@FindBy(how = How.ID, using = "submit") 
	private WebElement submitButton;
	
	@FindBy(how = How.XPATH, using = "//*[text()='Registration Form']") 
	private WebElement registrationFormLable;
	
	public boolean isRegistrationFormDisplayed() {
		
		boolean isDisp=registrationFormLable.isDisplayed();
		return isDisp;
	}
	
	
	public void clickOnAddNewRecord() {
		addNewRecordBtn.click();
	}
	
	public void fillTheEmployeeDetails(Map<String, String> fieldKeyValuePair) {
		
		Set<String> keys=fieldKeyValuePair.keySet();
		for(String key:keys) {
			
			  switch(key) {
			  
			  case "firstName":  firstName.sendKeys(fieldKeyValuePair.get(key));
			                      break; 
			  case "lastName":
				  lastName.sendKeys(fieldKeyValuePair.get(key));
                  break; 
			  case "email":
			  case "salary":	  
			  
			  }
		}
		
		submitButton.click();
	}
	
	
 public void printWebTableValues() {
	 

String headersLoc="//div[@class='rt-resizable-header-content']";
  List<WebElement> headersEles=driver.findElements(By.xpath(headersLoc));
  List<String> headerValues=new ArrayList<String>();
  int columnCount=headersEles.size();
   for(WebElement ele:headersEles){
  String value=ele.getText().trim();
  headerValues.add(value);
  }
  
  
  int rowCount=driver.findElements(By.xpath("//div[@class='rt-tbody']//div[@role='row']")).size();
  
  String celValueXpath="//div[@class='rt-tbody']//div[@class='rt-tr-group'][rowIndex]//div[@role='row']/div[@class='rt-td'][cellIndex]";
  
  for(int i=1;i<rowCount;i++)
{
   for(int j=1;j<=columnCount;j++){
   
   String cellPath=celValueXpath;
   cellPath=cellPath.replace("rowIndex",i+"");
   cellPath=cellPath.replace("cellIndex",j+"");
   
   WebElement ele=driver.findElement(By.xpath(cellPath));
   
   System.out.println(ele.getText());
   }

}  
  
 }
	
	
	
}
