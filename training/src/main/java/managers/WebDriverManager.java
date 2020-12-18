package managers;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import dataProviders.ConfigFileReader;
import enums.DriverType;
import enums.EnvironmentType;

public class WebDriverManager {
	private static WebDriver driver;
	private static DriverType driverType;
	private static EnvironmentType environmentType;
	private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";
	private static final String IE_DRIVER_PROPERTY = "webdriver.ie.driver";
	private  ConfigFileReader cfr=null;

	public WebDriverManager() {
		 
		FileReaderManager fr=FileReaderManager.getInstance();
		 cfr=fr.getConfigReader();
		driverType = cfr.getBrowser();
		environmentType = cfr.getEnvironment();
	}

	public WebDriver getDriver() {
		if(driver == null) driver = createDriver();
		return driver;
	}

	private WebDriver createDriver() {
		   switch (environmentType) {	    
	        case LOCAL : driver = createLocalDriver();
	        	break;
	        case REMOTE : driver = createRemoteDriver();
	        	break;
		   }
		   return driver;
	}

	private WebDriver createRemoteDriver() {
		throw new RuntimeException("RemoteWebDriver is not yet implemented");
	}

	private WebDriver createLocalDriver() {
        switch (driverType) {	    
        case FIREFOX : driver = new FirefoxDriver();
	    	break;
        case CHROME : 
        	String driverPath=cfr.getDriverPath();
        	System.setProperty(CHROME_DRIVER_PROPERTY, cfr.getDriverPath());
        			//FileReaderManager.getInstance().getConfigReader().getDriverPath());
        	driver = new ChromeDriver();
    		break;
        case INTERNETEXPLORER : driver = new InternetExplorerDriver();
    		break;
        }

        if(cfr.getBrowserWindowSize()) {
        		
        		//FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize())
        	driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(cfr.getImplicitlyWait(), TimeUnit.SECONDS);
      //  driver.get(cfr.getApplicationUrl());
		return driver;
	}	

	public void closeDriver() {
		driver.close();
		//driver.quit();
	}
	public void quitDriver() {
		
		driver.quit();
	}
}