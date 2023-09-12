@payment @aud
Feature: End to End Exchange flow - AUD

  Background:
    Given I setup test data file name for "Payments_Single" "AUD" "30"

  @corp
  Scenario Outline: Verify the end to end flow of Exchange flow for AUD corridor
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
    When I choose "<recipientType>" on Add a new recipient form
    When I enter "<recipientType>" details for "BUSINESS" recipient on Add a new recipient form
    When I save recipient details entered on Add a new recipient form
    Then I verify "<recipientType>" details in row "1" in Recipients grid on Payments & Recipients step
#    Then I verify "<recipientType>" recipient details in Payments & Recipients step on "Payments" page
    Then I calculate the payment details for the recipients added
    Then I verify payment details under checkout section in Payments & Recipients step on "Payments" page
    When I click "Checkout" button on "Payments" page
    Then I verify "Wallet" step is "Inactive" on "Payments" page
    Then I verify "Payments & Recipients" step is "Inactive" on "Payments" page
    Then I verify "Review & confirm" step is "Active" on "Payments" page
    Then I verify payment details in Review & confirm step on "Payments" page
##    Then I verify "<processingTime>" for the "<currency>" Payments
##    Then I verify settlement date in Review & confirm step on "Payments" page
    When I click "Confirm payment" button on "Payments" page
    Then I verify details on the Payment submitted page
    When I navigate to Dashboard page
    Then I navigate to Transactions tab page
    Then I search and verify payment for "<recipientType>" in the Transactions grid
    Then I open transaction details view of payment for "<recipientType>"
    Then I verify payment status is "Scheduled" on transaction details view
    Then I verify details of payment for "<recipientType>" on transaction details view
    When I close the transaction details view
    Then I navigate to Wallets tab page
    Then I verify available funds for the "<fromCurrency>" corridor
    When I'm Sign out from the Corp portal
    Given I'm Logged in to the Ops portal
#    Then I navigated to the "Transactions" page using left navigation
#    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
#    Then I navigate to Customer Overview page from the transaction details view
##    Then I search for customer by "Phone number" "<phone>" in the Customers page
    Then I open customer overview page of "Corporate Customers" registered with "Phone number" "<phone>"
    And I verify "Available" balance of "<fromCurrency>" wallet on the Customer Overview page
    When I navigate to "Wallet debits" tab page
#    Then I search for the transaction with "<reference>", "<type>" and "<status>"
    When I open payment details view for the payment of "<recipientType>" having "<transactionReference>"
    Then I verify payment details for "<recipientType>" on the transaction details view
#    Then I verify buttons "Available" on the transaction details view
#      |SUBMIT SINGLE PAYMENT|
#      |SUBMIT FULL BATCH|
#      |DECLINE|
#      |DOWNLOAD|
    When I submit "<recipientType>" payment to CAB for processing
    When I update data file after "<recipientType>" payment submitted to CAB
    Then I verify payment details for "<recipientType>" on the transaction details view
#    Then I verify buttons "Available" on the transaction details view
#      |Force Refresh Payment Statuses|
#      |DOWNLOAD|
#    Then I verify buttons "Not Available" on the transaction details view
#      |SUBMIT SINGLE PAYMENT|
#      |SUBMIT FULL BATCH|
#      |DECLINE|
    Then I navigate to Customer Overview page from the transaction details view
    When I update data file after "<recipientType>" payment processed by CAB
    And I verify "Available" balance of "<fromCurrency>" wallet after "<recipientType>" payment "COMPLETED"
    When I navigate to "Wallet debits" tab page
#    Then I search for the transaction with "<reference>", "<type>" and "<status>"
    When I open payment details view for the payment of "<recipientType>" having "<transactionReference>"
    Then I verify payment details for "<recipientType>" on the transaction details view
#
##    Then I verify buttons "Not Available" on the transaction details view
##      |SUBMIT SINGLE PAYMENT|
##      |SUBMIT FULL BATCH|
##      |DECLINE|
##      |Force Refresh Payment Statuses|
##    Then I verify buttons "Available" on the transaction details view
##      |DOWNLOAD|
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
#    Then I verify available funds for the "AED" currency after "<recipientType>" payment "COMPLETED"
#    And I verify available funds for the "<fromCurrency>" after "<recipientType>" payment "COMPLETED"
    Then I navigate to Transactions tab page
    Then I open transaction details view of payment for "<recipientType>"
    Then I verify payment status is "Completed" on transaction details view
    Then I verify details of payment for "<recipientType>" on transaction details view
    When I'm Sign out from the Corp portal
#    Given I'm Logged in to the Ops portal
#    Then I navigated to the "Transactions" page using left navigation
#    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
#    When I click "SETTLE" button on the transaction details view
#    Then I verify transaction "COMPLETED" on the transaction details view
#    Then I verify "<transactionType>" details on the transaction details view
#    Then I navigate to Customer Overview page from the transaction details view
#    And I verify "Available" balance of "AED" wallet after submitting Foreign Exchange to CAB
#    And I verify "Available" balance of "<currency>" wallet after submitting Foreign Exchange to CAB
#    Given I'm Logged in to the Corp portal
#    Then I verify Dashboard page is displayed
#    Then I verify available funds for the "<currency>" corridor after submitting Foreign Exchange to CAB
#    Then I verify available funds for the "AED" corridor after submitting Foreign Exchange to CAB
#    Then I navigate to Transactions tab page
#    Then I verify transaction status "Completed" in table and open transaction details view
#    Then I verify exchange transaction status is "Completed" on transaction details view
#    Then I verify details of exchange transaction on transaction details view
#    When I close the exchange transaction details view
#    When I'm Sign out from the Corp portal

    Examples:
      | fromCurrency | toCurrency | buyAmount | processingTime | phone          | transactionType  | recipientType|transactionReference|
      | AUD         | AUD         | 30.00     | Instant        | +91 7989730020 | Payment |  BUSINESS|                              |