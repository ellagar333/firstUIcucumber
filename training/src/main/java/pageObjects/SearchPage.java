package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

	

	public SearchPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.CSS, using = ".cart-button") 
	private WebElement btn_Cart;

	@FindBy(how = How.CSS, using = ".checkout-button.alt") 
	private WebElement btn_ContinueToCheckout;
///*****************************////
	
	
	public void clickOn_Cart() {
		btn_Cart.click();
	}
	
}
