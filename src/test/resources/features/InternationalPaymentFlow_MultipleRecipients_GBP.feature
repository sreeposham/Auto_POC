#@intlmultiplepayments @aud
Feature: End to End International payment flow with Multiple Recipients - GBP

  Background:
    Given I setup test data file name for "InternationalPayments_Multiple" "GBP" "60"

  @corp
  Scenario Outline: Verify the end to end flow of International payments with Multiple recipients for GBP corridor
    ###Login to Corporate Portal and Submit Local Payments for Business & Individual recipients###
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I Note the wallet balance of the "<fromCurrency>" and "<toCurrency>" on Dashboard page
    Then I navigate to International Payments page
    Then I verify default details on International payments page
#    Then I verify "ANITHA TEST BUSINESS Solutions Private Limited" company name and "Sreekanth j" full name on the page
    When I select "<toCurrency>" corridor on International Payments page
    When I select "2 business days" tenor on Exchange page
    Then I verify Live rate is displayed for "<toCurrency>" corridor
    Then I verify "<processingTime>" for the "<toCurrency>" Payments
    When I click "Next" button on "Payments" page
#    Then I verify default details in Payments & Recipients step on "Payments" page
    When I click "Add individual or business" button on "Payments" page
    Then I verify Add a new recipient form is displayed
    When I choose "BUSINESS" on Add a new recipient form
    When I enter "<recipientBusiness>" details for "BUSINESS" recipient on Add a new recipient form
    When I save recipient details entered on Add a new recipient form
    Then I verify "Recipient added" toast message is displayed
    Then I verify "<recipientBusiness>" details in row "1" in Recipients grid on Payments & Recipients step
    When I click "Add individual or business" button on "Payments" page
    When I choose "INDIVIDUAL" on Add a new recipient form
    When I enter "<recipientIndividual>" details for "INDIVIDUAL" recipient on Add a new recipient form
    When I save recipient details entered on Add a new recipient form
    Then I verify "Recipient added" toast message is displayed
    Then I verify "<recipientIndividual>" details in row "2" in Recipients grid on Payments & Recipients step
    Then I calculate the payment details for the recipients added for "International payments"
    Then I verify payment details under checkout section in Payments & Recipients step on "International Payments" page
    When I click "Checkout" button on "Payments" page
    Then I refresh rate and verify payment details in Review & confirm step for "International payments"
    When I "check" consent checkbox on "International payments" page
    When I click "Confirm Payment" button on "International Payments" page
    Then I verify details on the "International Payments" submitted page
    When I navigate to Dashboard page
    Then I navigate to Transactions tab page
    Then I verify and open Exchange details view of International payment from the Transactions grid
    Then I verify exchange transaction status is "Ordered" on transaction details view
    Then I verify details of exchange transaction on transaction details view
    When I close the transaction details view
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
    ##Login to Ops portal & Process the Exchange###
    Given I'm Logged in to the Ops portal
#    Then I navigated to the "Transactions" page using left navigation
#    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
#    Then I navigate to Customer Overview page from the transaction details view
#    Then I search for customer by "Phone number" "<phone>" in the Customers page
    Then I open customer overview page of "Corporate Customers" registered with "Phone number" "<phone>"
    And I verify "Available" balance of "<toCurrency>" wallet on the Customer Overview page
    And I verify "Available" balance of "<fromCurrency>" wallet on the Customer Overview page
    When I navigate to "Wallet debits" tab page
    When I open transaction details view of the "Foreign Exchange" transaction having "<transactionReference>"
    Then I verify transaction status is "Ordered" on the transaction details view
    Then I verify "Foreign Exchange" details on the transaction details view
    Then I verify "SETTLEMENT RECEIVED" button "Available" on the transaction details view
    When I click "SETTLEMENT RECEIVED" button on the transaction details view
    Then I verify "SETTLE" button "Available" on the transaction details view
    Then I verify transaction status is "ORDER_SETTLED" on the transaction details view
    Then I verify "Foreign Exchange" details on the transaction details view
    Then I navigate to Customer Overview page from the transaction details view
    And I verify "Available" balance of "<toCurrency>" wallet on the Customer Overview page
    Then I update wallet balance in test data file after submitting Foreign Exchange to CAB
