package anhtester.com.hubpay.ops.stepdefinitions;

import anhtester.com.common.CommonPage;
import anhtester.com.helpers.JsonHelpers;
import anhtester.com.keywords.WebUI;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CustomersPageStepDefinition extends CommonPage {

	private String fileName="";
	private Map<String,Map<String,String>> local_data_map;
	public CustomersPageStepDefinition() throws IOException {
		fileName=getTestDataFile();
//		System.out.println("DashboardPageStepDefinition:"+CommonPage.checkfilenameaccess);
		try {
			local_data_map= JsonHelpers.readFromJsonFile(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@When("I navigated to the {string} page using left navigation")
	public void i_navigated_to_the_page(String menu) throws InterruptedException {
		getCustomersPage().clickLeftMenuItem(menu);
	}

	@When("I search for customer by {string} {string} in the Customers page")
	public void i_search_customer_by_in_the_customers_page(String searchBy, String value) throws InterruptedException {
		// Write code here that turns the phrase above into concrete actions
		getCustomersPage().searchForCustomer(searchBy,value);
		getCustomersPage().clickSearch();
	}

	@Then("I verify customer registered with {string} in the Customers page")
	public void i_verify_customer_registered_with_in_the_customers_page(String string) {
		// Write code here that turns the phrase above into concrete actions
//	    throw new io.cucumber.java.PendingException();
	}

	@When("I open customer overview page of {string} registered with {string} {string}")
	public void i_view_the_customer_overview_page_of_registered_with(String customerType,String searchBy, String value) throws InterruptedException {
		// Write code here that turns the phrase above into concrete actions
		getCustomersPage().clickLeftMenuItem(customerType);
		getCustomersPage().searchForCustomer(searchBy,value);
		getCustomersPage().clickSearch();
		if(searchBy.equalsIgnoreCase("Phone number")){
			value=value.replace(" ","");
		}
		getCustomersPage().clickCustomerRecord(searchBy,value);
		getCustomersPage().verifyLaunchCustomerOverview();
	}
}
