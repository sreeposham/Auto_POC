@exchange1
Feature: Framework Sanity Testing
Background:
  Given I setup test data file name for "ForeignExchange" "EUR" "20"

  @corp
  Scenario Outline: Dashboard in the Corp portal
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I Note the wallet balance for the "<currency>" corridor
    Then I navigate to Exchange page
    Then I verify default details on Exchange page
    Then I verify "ANITHA TEST BUSINESS Solutions Private Limited" company name and "SRIKANTH PALLA BHANUMURTHY REDDY" full name on Exchange page
    Then I verify "Amount" step is "Active" on Exchange page
    Then I verify "Review & confirm" step is "Inactive" on Exchange page
    When I select "<currency>" corridor and input "<buyAmount>" amount on Exchange page
    Then I verify Live rate is displayed for "<currency>" corridor
    Then I verify "<processingTime>" for the "<currency>" corridor
    #Then I verify Settlement Date for the "<currency>" corridor
#    When I click "Next" button on Exchange page
##    Then I verify "Amount" step is "Inactive" on Exchange page
##    Then I verify "Review & confirm" step is "Active" on Exchange page
#    When I "check" consent checkbox on Exchange page
#    Then I review Exchange summary for "<currency>" corridor and "<buyAmount>" amount
#    #Then I create test data file with Exchange transaction details
#    When I Confirm the Exchange transaction
#    Then I verify details on Exchange Success modal popup
#    When I click "Done" button on Exchange Success modal popup
#    Then I verify Dashboard page is displayed
#    Then I navigate to Transactions page
#    Then I verify transaction status "Ordered" in table and open transaction details view
#    Then I verify exchange transaction status is "Ordered" on transaction details view
#    Then I verify details of exchange transaction on transaction details view
#    When I close the exchange transaction details view
#    When I'm Sign out from the Corp portal
##    When I'm Logged in to the Ops portal
##    Then I navigate to Transactions page
##    Then I search for the transaction with "<reference>", "<type>" and "<status>"
##    When I open transaction details view of the "<type>" transaction having "<reference>"
##    Then I verify transaction "<status>" on the transaction details view
##    Then I verify "<type>" details on the transaction details view
##    Then I verify "<type>" button "Available" on the transaction details view
##    When I click "" button on the transaction details view
##    Then I verify transaction "<status>" on the transaction details view
##    Then I verify "<type>" details on the transaction details view
##    Then I navigate to CustomerOverview page from the transaction details view
##    Then I verify "<currency>" wallet balance on the CustomerOverview page
##    Then I verify "<sellCurrency>" wallet balance on the CustomerOverview page
##    When I navigate to "Wallet Debits" tab page on the CustomerOverview page
##    When I'm Sign out from the Corp portal

    Examples:
      | currency                                | buyAmount          | processingTime |
      | EUR | 10.00 | Instant |

#  @e2e
#  Scenario Outline: Sign in to Corp portal
#    Given I'm Logged in to the Corp portal
#    When I'm Sign out from the Corp portal
#    When I'm Logged in to the Admin portal
#    When I'm Logged in to the Corp portal
#    And I'm Sign out from the Corp portal
#    Examples:
#      | url                                | username          | password |
#      | https://rise.fairsketch.com/signin | admin123@demo.com | riseDemo |