#    And I verify "Available" balance of "<fromCurrency>" wallet after submitting Foreign Exchange to CAB
    And I verify "Available" balance of "<fromCurrency>" wallet on the Customer Overview page
    Then I navigated to the "Transactions" page using left navigation
    When I open transaction details view of the "Foreign Exchange" transaction having "<transactionReference>"
    When I click "SETTLE" button on the transaction details view
    Then I verify transaction status is "COMPLETED" on the transaction details view
    Then I update wallet balance in test data file after Foreign Exchange processed by CAB
    Then I verify "Foreign Exchange" details on the transaction details view
    Then I navigate to Customer Overview page from the transaction details view
    And I verify "Available" balance of "<fromCurrency>" wallet on the Customer Overview page
    And I verify "Available" balance of "<toCurrency>" wallet on the Customer Overview page
    ###Login to Ops portal & Process the Payments###
    When I navigate to "Wallet debits" tab page
    Then I verify "payment" details for "<recipientBusiness>" in the "Wallet debits" grid
    When I open payment details view for the payment of "<recipientBusiness>" having "<transactionReference>"
    Then I verify payment details for "<recipientBusiness>" on the transaction details view
    Then I verify buttons "Available" on the transaction details view
      |SUBMIT SINGLE PAYMENT|
      |SUBMIT FULL BATCH|
      |DOWNLOAD|
    When I submit "FULL BATCH" payment to CAB for processing
    Then I verify "Order has been updated successfully" toast message is displayed
    When I update data file after "FULL BATCH" recipient "International payments" submitted to CAB
    Then I verify payment details for "<recipientBusiness>" on the transaction details view
    Then I verify buttons "Available" on the transaction details view
      |Force Refresh Payment Statuses|
      |DOWNLOAD|
    Then I verify buttons "Not Available" on the transaction details view
      |SUBMIT SINGLE PAYMENT|
      |SUBMIT FULL BATCH|
      |DECLINE|
    Then I navigate to Customer Overview page from the transaction details view
    When I update data file after "FULL BATCH" recipient "International payments" processed by CAB
    And I verify "Available" balance of "<fromCurrency>" wallet after "<recipientBusiness>" payment "COMPLETED"
    When I navigate to "Wallet debits" tab page
    Then I verify "payment" details for "<recipientBusiness>" in the "Wallet debits" grid
    When I open payment details view for the payment of "<recipientBusiness>" having "<transactionReference>"
    Then I verify payment details for "<recipientBusiness>" on the transaction details view
    Then I verify buttons "Available" on the transaction details view
      |DOWNLOAD|
    Then I verify buttons "Not Available" on the transaction details view
      |SUBMIT SINGLE PAYMENT|
      |SUBMIT FULL BATCH|
      |DECLINE|
      |Force Refresh Payment Statuses|
    Then I navigate to Customer Overview page from the transaction details view
    When I navigate to "Wallet debits" tab page
    Then I verify "payment" details for "<recipientIndividual>" in the "Wallet debits" grid
    When I open payment details view for the payment of "<recipientIndividual>" having "<transactionReference>"
    Then I verify payment details for "<recipientIndividual>" on the transaction details view
    Then I verify buttons "Available" on the transaction details view
      |DOWNLOAD|
    Then I verify buttons "Not Available" on the transaction details view
      |SUBMIT SINGLE PAYMENT|
      |SUBMIT FULL BATCH|
      |DECLINE|
      |Force Refresh Payment Statuses|
    ###Login to Corporate portal and verify Wallet Balance & payment details###
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I navigate to Transactions tab page
    Then I verify transaction status "Completed" in table and open transaction details view
    Then I verify exchange transaction status is "Completed" on transaction details view
    Then I verify details of exchange transaction on transaction details view
    When I close the transaction details view
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
    Then I verify available funds for the "<toCurrency>" corridor
    When I'm Sign out from the Corp portal

    Examples:
      | fromCurrency | toCurrency |  processingTime | phone          |  recipientBusiness | recipientIndividual | transactionReference |
      | AED          | GBP        |  Within 1 business day   | +44 7710010001 |  BUSINESS          | INDIVIDUAL          |                      |