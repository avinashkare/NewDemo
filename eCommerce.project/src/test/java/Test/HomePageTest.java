package Test;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageObjects.HomePageObjects;
import PageObjects.ProductDetailPageObject;
import PageObjects.SearchedProductsPageObject;
import utils.CommonUtility;

public class HomePageTest {
	
	WebDriver driver;
	HomePageObjects hpo;
	SearchedProductsPageObject sppo;
	ProductDetailPageObject pdpo;
	CommonUtility cu = new CommonUtility();
	
	@BeforeSuite
	@Parameters({"browser", "url"})
	void start(String browser, String url) {
		driver = cu.initSetup(browser);
		cu.launchURL(url);
	}
	
	@BeforeClass
	void initObjs() {
		hpo = new HomePageObjects(driver);
		sppo = new SearchedProductsPageObject(driver);
		pdpo = new ProductDetailPageObject(driver);
	}
	
	@Test(priority=1)
	void urlTest() throws IOException {
		String actualUrl = driver.getCurrentUrl();
		CommonUtility.takeSS(driver);
		Assert.assertEquals(actualUrl , cu.getDataFromExcel("C:\\Users\\Akash Kare\\eclipse-workspace\\eCommerce.project\\src\\test\\resources\\TestData\\homePageData.xlsx", "urlData", 0, 0) , cu.getDataFromExcel("C:\\Users\\Akash Kare\\eclipse-workspace\\eCommerce.project\\src\\test\\resources\\TestData\\homePageData.xlsx", "urlData", 0, 1));
	}
	
	@Test(priority=2)
	void logoTest() {
		hpo.closeIntroPopup();
		boolean actualLogo = hpo.isLogoPresent();
		Assert.assertTrue(actualLogo, "Logo Test is Successful");
	}
	
	@Test(priority=3)
	void searchFieldTest() throws EncryptedDocumentException, IOException
	{
		boolean searchBarFlag = hpo.isSearchBarPresent();
		Assert.assertTrue(searchBarFlag, "Search Field is Displayimg on Home Page");
		hpo.setSearchBarField(cu.getDataFromExcel("C:\\Users\\Akash Kare\\eclipse-workspace\\eCommerce.project\\src\\test\\resources\\TestData\\homePageData.xlsx", "addCartData", 0, 0));
		Assert.assertTrue(hpo.isSearchIconPresent(), "Search Icon is Present");
		hpo.clickSearchIcon();
		sppo.validateHeaderPage("Searched Products");
		sppo.clickFirstProduct();
		String actualHeader = pdpo.isProductDetailPageLoaded();
		Assert.assertEquals(actualHeader, "Home Product Detail", "Successfully navigated to Product Details Page");
		String actualProductName = pdpo.getProductName();
		Assert.assertEquals(actualProductName, "ACCUSURE TD BLOOD PRESSURE MONITORING SYSTEM (1 PC)" , "Correct Product is displaying");
		boolean actualAddtoCartFlag = pdpo.verifyAddToCardButton();
		Assert.assertTrue(actualAddtoCartFlag, "Add to cart button is displaying for product = " +actualProductName + ".");
	}
	
	@AfterClass
	void closeBrowser() throws InterruptedException {
		Thread.sleep(10);
		driver.quit();
	}

}
