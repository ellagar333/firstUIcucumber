package seleniumSteps;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import managers.WebDriverManager;

public class Datepicker {

	public static void main(String[] args) throws InterruptedException, AWTException {
		// TODO Auto-generated method stub

		WebDriverManager driverManager=new WebDriverManager();
		WebDriver driver=driverManager.getDriver();
	//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Datepicker obj=new Datepicker();
		obj.validateHeaders(driver,"Original Credit Report Date");
	}
	
	
	public void validateHeaders(WebDriver driver, String inputLastColumnName) throws InterruptedException, AWTException {
		
		String url="https://dev.toorakcapital.info/";
		
		 inputLastColumnName="Annual Flood Insurance";
		String userName="user1@triconinfotech.com";
		String password="Welcome1";
		String userNameLoc="usernameUserInput";
		String passwordLoc="password";
		String checkTermsLoc="chkTerms";
		String loginButtonLoc="submitButton";
		String statusFilter="//*[text()='Status']/following::div[@id='mutiple-checkbox'][1]";
		String submitCheckBox="//ul[@role='listbox']/li[@data-value='submit']/descendant::input";
		String leftTableHeadersLoc="//*[@data-testid='loan-list-enhanced-table']/descendant::div[@class='rdg-header-row']/div[@class='rdg-cell rdg-cell-frozen']/descendant::span[@class='headerText']";
		String rowValuesLeftSide="//*[@data-testid='loan-list-enhanced-table']/descendant::div[contains(@class,'rdg-row rdg-row-')][rowIndex]/div[@class='rdg-cell rdg-cell-frozen']/descendant::span";
		String righSideHeaderLoc="//*[@data-testid='loan-list-enhanced-table']/descendant::div[@class='rdg-header-row']/div[@class='rdg-cell']/descendant::span[@class='headerText']";
		String rowValuesRightSide="//*[@data-testid='loan-list-enhanced-table']/descendant::div[contains(@class,'rdg-row')][rowIndex]/div[@class='rdg-cell'][cellIndex]/descendant::span";
		
		String leftArrow="//img[@alt='left-arrow']";
		
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Thread.sleep(15000);
		driver.findElement(By.id(userNameLoc)).sendKeys(userName);;
		driver.findElement(By.id(passwordLoc)).sendKeys(password);;
		driver.findElement(By.id(checkTermsLoc)).click();;
		driver.findElement(By.id(loginButtonLoc)).click();;
		
		Thread.sleep(20000);
		
		driver.findElement(By.xpath(statusFilter)).click();;
		Thread.sleep(1000);
		driver.findElement(By.xpath(submitCheckBox)).click();;
		Thread.sleep(1000);
		driver.findElement(By.xpath(submitCheckBox)).sendKeys(Keys.TAB);
		Thread.sleep(1000);
		HashMap<String,Integer> leftHeadesindex=new HashMap<String, Integer>();
		HashMap<String,HashMap<String,String>> leftWindowValues=new HashMap<String,HashMap<String,String>>();
		

		HashMap<String,Integer> rightHeadesindex=new HashMap<String, Integer>();
		HashMap<String,HashMap<String,String>> rightWindowValues=new HashMap<String,HashMap<String,String>>();
		
		List<WebElement>leftHeadsEle=driver.findElements(By.xpath(leftTableHeadersLoc));
		
		for(int i=0;i<leftHeadsEle.size();i++) {
			String key=leftHeadsEle.get(i).getText();
			leftHeadesindex.put(key, i);
		}
		
		int Rindex=1;
		String leftRowXpath=rowValuesLeftSide.replace("rowIndex",Rindex+"" );
		List<WebElement> rowValues=driver.findElements(By.xpath(leftRowXpath));
		
		Set<String> leftCol=leftHeadesindex.keySet();
		HashMap<String,String> leftValues=new HashMap<String,String>();
		String loanId=rowValues.get(leftHeadesindex.get("Toorak Loan Id")).getText().trim();
		for(String col:leftCol) {
				int i=	leftHeadesindex.get(col);
				if(Rindex==1 && i!=leftHeadesindex.get("Toorak Loan Id")) {
					 JavascriptExecutor js = (JavascriptExecutor)driver;	
				//	js.executeScript("arguments[0].click();", rowValues.get(i));
				}
			String value=rowValues.get(i).getText()+"";
			leftValues.put(col, value.trim());
		}
		
		
		leftWindowValues.put(loanId, leftValues);
		 leftRowXpath=rowValuesLeftSide.replace("rowIndex",Rindex+"" );
		 rowValues=driver.findElements(By.xpath(leftRowXpath));
		 Rindex++;
	//	}
	System.out.println(leftWindowValues);	
		
	//driver.findElement(By.xpath(leftArrow)).click();
	//Thread.sleep(2000);
	
	List<WebElement>rightHeadsEle=driver.findElements(By.xpath(righSideHeaderLoc));
	Robot rbt=new Robot();
			
	
	System.out.println(rightHeadsEle.size());

	JavascriptExecutor js = (JavascriptExecutor)driver;
	WebElement ele=driver.findElement(By.xpath("//*[text()='Loan Type']"));
	js.executeScript("arguments[0].click();", ele);	
	
	int count=0;
	int maxCount=500;
	HashMap<String,String> rightValues=new HashMap<String, String>();
	while(true && count<maxCount) {
		count++;
		Thread.sleep(1000);
		
		WebElement requeredEle = null;
		rightHeadsEle=driver.findElements(By.xpath(righSideHeaderLoc));
		
		rightHeadesindex=new HashMap<String, Integer>();
		System.out.println(rightHeadsEle.size());
		
		for(int i=0;i<rightHeadsEle.size();i++) {
			String cellValueXpath=rowValuesRightSide.replace("cellIndex", (i+1)+"");
			String cellValue=driver.findElement(By.xpath(cellValueXpath)).getText().trim();
			try {
			rightValues.put(rightHeadsEle.get(i).getText().trim(), cellValue);
			}catch(StaleElementReferenceException e) {
		//	rightHeadesindex=new HashMap<String, Integer>();
				rightHeadsEle=driver.findElements(By.xpath(righSideHeaderLoc));
				
			rightValues.put(rightHeadsEle.get(i).getText().trim(), cellValue);
			}
			
			System.out.println(rightValues);
		}
		
		//for(int j=0;)
		
		System.out.println(rightHeadsEle.size());
		try {
		requeredEle=driver.findElement(By.xpath("//*[text()='"+inputLastColumnName+"']"));
		 Thread.sleep(50);
   	  rbt.keyPress(KeyEvent.VK_RIGHT);
   	  rbt.keyRelease(KeyEvent.VK_RIGHT);
   	 Thread.sleep(50);
	  rbt.keyPress(KeyEvent.VK_RIGHT);
	  rbt.keyRelease(KeyEvent.VK_RIGHT);
		}catch(Exception e) {
			
		}
		if(null==requeredEle) {
			//System.out.println("repeat element not found");
		}else {
			System.out.println(requeredEle.getText());
			for(int i=0;i<rightHeadsEle.size();i++) {
				String cellValueXpath=rowValuesRightSide.replace("cellIndex", (i+1)+"");
				String cellValue=driver.findElement(By.xpath(cellValueXpath)).getText().trim();
				try {
				rightValues.put(rightHeadsEle.get(i).getText().trim(), cellValue);
				}catch(StaleElementReferenceException e) {
			//	rightHeadesindex=new HashMap<String, Integer>();
					rightHeadsEle=driver.findElements(By.xpath(righSideHeaderLoc));
					
				rightValues.put(rightHeadsEle.get(i).getText().trim(), cellValue);
				}
				
				System.out.println(rightValues);
			}
			break;
		}
	
		 ele=driver.findElement(By.xpath("//*[text()='Loan Type']"));
		js.executeScript("arguments[0].click();", ele);	
			
    	   Thread.sleep(50);
    	  rbt.keyPress(KeyEvent.VK_RIGHT);
    	  rbt.keyRelease(KeyEvent.VK_RIGHT);
    	  
    	   Thread.sleep(50);
     	  rbt.keyPress(KeyEvent.VK_RIGHT);
     	  rbt.keyRelease(KeyEvent.VK_RIGHT);
     	   Thread.sleep(50);
     	  rbt.keyPress(KeyEvent.VK_RIGHT);
     	  rbt.keyRelease(KeyEvent.VK_RIGHT);
     	
	}
	System.out.println("********************************************88");
	System.out.println("second row right values="+rightValues.size());
	System.out.println(rightValues);
	System.out.println("************************************************************");
	System.out.println(count);
	Thread.sleep(1000);
	rightHeadsEle=driver.findElements(By.xpath(righSideHeaderLoc));
	int colIndewx=0;
	for(WebElement elee:rightHeadsEle) {
		String columnName=elee.getText().trim();
		if(inputLastColumnName.equalsIgnoreCase(columnName)) {
			colIndewx++;
			break;
		}else {
			colIndewx++;
		}
		System.out.println(elee.getText());
	}
	
	WebElement cellValue=null;
	if(count<maxCount && colIndewx==0) {
		
		System.out.println("Column found but column index not found");
	}else if(count>=maxCount) {
		System.out.println("coulumn not found:"+"Original Credit Report ");
	}else {
		
		String firstRowcellXpath=rowValuesRightSide.replace("rowIndex", "2");
		firstRowcellXpath=firstRowcellXpath.replace("cellIndex", ""+colIndewx);
		cellValue=driver.findElement(By.xpath(firstRowcellXpath));
		System.out.println("**************************column values fetched successfully**********************************");
		System.out.println(inputLastColumnName+"="+cellValue.getText().trim());
	}
		
	}


	  public HashMap<String,String> getAndInsertRowValuesIntoMap(WebDriver driver,String headersXpath,String rowValuesXpath ){
		
		  List<WebElement> headers=driver.findElements(By.xpath(headersXpath));
		  HashMap<String,String> rowMap=new HashMap<String, String>();
		
		  for(int i=0;i<headers.size();i++) {
			
			  String cellValueXpath=rowValuesXpath.replace("cellIndex", (i+1)+"");
				String cellValue=driver.findElement(By.xpath(cellValueXpath)).getText().trim();
				
				try {
					rowMap.put(headers.get(i).getText().trim(), cellValue);
					}catch(StaleElementReferenceException e) {
				//	rightHeadesindex=new HashMap<String, Integer>();
						headers=driver.findElements(By.xpath(headersXpath));
						
						rowMap.put(headers.get(i).getText().trim(), cellValue);
					}
					
		  }
		  return rowMap;
		  
	  }
	
	
}

