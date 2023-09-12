package anhtester.com.common;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CommonSteps extends CommonPage{
//    String email = "corp-demo-1@hubpay.ae";
//    String password = "CorpDemo123!";
    String email = "sreekanth@hubpay.ae";
    String password = "Passhub@1";
//    String email = "cadlocal@hubpay.ae";
//    String password = "Autopass@1";
    String ops_email = "sreekanth@hubpay.ae";
    String ops_password = "Passhub@1";
    @Given("I setup test data file name for {string} {string} {string}")
    public void I_create_testdata_file_for(String trxnType,String corridor,String amount) throws InterruptedException {
        setTestDataFileName(trxnType,corridor,amount);
    }
    @Given("I'm Logged in to the Ops portal")
    public void i_m_logged_in_to_the_admin_portal() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        launchApplication("Ops Portal");
//        loginMicrosoft(email, password);
        loginOpsPortal(ops_email, ops_password);
//		Thread.sleep(50000);
    }
    @Given("I landed on Corp portal Login Page")
	public void I_landed_on_CorpSuite_Login_Page() throws InterruptedException {
		launchApplication("Corp Portal");
	}

	@Given("I'm Logged in to the Corp portal")
	public void I_enter_credentials_on_CorpSuite_Login_Page() throws InterruptedException {
        launchApplication("Corp Portal");
        getLoginsPage().loginCorpPortal(email, password);
	}
    @When("I'm Sign out from the Corp portal")
    public void I_signout_corp_portal() throws InterruptedException {
        launchApplication("Corp Portal");
        getLoginsPage().signOutCorpPortal();
    }
}
