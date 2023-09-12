package anhtester.com.hubpay.corp.stepdefinitions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import anhtester.com.common.CommonPage;
import anhtester.com.helpers.JsonHelpers;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.hu.Ha;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class DashboardPageStepDefinition extends CommonPage {

	private Map<String, Map<String, String>> exchange_data_map=new HashMap<>();
	private String fileName="";

	public DashboardPageStepDefinition() throws IOException {
		fileName=getTestDataFile();
		try {
			exchange_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	public void getTestDataFileName(){
//	fileName="src/test/resources/testdata/" + scenario.getName().replace(" ","_")+".json";
//	System.out.println(fileName);
////	return fileName;
//	}
//@Given("I create test data file {string}")
//public void I_create_test_data_file(String testDataFile) throws IOException {
//	tempfile="src/test/resources/testdata/"+testDataFile;
//		createTestDataFile(tempfile);
//}
	@Then("I verify Dashboard page is displayed")
	public void I_verify_dashboard_page_is_displayed() throws IOException {
		getDashboardPage().verifyDashboardIsDisplayed();
	}
	@Then("I navigate to Dashboard page")
	public void I_navigate_to_dashboard_page() throws IOException {
		System.out.println("I_verify_default_details_on_exchange_page:"+getTestDataFile());
		getDashboardPage().verifyPageLaunch("Dashboard");
	}

	@Then("I navigate to Exchange page")
	public void I_navigate_to_exchange_page() throws IOException {
		System.out.println("I_verify_default_details_on_exchange_page:"+getTestDataFile());
		getDashboardPage().verifyPageLaunch("Exchange");
	}
	@Then("I navigate to Payments page")
	public void I_navigate_to_payments_page() throws IOException {
//		System.out.println("I_verify_default_details_on_exchange_page:"+getTestDataFile());
		getDashboardPage().verifyPageLaunch("Payments");
	}
	@Then("I navigate to International Payments page")
	public void I_navigate_to_international_payments_page() throws IOException {
//		System.out.println("I_verify_default_details_on_exchange_page:"+getTestDataFile());
		getDashboardPage().verifyPageLaunch("International Payments");
	}
	@Then("I Note the wallet balance of the {string} and {string} on Dashboard page")
	public void I_note_wallet_balance_for_corridor(String fromCurrency,String toCurrency) throws IOException {
		System.out.println("I_note_wallet_balance_for_corridor:"+getTestDataFile());
//		createTestDataFile(fileName);
		exchange_data_map.get("COMMON").put("fromCurrencyWalletBalance",getDashboardPage().getWalletBalanceOnDashboardPage(fromCurrency));
		exchange_data_map.get("COMMON").put("toCurrencyWalletBalance",getDashboardPage().getWalletBalanceOnDashboardPage(toCurrency));
		JsonHelpers.writeToJsonFile(fileName,exchange_data_map);
	}
	@Then("I verify available funds for the {string} corridor")
	public void I_verify_available_funds_for_corridor(String corridor){
		try {
			exchange_data_map=JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String funds="";
		if(exchange_data_map.get("COMMON").get("fromCurrencyCode").contentEquals(corridor)){
			funds=exchange_data_map.get("COMMON").get("fromCurrencyWalletBalance");
		}else if(exchange_data_map.get("COMMON").get("toCurrencyCode").contentEquals(corridor)){
			funds=exchange_data_map.get("COMMON").get("toCurrencyWalletBalance");
		}
		getDashboardPage().verifyAvailableFundsInCorridor(corridor,funds);
	}
	@Then("I verify available funds for the {string} corridor after submitting Foreign Exchange to CAB")
	public void I_verify_available_funds_for_corridor_after_exchange_submit_cab(String corridor){
		String funds="";
		try {
			exchange_data_map=JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(exchange_data_map.get("COMMON").get("toCurrencyCode").contentEquals(corridor)){
			funds=exchange_data_map.get("COMMON").get("toCurrencyWalletBalanceAfterSettlementReceived");
		}else if(exchange_data_map.get("COMMON").get("fromCurrencyCode").contentEquals(corridor)){
			funds=exchange_data_map.get("COMMON").get("fromCurrencyWalletBalanceAfterSettlementReceived");
		}
		getDashboardPage().verifyAvailableFundsInCorridor(corridor,funds);
	}
	@When("I navigate to Transactions tab page")
	public void I_navigate_to_transactions_page() throws IOException {
		getDashboardPage().navigateToTabPageOnDashboard("Transactions");
	}
	@When("I navigate to Wallets tab page")
	public void I_navigate_to_wallets_tab_page() throws IOException {
		getDashboardPage().navigateToTabPageOnDashboard("Wallets");
	}
	@Then("I verify transaction status {string} in table and open transaction details view")
	public void I_verify_transactions_status_open_details_view(String status) throws IOException {
		try {
			exchange_data_map=JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Before openTransactionDetailsPage:"+exchange_data_map);
		getDashboardPage().openTransactionDetailsPage(exchange_data_map,status);
	}
	@Then("I open transaction details view of payment for {string}")
	public void I_verify_payment_status_open_details_view(String recipientType) throws IOException {
		try {
			exchange_data_map=JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String reference=exchange_data_map.get(recipientType).get("reference");
		String recipientName=exchange_data_map.get(recipientType).get("recipientName");
		getDashboardPage().searchPaymentAndOpenTransactionDetailsView(recipientName,reference);
	}
	@Then("I verify payment status is {string} on transaction details view")
	public void I_verify_paymentstatus_on_details_view(String status) throws IOException {
		getDashboardPage().verifyStatusTransactionDetails(status);
	}
	@Then("I verify exchange transaction status is {string} on transaction details view")
	public void I_verify_transactions_status_on_details_view(String status) throws IOException {
		getDashboardPage().verifyStatusTransactionDetails(status);
	}
	@Then("I verify details of payment for {string} on transaction details view")
	public void I_verify_details_payment_details_view(String recipientType) throws IOException {
		try {
			exchange_data_map=JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("In details view extract:"+exchange_data_map);
		getDashboardPage().verifyPaymentDetailsOnDetailsView(exchange_data_map,recipientType);
	}
	@Then("I verify details of exchange transaction on transaction details view")
	public void I_verify_transactions_status_open_details_view() throws IOException {
		getDashboardPage().verifyOnlyExchangeTransactionDetails(exchange_data_map);
	}

	@When("I close the transaction details view")
	public void I_close_the_exchange_transaction_details_view() throws IOException {
		getDashboardPage().clickCloseBtn();
	}
	@Then("I search and verify payment for {string} in the Transactions grid")
	public void I_search_verify_payment_for_in_the_Transactions_grid(String paymentType) throws IOException {
		try {
			exchange_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		getDashboardPage().searchAndVerifyPaymentInTransactionGrid(exchange_data_map.get(paymentType));
		String paymentReference=getDashboardPage().getPaymentReferenceInTransactionGrid(exchange_data_map.get(paymentType));
		exchange_data_map.get(paymentType).put("reference",paymentReference);
		JsonHelpers.writeToJsonFile(fileName,exchange_data_map);
	}
	@Then("I verify and open Exchange details view of International payment from the Transactions grid")
	public void I_verify_and_open_exchange_details_international_payment_from_the_Transactions_grid() throws IOException {
		try {
			exchange_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String exchangeReference=getDashboardPage().getExchangeReferenceInTransactionGrid(exchange_data_map);
		exchange_data_map.get("EXCHANGE").put("reference",exchangeReference);
		getDashboardPage().verifyExchangeDetailsInTransactionGrid(exchange_data_map);
		getDashboardPage().openExchangeDetailsFromTransactionGrid(exchange_data_map);
		JsonHelpers.writeToJsonFile(fileName,exchange_data_map);
	}
//
//	@Then("I navigate to Exchange page")
//	public void I_navigate_to_exchange_page() throws IOException {
//		exchangePage=dashboardPage.navigateToExchangePage("Exchange");
//	}
//
//	@Then("I verify default details on Exchange page")
//	public void I_verify_default_details_on_exchange_page() throws IOException {
//		exchangePage.defaultDetailsExchangePage();
//	}
//
//	@Then("I verify {string} company name and {string} full name on Exchange page")
//	public void I_verify_default_details_on_exchange_page(String company, String user) throws IOException {
//		exchangePage.verifyCompanyUserDetails(company,user);
//	}
//
//	@Then("I verify {string} step is {string} on Exchange page")
//	public void I_verify_step_status_on_exchange_page(String stepName,String stepStatus) throws IOException {
//		exchangePage.verifyStepStatusExchangePage(stepName,stepStatus);
//	}
//
//	@When("I select {string} corridor and input {string} amount on Exchange page")
//	public void I_select_corridor_and_input_amount_on_exchange_page(String corridor,String amount) throws IOException {
//		exchangePage.selectAndEnterBuyAmount(corridor,amount);
//	}
//	@Then("I verify Live rate is displayed for {string} corridor")
//	public void I_verify_live_rate_is_displayed_exchange_page(String corridor) throws IOException {
//		exchangePage.verifyLiveRateDisplayed(corridor);
//	}
//	@Then("I verify {string} for the {string} corridor")
//	public void I_verify_processingtime_for_the_corridor(String procTime,String corridor) throws IOException {
//		exchangePage.verifyProcessingTime(procTime,corridor);
//		temp_exchange_map=exchangePage.getDetailsForReview();
//		temp_exchange_map.put("buyCurrencyWalletBalance", temp_exchange_buycurrencywalletbalance);
//		temp_exchange_map.put("sellCurrencyWalletBalance", temp_exchange_sellcurrencywalletbalance);
//		System.out.println("In Amount step:");
//		System.out.println(temp_exchange_map);
//	}
//	@Then("I review Exchange summary for {string} corridor and {string} amount")
//	public void I_review_exchangesummary_for_corridor_and_amount(String corridor,String buyAmount) throws IOException {
////		exchangePage.verifyProcessingTime(procTime,corridor);
////		temp_exchange_map=exchangePage.getDetailsForReview();
//		if(corridor.contentEquals("EUR")) {
//			temp_exchange_map.put("buyAmount", buyAmount+".00");
//		}
//		exchangePage.reviewExchangeSummaryBeforeExpired(temp_exchange_map);
//	}
//	@When("I Confirm the Exchange transaction")
//	public void I_confirm_the_exchange_transaction() throws IOException {
//		String settlementDate=temp_exchange_map.get("settlementDate");
//		exchangePage.clickReviewConfirmCheckbox(settlementDate);
//		exchangePage.clickBtn("Confirm Exchange");
//	}
//
////	@When("I create test data file with Exchange transaction details")
////	public void I_create_testdata_file_with_exchange_transaction_details() throws IOException {
////		exchangePage.createExchangeTransactionDataFile();
////	}
//	@When("I verify details on Exchange Success modal popup")
//	public void I_verify_details_exchange_success_mmodal() throws IOException {
//		String trxnReference=exchangePage.verifyDetailsOnSuccessModal(temp_exchange_map);
//		temp_exchange_map.put("type", "Foreign Exchange");
//		temp_exchange_map.put("createdAt", "Foreign Exchange");
//		temp_exchange_map.put("transactionReference", trxnReference);
//	}
//	//	@When("I select {string} corridor on Exchange page")
////	public void I_select_corridor_exchange_page(String wallet) throws IOException{
////		exchangePage.selectWallet(wallet);
//
//	@When("I click {string} button on Exchange page")
//	public void I_click_button_on_exchange_page(String btn) throws IOException{
//		exchangePage.clickBtn(btn);
//	}
//
//	@When("I click {string} button on Exchange Success modal popup")
//	public void I_click_button_exchange_success_modal(String btn) throws IOException{
//		dashboardPage=exchangePage.modal_ClickBtn();
//	}
//
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
//		paymentsPage.selectWallet(wallet);
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
//		paymentsPage.clickBtn(btn);
//	}
//	@When("I open Add new recipient form")
//	public void I_open_add_new_recipient_form() throws IOException {
//		paymentsPage.clickBtn("Add individual or business");
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
