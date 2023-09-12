@payment @formValidation
Feature: Recipient form field level validations

  Background:
    Given I setup test data file name for "RecipientForm" "Fields" "Validation"

  @corp
  Scenario Outline: Verify the field level validation on the Recipient form for GBP currency
    Given I'm Logged in to the Corp portal
    Then I verify Dashboard page is displayed
    Then I navigate to Payments page
    When I select "<currency>" corridor on Payments page
    When I click "Next" button on "Payments" page
    When I click "Add individual or business" button on "Payments" page
    Then I verify Add a new recipient form is displayed
    When I choose "BUSINESS" on Add a new recipient form
    When I click "Next" button on "Payments" page
    Then I verify required field validation for "<currency>" corridor "BUSINESS" recipient on Add a new recipient form
    When I enter "<maxvalue>" details for "BUSINESS" recipient on Add a new recipient form
    When I click "Next" button on "Payments" page
    Then I verify maximum value validation for "<currency>" corridor "BUSINESS" recipient on Add a new recipient form
    When I enter "<minvalue>" details for "BUSINESS" recipient on Add a new recipient form
    When I click "Next" button on "Payments" page
    Then I verify minimum value validation for "<currency>" corridor "BUSINESS" recipient on Add a new recipient form
    When I enter "<specialchars>" details for "BUSINESS" recipient on Add a new recipient form
    When I click "Next" button on "Payments" page
    Then I verify special characters validation for "<currency>" corridor "BUSINESS" recipient on Add a new recipient form
    When I choose "INDIVIDUAL" on Add a new recipient form
    When I click "Next" button on "Payments" page
    Then I verify required field validation for "<currency>" corridor "INDIVIDUAL" recipient on Add a new recipient form
    When I enter "<maxvalue>" details for "INDIVIDUAL" recipient on Add a new recipient form
    When I click "Next" button on "Payments" page
    Then I verify maximum value validation for "<currency>" corridor "INDIVIDUAL" recipient on Add a new recipient form
    When I enter "<minvalue>" details for "INDIVIDUAL" recipient on Add a new recipient form
    When I click "Next" button on "Payments" page
    Then I verify minimum value validation for "<currency>" corridor "INDIVIDUAL" recipient on Add a new recipient form
    When I enter "<specialchars>" details for "INDIVIDUAL" recipient on Add a new recipient form
    When I click "Next" button on "Payments" page
    Then I verify special characters validation for "<currency>" corridor "INDIVIDUAL" recipient on Add a new recipient form
    When I click "Cancel" button on "Payments" page
    When I'm Sign out from the Corp portal



    Examples:
      | currency | maxvalue | minvalue | specialchars |
#      | GBP         | MAXIMUM-COMMON         | MINIMUM-COMMON    | SPECIALCHARS        |
      | AUD         | MAXIMUM-BANKCODE         | MINIMUM-BANKCODE    | SPECIALCHARS        |