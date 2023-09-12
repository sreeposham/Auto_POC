@multiplepayments @aud
Feature: End to End Exchange flow with Multiple Recipients - AUD

  Background:
    Given I setup test data file name for "Payments_Multiple" "AUD" "50"

  @corp
  Scenario Outline: Verify the end to end flow of Local payments with Multiple recipients for AUD corridor
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I Note the wallet balance of the "<fromCurrency>" and "<toCurrency>" on Dashboard page
    Then I navigate to Payments page
    Then I verify default details on Payments page
#    Then I verify "ANITHA TEST BUSINESS Solutions Private Limited" company name and "Sreekanth j" full name on the page
    Then I verify "Wallet" step is "Active" on "Payments" page
    Then I verify "Payments & Recipients" step is "Inactive" on "Payments" page
    Then I verify "Review & confirm" step is "Inactive" on "Payments" page
    When I select "<fromCurrency>" corridor on Payments page
    When I verify "<fromCurrency>" corridor details on Payments page
    Then I verify "<processingTime>" for the "<fromCurrency>" Payments
#    Then I verify Settlement Date for the "<fromCurrency>" corridor
    When I click "Next" button on "Payments" page
    Then I verify "Wallet" step is "Inactive" on "Payments" page
    Then I verify "Payments & Recipients" step is "Active" on "Payments" page
    Then I verify "Review & confirm" step is "Inactive" on "Payments" page
#    Then I verify default details in Payments & Recipients step on "Payments" page
    When I click "Add individual or business" button on "Payments" page
    Then I verify Add a new recipient form is displayed
    When I choose "<recipientBusiness>" on Add a new recipient form
    When I enter "<recipientBusiness>" details for "BUSINESS" recipient on Add a new recipient form
    When I save recipient details entered on Add a new recipient form
    Then I verify "Recipient added" toast message is displayed
    Then I verify "<recipientBusiness>" details in row "1" in Recipients grid on Payments & Recipients step
#    Then I verify "<recipientType>" recipient details in Payments & Recipients step on "Payments" page
    When I click "Add individual or business" button on "Payments" page
    When I choose "INDIVIDUAL" on Add a new recipient form
    When I enter "<recipientIndividual>" details for "INDIVIDUAL" recipient on Add a new recipient form
    When I save recipient details entered on Add a new recipient form
    Then I verify "Recipient added" toast message is displayed
    Then I verify "<recipientIndividual>" details in row "2" in Recipients grid on Payments & Recipients step
    Then I calculate the payment details for the recipients added
    Then I verify payment details under checkout section in Payments & Recipients step on "Payments" page
    When I click "Checkout" button on "Payments" page
    Then I verify "Wallet" step is "Inactive" on "Payments" page
    Then I verify "Payments & Recipients" step is "Inactive" on "Payments" page
    Then I verify "Review & confirm" step is "Active" on "Payments" page
    Then I verify payment details in Review & confirm step on "Payments" page
#    Then I verify "<processingTime>" for the "<currency>" Payments
#    Then I verify settlement date in Review & confirm step on "Payments" page
    When I click "Confirm payment" button on "Payments" page
    Then I verify details on the Payment submitted page
    When I navigate to Dashboard page
    Then I navigate to Transactions tab page
    Then I search and verify payment for "<recipientBusiness>" in the Transactions grid
    Then I open transaction details view of payment for "<recipientBusiness>"
    Then I verify payment status is "Scheduled" on transaction details view
    Then I verify details of payment for "<recipientBusiness>" on transaction details view
    When I close the transaction details view
    Then I search and verify payment for "<recipientIndividual>" in the Transactions grid
    Then I open transaction details view of payment for "<recipientIndividual>"
    Then I verify payment status is "Scheduled" on transaction details view
    Then I verify details of payment for "<recipientIndividual>" on transaction details view
    When I close the transaction details view
    Then I navigate to Wallets tab page
    Then I verify available funds for the "<fromCurrency>" corridor
    When I'm Sign out from the Corp portal
    Given I'm Logged in to the Ops portal
#    Then I navigated to the "Transactions" page using left navigation
#    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
#    Then I navigate to Customer Overview page from the transaction details view
#    Then I search for customer by "Phone number" "<phone>" in the Customers page
    Then I open customer overview page of "Corporate Customers" registered with "Phone number" "<phone>"
    And I verify "Available" balance of "<fromCurrency>" wallet on the Customer Overview page
    When I navigate to "Wallet debits" tab page
