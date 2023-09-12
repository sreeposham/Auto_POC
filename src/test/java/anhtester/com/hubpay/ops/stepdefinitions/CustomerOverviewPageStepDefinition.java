package anhtester.com.hubpay.ops.stepdefinitions;

import anhtester.com.common.CommonPage;
import anhtester.com.helpers.JsonHelpers;
import anhtester.com.keywords.WebUI;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.Map;


public class CustomerOverviewPageStepDefinition extends CommonPage {

	private String fileName="";
	private Map<String,Map<String,String>> local_data_map;
	public CustomerOverviewPageStepDefinition() throws IOException {
		fileName=getTestDataFile();
//		System.out.println("DashboardPageStepDefinition:"+CommonPage.checkfilenameaccess);
		try {
			local_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@When("I navigate to {string} tab page")
	public void inavigateToTabPage(String tab) {
		// Write code here that turns the phrase above into concrete actions
		getCustomerOverviewPage().clickTabMenu(tab);
	}
	@Then("I verify {string} balance of {string} wallet on the Customer Overview page")
	public void i_verify_wallet_balance_customer_overview_page(String balanceType, String corridor) {
		try {
			local_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String balance="";
		if(local_data_map.get("COMMON").get("toCurrencyCode").contentEquals(corridor)){
			balance=local_data_map.get("COMMON").get("toCurrencyWalletBalance");
		}else if(local_data_map.get("COMMON").get("fromCurrencyCode").contentEquals(corridor)){
			balance=local_data_map.get("COMMON").get("fromCurrencyWalletBalance");
		}
		getCustomerOverviewPage().verifyWalletBalance(balanceType,corridor,balance);
	}
	@Then("I verify {string} balance of {string} wallet after submitting Foreign Exchange to CAB")
	public void i_verify_wallet_balance_after_CAB_submission(String balanceType, String corridor) {
		try {
			local_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String balance="";
		System.out.println("after submitting Foreign Exchange to CAB "+local_data_map);
		if(local_data_map.get("COMMON").get("toCurrencyCode").contentEquals(corridor)){
			balance=local_data_map.get("COMMON").get("toCurrencyWalletBalanceAfterSettlementReceived");
		}else if(local_data_map.get("COMMON").get("fromCurrencyCode").contentEquals(corridor)){
			balance=local_data_map.get("COMMON").get("fromCurrencyWalletBalanceAfterSettlementReceived");
		}
		getCustomerOverviewPage().verifyWalletBalance(balanceType,corridor,balance);
	}

	@When("I verify {string} balance of {string} wallet after {string} payment {string}")
	public void i_verify_balance_of_wallet_after_payment(String balanceType, String wallet, String recipientType, String paymentStatus) {
		try {
			local_data_map = JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String balance = "";
//		if(local_data_map.get("COMMON").get("fromCurrencyCode").contentEquals(wallet)){
//			if (paymentStatus.equalsIgnoreCase("COMPLETED"))
//				balance = local_data_map.get("COMMON").get("fromCurrencyWalletBalanceAfterPaymentCompleted");
//			else if (paymentStatus.equalsIgnoreCase("SCHEDULED")) {
//				balance = local_data_map.get("COMMON").get("fromCurrencyWalletBalance");
//			}
//		} else {
//			if (local_data_map.get("COMMON").get("toCurrencyCode").contentEquals(wallet)) {
//				if (paymentStatus.equalsIgnoreCase("COMPLETED"))
//					balance = local_data_map.get("COMMON").get("toCurrencyWalletBalanceAfterPaymentCompleted");
//				else if (paymentStatus.equalsIgnoreCase("SCHEDULED")) {
//					balance = local_data_map.get("COMMON").get("toCurrencyWalletBalance");
//				}
////			} else if (local_data_map.get("COMMON").get("fromCurrencyCode").contentEquals(wallet)) {
////				if (paymentStatus.equalsIgnoreCase("COMPLETED"))
////					balance = local_data_map.get("COMMON").get("fromCurrencyWalletBalanceAfterPaymentCompleted");
////				else if (paymentStatus.equalsIgnoreCase("SCHEDULED")) {
////					balance = local_data_map.get("COMMON").get("fromCurrencyWalletBalance");
////				}
//			}
		if(local_data_map.get("COMMON").get("fromCurrencyCode").contentEquals(wallet)){
				balance = local_data_map.get("COMMON").get("fromCurrencyWalletBalance");
		} else if(local_data_map.get("COMMON").get("toCurrencyCode").contentEquals(wallet)) {
					balance = local_data_map.get("COMMON").get("toCurrencyWalletBalance");
				}
		getCustomerOverviewPage().verifyWalletBalance(balanceType, wallet, balance);
	}
}
