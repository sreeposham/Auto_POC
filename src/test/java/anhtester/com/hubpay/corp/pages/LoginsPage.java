package anhtester.com.hubpay.corp.pages;

import anhtester.com.common.CommonPage;
import anhtester.com.hubpay.ops.pages.CustomersPage;
import anhtester.com.keywords.WebUI;
import org.openqa.selenium.By;


public class LoginsPage extends CommonPage {

	private By corp_login_emailInput = By.cssSelector("input[name='username']");
	private By corp_login_pwdInput = By.cssSelector("input[name='password']");
	private By corp_login_nextBtn = By.xpath("//button[text()='Next']");
	private By corp_signoutBtn = By.xpath("//li[contains(.,'Sign out')]");
	public LoginsPage() {

	}

	public DashboardPage loginCorpPortal(String email, String password) {
//		System.out.println("After try");
		WebUI.setText(corp_login_emailInput,email);
		WebUI.setText(corp_login_pwdInput,password);
		WebUI.clickElementWithJs(corp_login_nextBtn);
//		WebUI.clickElement(corp_login_nextBtn,10);
		WebUI.waitForElementVisible(corp_signoutBtn);
		System.out.println("Login successful");
		return new DashboardPage();
	}
	public void signOutCorpPortal() {
//		System.out.println("After try");
		WebUI.clickElement(corp_signoutBtn,10);
		WebUI.verifyElementVisible(corp_login_emailInput,10);
	}
	


}
