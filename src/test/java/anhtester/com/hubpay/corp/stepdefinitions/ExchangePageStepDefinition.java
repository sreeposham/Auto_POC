package anhtester.com.hubpay.corp.stepdefinitions;

import anhtester.com.common.CommonPage;
import anhtester.com.helpers.JsonHelpers;
import anhtester.com.keywords.WebUI;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ExchangePageStepDefinition extends CommonPage {

//	public String temp_exchange_sellcurrencywalletbalance="";
//	public String temp_exchange_buycurrencywalletbalance="";
	public String temp_exchange_liverate="";
	public String temp_exchange_sellamount="";
	public String temp_exchange_settlmentdate="";
	private String fileName="";
	String tempfileName="";
	public Map<String, Map<String, String>> local_exchange_map=new HashMap<>();
	public Map<String, String> individualMap;
	public Map<String, String> dynamicDataMap;
	Scenario scenario;
	//	public ProductCatalogue productCatalogue;
//	public ConfirmationPage confirmationPage;

	public ExchangePageStepDefinition() {
		fileName=getTestDataFile();
//		tempfileName=DashboardPageStepDefinition.tempfile;
//		System.out.println("ExchangePageStepDefinition:"+CommonPage.checkfilenameaccess);
		try {
			local_exchange_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public void getTestDataFileName(){
//		fileName="src/test/resources/testdata/" + scenario.getName().replace(" ","_")+".json";
//		System.out.println(fileName);
////	return fileName;
//	}

//	@Then("I navigate to Payments page and select {string} corridor")
//	public void I_navigate_to_payments_page(String corridor) throws IOException {
////		dynamicDataMap.put("corridor", email)
//		paymentsPage=dashboardPage.navigateToPaymentsPage("payments");
//		paymentsPage.selectWallet(corridor);
//	}
	
//	@Then("I navigate to Exchange page")
//	public void I_navigate_to_exchange_page() throws IOException {
//		System.out.println("I_verify_default_details_on_exchange_page:"+getTestDataFile());
//		getDashboardPage().verifyPageLaunch("Exchange");
//	}

	@Then("I verify default details on Exchange page")
	public void I_verify_default_details_on_exchange_page() throws IOException {

		getExchangePage().defaultDetailsExchangePage();
	}	
	
	@Then("I verify {string} company name and {string} full name on the page")
	public void I_verify_default_details_on_the_page(String company, String user) throws IOException {
		getExchangePage().verifyCompanyUserDetails(company,user);
	}
	
	@Then("I verify {string} step is {string} on {string} page")
	public void I_verify_step_status_on_exchange_page(String stepName,String stepStatus,String trxnType) throws IOException {
		getExchangePage().verifyStepStatusExchangePage(stepName,stepStatus,trxnType);
	}

	@When("I select {string} corridor and input {string} amount on Exchange page")
	public void I_select_corridor_and_input_amount_on_exchange_page(String corridor,String amount) throws IOException {
		getExchangePage().selectAndEnterBuyAmount(corridor,amount);
	}

	@When("I select {string} tenor on Exchange page")
	public void I_select_tenor_for_currency_on_exchange_page(String tenor) throws IOException {
		getExchangePage().selectTenorDateOnExchange(tenor);
	}
	@Then("I verify Live rate is displayed for {string} corridor")
	public void I_verify_live_rate_is_displayed_exchange_page(String corridor) throws IOException {
		getExchangePage().verifyLiveRateDisplayed(corridor);
	}
	@Then("I verify {string} for the {string} corridor")
	public void I_verify_processingtime_for_the_corridor(String procTime,String corridor) throws Exception {
		getExchangePage().verifyProcessingTime(procTime,corridor);
//		String fileName=getTestDataFile();
//		local_exchange_map= JsonHelpers.readFromJsonFile(fileName);

//		System.out.println("In Exchange steps:Print from File");
//		System.out.println(local_exchange_map);
		local_exchange_map=getExchangePage().getDetailsForReview(local_exchange_map);
//		System.out.println(getValueFromMap("sellCurrencyWalletBalance"));

//		local_exchange_map=getExchangePage().getDetailsForReview();
//		System.out.println("After Merging Maps:");
//		System.out.println(local_exchange_map);
		JsonHelpers.writeToJsonFile(fileName,local_exchange_map);
//		System.out.println("From File after Merging Maps:");
//		System.out.println(JsonHelpers.readFromJsonFile(fileName));
	}

	@Then("I {string} consent checkbox on {string} page")
	public void I_consent_checkbox_exchange_page(String consent,String trxnType) throws IOException {
		getExchangePage().clickReviewConfirmCheckbox(consent);
	}

	@Then("I refresh rate and verify exchange summary for {string} corridor")
	public void I_refresh_rate_and_verify_exchange_summary_for_corridor(String corridor) throws IOException {
		try {
			local_exchange_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getExchangePage().verifyStaticDetailsOnReviewExchangeSummary(local_exchange_map);
		getExchangePage().refreshLiveRateOnReviewScreen();
		local_exchange_map=getExchangePage().calculateExchangeSummaryOnReviewConfirm(local_exchange_map);
		getExchangePage().verifyCalculatedValuesOnReviewExchangeSummary(local_exchange_map);
		JsonHelpers.writeToJsonFile(fileName,local_exchange_map);
	}
	@Then("I review Exchange summary for {string} corridor and {string} amount")
	public void I_review_exchangesummary_for_corridor_and_amount(String corridor,String buyAmount) throws IOException {
//		exchangePage.verifyProcessingTime(procTime,corridor);
//		temp_exchange_map=exchangePage.getDetailsForReview();
//		if(corridor.contentEquals("EUR")) {
//			local_exchange_map.put("buyAmount", buyAmount+".00");
//		}
		try {
			JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		getExchangePage().reviewExchangeSummaryBeforeExpired(local_exchange_map);
		getExchangePage().review_verifyProcessingTime(local_exchange_map.get("COMMON").get("processingTime"));
	}
	@When("I Confirm the Exchange transaction")
	public void I_confirm_the_exchange_transaction() throws IOException {
		getExchangePage().clickConfirmExchangeBtn();
	}
	
//	@When("I create test data file with Exchange transaction details")
//	public void I_create_testdata_file_with_exchange_transaction_details() throws IOException {
//		exchangePage.createExchangeTransactionDataFile();
//	}
	@When("I verify details on Exchange Success modal popup")
	public void I_verify_details_exchange_success_modal() throws IOException {
		try {
			JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		getExchangePage().verifyDetailsOnSuccessModal(local_exchange_map);
		String trxnReference=getExchangePage().getReferenceFromExchangeSuccessModal();
		local_exchange_map.get("EXCHANGE").put("type", "Foreign Exchange");
		local_exchange_map.get("EXCHANGE").put("createdAt", (new SimpleDateFormat("EEEE d MMMM yyyy").format(new Date())).toString());
		local_exchange_map.get("EXCHANGE").put("reference", trxnReference);
		local_exchange_map.get("EXCHANGE").put("orderedAt",(new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toString());
		local_exchange_map.get("EXCHANGE").put("orderSettledAt","-");
		local_exchange_map.get("EXCHANGE").put("rejectedAt","-");
		local_exchange_map.get("EXCHANGE").put("completedAt","-");
		local_exchange_map.get("EXCHANGE").put("totalFundsDue",local_exchange_map.get("EXCHANGE").get("fromAmount"));
		local_exchange_map.get("EXCHANGE").put("totalPayable",local_exchange_map.get("EXCHANGE").get("fromAmount"));
		JsonHelpers.writeToJsonFile(fileName,local_exchange_map);
	}
	//	@When("I select {string} corridor on Exchange page")
//	public void I_select_corridor_exchange_page(String wallet) throws IOException{
//		exchangePage.selectWallet(wallet);

	@When("I click {string} button on {string} page")
	public void I_click_button_on_exchange_page(String btn,String flow) throws IOException{
//		WebUI.scrollToElement(btn);
		getExchangePage().clickBtn(btn,flow);
	}
	
	@When("I click {string} button on Exchange Success modal popup")
	public void I_click_button_exchange_success_modal(String btn) throws IOException{
		getExchangePage().modal_ClickBtn();
	}

//	@Then("I navigate to Transactions page")
//	public void I_navigate_to_transactions_page() throws IOException {
//		transactionsPage=dashboardPage.navigateToTransactionsPage();
//	}
//	@Then("I verify transaction status {string} in table and open transaction details view")
//	public void I_verify_transactions_status_open_details_view(String status) throws IOException {
//		transactionsPage.openTransactionDetailsPage(temp_exchange_map,status);
//	}
//	@Then("I verify exchange transaction status is {string} on transaction details view")
//	public void I_verify_transactions_status_on_details_view(String status) throws IOException {
//		transactionsPage.verifyStatusTransactionDetails(status);
//	}
//	@Then("I verify details of exchange transaction on transaction details view")
//	public void I_verify_transactions_status_open_details_view() throws IOException {
//		transactionsPage.verifyOnlyExchangeTransactionDetails(temp_exchange_map);
//	}
//
//	@When("I close the exchange transaction details view")
//	public void I_close_the_exchange_transaction_details_view() throws IOException {
//		transactionsPage.clickCloseBtn();
//	}
//
//	@When("I sign out from Corp portal")
//	public void I_sign_out_from_corp_portal() throws IOException {
//		dashboardPage.signoutCorpPortal();
//	}
//
//
//
//	@When("I select {string} corridor on Payments page")
//	public void I_select_corridor_payments_page(String wallet) throws IOException{
//		getExchangePage().selectWallet(wallet);
//	}
//	@Then("I verify {string} for the selected {string} corridor")
//	public void I_verify_proctime_for_the_selected_corridor(String procTime,String corridor) throws IOException{
//		paymentsPage.verifyProcessingTime(procTime, corridor);
//	}
//	@Then("I verify the Payment summary for {string} corridor before recipients added")
//	public void I_verify_the_paymentsummary_before_recipients_added(String currency) throws IOException{
//		paymentsPage.verifyCheckoutBeforeRecipientAdded(currency,"PAYMENTS");
//	}
//	@When("I click {string} button on Payments page")
//	public void I_click_button_on_payments_page(String btn) throws IOException{
//		getExchangePage().clickBtn(btn);
//	}
//	@When("I open Add new recipient form")
//	public void I_open_add_new_recipient_form() throws IOException {
//		getExchangePage().clickBtn("Add individual or business");
//	}
//	@When("I fill data for the {string} recipient")
//	public void I_fill_data_for_the_recipient(String recipientType) throws IOException{
//		paymentsPage.enterRecipientFormData(individualMap);
//	}
//	@When("I save the recipient details")
//	public void I_save_the_recipient_details() throws IOException{
//		paymentsPage.saveRecipientDetails("Recipient added");
//	}
}
