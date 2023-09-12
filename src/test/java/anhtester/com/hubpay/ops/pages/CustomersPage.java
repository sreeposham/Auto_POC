package anhtester.com.hubpay.ops.pages;

import java.nio.charset.StandardCharsets;
import java.util.List;

import anhtester.com.common.CommonPage;
import anhtester.com.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;


public class CustomersPage extends CommonPage {
	
	private By loginemailInputFld=By.cssSelector("input[name='loginfmt']");
	private By phoneInputFld=By.xpath("//div[text()='Phone']/following-sibling::input");
	private By emailInputFld=By.cssSelector("input[name='emailAddress']");
	private By ibanInputFld=By.cssSelector("input[name='iban']");
	private By fullNameInputFld=By.cssSelector("input[name='fullName']");
	private By searchBtn=By.xpath("//button[text()='Search']");
	private By flagFld=By.xpath("//div[@class='selected-flag']");
	private By searchFlagInputFld=By.xpath("//li[@class='search']/input");
	@FindBy(xpath = "//img")
	private List<WebElement> images;
	private By profileLbl=By.xpath("//h5[text()='Profile']");
	public CustomersPage() {

	}
	
//	public void loginToAdminPortal(String email, String pwd) {
//		clearAndSendKeys(loginemailInputFld, email);
//		click(emailNextBtn);
//		clearAndSendKeys(loginPwdInputFld, pwd);
//		click(pwdSignInBtn);
//		click(signedInYesBtn);
//	}
	
	public void clickLeftMenuItem(String menu) throws InterruptedException {
		WebUI.sleep(10);
		String raw = "(//span[text()='"+menu+"'][contains(@class,'MuiListItemText-primary')]/ancestor::a)[1]";
		By leftMenu = By.xpath(raw);
		WebUI.waitForElementClickable(leftMenu);
//		WebUI.sleep(10);
		WebUI.clickElement(leftMenu,20);
		By header = By.xpath("(//div[text()='"+menu+"'][contains(@class,'MuiTypography-h4')])[1]");
		WebUI.waitForElementVisible(header);
		WebUI.verifyElementVisible(header);
	}
	
	public void selectPhoneCountry(String country) {
		WebUI.clickElement(flagFld);
		String countryName="";
		switch(country){
			case "+91": countryName="India"; break;
			case "+44": countryName="United Kingdom"; break;
			case "+971": countryName="United Arab Emirates"; break;
			case "+92": countryName="Pakistan"; break;
			default: WebUI.verifyTrue(false,"Unsupported country phone number provided");
		}
		WebUI.setText(searchFlagInputFld, countryName);
		By searchedcountry = By.xpath("//span[@class='dial-code'][text()='"+country+"']/parent::li");
//		WebUI.scrollToElement(searchedcountry);
		WebUI.clickElement(searchedcountry);
	}
	public void searchForCustomer(String searchBy, String value){
		WebUI.waitForPageLoaded();
		WebUI.waitForElementVisible(flagFld,5000);
		WebUI.waitForElementVisible(By.xpath("//div[@data-field='mobileNumber']/parent::div[@role='row'][@aria-rowindex='2']"),10000);
//		WebUI.smartWait();
//
//		WebUI.sleep(120);
//		WebUI.waitForElementClickable(searchBtn,15000);
		switch(searchBy.toUpperCase()){
			case "PHONE NUMBER":
				try {
					enterPhoneNumber(value);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case "FULL NAME":
				enterFullName(value);
				break;
			case "IBAN":
				enterIBAN(value);
				break;
			case "EMAIL":
				enterEmail(value);
				break;
				default:
			WebUI.verifyTrue(false,"Incorrect search criteria provided");
		}
	}
	public void enterPhoneNumber(String phone) throws InterruptedException {
		WebUI.smartWait();
		String country=phone.split(" ")[0].trim();
		String number=phone.split(" ")[1].trim();
		WebUI.waitForElementVisible(phoneInputFld);
//		String number=phone.split(" ")[1];
		selectPhoneCountry(country);
		WebUI.setText(phoneInputFld, number);
	}
//
	public void enterIBAN(String iban) {
		WebUI.setText(ibanInputFld, iban);
	}
//
	public void enterFullName(String name) {
		WebUI.setText(fullNameInputFld, name);
	}
	public void enterEmail(String email) {
		WebUI.setText(emailInputFld, email);
	}
//
	public void clickSearch() throws InterruptedException {
		WebUI.smartWait();
		WebUI.waitForElementClickable(searchBtn,60);
		WebUI.moveToElement(searchBtn);
		WebUI.clickElementWithJs(searchBtn);
		WebUI.sleep(5);
	}
////"//div[contains(@class,'MuiDataGrid-virtualScrollerRenderZone')]/div[@class='MuiDataGrid-row']"
	public void clickCustomerRecord(String searchStr,String value) {
		By cusRecord=null;
		switch(searchStr.toUpperCase()) {
			case "PHONE NUMBER":
				cusRecord = By.xpath("(//div[@data-field='mobileNumber'][.='" + value + "']/parent::div[contains(@class,'MuiDataGrid-row')])[1]");
				break;
			case "EMIRATES ID":
				cusRecord = By.xpath("(//div[@data-field='idNumber'][.='" + value + "']/parent::div[contains(@class,'MuiDataGrid-row')])[1]");
				break;
			case "FULL NAME":
				cusRecord = By.xpath("(//div[@data-field='fullName'][.='" + value + "']/parent::div[contains(@class,'MuiDataGrid-row')])[1]");
				break;
			case "EMAIL":
				cusRecord = By.xpath("(//div[@data-field='emailAddress'][.='" + value + "']/parent::div[contains(@class,'MuiDataGrid-row')])[1]");
				break;
		}
		WebUI.waitForElementVisible(cusRecord);
		WebUI.waitForElementClickable(cusRecord);
		WebUI.clickElement(cusRecord);
		WebUI.waitForElementVisible(profileLbl);
	}
	public void verifyLaunchCustomerOverview() {
		WebUI.waitForElementVisible(profileLbl);
		WebUI.verifyElementVisible(profileLbl, "Customer overview screen is displayed");
	}
}
