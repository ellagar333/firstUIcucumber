package seleniumSteps;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import managers.PageObjectManager;
import managers.WebDriverManager;
public class BrowserStepDefinions {

	
	WebDriver driver;
	WebDriverManager webDriverManager;

	@When("^open browser with url \"([^\"]*)\"$")
		public void openBrowserWithUrl(String url){
		webDriverManager = new WebDriverManager();
		driver = webDriverManager.getDriver();
		
		driver.get(url);
	}

	
	@When("^scroll down$")
	public void scrollDown(){
	webDriverManager = new WebDriverManager();
	driver = webDriverManager.getDriver();
	JavascriptExecutor js = (JavascriptExecutor) driver;
		
      js.executeScript("window.scrollBy(0,1000)");
	}
	
	@When("^scroll up$")
	public void scrollUp(){
	webDriverManager = new WebDriverManager();
	driver = webDriverManager.getDriver();
	JavascriptExecutor js = (JavascriptExecutor) driver;
		
      js.executeScript("window.scrollBy(1000,0)");
	}
	@When("^backword$")
	public void backward(){
	webDriverManager = new WebDriverManager();
	driver = webDriverManager.getDriver();
	
	driver.navigate().back();
	}
	
	@When("^forward$")
	public void forwar(){
	webDriverManager = new WebDriverManager();
	driver = webDriverManager.getDriver();
	
	driver.navigate().forward();;
	}
	

	@When("^accept alert$")
	public void alertAccep(){
	webDriverManager = new WebDriverManager();
	driver = webDriverManager.getDriver();
	
	driver.switchTo().alert().accept();
	}
	
	@When("^cancel alert$")
	public void alertCance(){
	webDriverManager = new WebDriverManager();
	driver = webDriverManager.getDriver();
	
	driver.switchTo().alert().dismiss();
	}
	
	@When("^type text in alert \"([^\"]*)\"$")
	public void sendTextToAlert(String data){
	webDriverManager = new WebDriverManager();
	driver = webDriverManager.getDriver();
	driver.switchTo().alert().sendKeys(data);
	}
	
	
}
