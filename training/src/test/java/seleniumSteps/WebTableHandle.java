package seleniumSteps;

import java.awt.AWTException;


import java.util.HashMap;
import java.util.List;

import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import managers.WebDriverManager;

public class WebTableHandle {

	public static void main(String[] args) throws InterruptedException, AWTException {
		// TODO Auto-generated method stub

		WebDriverManager driverManager=new WebDriverManager();
		WebDriver driver=driverManager.getDriver();
	//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		WebTableHandle obj=new WebTableHandle();
		HashMap<String,String> rowValues=obj.validateHeaders(1,driver,"Annual Flood Insurance");
		String loanId=rowValues.get("Toorak Loan Id");
		System.out.println(loanId);
	}
	
	
	public HashMap<String, String> validateHeaders(int Rindex, WebDriver driver, String inputLastColumnName) throws InterruptedException, AWTException {
		
		String url="https://dev.toorakcapital.info/";
		
		// inputLastColumnName="Annual Flood Insurance";
		String userName="user1@triconinfotech.com";
		String password="Welcome1";
		String userNameLoc="usernameUserInput";
		String passwordLoc="password";
		String checkTermsLoc="chkTerms";
		String loginButtonLoc="submitButton";
		String statusFilter="//*[text()='Status']/following::div[@id='mutiple-checkbox'][1]";
		String submitCheckBox="//ul[@role='listbox']/li[@data-value='submit']/descendant::input";
		String leftTableHeadersLoc="//*[@data-testid='loan-list-enhanced-table']/descendant::div[@class='rdg-header-row']/div[@class='rdg-cell rdg-cell-frozen']/descendant::span[@class='headerText']";
		String rowValuesLeftSide="//*[@data-testid='loan-list-enhanced-table']/descendant::div[contains(@class,'rdg-row rdg-row-')][rowIndex]/div[@class='rdg-cell rdg-cell-frozen'][cellIndex]/descendant::span";
		String righSideHeaderLoc="//*[@data-testid='loan-list-enhanced-table']/descendant::div[@class='rdg-header-row']/div[@class='rdg-cell']/descendant::span[@class='headerText']";
		String rowValuesRightSide="//*[@data-testid='loan-list-enhanced-table']/descendant::div[contains(@class,'rdg-row')][rowIndex]/div[@class='rdg-cell'][cellIndex]/descendant::span";
		
	//	String leftArrow="//img[@alt='left-arrow']";
		
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Thread.sleep(15000);
		driver.findElement(By.id(userNameLoc)).sendKeys(userName);;
		driver.findElement(By.id(passwordLoc)).sendKeys(password);;
		driver.findElement(By.id(checkTermsLoc)).click();;
		driver.findElement(By.id(loginButtonLoc)).click();;
		
		Thread.sleep(10000);
		//handleBrowseFile(driver);
		driver.findElement(By.xpath(statusFilter)).click();;
		Thread.sleep(1000);
		driver.findElement(By.xpath(submitCheckBox)).click();;
		Thread.sleep(1000);
		driver.findElement(By.xpath(submitCheckBox)).sendKeys(Keys.TAB);
		Thread.sleep(1000);
		
		String leftRowXpath=rowValuesLeftSide.replace("rowIndex",Rindex+"" );
		HashMap<String,String> leftRowValues=getRowValuesFromWebTable(driver,leftTableHeadersLoc,leftRowXpath,"left",inputLastColumnName);
		String loanId=leftRowValues.get("Toorak Loan Id");
		
		String rightRowXpath=rowValuesRightSide.replace("rowIndex",Rindex+"" );
		HashMap<String,String>rightRowValues=getRowValuesFromWebTable(driver,righSideHeaderLoc,rightRowXpath,"right",inputLastColumnName);
	
		
		
		System.out.println(loanId);
		System.out.println("left column count=" +leftRowValues.size());
		System.out.println(leftRowValues);
		System.out.println("right column count="+rightRowValues.size());
		System.out.println(rightRowValues);
		
		HashMap<String,String>allColumnValues=new HashMap<String, String>();
		allColumnValues.putAll(leftRowValues);
		allColumnValues.putAll(rightRowValues);
	
		System.out.println("All column count=" +allColumnValues.size());
		System.out.println(allColumnValues);
		return allColumnValues;
		
	}


	
	  
