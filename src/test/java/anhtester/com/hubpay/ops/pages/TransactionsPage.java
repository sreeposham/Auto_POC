package anhtester.com.hubpay.ops.pages;

import anhtester.com.common.CommonPage;
import anhtester.com.keywords.WebUI;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class TransactionsPage extends CommonPage {

	private By settlementreceivedBtn=By.xpath("//button[text()='Settlement Received']");
	private By settleBtn=By.xpath("//button[text()='Settle']");
	private By userAccountID=By.xpath("//a[text()='User Account ID']");
	private By downloadBtn=By.xpath("//label[title='Download']");
	private By emailInputFld=By.cssSelector("input[name='emailAddress']");
	private By ibanInputFld=By.cssSelector("input[name='iban']");
	private By fullNameInputFld=By.cssSelector("input[name='fullName']");
	private By searchBtn=By.xpath("//button[text()='Search']");
	private By flagFld=By.xpath("//div[@class='selected-flag']");
	private By searchFlagInputFld=By.xpath("//input[@class='search-box']");
	@FindBy(xpath = "//img")
	private List<WebElement> images;
	private By profileLbl=By.xpath("//h5[text()='Profile']");
	private By submitSinglePayment=By.xpath("//div[.='Actions']/following-sibling::div//button[.='Submit Single Payment']");
	private By submitFullBatch=By.xpath("//div[.='Actions']/following-sibling::div//button[.='Submit Full Batch']");
	private By decline=By.xpath("//div[.='Actions']/following-sibling::div//button[.='Decline']");
	private By forceRefreshPaymentStatuses=By.xpath("//div[.='Actions']/following-sibling::div//button[.='Force Refresh Payment Statuses']");
	private By label_RelatedPayments=By.xpath("//div[@role='separator']/span[text()='Related Payments']");
	private By download=By.xpath("//label[@title='Download']");
	public TransactionsPage() {

	}
	
	public void verifyFieldValue(String fieldLabel,String fieldValue){
		if(fieldValue==null){
			fieldValue="-";
		}
		WebUI.verifyElementVisible(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']"));
		WebUI.verifyElementText(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']/following-sibling::div/p"),fieldValue);
	}
	public void verifyFieldValueIsNotNull(String fieldLabel,int index){
		WebUI.verifyElementVisible(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']["+index+"]"));
		WebUI.verifyTrue(!WebUI.getTextElement(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']/following-sibling::div/p")).contentEquals("-"),"'"+fieldLabel+"' field value is null");
	}
	public void verifyFieldValueIsNotNull(String fieldLabel){
		verifyFieldValueIsNotNull(fieldLabel,1);
	}
	public void verifyFieldContainsValue(String fieldLabel,String fieldValue){
		WebUI.verifyElementVisible(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']"));
		String actValue=WebUI.getTextElement(By.xpath("//p[contains(@class,'MuiTypography-root')][text()='"+fieldLabel+"']/following-sibling::div/p"));
		WebUI.verifyContains(actValue,fieldValue,"Incorrect value: Actual-'"+actValue+"' Expected-'"+fieldValue+"'");
	}
    public void verifyStatusInTransactionDetailsView(String status){
		verifyFieldValue("Order Status",status);
	}
	public void verifyExchangeTransactionDetails(Map<String,Map<String,String>> trxnDetails) throws ParseException {
		String fieldValue="";
//		verifyFieldValue("Order Status",fieldValue);
		verifyFieldValue("Reference",trxnDetails.get("EXCHANGE").get("reference"));
		Date dateEEE=new SimpleDateFormat("EEEE d MMMM yyyy").parse(trxnDetails.get("EXCHANGE").get("createdAt"));
//		Date dateD=new SimpleDateFormat("d MMMM yyyy").parse(trxnDetails.get("COMMON").get("formatted_settlementDate"));
		SimpleDateFormat dateDD=new SimpleDateFormat("dd/MM/yyyy");
		verifyFieldContainsValue("Created At",(dateDD.format(dateEEE)).toString());
		verifyFieldContainsValue("Ordered At",(dateDD.format(dateEEE)).toString());
		verifyFieldValue("Order Settled At",trxnDetails.get("EXCHANGE").get("orderSettledAt"));
		verifyFieldValue("Rejected At",trxnDetails.get("EXCHANGE").get("rejectedAt"));
		verifyFieldContainsValue("Settlment Date",trxnDetails.get("COMMON").get("settlementDate"));
		verifyFieldValue("Completed At",trxnDetails.get("EXCHANGE").get("completedAt"));
		/*###Amount & Fees###*/
		verifyFieldValue("Total Funds Due",trxnDetails.get("EXCHANGE").get("totalFundsDue"));
		String arr[]=trxnDetails.get("COMMON").get("liveRate").split(" ");
		verifyFieldValue("Offer Rate",arr[1]+"/"+arr[4]+" "+arr[3]);
		verifyFieldValue("Send Amount",trxnDetails.get("COMMON").get("fromCurrencyCode")+" "+trxnDetails.get("EXCHANGE").get("fromAmount"));
		verifyFieldValue("Receive Amount",trxnDetails.get("COMMON").get("toCurrencyCode")+" "+trxnDetails.get("EXCHANGE").get("toAmount"));
	}
	private By providerStatusDesc=By.xpath("//p[text()='Status Description']/following-sibling::div/p");
	public void verifyPaymentsTransactionDetails(Map<String,Map<String,String>> trxnDetails,String recipientType) throws ParseException {
		WebUI.sleep(2);
		String fieldValue="";
		verifyFieldValueIsNotNull("Id");
//		verifyFieldValueIsNotNull("User Account ID");
		verifyFieldValue("Processing Status",trxnDetails.get(recipientType).get("processingStatus"));
		verifyFieldValue("Result Status",trxnDetails.get(recipientType).get("resultStatus"));
		verifyFieldValue("Receive amount",trxnDetails.get(recipientType).get("toAmount"));
		verifyFieldValue("Gross Fee amount",trxnDetails.get("COMMON").get("totalFees"));
		verifyFieldValue("Reference For The Receiving Party",trxnDetails.get(recipientType).get("thirdPartyReference"));
		verifyFieldValue("Document Number",trxnDetails.get(recipientType).get("documentNumber"));
		verifyFieldValue("Purpose Of Payment",trxnDetails.get(recipientType).get("purposeOfPayment"));
		verifyFieldValue("Reference",trxnDetails.get(recipientType).get("reference"));
//		Date dateEEE=new SimpleDateFormat("EEEE dd MMMM yyyy").parse(trxnDetails.get("COMMON").get("createdAt"));
//		String dt_ExpPayDate=trxnDetails.get("COMMON").get("expectedPaymentDate");
//		String dd_ExpPayDate=dt_ExpPayDate.split(" ")[0];
//		if (dd_ExpPayDate.contains("rd")) {
//			dd_ExpPayDate = dd_ExpPayDate.replace("rd", "");
//		} else if (dd_ExpPayDate.contains("st")) {
//			dd_ExpPayDate = dd_ExpPayDate.replace("st", "");
//		} else if (dd_ExpPayDate.contains("nd")) {
//			dd_ExpPayDate = dd_ExpPayDate.replace("nd", "");
//		} else if (dd_ExpPayDate.contains("th")) {
//			dd_ExpPayDate = dd_ExpPayDate.replace("th", "");
//		}
//		String formatted_ExpPayDate=dd_ExpPayDate+" "+dt_ExpPayDate.split(" ")[1].trim()+" "+dt_ExpPayDate.split(" ")[2].trim();
//		Date dateD=new SimpleDateFormat("d MMMM yyyy").parse(formatted_ExpPayDate);
		SimpleDateFormat dateDD=new SimpleDateFormat("dd/MM/yyyy");
		verifyFieldContainsValue("Created At",trxnDetails.get("COMMON").get("scheduledAt"));
		verifyFieldValue("Payment Method",trxnDetails.get("COMMON").get("paymentMethod"));
		verifyFieldValueIsNotNull("Payment Instruction ID");
		verifyFieldContainsValue("Scheduled At",trxnDetails.get("COMMON").get("scheduledAt"));
		if(!(trxnDetails.get(recipientType).get("completedAt").contentEquals("-"))){
		verifyFieldContainsValue("Completed At",trxnDetails.get(recipientType).get("completedAt"));}
		verifyFieldContainsValue("Submitting At",trxnDetails.get(recipientType).get("submittingAt"));
		verifyFieldContainsValue("Pending At",trxnDetails.get(recipientType).get("pendingAt"));
		verifyFieldContainsValue("Last Updated At",(dateDD.format(new Date())).toString());
		verifyFieldContainsValue("Declined At",trxnDetails.get(recipientType).get("declinedAt"));
		verifyFieldValue("Declined By",trxnDetails.get(recipientType).get("declinedBy"));
		verifyFieldValue("Declined Reason",trxnDetails.get(recipientType).get("declinedReason"));

		/*###Recipient details###*/
		verifyFieldValueIsNotNull("Id",2);
		verifyFieldValue("Type",trxnDetails.get(recipientType).get("recipientType").toUpperCase());
		verifyFieldValue("Name",trxnDetails.get(recipientType).get("recipientName"));
		verifyFieldValue("Address Line 1",trxnDetails.get(recipientType).get("addressLineOne"));
		verifyFieldValue("Address Line 2",trxnDetails.get(recipientType).get("addressLineTwo"));
		verifyFieldValue("City",trxnDetails.get(recipientType).get("city"));
		verifyFieldValue("Province",trxnDetails.get(recipientType).get("province"));
		verifyFieldValue("Postal Code",trxnDetails.get(recipientType).get("postalCode"));
		verifyFieldValue("Country",trxnDetails.get(recipientType).get("countryCode"));
		verifyFieldValue("Account Number",trxnDetails.get(recipientType).get("accountNumber"));
		verifyFieldValue("Bic",trxnDetails.get(recipientType).get("bicCode"));
		verifyFieldValue("Bank Code",trxnDetails.get(recipientType).get("bankCode"));

		/*###Provider response details###*/
		verifyFieldValue("Provider",trxnDetails.get("COMMON").get("provider"));
		verifyFieldValueIsNotNull("Transaction Id");

		verifyFieldValue("Status Code",trxnDetails.get(recipientType).get("providerStatusCode"));
		if(trxnDetails.get(recipientType).get("resultStatus").equalsIgnoreCase("PAID")){
			verifyFieldContainsValue("Status Description","Succeeded with provider transaction ID");
			String providerTransactionIdVal=WebUI.getTextElement(providerStatusDesc).split(" ")[5].trim();
			verifyFieldValue("Provider Transaction Id",providerTransactionIdVal);}else{
			verifyFieldValue("Status Description","-");
			verifyFieldValue("Provider Transaction Id","-");

		}
		verifyFieldValue("Amount Paid",trxnDetails.get("COMMON").get("providerAmountPaid"));
		verifyFieldValue("Fee Paid",trxnDetails.get(recipientType).get("providerFeePaid"));

		verifyFieldValue("Date Paid",trxnDetails.get(recipientType).get("providerDatePaid"));
		verifyFieldValue("Payment Value Date",trxnDetails.get(recipientType).get("providerValueDate"));
		int noOfPayments=Integer.parseInt(trxnDetails.get("COMMON").get("noOfPayments"));
		//Related payments
		if(noOfPayments>1){
			WebUI.verifyElementVisible(label_RelatedPayments,"Related payments section is not available");
			verifyFieldValue("Total Fee (Inc. 5% VAT)",trxnDetails.get("COMMON").get("totalFees"));
			verifyFieldValue("Expected Payment Date",trxnDetails.get("COMMON").get("expectedPaymentDate"));
			verifyFieldValue("Total Amount",trxnDetails.get("COMMON").get("totalReceiveAmount"));
			verifyFieldValue("No of Payments",trxnDetails.get("COMMON").get("noOfPayments"));
			//implement logic to verify transaction references
			//p[text()='Payment transaction reference(s)']/following-sibling::div//a
		}
	}

	public void openTransactionDetailsViewFromTabPage(String trxnType,String trnreference){
		String trxnTypeHeader="Payment details";
		WebUI.verifyElementVisible(getTransactionInTabGrid(trnreference),20);
		WebUI.clickElement(getTransactionInTabGrid(trnreference));
		if(trxnType.equalsIgnoreCase("Exchange")|trxnType.equalsIgnoreCase("International Exchange")|trxnType.equalsIgnoreCase("Foreign Exchange")){
			trxnTypeHeader="Foreign Exchange";
		}
		By trxnDetailsHdr=By.xpath("//h2[text()='"+trxnTypeHeader+"']");
		WebUI.waitForElementVisible(trxnDetailsHdr,10);
		WebUI.verifyElementVisible(trxnDetailsHdr,20,trxnType+" Transaction details view is not displayed");
	}
	public By getTransactionInTabGrid(String trxnreference){
		By trxnRecord=By.xpath("//div[@data-field='reference'][.='"+trxnreference+"']/parent::div[@role='row']");
		return trxnRecord;
	}
	public void verifyTransactionStatusInTabGrid(String trxnreference,String status){
		By trxnRecord=getTransactionInTabGrid(trxnreference);
		WebElement trxnStatus=WebUI.getWebElement(trxnRecord).findElement(By.xpath("//div[@data-field='status']"));
		WebUI.verifyTrue(trxnStatus.getText().contentEquals(status.toUpperCase()),"Status mismatch: Expected:'"+status+"' & Actual:'"+trxnStatus.getText()+"'");
	}
	public void verifyTransactionSearchInOpsPortal(String trxnreference,String type,String status){
		//yet to work on
	}
	public void verifyButtonOnTransactionDetailsView(String button,String status){
		switch(button.toUpperCase()){
			case "SETTLEMENT RECEIVED":
				boolean flag=false;
				if(status.equalsIgnoreCase("Available")){
					WebUI.verifyElementVisible(settlementreceivedBtn,button+ " is not available");
				}else if(status.equalsIgnoreCase("Not Available")){
					WebUI.verifyTrue(!WebUI.verifyElementVisible(settlementreceivedBtn,button+ " is available"));
				}
				break;
			case "SETTLE":
				if(status.equalsIgnoreCase("Available")){
					WebUI.verifyElementVisible(settleBtn,button+ " is not available");
				}else if(status.equalsIgnoreCase("Not Available")){
					WebUI.verifyTrue(!WebUI.verifyElementVisible(settleBtn,button+ " is available"));
				}
				break;
		}
	}
	public void clickButtonOnTransactionDetailsView(String button) {
		switch (button.toUpperCase()) {
			case "SETTLEMENT RECEIVED":
				WebUI.clickElement(settlementreceivedBtn);
				break;
			case "SETTLE":
				WebUI.clickElement(settleBtn);
				break;
			case "USER ACCOUNT ID":
				WebUI.clickElement(userAccountID);
				break;
			case "SUBMIT SINGLE PAYMENT":
				WebUI.clickElement(submitSinglePayment);
				break;
			case "SUBMIT FULL BATCH":
				WebUI.clickElement(submitFullBatch);
				break;
			case "DECLINE":
				WebUI.clickElement(decline);
				break;
			case "DOWNLOAD":
				WebUI.clickElement(downloadBtn);
				break;
			default:
				WebUI.verifyTrue(false, button + " button is not Available");
		}
	}

	public void verifyButtonStatusOnDetailsView(List<String> buttons, String btnStatus) {
		if(btnStatus.isBlank()|buttons.isEmpty()) return;
		for (String button : buttons) {
			switch(button.toUpperCase())	{
				case "SUBMIT SINGLE PAYMENT":
					if(btnStatus.equalsIgnoreCase("Available")){
						WebUI.verifyElementVisible(submitSinglePayment,"Submit Single Payment button is not available");
					}else if(btnStatus.equalsIgnoreCase("Not Available")){
						WebUI.verifyElementNotVisible(submitSinglePayment,"Submit Single Payment button is available");
					}
					break;
				case "SUBMIT FULL BATCH":
					if(btnStatus.equalsIgnoreCase("Available")){
						WebUI.verifyElementVisible(submitFullBatch,"Submit full batch button is not available");
					}else if(btnStatus.equalsIgnoreCase("Not Available")){
						WebUI.verifyElementNotVisible(submitFullBatch,"Submit full batch button is available");
					}
					break;
				case "DECLINE":
					if(btnStatus.equalsIgnoreCase("Available")){
						WebUI.verifyElementVisible(decline,"Decline button is not available");
					}else if(btnStatus.equalsIgnoreCase("Not Available")){
						WebUI.verifyElementNotVisible(decline,"Decline button is available");
					}
					break;
				case "FORCE REFRESH PAYMENT STATUSES":
					if(btnStatus.equalsIgnoreCase("Available")){
						WebUI.verifyElementVisible(forceRefreshPaymentStatuses,"Force Refresh Payment Statuses button is not available");
					}else if(btnStatus.equalsIgnoreCase("Not Available")){
						WebUI.verifyElementNotVisible(forceRefreshPaymentStatuses,"Force Refresh Payment Statuses button is available");
					}
					break;
				case "DOWNLOAD":
					if(btnStatus.equalsIgnoreCase("Available")){
						WebUI.verifyElementVisible(download,"Download button is not available");
					}else if(btnStatus.equalsIgnoreCase("Not Available")){
						WebUI.verifyElementNotVisible(download,"Download button is available");
					}
					break;
			}
		}

	}

	public Map<String, Map<String, String>> updateDataFileForPaymentAfterSubmitToCAB(Map<String, Map<String, String>> local_data_map,String recipientType) {
		local_data_map.get(recipientType).put("processingStatus","PENDING");
		local_data_map.get(recipientType).put("resultStatus","PENDING");
		String todaydt=new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString();
		local_data_map.get(recipientType).put("submittingAt",todaydt);
		local_data_map.get(recipientType).put("pendingAt",todaydt);
		local_data_map.get(recipientType).put("lastUpdatedAt",todaydt);
//		local_data_map.get(recipientType).put("completedAt",todaydt);
		return local_data_map;
	}

	public Map<String, Map<String, String>> updateDataFileForAfterFullBatchPaymentsSubmittedToCAB(Map<String, Map<String, String>> local_data_map) {
		for (Map.Entry<String, Map<String, String>> recipients_map : local_data_map.entrySet()) {
			String recipientType = recipients_map.getKey();
			if (!(recipientType.equalsIgnoreCase("COMMON") | recipientType.equalsIgnoreCase("Exchange") | recipientType.equalsIgnoreCase("Foreign Exchange"))) {
				local_data_map.get(recipientType).put("processingStatus", "PENDING");
				local_data_map.get(recipientType).put("resultStatus", "PENDING");
				String todaydt = new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString();
				local_data_map.get(recipientType).put("submittingAt", todaydt);
				local_data_map.get(recipientType).put("pendingAt", todaydt);
				local_data_map.get(recipientType).put("lastUpdatedAt", todaydt);
			}
		}
		return local_data_map;
	}

	private By statusDsc=By.xpath("//p[text()='Status Description']/following-sibling::div/p");
	public String calculateAmount(String amountOne,String amountTwo,String operation){
		String strOne=null;
		if(amountOne.contains(",")){
			strOne=amountOne.replace(",","");
			amountOne=strOne;
		}

		if(amountTwo.contains(",")){
			strOne=amountTwo.replace(",","");
			amountTwo=strOne;
		}

		double calculatedValue= 0.00;

		if(amountOne.contains(" ")){
			calculatedValue=calculatedValue+Double.parseDouble(amountOne.split(" ")[1].trim());
		}else{
			calculatedValue=calculatedValue+Double.parseDouble(amountOne);
		}
		switch (operation.toUpperCase()){
			case "ADD":

		if(amountTwo.contains(" ")){
			calculatedValue=calculatedValue+Double.parseDouble(amountTwo.split(" ")[1].trim());
		}else{
			calculatedValue=calculatedValue+Double.parseDouble(amountTwo);
		} break;
			case "SUBTRACT":
				if(amountTwo.contains(" ")){
					calculatedValue=calculatedValue-Double.parseDouble(amountTwo.split(" ")[1].trim());
				}else{
					calculatedValue=calculatedValue-Double.parseDouble(amountTwo);
				} break;
		}
		System.out.println("calculated amount:"+getRoundedValue(calculatedValue));
		if(amountOne.contains(" ")){
			return amountOne.split(" ")[0]+" "+String.format("%.2f",calculatedValue);
		}else{
			return String.format("%.2f",calculatedValue);
		}



	}
	public Map<String, Map<String, String>> updateDataFileForPaymentAfterProcessedByCAB(Map<String, Map<String, String>> local_data_map,String recipientType) {
		String paymentAmountWithFee=calculateAmount(local_data_map.get("COMMON").get("feePerPayment"),local_data_map.get(recipientType).get("fromAmount"),"ADD");
		String updatedWalletBal=calculateAmount(local_data_map.get("COMMON").get("fromCurrencyWalletBalance"),paymentAmountWithFee,"SUBTRACT");
		local_data_map.get("COMMON").put("fromCurrencyWalletBalanceAfterPaymentCompleted",updatedWalletBal);
		local_data_map.get("COMMON").put("fromCurrencyWalletBalance",updatedWalletBal);
		local_data_map.get(recipientType).put("status","Completed");
		local_data_map.get(recipientType).put("processingStatus","COMPLETED");
		local_data_map.get(recipientType).put("resultStatus","PAID");
		String todaydt=new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString();
		local_data_map.get(recipientType).put("completedAt",todaydt);
		local_data_map.get(recipientType).put("providerStatusCode","200");
//		String statusDesc=WebUI.getTextElement(statusDsc);
//		if(WebUI.verifyElementTextContains(statusDsc,"Succeeded with provider transaction ID")){
//			local_data_map.get(recipientType).put("providerStatusDescription",statusDesc.split("transaction ID")[0].trim());
//			local_data_map.get(recipientType).put("providerTransactionId",statusDesc.split("transaction ID")[1].trim());
//		}
		local_data_map.get(recipientType).put("providerAmountPaid",local_data_map.get(recipientType).get("toAmount"));
		local_data_map.get(recipientType).put("providerDatePaid",todaydt);
		local_data_map.get(recipientType).put("providerPaymentValueDate","-");
		return local_data_map;
	}
	public Map<String, Map<String, String>> updateDataFileAfterInternationalPaymentProcessedByCAB(Map<String, Map<String, String>> local_data_map,String recipientType) {
//		String paymentAmountWithFee=calculateAmount(local_data_map.get("COMMON").get("feePerPayment"),local_data_map.get(recipientType).get("fromAmount"),"ADD");
		String updatedWalletBal=calculateAmount(local_data_map.get("COMMON").get("toCurrencyWalletBalance"),local_data_map.get(recipientType).get("toAmount"),"SUBTRACT");
		local_data_map.get("COMMON").put("fromCurrencyWalletBalanceAfterPaymentCompleted",updatedWalletBal);
		local_data_map.get("COMMON").put("fromCurrencyWalletBalance",updatedWalletBal);
		local_data_map.get(recipientType).put("status","Completed");
		local_data_map.get(recipientType).put("processingStatus","COMPLETED");
		local_data_map.get(recipientType).put("resultStatus","PAID");
		String todaydt=new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString();
		local_data_map.get(recipientType).put("completedAt",todaydt);
		local_data_map.get(recipientType).put("providerStatusCode","200");
//		String statusDesc=WebUI.getTextElement(statusDsc);
//		if(WebUI.verifyElementTextContains(statusDsc,"Succeeded with provider transaction ID")){
//			local_data_map.get(recipientType).put("providerStatusDescription",statusDesc.split("transaction ID")[0].trim());
//			local_data_map.get(recipientType).put("providerTransactionId",statusDesc.split("transaction ID")[1].trim());
//		}
		local_data_map.get(recipientType).put("providerAmountPaid",local_data_map.get(recipientType).get("toAmount"));
		local_data_map.get(recipientType).put("providerDatePaid",todaydt);
		local_data_map.get(recipientType).put("providerPaymentValueDate","-");
		return local_data_map;
	}
	public Map<String, Map<String, String>> updateDataFileForAfterFullBatchPaymentsProcessedByCAB(Map<String, Map<String, String>> local_data_map,String paymentType) {
		String updatedWalletBal = "";
		switch (paymentType.toUpperCase()) {
			case "PAYMENT":
			case "PAYMENTS":
				updatedWalletBal = calculateAmount(local_data_map.get("COMMON").get("toCurrencyWalletBalance"), local_data_map.get("COMMON").get("totalPayable"), "SUBTRACT");
				break;
			case "INTERNATIONAL PAYMENT":
			case "INTERNATIONAL PAYMENTS":
				updatedWalletBal = calculateAmount(local_data_map.get("COMMON").get("toCurrencyWalletBalance"), local_data_map.get("COMMON").get("totalReceiveAmount"), "SUBTRACT");
				break;
		}
		local_data_map.get("COMMON").put("toCurrencyWalletBalanceAfterPaymentCompleted",updatedWalletBal);
		local_data_map.get("COMMON").put("toCurrencyWalletBalance",updatedWalletBal);
		for (Map.Entry<String, Map<String, String>> recipients_map : local_data_map.entrySet()) {
			String recipientType = recipients_map.getKey();
			if(!(recipientType.equalsIgnoreCase("COMMON")|recipientType.equalsIgnoreCase("Exchange")|recipientType.equalsIgnoreCase("Foreign Exchange"))){
				local_data_map.get(recipientType).put("status","Completed");
				local_data_map.get(recipientType).put("processingStatus","COMPLETED");
				local_data_map.get(recipientType).put("resultStatus","PAID");
				String todaydt=new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString();
				local_data_map.get(recipientType).put("completedAt",todaydt);
				local_data_map.get(recipientType).put("providerStatusCode","200");
				local_data_map.get(recipientType).put("providerAmountPaid",local_data_map.get(recipientType).get("toAmount"));
				local_data_map.get(recipientType).put("providerDatePaid",todaydt);
				local_data_map.get(recipientType).put("providerPaymentValueDate","-");
			}
		}
		return local_data_map;
	}
	public Map<String, Map<String, String>> updateDataFileAfterPaymentScheduled(Map<String, Map<String, String>> local_data_map,String recipientType) {
		local_data_map.get(recipientType).put("processingStatus","SCHEDULED");
		local_data_map.get(recipientType).put("resultStatus","PENDING");
		String todaydt=new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString();
		local_data_map.get(recipientType).put("submittingAt","-");
		local_data_map.get(recipientType).put("pendingAt","-");
		local_data_map.get(recipientType).put("lastUpdatedAt",todaydt);
		local_data_map.get(recipientType).put("completedAt","-");
		local_data_map.get(recipientType).put("providerStatusCode","-");
		local_data_map.get(recipientType).put("providerStatusDescription","-");
		local_data_map.get(recipientType).put("providerAmountPaid","-");
		local_data_map.get(recipientType).put("providerFeePaid","-");
		local_data_map.get(recipientType).put("providerDatePaid","-");
		local_data_map.get(recipientType).put("providerTransactionId","-");
		local_data_map.get(recipientType).put("providerPaymentValueDate","-");
		return local_data_map;
	}

	public void verifyTransactionDetailsWalletDebitsGrid(Map<String, Map<String, String>> local_data_map, String trxnType, String recipientType) {
		String trxnReference=local_data_map.get(recipientType).get("reference");
		By walletDebitGridRow=By.xpath("//div[@data-field='reference'][.='"+trxnReference+"']/parent::div[@role='row'][@class='MuiDataGrid-row']");
		WebUI.waitForPageLoaded();
		WebUI.sleep(3);
		WebUI.scrollToElement(walletDebitGridRow);
		WebUI.verifyTrue(WebUI.getWebElement(walletDebitGridRow).findElement(By.xpath("./div[@data-field='createdAt']")).getText().contains(local_data_map.get("COMMON").get("scheduledAt")));
		WebUI.verifyTrue(WebUI.getWebElement(walletDebitGridRow).findElement(By.xpath("./div[@data-field='type']")).getText().contentEquals(trxnType));
		if(trxnType.equalsIgnoreCase("FOREIGN_EXCHANGE")){

		}else if(trxnType.equalsIgnoreCase("PAYMENT")){
			WebUI.verifyTrue(WebUI.getWebElement(walletDebitGridRow).findElement(By.xpath("./div[@data-field='amount']")).getText().contentEquals(local_data_map.get(recipientType).get("toAmount")));
		}
//		WebUI.verifyTrue(WebUI.getWebElement(walletDebitGridRow).findElement(By.xpath("./div[@data-field='status']")).getText().contentEquals(local_data_map.get(recipientType).get("status").toUpperCase()));

	}
}
