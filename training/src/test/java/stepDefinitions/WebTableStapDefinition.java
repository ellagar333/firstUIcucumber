package stepDefinitions;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import managers.PageObjectManager;
import managers.WebDriverManager;
import pageObjects.WebtablesPage;

public class WebTableStapDefinition {
	
	WebDriver driver;
	WebDriverManager webDriverManager;
	PageObjectManager pageObjectManager;	
	WebtablesPage webtablesPage;
	static int count=0;
	private void intiateObjects() {
		if(count==0) {
		count++;
		webDriverManager = new WebDriverManager();
		driver =webDriverManager.getDriver();
		pageObjectManager = new PageObjectManager(driver);
		webtablesPage = pageObjectManager.getWebtablePage();
		}
	}
	
	
	@When("^user click on add row button$")
	public void clickOnAddNewRow() {
		intiateObjects();
		webtablesPage.clickOnAddNewRecord();
	}
	@Then("^verify registration form is displayed$")
	public void verifyRegistrationForm() {
		intiateObjects();
		boolean isDisp=webtablesPage.isRegistrationFormDisplayed();
		
		Assert.assertTrue("Registration form is not displayed",isDisp);
	}
	
	
	@When("^user enters employeedetails$")
	
	public void enterUserDetails(DataTable userDetails) throws Throwable {
		intiateObjects();
		 List<Map<String,String>> data=userDetails.asMaps(String.class, String.class);
		
		 Map<String,String> userData=data.get(0);
		 webtablesPage.fillTheEmployeeDetails(userData);
		 
	}
	
	@When("^print WebTable values$")
	public void printWebTableValue() {
		intiateObjects();
		webtablesPage.printWebTableValues();
	}
	
}