#    Then I search for the transaction with "<reference>", "<type>" and "<status>"
    When I open payment details view for the payment of "<recipientBusiness>" having "<transactionReference>"
    Then I verify payment details for "<recipientBusiness>" on the transaction details view
    Then I verify buttons "Available" on the transaction details view
      |SUBMIT SINGLE PAYMENT|
      |SUBMIT FULL BATCH|
      |DECLINE|
      |DOWNLOAD|
    When I submit "<recipientBusiness>" payment to CAB for processing
    Then I verify "Order has been updated successfully" toast message is displayed
    When I update data file after "<recipientBusiness>" payment submitted to CAB
    Then I verify payment details for "<recipientBusiness>" on the transaction details view
    Then I verify buttons "Available" on the transaction details view
      |Force Refresh Payment Statuses|
      |DOWNLOAD|
    Then I verify buttons "Not Available" on the transaction details view
      |SUBMIT SINGLE PAYMENT|
      |SUBMIT FULL BATCH|
      |DECLINE|
    Then I navigate to Customer Overview page from the transaction details view
    When I update data file after "<recipientBusiness>" payment processed by CAB
    And I verify "Available" balance of "<fromCurrency>" wallet after "<recipientBusiness>" payment "COMPLETED"
    When I navigate to "Wallet debits" tab page
#    Then I search for the transaction with "<reference>", "<type>" and "<status>"
    When I open payment details view for the payment of "<recipientBusiness>" having "<transactionReference>"
    Then I verify payment details for "<recipientBusiness>" on the transaction details view
    Then I navigate to Customer Overview page from the transaction details view
    When I navigate to "Wallet debits" tab page
#    Then I search for the transaction with "<reference>", "<type>" and "<status>"
    When I open payment details view for the payment of "<recipientIndividual>" having "<transactionReference>"
    Then I verify payment details for "<recipientIndividual>" on the transaction details view
    Then I verify buttons "Available" on the transaction details view
      |SUBMIT SINGLE PAYMENT|
      |SUBMIT FULL BATCH|
      |DECLINE|
      |DOWNLOAD|
    When I submit "<recipientIndividual>" payment to CAB for processing
    Then I verify "Order has been updated successfully" toast message is displayed
    When I update data file after "<recipientIndividual>" payment submitted to CAB
    Then I verify payment details for "<recipientIndividual>" on the transaction details view
    Then I navigate to Customer Overview page from the transaction details view
    When I update data file after "<recipientIndividual>" payment processed by CAB
    And I verify "Available" balance of "<fromCurrency>" wallet after "<recipientIndividual>" payment "COMPLETED"
    When I navigate to "Wallet debits" tab page
    Then I verify "payment" details for "<recipientIndividual>" in the "Wallet debits" grid
    When I open payment details view for the payment of "<recipientIndividual>" having "<transactionReference>"
    Then I verify payment details for "<recipientIndividual>" on the transaction details view
    Then I verify buttons "Not Available" on the transaction details view
      |SUBMIT SINGLE PAYMENT|
      |SUBMIT FULL BATCH|
      |DECLINE|
      |Force Refresh Payment Statuses|
    Then I verify buttons "Available" on the transaction details view
      |DOWNLOAD|
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I navigate to Transactions tab page
    Then I open transaction details view of payment for "<recipientBusiness>"
    Then I verify payment status is "Completed" on transaction details view
    Then I verify details of payment for "<recipientBusiness>" on transaction details view
    When I close the transaction details view
    Then I open transaction details view of payment for "<recipientIndividual>"
    Then I verify payment status is "Completed" on transaction details view
    Then I verify details of payment for "<recipientIndividual>" on transaction details view
    When I close the transaction details view
    Then I navigate to Wallets tab page
    Then I verify available funds for the "<fromCurrency>" corridor
    When I'm Sign out from the Corp portal

    Examples:
      | fromCurrency | toCurrency |  processingTime | phone          |  recipientBusiness | recipientIndividual | transactionReference |
      | AUD          | AUD        |  Instant        | +91 7989730020 |  BUSINESS          | INDIVIDUAL          |                      |