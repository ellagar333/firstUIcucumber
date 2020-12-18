package managers;



import org.openqa.selenium.WebDriver;

import pageObjects.CartPage;

import pageObjects.CheckoutPage;

//import pageObjects.ConfirmationPage;

import pageObjects.HomePage;
import pageObjects.PracticeForm;
import pageObjects.ProductListingPage;
import pageObjects.SearchPage;
import pageObjects.WebtablesPage;



public class PageObjectManager {

	private WebDriver driver;

	private ProductListingPage productListingPage;

	private CartPage cartPage;

	private HomePage homePage;

	private CheckoutPage checkoutPage;
	private PracticeForm practiceForm;
	private SearchPage searchPage;
	private WebtablesPage webtablesPage;


	//private ConfirmationPage confirmationPage;

	

	public PageObjectManager(WebDriver driver) {

		this.driver = driver;

	}

	

	public HomePage getHomePage(){

		return (homePage == null) ? homePage = new HomePage(driver) : homePage;

	}

	

	public ProductListingPage getProductListingPage() {

		return (productListingPage == null) ? productListingPage = new ProductListingPage(driver) : productListingPage;

	}

	

	public CartPage getCartPage() {

		return (cartPage == null) ? cartPage = new CartPage(driver) : cartPage;

	}

	

	public CheckoutPage getCheckoutPage() {

		return (checkoutPage == null) ? checkoutPage = new CheckoutPage(driver) : checkoutPage;

	}
	
	public PracticeForm getPracticeForm() {

	
		return (practiceForm == null) ? practiceForm = new PracticeForm(driver) : practiceForm;

	}

	public SearchPage getSearchpage() {

		 if(searchPage==null) {
			 searchPage = new SearchPage(driver) ;
		 }else {
			 //ignore
		 }
		 return searchPage;
		//return (searchPage == null) ? searchPage = new SearchPage(driver) : searchPage;

	}
	
	
	
	public WebtablesPage getWebtablePage() {

		
		return (webtablesPage == null) ? webtablesPage = new WebtablesPage(driver) : webtablesPage;

	}
}