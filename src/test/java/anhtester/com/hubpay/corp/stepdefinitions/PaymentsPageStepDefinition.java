package anhtester.com.hubpay.corp.stepdefinitions;

import anhtester.com.common.CommonPage;
import anhtester.com.helpers.JsonHelpers;
import anhtester.com.keywords.WebUI;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PaymentsPageStepDefinition extends CommonPage {

    private Map<String, Map<String, String>> payments_data_map = new HashMap<>();
    private String fileName = "";

    public PaymentsPageStepDefinition() throws IOException {
        fileName = getTestDataFile();
        try {
            payments_data_map = JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("I verify Payments page is displayed")
    public void I_verify_dashboard_page_is_displayed() throws IOException {
        getDashboardPage().verifyDashboardIsDisplayed();
    }

    @Then("I verify default details on Payments page")
    public void I_verify_defaultdetails_payments_page() throws IOException {
        getPaymentsPage().verifyDefaultDetails("Payments");
    }
    @Then("I verify default details on International payments page")
    public void I_verify_defaultdetails_international_payments_page() throws IOException {
        getPaymentsPage().verifyDefaultDetails("International Payments");
    }

    @Then("I select {string} corridor on Payments page")
    public void I_select_corridor_on_payments_page(String corridor) throws IOException {
        getPaymentsPage().selectPaymentCurrency(corridor);
//        payments_data_map.get("COMMON").put("toCurrencyCode", corridor);
//        payments_data_map.get("COMMON").put("toCurrencyName", getCurrencyName(corridor));
        JsonHelpers.writeToJsonFile(fileName, payments_data_map);
    }
    @Then("I select {string} corridor on International Payments page")
    public void I_select_corridor_on_international_payments_page(String corridor) throws IOException {
//        getExchangePage().selectWallet(corridor);
        getPaymentsPage().selectPaymentCurrency(corridor);
//        payments_data_map.get("COMMON").put("toCurrencyCode", corridor);
//        payments_data_map.get("COMMON").put("toCurrencyName", getCurrencyName(corridor));
        JsonHelpers.writeToJsonFile(fileName, payments_data_map);
    }
    @When("I select {string} tenor on International Payments page")
    public void I_select_tenor_for_currency_on_international_payments_page(String tenor) throws IOException {
        getExchangePage().selectTenorDateOnExchange(tenor);
    }
    @Then("I verify {string} corridor details on Payments page")
    public void I_verify_corridor_details_on_payments_page(String corridor) throws IOException {
        if(corridor.equalsIgnoreCase(payments_data_map.get("COMMON").get("toCurrencyCode"))){
        getPaymentsPage().verifySelectedCorridorDetails(payments_data_map.get("COMMON"),"TO CURRENCY");}else if
        (corridor.equalsIgnoreCase(payments_data_map.get("COMMON").get("fromCurrencyCode"))){
            getPaymentsPage().verifySelectedCorridorDetails(payments_data_map.get("COMMON"),"FROM CURRENCY");
        }
    }

    @Then("I verify {string} for the {string} Payments")
    public void I_verify_processingtime_for_the_payments(String procTime, String corridor) throws Exception {
        getExchangePage().verifyProcessingTime(payments_data_map.get("COMMON").get("processingTime"), corridor);
        payments_data_map=getPaymentsPage().getPaymentSettlementDate(payments_data_map);
        JsonHelpers.writeToJsonFile(fileName, payments_data_map);

    }

    @Then("I verify Add a new recipient form is displayed")
    public void I_verify_add_new_recipient_form_displayed() throws Exception {
        getPaymentsPage().verifyRecipientFormOpened();
    }

    @When("I choose {string} on Add a new recipient form")
    public void I_choose_on_Add_new_recipient_form(String recipientType) throws IOException {
        getPaymentsPage().chooseRecipientTypeOnForm(recipientType);
    }

    @When("I enter {string} details for {string} recipient on Add a new recipient form")
    public void I_enter_details_for_recipent_on_Add_new_recipient_form(String recipDetails, String recipientType) throws IOException {
        try {
            payments_data_map = JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String payCorridor = "";
        payCorridor = payments_data_map.get("COMMON").get("toCurrencyCode");
        getPaymentsPage().enterDataOnRecipientForm(payments_data_map.get(recipDetails.toUpperCase()), payCorridor,recipientType);
    }

    @When("I click {string} button on Add a new recipient form")
    public void I_click_button_on_Add_new_recipient_form(String btnName) throws IOException {
        getExchangePage().clickBtn(btnName, "Payments");
    }

    @When("I save recipient details entered on Add a new recipient form")
    public void I_save_recipient_details_entered_Add_new_recipient_form() throws IOException {
        getPaymentsPage().saveBeneficiaryDetails();
    }

    @When("I verify {string} details in row {string} in Recipients grid on Payments & Recipients step")
    public void I_verify_details_in_row_in_recipientsgrid_on_paymentsrecipients_step(String recipientType, String index) throws IOException {
        String payCorridor = "";
        payCorridor = payments_data_map.get("COMMON").get("toCurrencyCode");
        getPaymentsPage().verifydetailsOnRecipientsGrid(payments_data_map.get(recipientType.toUpperCase()), Integer.valueOf(index));
    }

    @Then("I calculate the payment details for the recipients added for {string}")
    public void I_calculate_the_paymentdetails_for_the_recipients_added(String flow) throws IOException {
        try {
            payments_data_map = JsonHelpers.readFromJsonFile(fileName);
            System.out.println("Before calculate:" + payments_data_map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String noOfPayments=getPaymentsPage().getNumberOfPayments();
        payments_data_map.get("COMMON").put("noOfPayments",noOfPayments);
        JsonHelpers.writeToJsonFile(fileName, getPaymentsPage().calculatePaymentDetails(payments_data_map,flow));
        System.out.println("After calculate:" + payments_data_map);
    }

    @When("I verify payment details under checkout section in Payments & Recipients step on {string} page")
    public void I_verify_paymentdetails_under_checkout_payments_recipients_step(String flow) throws IOException {
        if (flow.equalsIgnoreCase("Payments")) {
            getPaymentsPage().verifySelectedCorridorDetails(payments_data_map.get("COMMON"),"FROM CURRENCY");
            getPaymentsPage().verifyPaymentDetailsCheckout(flow, payments_data_map.get("COMMON"));
        } else if (flow.equalsIgnoreCase("International Payments")) {
            getPaymentsPage().verifySelectedCorridorDetails(payments_data_map.get("COMMON"),"FROM CURRENCY");
            getPaymentsPage().verifyPaymentDetailsCheckout(flow, payments_data_map.get("COMMON"));
        }
    }
    @When("I refresh rate and verify payment details in Review & confirm step for {string}")
    public void I_refresh_rate_and_verify_paymentdetails_in_review_confirm_payments_page(String flow) throws IOException {
        try {
            payments_data_map= JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getExchangePage().verifyStaticDetailsOnReviewInternationPaymentsSummary(payments_data_map);
        getExchangePage().refreshLiveRateOnReviewScreen();
        payments_data_map=getPaymentsPage().calculatePaymentDetails(payments_data_map,flow);
        getPaymentsPage().verifyPaymentSummaryIntlPaymentsReviewConfirm(payments_data_map.get("COMMON"));
        JsonHelpers.writeToJsonFile(fileName,payments_data_map);
//        if (flow.equalsIgnoreCase("International Payments")) {
//            getExchangePage().verifyStaticDetailsOnReviewInternationPaymentsSummary(payments_data_map);
//            getPaymentsPage().verifyPaymentSummaryIntlPaymentsReviewConfirm(payments_data_map.get("COMMON"));
//        }
    }
    @When("I verify payment details in Review & confirm step for {string}")
    public void I_verify_paymentdetails_in_review_confirm_payments_page(String flow) throws IOException {
        getPaymentsPage().verifyFromCorridorNameCode(payments_data_map.get("COMMON"));
        if (flow.equalsIgnoreCase("Payments")) {
            getPaymentsPage().verifyPaymentDetailsReviewConfirm(payments_data_map.get("COMMON"));
        } else if (flow.equalsIgnoreCase("International Payments")) {
            getExchangePage().verifyStaticDetailsOnReviewInternationPaymentsSummary(payments_data_map);
            getPaymentsPage().verifyPaymentSummaryIntlPaymentsReviewConfirm(payments_data_map.get("COMMON"));
        }
    }

    @Then("I verify details on the {string} submitted page")
    public void I_verify_details_on_submitted_page(String paymentType) throws IOException {
        try {
            payments_data_map = JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getPaymentsPage().verifyDetailsOnInternationalPaymentSubmittedPage(payments_data_map.get("COMMON"));
        payments_data_map.get("COMMON").put("expectedPaymentDate", getPaymentsPage().getExpPaymentDate());
        payments_data_map.get("COMMON").put("createdAt", new SimpleDateFormat("EEEE d MMMM yyyy").format(new Date()).toString());
        payments_data_map.get("COMMON").put("scheduledAt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString());
        for (Map.Entry<String, Map<String, String>> mapElement : payments_data_map.entrySet()) {
            String key = mapElement.getKey();
            if (!(key.equalsIgnoreCase("COMMON") | key.equalsIgnoreCase("EXCHANGE")) | key.equalsIgnoreCase("Foreign Exchange")) {
                payments_data_map = getTransactionsPage().updateDataFileAfterPaymentScheduled(payments_data_map, key);
            }else if(key.equalsIgnoreCase("EXCHANGE")){
                payments_data_map.get("EXCHANGE").put("createdAt", (new SimpleDateFormat("EEEE d MMMM yyyy").format(new Date())).toString());
                payments_data_map.get("EXCHANGE").put("orderedAt",(new SimpleDateFormat("dd/MM/yyyy").format(new Date())).toString());
                payments_data_map.get("EXCHANGE").put("orderSettledAt","-");
                payments_data_map.get("EXCHANGE").put("rejectedAt","-");
                payments_data_map.get("EXCHANGE").put("completedAt","-");
                payments_data_map.get("EXCHANGE").put("totalFundsDue",payments_data_map.get("COMMON").get("totalPayable"));
                payments_data_map.get("EXCHANGE").put("toAmount",payments_data_map.get("COMMON").get("totalReceiveAmount").split(" ")[1]);
                payments_data_map.get("EXCHANGE").put("fromAmount",payments_data_map.get("COMMON").get("totalSendAmount"));
            }
            JsonHelpers.writeToJsonFile(fileName, payments_data_map);
            System.out.println("After submission and write to file:" + payments_data_map);
        }

    }
    @Then("I verify details on the Payment submitted page")
    public void I_verify_details_on_payment_submitted_page(String paymentType) throws IOException {
        try {
            payments_data_map = JsonHelpers.readFromJsonFile(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getPaymentsPage().verifyDetailsOnPaymentSubmittedPage(payments_data_map.get("COMMON"));
        payments_data_map.get("COMMON").put("expectedPaymentDate", getPaymentsPage().getExpPaymentDate());
        payments_data_map.get("COMMON").put("createdAt", new SimpleDateFormat("EEEE d MMMM yyyy").format(new Date()).toString());
        payments_data_map.get("COMMON").put("scheduledAt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()).toString());
        for (Map.Entry<String, Map<String, String>> mapElement : payments_data_map.entrySet()) {
            String key = mapElement.getKey();
            if (!(key.equalsIgnoreCase("COMMON") | key.equalsIgnoreCase("EXCHANGE")) | key.equalsIgnoreCase("Foreign Exchange")) {
                payments_data_map = getTransactionsPage().updateDataFileAfterPaymentScheduled(payments_data_map, key);
            }
            JsonHelpers.writeToJsonFile(fileName, payments_data_map);
            System.out.println("After submission and write to file:" + payments_data_map);
        }

    }
    @Then("I verify required field validation for {string} corridor {string} recipient on Add a new recipient form")
    public void I_verify_required_field_validation_for_corridor_recipient_on_Add_a_new_recipient_form(String currency, String recipientType) throws IOException {
        getPaymentsPage().verifyRequiredFieldValidation(currency,recipientType);
    }
    @Then("I verify maximum value validation for {string} corridor {string} recipient on Add a new recipient form")
    public void I_verify_maximum_value_validation_for_corridor_recipient_on_Add_a_new_recipient_form(String currency, String recipientType) throws IOException {
        getPaymentsPage().verifyMaximumValueForFieldsValidation(currency,recipientType);
    }
    @Then("I verify minimum value validation for {string} corridor {string} recipient on Add a new recipient form")
    public void I_verify_minimum_value_validation_for_corridor_recipient_on_Add_a_new_recipient_form(String currency, String recipientType) throws IOException {
        getPaymentsPage().verifyMinimumValueForFieldsValidation(currency,recipientType);
    }
    @Then("I verify special characters validation for {string} corridor {string} recipient on Add a new recipient form")
    public void I_verify_special_characters_validation_for_corridor_recipient_on_Add_a_new_recipient_form(String currency, String recipientType) throws IOException {
        getPaymentsPage().verifyMinimumValueForFieldsValidation(currency,recipientType);
    }
    @Then("I verify {string} toast message is displayed")
    public void I_verify_toast_message_is_displayed(String toastMsg) throws IOException {
        getPaymentsPage().verifyToastMessage(toastMsg);
    }
}
