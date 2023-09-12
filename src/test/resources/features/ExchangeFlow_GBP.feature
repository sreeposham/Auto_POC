@exchange @gbp @offer
Feature: End to End Exchange flow - GBP

  Background:
    Given I setup test data file name for "ForeignExchange" "GBP" "170"

  @corp
  Scenario Outline: Verify the end to end flow of Exchange flow for AUD corridor
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I Note the wallet balance of the "<buyCurrency>" and "AED" on Dashboard page
    Then I navigate to Exchange page
    Then I verify default details on Exchange page
    Then I verify "Amount" step is "Active" on "Exchange" page
    Then I verify "Review & confirm" step is "Inactive" on "Exchange" page
    When I select "<buyCurrency>" corridor and input "<buyAmount>" amount on Exchange page
    When I select "2 business days" tenor on Exchange page
    Then I verify Live rate is displayed for "<buyCurrency>" corridor
    Then I verify "<processingTime>" for the "<buyCurrency>" corridor
    When I click "Next" button on "Exchange" page
    When I "check" consent checkbox on Exchange page
    Then I refresh rate and verify exchange summary for "<buyCurrency>" corridor
    When I Confirm the Exchange transaction
    Then I verify details on Exchange Success modal popup
    When I click "Done" button on Exchange Success modal popup
    Then I verify Dashboard page is displayed
    Then I navigate to Transactions tab page
    Then I verify transaction status "Ordered" in table and open transaction details view
    Then I verify exchange transaction status is "Ordered" on transaction details view
    Then I verify details of exchange transaction on transaction details view
    When I close the transaction details view
    Then I navigate to Wallets tab page
    Then I verify available funds for the "<buyCurrency>" corridor
    Then I verify available funds for the "AED" corridor
    When I'm Sign out from the Corp portal
    Given I'm Logged in to the Ops portal
#    Then I navigated to the "Transactions" page using left navigation
#    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
#    Then I navigate to Customer Overview page from the transaction details view
##    Then I search for customer by "Phone number" "<phone>" in the Customers page
    Then I open customer overview page of "Corporate Customers" registered with "Phone number" "<phone>"
    And I verify "Available" balance of "<buyCurrency>" wallet on the Customer Overview page
    And I verify "Available" balance of "AED" wallet on the Customer Overview page
    When I navigate to "Wallet debits" tab page
#    Then I search for the transaction with "<reference>", "<type>" and "<status>"
    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
    Then I verify transaction status is "Ordered" on the transaction details view
    Then I verify "<transactionType>" details on the transaction details view
    Then I verify "SETTLEMENT RECEIVED" button "Available" on the transaction details view
    When I click "SETTLEMENT RECEIVED" button on the transaction details view
    Then I verify "SETTLE" button "Available" on the transaction details view
    Then I verify transaction status is "ORDER_SETTLED" on the transaction details view
    Then I verify "<transactionType>" details on the transaction details view
    Then I navigate to Customer Overview page from the transaction details view
    And I verify "Available" balance of "<buyCurrency>" wallet on the Customer Overview page
    Then I calculate and update wallet balance in test data file
    And I verify "Available" balance of "AED" wallet after submitting Foreign Exchange to CAB
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I verify available funds for the "AED" corridor after submitting Foreign Exchange to CAB
    And I verify available funds for the "<buyCurrency>" corridor
    Then I navigate to Transactions tab page
    Then I verify transaction status "Settled" in table and open transaction details view
    Then I verify exchange transaction status is "Order Settled" on transaction details view
    Then I verify details of exchange transaction on transaction details view
    When I close the transaction details view
    When I'm Sign out from the Corp portal
    Given I'm Logged in to the Ops portal
    Then I navigated to the "Transactions" page using left navigation
    When I open transaction details view of the "<transactionType>" transaction having "<transactionReference>"
    When I click "SETTLE" button on the transaction details view
    Then I verify transaction status is "COMPLETED" on the transaction details view
    Then I verify "<transactionType>" details on the transaction details view
    Then I navigate to Customer Overview page from the transaction details view
    And I verify "Available" balance of "AED" wallet after submitting Foreign Exchange to CAB
    And I verify "Available" balance of "<buyCurrency>" wallet after submitting Foreign Exchange to CAB
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I verify available funds for the "<buyCurrency>" corridor after submitting Foreign Exchange to CAB
    Then I verify available funds for the "AED" corridor after submitting Foreign Exchange to CAB
    Then I navigate to Transactions tab page
    Then I verify transaction status "Completed" in table and open transaction details view
    Then I verify exchange transaction status is "Completed" on transaction details view
    Then I verify details of exchange transaction on transaction details view
    When I close the transaction details view
    When I'm Sign out from the Corp portal

    Examples:
      | buyCurrency | buyAmount | processingTime | phone          | transactionType  | transactionReference |
      | GBP         | 170.00     | Instant        | +91 7989730020 | Foreign Exchange |                      |