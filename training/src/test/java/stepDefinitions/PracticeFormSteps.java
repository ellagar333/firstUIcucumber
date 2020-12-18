package stepDefinitions;

import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import managers.PageObjectManager;
import managers.WebDriverManager;
import pageObjects.PracticeForm;
import pageObjects.SearchPage;

public class PracticeFormSteps {
	WebDriver driver;
	WebDriverManager webDriverManager;
	PageObjectManager pageObjectManager;	
	PracticeForm practiceForm;
	SearchPage searchPage;
	@Given("^open Browser with url$")
	public void upenBroweserWithUrl(){
		
		webDriverManager = new WebDriverManager();
		driver = webDriverManager.getDriver();
		pageObjectManager = new PageObjectManager(driver);
		practiceForm = pageObjectManager.getPracticeForm();
		searchPage=pageObjectManager.getSearchpage();
		
	}
	
	@When("^fill FormDetails \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\\\"]*)\"$")
		public void fillFormDetails(String firstName,String lastName,String sex, String profision,String yerasOfExp, String Date)  {
		
		practiceForm.fillFormDeatials(firstName, lastName, sex, yerasOfExp, Date, profision);
	
	}


	
}
