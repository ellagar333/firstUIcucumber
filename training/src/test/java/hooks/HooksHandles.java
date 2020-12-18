package hooks;

import org.openqa.selenium.WebDriver;

import managers.WebDriverManager;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.cucumber.listener.Reporter;
import com.google.common.io.Files;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class HooksHandles {

	WebDriver driver;
	WebDriverManager webDriverManager;
	
	
	@Before("@abcd")
	 public void beforeAbcdo(Scenario scenario) {
	System.out.println("before abcd scenario");	
	}	
	@After("@selenium")
	 public void afterScenario(Scenario scenario) {
		webDriverManager = new WebDriverManager();
		driver = webDriverManager.getDriver();
		
	 if (scenario.isFailed()) {
	 String screenshotName = scenario.getName().replaceAll(" ", "_");
	 try {
	 //This takes a screenshot from the driver at save it to the specified location
	 File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	 
	 //Building up the destination path for the screenshot to save
	 //Also make sure to create a folder 'screenshots' with in the cucumber-report folder
	 String userDir=System.getProperty("user.dir");
	 System.out.println(userDir);
	 File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + screenshotName + ".png");
	 
	 //Copy taken screenshot from source location to destination location
	 Files.copy(sourcePath, destinationPath);   
	 
	 //This attach the specified screenshot to the test
	 Reporter.addScreenCaptureFromPath(destinationPath.toString());
	 } catch (IOException e) {
		 e.printStackTrace();
	 } 
	 }
	 }
}
