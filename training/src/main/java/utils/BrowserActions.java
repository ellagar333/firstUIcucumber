package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import managers.WebDriverManager;

public class BrowserActions {
	WebDriverManager webDriverManager = new WebDriverManager();
	WebDriver 	driver = webDriverManager.getDriver();
	
	
	
	public void javaScriptClick(WebElement ele) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", ele);
	}
}
