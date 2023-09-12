package anhtester.com.hubpay.ops.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anhtester.com.common.CommonPage;
import anhtester.com.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class CustomerOverviewPage extends CommonPage {

    private By promoteBusinessBtn=By.xpath("//button[text()='Promote account to business']");
	;


	private By promoteBusinessFormHdr=By.xpath("//h6[text()='Please enter business details']");


	private By submitBtn=By.xpath("//button[text()='Submit']");


	private By profileLbl=By.xpath("//h5[text()='Profile']");

	public CustomerOverviewPage() {

	}
    public String getWalletBalance(String walletCurrency, String balanceType) {
        String act="";
		String labelAvailableBal="AVAILABLE BALANCE";
		String labelPendingReserveBal="PENDING TRANSACTIONS (RESERVED)";
        WebUI.waitForElementVisible(By.xpath("//div[@aria-label='currency'][text()='AED']"));
		By availableBalance=By.xpath("//div[.='"+walletCurrency+"']/following-sibling::div/span[contains(text(),'Available Balance')]");
        By pendingTransactionBalance=By.xpath("//div[.='"+walletCurrency+"']/following-sibling::div/span[contains(text(),'Pending transactions')]");
        if(labelAvailableBal.contains(balanceType.toUpperCase())) {
            act=walletCurrency+" "+(WebUI.getTextElement(availableBalance).toString().split(":")[1]).trim();
        }else if(labelPendingReserveBal.contains(balanceType.toUpperCase())) {
            act=walletCurrency+" "+(WebUI.getTextElement(pendingTransactionBalance).toString().split(" ")[1]).trim();
        }
        return act;
    }
	public String formatDecimalValue(String act){
		String formattedAct="";
		Double totalPayableVal=Double.valueOf(act);
//		NumberFormat nf = NumberFormat.getNumberInstance();
//		nf.setMaximumFractionDigits(2);
//		String str_totalPayableVal = nf.format(totalPayableVal);
		formattedAct =String.format("%.2f",totalPayableVal);
		return formattedAct;
	}
    public void verifyWalletBalance( String balanceType,String walletCurrency, String balance) {
		String act = getWalletBalance(walletCurrency, balanceType).split(" ")[0] + " " +
				formatDecimalValue(getWalletBalance(walletCurrency, balanceType).split(" ")[1].trim());
		if (balance.contains(",")) {
			balance = balance.replace(",", "");
		}
		WebUI.verifyTrue(balance.contentEquals(act), "Actual " + balanceType + ":'" + act + "' & Expected " + balanceType + ":'" + balance + "'");
    }
    public void clickTab(String tab) {
        By byTab=By.xpath("//button[.='"+tab+"']");
        WebUI.clickElement(byTab);

    }
    public void verifyTabPageDisplayed(String tab) {
        By byTab=By.xpath("//button[.='"+tab+"']");
        String act=WebUI.getAttributeElement(byTab,"aria-selected").trim();
        WebUI.verifyTrue(act.contentEquals("true"),"Actual: '"+act+"' & Expected: 'true'");
    }

//	public void openPromoteToBusinessForm() throws InterruptedException {
//        WebUI.waitForElementVisible(promoteBusinessBtn);
//		WebUI.clickElementWithJs(promoteBusinessBtn);
//		WebUI.waitForElementVisible(promoteBusinessFormHdr);
//	}
//
//	public void fillBusinessDetailsInForm(HashMap<String, String> datamap) throws InterruptedException {
//		if (datamap == null)
//			return;
//		for (Map.Entry<String, String> entry : datamap.entrySet()) {
//			switch (entry.getKey().toLowerCase()) {
//			case "business registration name":
//				clearAndSendKeys("business registration name", entry.getValue());
//				break;
//			case "business registration id":
//				clearAndSendKeys("business registration id", entry.getValue());
//				break;
//			case "registered country":
//				clickAndSelectValueForDropdown("Registered country", entry.getValue());
//				break;
//			case "business operations type":
//				clickAndSelectValueForDropdown("business operations type", entry.getValue());
//				break;
//			case "legal business type":
//				clickAndSelectValueForDropdown("legal business type", entry.getValue());
//				break;
//			case "business address":
//				clearAndSendKeys("business address", entry.getValue());
//				break;
//			case "busines city":
//				clearAndSendKeys("busines city", entry.getValue());
//				break;
//			case "business postal code":
//				clearAndSendKeys("business postal code", entry.getValue());
//				break;
//			case "business province state":
//				clearAndSendKeys("business province state", entry.getValue());
//				break;
//			case "expected monthly turnover amount":
//				clearAndSendKeys("Expected monthly turnover amount", entry.getValue());
//				break;
//			case "turnover currency":
//				clearAndSendKeys("turnover currency", entry.getValue());
//				break;
//			default:
//				Assert.assertFalse(true, "Incorrect field name provided");
//			}
//		}
//
//	}
//
//	public void clickSubmitBtn() throws InterruptedException{
//		jsClick(submitBtn);
//		waitForElementToBeDisplayed(profileLbl);
//	}
//
//	public void editCustomerStatus(String statusLabel, String status, String remarks) throws InterruptedException {
//		Thread.sleep(2000);
//		By statusEditFld=By.xpath("//p[text()='"+statusLabel+"']/following-sibling::div//button");
//		waitForElementToBeDisplayed(statusEditFld);
//		jsClick(statusEditFld);
//		By statusEditFormHdr=By.xpath("//h2[text()='Set "+statusLabel.toLowerCase()+"']");
//		waitForElementToBeDisplayed(statusEditFormHdr);
//		SelectValueForDropdown("Status",status);
//		clearAndSendKeys(driver.findElement(By.xpath("//textarea[@name='note']")),remarks);
//		Thread.sleep(3000);
//		Actions act = new Actions(driver);
//		   act.sendKeys(Keys.TAB).build().perform();
//		   act.sendKeys(Keys.RETURN).build().perform();
//		Thread.sleep(3000);
//	}
//
//	public void verifyAlertMessage(String msgText) {
//		try {
//			Thread.sleep(2000);
//			System.out.println(driver.findElement(By.xpath("//div[@class='SnackbarItem-message']")).getText());
////			By byAlert=By.xpath("//div[@class='SnackbarItem-message'][.='"+msgText+"']");
//			By byAlert=By.xpath("//div[@class='SnackbarItem-message']");
//			Assert.assertTrue(driver.findElement(byAlert).getText().equals(msgText), "Toast Alert Message: '"+msgText+"' is not displayed");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	public void verifyBusinessUserStatusCheck() throws InterruptedException {
//		//verify promoted to business button not available
//		//Business verification status Approved
//		Assert.assertTrue(!checkElementDisplayed(promoteBusinessBtn), "Customer is not promoted, hence Promote to business button is still available");
//		verifyUserStatus("Business verification status","APPROVED");
//	}

	public void verifyUserStatus(String statusLvl, String status) {
		By statusCheck=By.xpath("//p[text()='"+statusLvl+"']/following-sibling::div/p");
		WebUI.verifyElementText(statusCheck,status);
//		String actStatus=driver.findElement(statusCheck).getText().trim();
//		Assert.assertTrue(status.toUpperCase().equals(actStatus),"User '"+statusLvl+"' actual status: '"+actStatus+"'");
	}

	//***Wallet Management***//

	public void clickTabMenu(String menu) {
		if(menu==null) return;
		By tab=By.xpath("//button[@role='tab'][text()='"+menu+"']");
		WebUI.sleep(2);
		WebUI.verifyElementVisible(tab);
		WebUI.waitForElementClickable(tab);
		WebUI.clickElement(tab);
		By selectedtab=By.xpath("//button[@role='tab'][@aria-selected='true'][text()='"+menu+"']");
		WebUI.verifyElementVisible(selectedtab);
	}

	public void click_WalletAction(String btn) {
		if(btn==null) return;
		By walletAction=null;
			switch(btn.toUpperCase()) {
			case "DIRECT BANK TOPUP":
				walletAction=By.xpath("//button[text()='Direct bank topup']");
				WebUI.clickElement(walletAction);
				break;
			case "CREDIT WALLET":
				walletAction=By.xpath("//button[contains(text()='CREDIT') and contains(.='wallet')]");
				WebUI.clickElement(walletAction);
				break;
			case "DEBIT WALLET":
				walletAction=By.xpath("//button[contains(text()='DEBIT') and contains(.='wallet')]");
				WebUI.clickElement(walletAction);
				break;
			default:
				WebUI.verifyTrue(false,"Incorrect button name provided");
			}
	}

//	By.xpath("//button[text()='Create journal']")
//	private By createJournalBtn;
//
//	By.xpath("//label[text()='Debit account']/parent::div")
//	private By debitAccountFld;
//	By.xpath("//label[text()='Debit account']/following-sibling::div")
//	private By debitAccountBox;
//
//	public void openAddANewJournalForm() {
//		try {
//			Thread.sleep(3000);
//			click(createJournalBtn);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		jsClick(debitAccountFld);
//	}
//
//	public void printAllDebitAccounts() {
//		click(debitAccountBox);
//		List<WebElement> debtAccountsLs=driver.findElements(By.xpath("//div[@id='menu-debitAccount']/descendant::li"));
//		for(WebElement ele:debtAccountsLs) {
//			System.out.println(ele.getText());
//		}
//	}




}