	  public HashMap<String,String> getRowValuesFromWebTable(WebDriver driver,String headersXpath,String rowValuesXpath,String leftOrRight,String inputLastColumnName ) throws AWTException, InterruptedException{
		  HashMap<String,String> rowMap=new HashMap<String,String>();
		  
		  JavascriptExecutor js = (JavascriptExecutor)driver;
		  int maxCount=500;
		  int count=0;
		  while(count<maxCount) {
			 try {
			  HashMap<String,String> temp=null;
			  if(rowMap.keySet().contains(inputLastColumnName)){
				  break;
			  }
			  WebElement ele=driver.findElement(By.xpath("//*[text()='Loan Type']"));
			  js.executeScript("arguments[0].click();", ele);	
			  List<WebElement> headers=driver.findElements(By.xpath(headersXpath));
			  Thread.sleep(50);
			  if(leftOrRight.equalsIgnoreCase("left")) {
				  temp= getpartialValues(driver,headers,2,rowValuesXpath);
				  rowMap.putAll(temp);
			  break;
			  }else {
				  temp= getpartialValues(driver,headers,1,rowValuesXpath);
				  rowMap.putAll(temp);
			  }
			  
			 
				 Actions actionProvider = new Actions(driver);
				 actionProvider.sendKeys(Keys.ARROW_RIGHT).build().perform();
				 actionProvider.sendKeys(Keys.ARROW_RIGHT).build().perform();
				 actionProvider.sendKeys(Keys.ARROW_RIGHT).build().perform();
				 actionProvider.sendKeys(Keys.ARROW_RIGHT).build().perform();
				 actionProvider.sendKeys(Keys.ARROW_RIGHT).build().perform();
				 actionProvider.sendKeys(Keys.ARROW_RIGHT).build().perform();
				 Thread.sleep(200);
			 }catch(Exception e){
				 
			 }
		  }
			 
			 return rowMap;
	  }
	  
	  public  HashMap<String,String> getpartialValues(WebDriver driver,List<WebElement> headers,int startingIndex,String rowValuesXpath){
		  System.out.println(startingIndex);
		  HashMap<String,String> rowPartialMap=new HashMap<String,String>();
		  int cellIndex=0;
		  for(int i=0;i<headers.size()+startingIndex;i++) {
			  cellIndex=i+startingIndex;
			  try {
				 
			  String cellValueXpath=rowValuesXpath.replace("cellIndex", cellIndex+"");
				String cellValue=driver.findElement(By.xpath(cellValueXpath)).getText().trim();
				rowPartialMap.put(headers.get(i).getText().trim(), cellValue);
			  }catch(Exception e) {
				break;  
			  }
			  }
		  System.out.println(rowPartialMap);
		  return rowPartialMap;
	  }
	
	  
	  
	  public void handleBrowseFile(WebDriver driver) throws InterruptedException {
		  String documentsLoc="//*[@id='root']/div/div[2]/div[1]/div/div[1]/div/div[5]/div[3]/div[1]/h5";
		  String browsButton="//*[@id='raised-button-file']";
		  String loanIdLoc="//*[text()='BR001255']";
		  String filePath="C:/Users/REKHA/Desktop/sampleToorak.xlsx";
		  JavascriptExecutor js = (JavascriptExecutor)driver;
		  
		  WebElement ele=driver.findElement(By.xpath(loanIdLoc));
		  js.executeScript("arguments[0].click();", ele);	
		  
		  Thread.sleep(7000);
		  ele=driver.findElement(By.xpath(documentsLoc));
		  js.executeScript("arguments[0].click();", ele);
		  Thread.sleep(1000);
		  ele=driver.findElement(By.xpath(browsButton));
		  js.executeScript("arguments[0].removeAttribute('style');", ele);
		  Thread.sleep(1000);
		  ele=driver.findElement(By.xpath(browsButton));
		  ele.sendKeys(filePath);
		  
		  
	  }
	
}

