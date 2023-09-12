package anhtester.com.hubpay.corp.pages;

import anhtester.com.common.CommonPage;
import anhtester.com.keywords.WebUI;
import net.bytebuddy.build.Plugin;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DashboardPage extends CommonPage {
	private By sendBtn=By.xpath("//a[text()='Send']");
	private By dashboardMenu=By.xpath("//a[text()='Dashboard']");
	private By sendInternationalBtn=By.xpath("//a[text()='Send international']");
	private By exchangeBtn=By.xpath("//a[text()='Exchange']");
	private By addFundsBtn=By.xpath("//button[text()='Add funds']");
	private By withdrawBtn=By.xpath("//button[text()='Withdraw']");
	private By exchangeHdr = By.xpath("//h1[text()='Exchange']");
	private By paymentsHdr = By.xpath("//h1[text()='Payments']");
	private By dashboardHdr = By.xpath("//h1[text()='Dashboard']");
	private By internaltionalPaymentsHdr = By.xpath("//h1[text()='International payment']");
	private By addFundsHdr = By.xpath("//h2[text()='Add funds']");
	private By withdrawFundsHdr = By.xpath("//h2[text()='Withdraw funds']");
	private By transactionsTabLink=By.xpath("//button[text()='Transactions']");
	private By walletsTabLink=By.xpath("//button[text()='Wallets']");
	private By gbpWalletCard=By.xpath("//p[text()='British Pound Sterling']/parent::div");
	private By dashbrdTitle=By.xpath("//h1[text()='Dashboard']");
	private By leftmenu_signout=By.xpath("//li[text()=' Sign out']");
	private By corp_signoutBtn = By.xpath("//li[contains(.,'Sign out')]");
	private By transactions_searchbox=By.xpath("//input[@id='search']");
	private By label_RecipientDetails=By.xpath("//h2[text()='Recipient details']");
	private By label_RelatedPayments=By.xpath("//h2[text()='Related payments']");
	public DashboardPage() {

	}

	public void verifyPageLaunch(String page) {

		switch (page.toUpperCase()) {
			case "DASHBOARD":
				WebUI.clickElement(dashboardMenu);
				WebUI.verifyElementVisible(dashboardHdr,30,"Dashboard page is not displayed");
				break;
			case "PAYMENTS":
				WebUI.clickElement(sendBtn);
				WebUI.verifyElementVisible(paymentsHdr,30,"Payments page is not displayed");
				break;
			case "INTERNATIONAL PAYMENTS":
				WebUI.clickElement(sendInternationalBtn);
				WebUI.verifyElementVisible(internaltionalPaymentsHdr,30,"International Payments page is not displayed");
				break;
			case "EXCHANGE":
				WebUI.clickElement(exchangeBtn);
				WebUI.verifyElementVisible(exchangeHdr,30,"Exchange page is not displayed");
				break;
			case "ADD FUNDS":
				WebUI.clickElement(addFundsBtn);
				WebUI.verifyElementVisible(addFundsHdr,30,"Add Funds modal is not displayed");
				break;
			case "WITHDRAW":
				WebUI.clickElement(withdrawBtn);
				WebUI.verifyElementVisible(withdrawFundsHdr,30,"Withdraw funds modal is not displayed");
				break;
			default:
				WebUI.verifyTrue(false, "Incorrect button provided");
		}
	}
	public void verifyDashboardIsDisplayed() {
			WebUI.waitForElementVisible(dashbrdTitle);
			WebUI.verifyElementVisible(dashbrdTitle,"Dashboard page is not displayed");
	}

	public String getWalletBalanceOnDashboardPage(String corridor) {
		By by=By.xpath("//p[contains(text(),'"+corridor+"')]");
		return WebUI.getTextElement(by);
	}
/*######################TRANSACTIONS TAB#########################*/

	By title_TransactionDetail=By.xpath("//h2[contains(@id,'radix-')]/h1");

	By title_TransactionStatus=By.xpath("//h1[contains(text(),'receipt')]/following-sibling::*");

	By title_RelatedPayments=By.xpath("//h2[text()='Related payments']");


	By iconClose=By.xpath("//span[text()='Close']/parent::button");

	By btnDownload=By.xpath("//button[text()='Download']");
	By contactSupport=By.xpath("//p[text()='Have a question about this transaction?']/a[text()='Contact support']");
	public void openTransactionDetailsPage(Map<String,Map<String,String>> exchange_details, String status) {
		String amount=exchange_details.get("COMMON").get("toCurrencyCode")+" "+exchange_details.get("EXCHANGE").get("toAmount");
		By record=By.xpath("//td[.='"+exchange_details.get("EXCHANGE").get("reference")+"']/parent::tr");
		WebUI.verifyTrue(WebUI.verifyElementVisible(record),"Transaction is not available in the table:"+exchange_details.get("EXCHANGE").get("reference"));
		List<WebElement> lsTableCols=WebUI.getWebElements(By.xpath("//td[.='"+exchange_details.get("EXCHANGE").get("reference")+"']/parent::tr/td[contains(@class,'text-sm')]"));
		System.out.println(lsTableCols.size());
		WebUI.verifyTrue(lsTableCols.get(2).getText().contentEquals(amount));
		WebUI.verifyTrue(lsTableCols.get(4).getText().equalsIgnoreCase(status));
		if(exchange_details.get("EXCHANGE").get("type").equalsIgnoreCase("Foreign Exchange")|exchange_details.get("EXCHANGE").get("type").equalsIgnoreCase("International Exchange")|exchange_details.get("EXCHANGE").get("type").equalsIgnoreCase("Exchange")){
			WebUI.verifyTrue(lsTableCols.get(3).getText().contentEquals("Foreign Exchange"));
			WebUI.verifyTrue(lsTableCols.get(5).getText().contentEquals("-"));
		} else if (exchange_details.get("EXCHANGE").get("type").equalsIgnoreCase("Payment")) {
			WebUI.verifyTrue(lsTableCols.get(5).getText().contentEquals("Recipient name"));
		}
		WebUI.clickElement(record);
		WebUI.verifyElementVisible(title_TransactionDetail,"Transaction details page is not opened");
	}
	public void verifyAvailableFundsInCorridor(String corridor,String funds){
		By availableFunds=By.xpath("//p[text()='Available funds']/following-sibling::p[contains(text(),'"+corridor+"')]");
		String formatAct="";
		String formatFunds="";
		if(funds.contains(corridor)){
			if(funds.contains(",")){
				formatAct=WebUI.getTextElement(availableFunds).trim();
			}
			else {
				formatAct=WebUI.getTextElement(availableFunds).trim().replace(",","");
			}
		}else{
			if(funds.contains(",")){
				formatAct=WebUI.getTextElement(availableFunds).split(" ")[1].trim();
			}else{
				formatAct=WebUI.getTextElement(availableFunds).split(" ")[1].trim().replace(",","");

			}
		}
		WebUI.verifyTrue(funds.contentEquals(formatAct),"Funds mismatch: Actual:'"+formatAct+" Expected:'"+funds+"'");
	}
	public void verifyFieldValue(String fldName,String value) {
		By by=By.xpath("//span[text()='"+fldName+"']/following-sibling::div");
		if(fldName.contains("RelatedPayments_")){
			fldName=fldName.replace("RelatedPayments_","");
			by=By.xpath("(//span[text()='"+fldName+"']/following-sibling::div)[2]");
		}
		String act="";
		String exp="";
		if(fldName.contentEquals("Exchange rate")){
			act=WebUI.getAttributeElement(by,"innerText").trim();
			String[] rate=value.split(" ");
			//1 AUD = 2.46706 AED
			exp=rate[1].trim()+" "+rate[0].trim()+" = "+rate[4].trim()+" "+rate[3].trim();
		}else {
			act=WebUI.getTextElement(by).trim();
			exp=value;
		}

		WebUI.verifyTrue(act.contentEquals(exp),"Actual value:'"+act+" & Expected value:'"+exp+"'");
	}

	public void verifyOnlyExchangeTransactionDetails(Map<String,Map<String,String>> exchange_details) {
		String settlementDt="";
		By creationDate=By.xpath("//h1[text()='Exchange receipt']/parent::h2/following-sibling::p");
//		String expCreateDt=(new SimpleDateFormat("EEEE dd MMMM yyyy").format(new Date())).toString();
		WebUI.verifyElementTextContains(creationDate,exchange_details.get("EXCHANGE").get("createdAt"));
		String amount=exchange_details.get("EXCHANGE").get("fromAmount");
		verifyFieldValue("Settlement due date",exchange_details.get("COMMON").get("settlementDate"));
		verifyFieldValue("From",amount);
		amount=exchange_details.get("COMMON").get("toCurrencyCode")+" "+exchange_details.get("EXCHANGE").get("toAmount");
		verifyFieldValue("To",amount);
		verifyFieldValue("Exchange rate",exchange_details.get("COMMON").get("liveRate"));
		verifyFieldValue("Transaction reference",exchange_details.get("EXCHANGE").get("reference"));
		if(exchange_details.get("EXCHANGE").get("type").equalsIgnoreCase("International Exchange")){
			verifyFieldValue("Total settlement amount (incl. payment fees)",exchange_details.get("COMMON").get("totalPayable"));
			WebUI.verifyElementVisible(title_RelatedPayments);
			verifyFieldValue("Total fees (incl. 5% VAT)",exchange_details.get("COMMON").get("totalFees"));
			verifyFieldValue("Total amount",exchange_details.get("COMMON").get("totalReceiveAmount"));
			verifyFieldValue("Number of payments",exchange_details.get("COMMON").get("noOfPayments"));
			if(!exchange_details.get("EXCHANGE").get("status").equalsIgnoreCase("ordered")){
				verifyFieldValue("Expected payment date",exchange_details.get("COMMON").get("settlementDate"));
			}
		}else{
			verifyFieldValue("Total funds due",exchange_details.get("COMMON").get("totalPayable"));
		}
		WebUI.verifyElementVisible(btnDownload,"Download button is not available");
	}

	public void clickDownloadBtn() {
		WebUI.clickElement(btnDownload);
	}
	public void clickCloseBtn() {
		WebUI.clickElement(iconClose);
	}

	public void verifyStatusTransactionDetails(String stepStatus) {
			WebUI.waitForElementVisible(title_TransactionStatus);
		String act=WebUI.getTextElement(title_TransactionStatus).trim();
		WebUI.verifyTrue(act.equalsIgnoreCase(stepStatus.trim()),"Actual value:'"+act+"' & Expected value:'"+stepStatus+"'");
		act=WebUI.getAttributeElement(title_TransactionStatus,"class");
		if(stepStatus.equalsIgnoreCase("ordered")|stepStatus.equalsIgnoreCase("order settled")|stepStatus.equalsIgnoreCase("completed")|stepStatus.equalsIgnoreCase("pending")){
			WebUI.verifyTrue(act.contains("bg-blue") | act.contains("bg-primary"),"Status field background color is not 'Blue'");
		}else if(stepStatus.equalsIgnoreCase("scheduled")){
		WebUI.verifyTrue(act.contains("bg-orange"),"Status field background color is not 'Orange'");}
	}
	public void navigateToTabPageOnDashboard(String tab) {
//		WebUI.sleep(60);
		switch(tab.toUpperCase()){
			case "TRANSACTIONS":
				WebUI.waitForElementClickable(transactionsTabLink,5000);
				WebUI.clickElement(transactionsTabLink);
				break;
			case "WALLETS":
				WebUI.waitForElementClickable(walletsTabLink,5000);
				WebUI.clickElement(walletsTabLink);
		}
	}

	public void searchAndVerifyPaymentInTransactionGrid(Map<String, String> payment_data_map) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String createdDt=(dtf.format(now)).toString();
		WebElement payment_record=searchforPaymentInTransactionGrid(createdDt,payment_data_map.get("recipientName"));
		WebUI.verifyTrue(payment_record.findElement(By.xpath("./td[3]")).getText().contentEquals(payment_data_map.get("toAmount")));
		WebUI.verifyTrue(payment_record.findElement(By.xpath("./td[4]")).getText().equalsIgnoreCase("Payment"));
//		WebUI.verifyTrue(payment_record.findElement(By.xpath("./td[5]")).getText().equalsIgnoreCase(payment_data_map.get("status")));
	}
	public String getPaymentReferenceInTransactionGrid(Map<String, String> payment_data_map) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		String createdDt=(dtf.format(now)).toString();
		WebElement payment_record=searchforPaymentInTransactionGrid(createdDt,payment_data_map.get("recipientName"));
		return payment_record.findElement(By.xpath("./td[2]")).getText().trim();
	}
	public WebElement searchforPaymentInTransactionGrid(String date, String recipientName) {
		WebUI.clearAndFillText(transactions_searchbox, recipientName);
		WebUI.smartWait();
		WebUI.sleep(1);
		By paymentRecord = By.xpath("//td[.='" + recipientName + "']/preceding-sibling::td[contains(.,'" + date + "')]/parent::tr[1]");
		WebUI.waitForElementVisible(paymentRecord);
		WebUI.verifyElementVisible(paymentRecord, "Payment record is not available in the Transactions grid");
		return WebUI.getWebElement(paymentRecord);
	}
	public void searchPaymentAndOpenTransactionDetailsView(String recipientName,String reference){
		WebUI.clearAndFillText(transactions_searchbox, recipientName);
		WebUI.smartWait();
		WebUI.sleep(1);
		By paymentRecord = By.xpath("//td[.='" + recipientName + "']/preceding-sibling::td[contains(.,'" + reference + "')]/parent::tr[1]");
		WebUI.waitForElementVisible(paymentRecord);
		WebUI.verifyElementVisible(paymentRecord, "Payment record is not available in the Transactions grid");
		WebUI.clickElement(paymentRecord);
		WebUI.waitForElementVisible(btnDownload);
	}
	public void verifyPaymentDetailsOnDetailsView(Map<String, Map<String, String>> payment_details, String recipientType) {
		String settlementDt="";
		By creationDate=By.xpath("//h1[contains(text(),'receipt')]/parent::h2/following-sibling::p");
		WebUI.verifyElementTextContains(creationDate,payment_details.get("COMMON").get("createdAt"));
		String amount=payment_details.get("COMMON").get("totalPayable");
		verifyFieldValue("Gross send amount (incl. payment fees)",amount);
		verifyFieldValue("Recipient gets",payment_details.get(recipientType).get("toAmount"));
		verifyFieldValue("Expected payment date",payment_details.get("COMMON").get("expectedPaymentDate"));
		verifyFieldValue("Transaction reference",payment_details.get(recipientType).get("reference"));
		verifyFieldValue("Total fees (incl. 5% VAT)",payment_details.get("COMMON").get("feePerPayment"));
		verifyFieldValue("Invoice/document ID",payment_details.get(recipientType).get("documentNumber"));
		verifyFieldValue("Purpose of payment",payment_details.get(recipientType).get("purposeOfPayment"));

		//Recipient Details
		WebUI.verifyElementVisible(label_RecipientDetails,"Related payments section is not available");
		verifyFieldValue("Recipient name",payment_details.get(recipientType).get("recipientName"));
		verifyFieldValue("Account number",payment_details.get(recipientType).get("accountNumber"));
		int noOfPayments=Integer.parseInt(payment_details.get("COMMON").get("noOfPayments"));
		//Related payments
		if(noOfPayments>1){
			WebUI.verifyElementVisible(label_RelatedPayments,"Related payments section is not available");
			verifyFieldValue("RelatedPayments_Total fees (incl. 5% VAT)",payment_details.get("COMMON").get("totalFees"));
			verifyFieldValue("RelatedPayments_Expected payment date",payment_details.get("COMMON").get("expectedPaymentDate"));
			verifyFieldValue("Total amount",payment_details.get("COMMON").get("totalReceiveAmount"));
			verifyFieldValue("Number of payments",payment_details.get("COMMON").get("noOfPayments"));
		}
		WebUI.verifyElementVisible(btnDownload,"Download button is not available");
		WebUI.verifyElementVisible(contactSupport,"Contact support link is not available");
	}

    public String getExchangeReferenceInTransactionGrid(Map<String, Map<String, String>> exchange_data_map) {
		String currencycode=exchange_data_map.get("COMMON").get("totalReceiveAmount").split(" ")[0];
				String amount=exchange_data_map.get("COMMON").get("totalReceiveAmount").split(" ")[1];
		By exchangeRecord = By.xpath("//td[contains(text(),'" + currencycode + "')][contains(text(),'" + amount + "')]/following-sibling::td[.='Foreign Exchange']/preceding-sibling::td[contains(.,'" + new SimpleDateFormat("dd/MM/YYYY").format(new Date()) + "')]/parent::tr[1]");
		WebUI.waitForElementVisible(exchangeRecord);
		WebUI.verifyElementVisible(exchangeRecord, "Exchange record is not available in the Transactions grid");
		return WebUI.getWebElement(exchangeRecord).findElement(By.xpath("./td[2]/span")).getText();
    }

	public void verifyExchangeDetailsInTransactionGrid(Map<String, Map<String, String>> exchange_data_map) {
		By exchangeRecord = By.xpath("//td[.='" + exchange_data_map.get("EXCHANGE").get("reference") + "']/parent::tr[1]");
		WebUI.waitForElementVisible(exchangeRecord);
		WebElement exchange_record=WebUI.getWebElement(exchangeRecord);
		WebUI.verifyTrue(exchange_record.findElement(By.xpath("./td[3]")).getText().contentEquals(exchange_data_map.get("COMMON").get("totalReceiveAmount")));
		WebUI.verifyTrue(exchange_record.findElement(By.xpath("./td[4]")).getText().equalsIgnoreCase("Foreign Exchange"));
		WebUI.verifyTrue(exchange_record.findElement(By.xpath("./td[5]")).getText().equalsIgnoreCase(exchange_data_map.get("EXCHANGE").get("status")));
		WebUI.verifyTrue(exchange_record.findElement(By.xpath("./td[6]")).getText().equalsIgnoreCase("-"));
	}

	public void openExchangeDetailsFromTransactionGrid(Map<String, Map<String, String>> exchange_data_map) {
		By exchangeRecord = By.xpath("//td[.='" + exchange_data_map.get("EXCHANGE").get("reference") + "']/parent::tr[1]");
		WebUI.waitForElementVisible(exchangeRecord);
		WebUI.clickElement(exchangeRecord);
		WebUI.waitForElementVisible(By.xpath("//h1[text()='Exchange receipt']"));
		WebUI.clickElement(By.xpath("//h1[text()='Exchange receipt']"));
	}
}
