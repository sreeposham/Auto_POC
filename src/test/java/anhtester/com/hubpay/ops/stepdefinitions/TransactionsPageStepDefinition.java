package anhtester.com.hubpay.ops.stepdefinitions;

import anhtester.com.common.CommonPage;
import anhtester.com.helpers.JsonHelpers;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class TransactionsPageStepDefinition extends CommonPage {

    private String fileName = "";
    private Map<String, Map<String, String>> local_data_map;

    public TransactionsPageStepDefinition() throws IOException {
        fileName = getTestDataFile();
//		System.out.println("DashboardPageStepDefinition:"+CommonPage.checkfilenameaccess);
        try {
            local_data_map = JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("I open transaction details view of the {string} transaction having {string}")
    public void i_open_transaction_detailsview_transaction_having(String trxnType, String reference) {
        // Write code here that turns the phrase above into concrete actions
        if (reference.isEmpty()) {
            reference = local_data_map.get("EXCHANGE").get("reference");
        }
        getTransactionsPage().openTransactionDetailsViewFromTabPage(trxnType, reference);
    }

    @When("I open payment details view for the payment of {string} having {string}")
    public void i_open_payment_detailsview_for_the_having(String recipientType, String reference) {
        // Write code here that turns the phrase above into concrete actions
        if (reference.isEmpty() | reference.contentEquals("")) {
            reference = local_data_map.get(recipientType).get("reference");
        }
        if (recipientType.equalsIgnoreCase("Foreign Exchange") | recipientType.equalsIgnoreCase("Exchange")) {
            getTransactionsPage().openTransactionDetailsViewFromTabPage("Foreign Exchange", reference);
        } else {
            getTransactionsPage().openTransactionDetailsViewFromTabPage("Payment details", reference);
        }
    }

    @When("I verify {string} details for {string} in the {string} grid")
    public void i_verify_transaction_details_for_in_the_wallet_debits_grid(String trxnType, String recipientType, String grid) {
        // Write code here that turns the phrase above into concrete actions
		boolean trxnTypeFlag = trxnType.equalsIgnoreCase("Foreign Exchange") | trxnType.equalsIgnoreCase("Exchange");
		switch (grid.toUpperCase()) {
            case "WALLET DEBITS":
                if (trxnTypeFlag) {
                    getTransactionsPage().verifyTransactionDetailsWalletDebitsGrid(local_data_map, "FOREIGN_EXCHANGE", "Foreign Exchange");
                } else {
                    getTransactionsPage().verifyTransactionDetailsWalletDebitsGrid(local_data_map, trxnType.toUpperCase(), recipientType);
                }
                break;
            case "TRANSACTIONS":
                if (trxnTypeFlag) {
                    getTransactionsPage().verifyTransactionDetailsWalletDebitsGrid(local_data_map, "Foreign Exchange", "Foreign Exchange");
                } else {
                    getTransactionsPage().verifyTransactionDetailsWalletDebitsGrid(local_data_map, "Payment", recipientType);
                }
                break;
        }
    }

    @When("I verify transaction status is {string} on the transaction details view")
    public void i_verify_transaction_status_transaction_detailsview(String trxnStatus) {
        // Write code here that turns the phrase above into concrete actions
        getTransactionsPage().verifyStatusInTransactionDetailsView(trxnStatus);
    }

    @Then("I verify {string} details on the transaction details view")
    public void i_verify_transaction_details_on_transaction_detailsview(String trxnType) {
        // Write code here that turns the phrase above into concrete actions
        if (trxnType.equalsIgnoreCase("Foreign Exchange")) {
            local_data_map.get("COMMON").get("reference");
        }
        try {
            getTransactionsPage().verifyExchangeTransactionDetails(local_data_map);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Then("I verify payment details for {string} on the transaction details view")
    public void i_verify_payment_details_for_on_transaction_detailsview(String recipientType) throws ParseException {
        // Write code here that turns the phrase above into concrete actions
        getTransactionsPage().verifyPaymentsTransactionDetails(local_data_map, recipientType);
    }

    @Then("I verify {string} button {string} on the transaction details view")
    public void i_verify_button_on_transaction_detailsview(String button, String status) {
        // Write code here that turns the phrase above into concrete actions
        getTransactionsPage().verifyButtonOnTransactionDetailsView(button, status);
    }

    @Then("I click {string} button on the transaction details view")
    public void i_click_button_on_transaction_detailsview(String button) {
        // Write code here that turns the phrase above into concrete actions
        getTransactionsPage().clickButtonOnTransactionDetailsView(button);
        if (button.equalsIgnoreCase("SETTLEMENT RECEIVED")) {
            local_data_map.get("EXCHANGE").put("orderSettledAt", (new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toString());
        } else if (button.equalsIgnoreCase("SETTLE")) {
            local_data_map.get("EXCHANGE").put("completedAt", (new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toString());
        }

        JsonHelpers.writeToJsonFile(fileName, local_data_map);
    }

    @Then("I submit {string} payment to CAB for processing")
    public void i_submit_payment_to_CAB_for_processing(String recipientType) throws ParseException {
        if (recipientType.equalsIgnoreCase("FULL BATCH")) {
            getTransactionsPage().clickButtonOnTransactionDetailsView("SUBMIT FULL BATCH");
        } else {
            getTransactionsPage().clickButtonOnTransactionDetailsView("SUBMIT SINGLE PAYMENT");
        }
    }

    @Then("I update data file after {string} payment submitted to CAB")
    public void i_update_data_file_after_payment_submitted_to_CAB(String recipientType) throws ParseException {
        try {
            local_data_map = JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (recipientType.equalsIgnoreCase("FULL BATCH")) {
//			local_data_map=getTransactionsPage().updateDataFileForPaymentAfterSubmitToCAB(local_data_map,recipientType);
        } else {
            local_data_map = getTransactionsPage().updateDataFileForPaymentAfterSubmitToCAB(local_data_map, recipientType);
//			getTransactionsPage().verifyPaymentsTransactionDetails(local_data_map,recipientType);
        }
        JsonHelpers.writeToJsonFile(fileName, local_data_map);
    }

    @Then("I update data file after {string} payment processed by CAB")
    public void i_update_data_file_after_payment_processed_by_CAB(String recipientType) throws ParseException {
        try {
            local_data_map = JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (recipientType.equalsIgnoreCase("FULL BATCH")) {
//			local_data_map=getTransactionsPage().updateDataFileForAfterFullBatchPaymentsProcessedByCAB(local_data_map,recipientType);
        } else {
            local_data_map = getTransactionsPage().updateDataFileForPaymentAfterProcessedByCAB(local_data_map, recipientType);
        }
        JsonHelpers.writeToJsonFile(fileName, local_data_map);
    }

    @Then("I update data file after {string} recipient {string} submitted to CAB")
    public void i_update_data_file_after_recipient_submitted_to_CAB(String recipient, String paymentType) throws ParseException {
        try {
            local_data_map = JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (paymentType.toUpperCase()) {
            case "PAYMENT":
            case "PAYMENTS":
//				if(recipient.equalsIgnoreCase("FULL BATCH")){
//					local_data_map=getTransactionsPage().updateDataFileForAfterFullBatchPaymentsProcessedByCAB(local_data_map,paymentType);
//				}else{
//					local_data_map=getTransactionsPage().updateDataFileForPaymentAfterProcessedByCAB(local_data_map,recipient);
//				}
//				break;
            case "INTERNATIONAL PAYMENT":
            case "INTERNATIONAL PAYMENTS":
                if (recipient.equalsIgnoreCase("FULL BATCH")) {
                    local_data_map = getTransactionsPage().updateDataFileForAfterFullBatchPaymentsSubmittedToCAB(local_data_map);
                } else {
                    local_data_map = getTransactionsPage().updateDataFileForPaymentAfterSubmitToCAB(local_data_map, recipient);
                }
                break;
        }
        JsonHelpers.writeToJsonFile(fileName, local_data_map);
    }

    @Then("I update data file after {string} recipient {string} processed by CAB")
    public void i_update_data_file_after_recipient_processed_by_CAB(String recipient, String paymentType) throws ParseException {
        try {
            local_data_map = JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (paymentType.toUpperCase()) {
            case "PAYMENT":
            case "PAYMENTS":
                if (recipient.equalsIgnoreCase("FULL BATCH")) {
                    local_data_map = getTransactionsPage().updateDataFileForAfterFullBatchPaymentsProcessedByCAB(local_data_map, paymentType);
                } else {
                    local_data_map = getTransactionsPage().updateDataFileForPaymentAfterProcessedByCAB(local_data_map, recipient);
                }
                break;
            case "INTERNATIONAL PAYMENT":
            case "INTERNATIONAL PAYMENTS":
                if (recipient.equalsIgnoreCase("FULL BATCH")) {
                    local_data_map = getTransactionsPage().updateDataFileForAfterFullBatchPaymentsProcessedByCAB(local_data_map, paymentType);
                } else {
                    local_data_map = getTransactionsPage().updateDataFileAfterInternationalPaymentProcessedByCAB(local_data_map, recipient);
                }
                break;
        }
        JsonHelpers.writeToJsonFile(fileName, local_data_map);
    }

    @Then("I calculate and update wallet balance in test data file")
    public void i_claculate_update_wallet_balances_in_test_data_file() {
        String bugamount = local_data_map.get("EXCHANGE").get("toAmount");
        String sellamount = local_data_map.get("EXCHANGE").get("fromAmount").split(" ")[1];
        String buyCurrencyWalletBalance = local_data_map.get("COMMON").get("toCurrencyWalletBalance").split(" ")[1].replace(",", "");
        String sellCurrencyWalletBalance = local_data_map.get("COMMON").get("fromCurrencyWalletBalance").split(" ")[1].replace(",", "");
        ;
//		String temp= Double.toString(Double.parseDouble(buyCurrencyWalletBalance)-Double.parseDouble(bugamount));
        String temp1 = String.format("%.2f", (Double.parseDouble(buyCurrencyWalletBalance) + Double.parseDouble(bugamount)));
        System.out.println("buyCurrencyWalletBalance: " + buyCurrencyWalletBalance + " and Temp:" + temp1);
        local_data_map.get("COMMON").put("toCurrencyWalletBalanceAfterSettlementReceived", (local_data_map.get("COMMON").get("toCurrencyCode") + " " + temp1).toString());
        String temp2 = String.format("%.2f", (Double.parseDouble(sellCurrencyWalletBalance) - Double.parseDouble(sellamount)));
        System.out.println("sellCurrencyWalletBalance: " + sellCurrencyWalletBalance + " and Temp:" + temp2);
        local_data_map.get("COMMON").put("fromCurrencyWalletBalanceAfterSettlementReceived", (local_data_map.get("COMMON").get("fromCurrencyCode") + " " + temp2).toString());
        JsonHelpers.writeToJsonFile(fileName, local_data_map);
        try {
            System.out.println("After calculating balances:" + JsonHelpers.readFromJsonFile(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("I update wallet balance in test data file after submitting Foreign Exchange to CAB")
    public void i_update_wallet_balances_in_test_data_file_after_submitting_exchange_to_CAB() {
        String sellamount = local_data_map.get("COMMON").get("totalPayable").split(" ")[1];
        String sellCurrencyWalletBalance = local_data_map.get("COMMON").get("fromCurrencyWalletBalance").split(" ")[1].replace(",", "");
        ;
        if (Double.parseDouble(sellCurrencyWalletBalance) > Double.parseDouble(sellamount)) {
            Double temp2 = Double.parseDouble(sellCurrencyWalletBalance) - Double.parseDouble(sellamount);
            local_data_map.get("COMMON").put("fromCurrencyWalletBalance", (local_data_map.get("COMMON").get("fromCurrencyCode") + " " + String.format("%.2f", temp2).toString()));
            System.out.println("fromCurrencyWalletBalance: " + sellCurrencyWalletBalance + " and Temp:" + temp2);
        }
        JsonHelpers.writeToJsonFile(fileName, local_data_map);
        try {
            System.out.println("After calculating balances:" + JsonHelpers.readFromJsonFile(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("I update wallet balance in test data file after Foreign Exchange processed by CAB")
    public void i_update_wallet_balances_in_test_data_file_after_exchange_processed_by_CAB() {
        Double buyamount = Double.parseDouble(local_data_map.get("EXCHANGE").get("toAmount"));
        Double buyCurrencyWalletBalance = Double.parseDouble(local_data_map.get("COMMON").get("toCurrencyWalletBalance").split(" ")[1].replace(",", ""));
        Double temp2 = buyamount + buyCurrencyWalletBalance;
        local_data_map.get("COMMON").put("toCurrencyWalletBalance", (local_data_map.get("COMMON").get("toCurrencyCode") + " " + String.format("%.2f", temp2).toString()));
        System.out.println("toCurrencyWalletBalance: " + buyCurrencyWalletBalance + " and Temp:" + temp2);
        JsonHelpers.writeToJsonFile(fileName, local_data_map);
        try {
            System.out.println("After calculating balances:" + JsonHelpers.readFromJsonFile(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("I navigate to Customer Overview page from the transaction details view")
    public void i_navigate_customer_overview_from_transaction_detailsview() {
        // Write code here that turns the phrase above into concrete actions
        getTransactionsPage().clickButtonOnTransactionDetailsView("User Account Id");
        getCustomersPage().verifyLaunchCustomerOverview();
    }

    @Then("I verify buttons {string} on the transaction details view")
    public void i_verify_buttons_not_available_on_transaction_detailsview(String btnStatus, DataTable buttons) {
        // Write code here that turns the phrase above into concrete actions
        List<String> ls_button = buttons.asList(String.class);
        getTransactionsPage().verifyButtonStatusOnDetailsView(ls_button, btnStatus);
    }
}
