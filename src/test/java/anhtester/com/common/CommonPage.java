package anhtester.com.common;

import anhtester.com.helpers.PropertiesHelpers;
import anhtester.com.hubpay.corp.pages.DashboardPage;
import anhtester.com.hubpay.corp.pages.ExchangePage;
import anhtester.com.hubpay.corp.pages.LoginsPage;
import anhtester.com.hubpay.corp.pages.PaymentsPage;
import anhtester.com.hubpay.ops.pages.CustomerOverviewPage;
import anhtester.com.hubpay.ops.pages.CustomersPage;
import anhtester.com.hubpay.ops.pages.TransactionsPage;
import anhtester.com.keywords.WebUI;
import com.aventstack.extentreports.gherkin.model.ScenarioOutline;
import io.cucumber.java.Before;

import io.cucumber.java.Scenario;
import org.openqa.selenium.By;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import static com.aventstack.extentreports.gherkin.model.Scenario.getGherkinName;
import static io.cucumber.java.Scenario.*;

public class CommonPage {
    private static String checkfilenameaccess="";
    private By loginemailInputFld = By.cssSelector("input[name='loginfmt']");
    private By emailNextBtn = By.cssSelector("input[value='Next']");
    private By loginPwdInputFld = By.cssSelector("input[name='passwd']");
    private By pwdSignInBtn = By.cssSelector("input[value='Sign in']");
    private By signedInYesBtn = By.cssSelector("input[value='Yes']");
    private By corporateCustomers_leftmenu = By.xpath("//a/div[.='Corporate Customers']");

    public String temp_exchange_sellcurrencywalletbalance="";
    public String temp_exchange_buycurrencywalletbalance="";
    public Map<String, String> temp_exchange_map=new HashMap<>();
    public LoginsPage loginsPage;
    public DashboardPage dashboardPage;
    public ExchangePage exchangePage;
    public CustomersPage customersPage;
    public TransactionsPage transactionsPage;
    public PaymentsPage paymentsPage;

    public CustomerOverviewPage customerOverviewPage;
//    public DashboardPageCRM dashboardPage;
//    public ClientPageCRM clientPage;
//    public ProjectPageCRM projectPage;

//    public SignInPageCRM signOut() {
//        WebUI.clickElement(dropdownAccount);
//        WebUI.clickElement(buttonSignOut);
//        return new SignInPageCRM();
//    }

    public void setTestDataFileName(String trxnType,String corridor,String amount) {
        checkfilenameaccess="src/test/resources/testdata/" + trxnType+"_"+corridor+"_"+amount+".json";
    }
    public String getTestDataFile() {

//        System.out.println("ScenarioOutline.class.getName()"+ScenarioOutline.class.getName());
//        System.out.println("ScenarioOutline.class.getCanonicalName()"+ScenarioOutline.class.getCanonicalName());
//        System.out.println("ScenarioOutline.class.getSimpleName()"+ScenarioOutline.class.getSimpleName());
//        String scName= getGherkinName().replace(" ","_");
////        String scName= Scenario.class.getName().replace(" ","_");
//        System.out.println("Scenario name"+scName);
//
//        String fileName="src/test/resources/testdata/" + scName+".json";
//        return fileName;
        return checkfilenameaccess;
    }
    public void createTestDataFile(String fileName) {
        File file = new File(fileName); //filepath is being passes through //ioc         //and filename through a method

        if (file.exists()) {
            file.delete(); //you might want to check if delete was successfull
        }
        try {
            file.createNewFile();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
public void loginMicrosoft(String email, String password) {
    WebUI.setText(loginemailInputFld, email);
    WebUI.clickElement(emailNextBtn);
    WebUI.setText(loginPwdInputFld, password);
    WebUI.clickElement(pwdSignInBtn,20);
    WebUI.clickElement(signedInYesBtn,20);
    WebUI.waitForElementVisible(corporateCustomers_leftmenu);
}

    public void loginOpsPortal(String email, String password) {
        try {
            WebUI.smartWait();
            if(WebUI.verifyElementVisible(loginemailInputFld))
            loginMicrosoft(email, password);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        loginMicrosoft(email, password);
//        new CustomersPage();
    }
public void launchApplication(String app) {
    if (app.equals(null))
        return;
    switch (app.toUpperCase()) {
        case "CORP PORTAL":
            WebUI.getURL(PropertiesHelpers.getValue("URL_CORP"));
            break;
        case "OPS PORTAL":
            WebUI.getURL(PropertiesHelpers.getValue("URL_OPS"));
            break;
        default:
    }
}
    public String getValueFromMap(String key) {
        return temp_exchange_map.get(key);
    }

    public void setValueToMap(String key, String value) {
        this.temp_exchange_map.put(key, value);
    }
    public String getCurrencyName(String corridor) {
        String name="";
        switch(corridor.toUpperCase()) {
            case "GBP": name="British Pound"; break;
            case "EUR": name="Euro"; break;
            case "AUD": name="Australian Dollar"; break;
            case "CAD": name="Canadian Dollar"; break;
            case "AED": name="UAE Dirham"; break;
        }
        return name;
    }
    public String getRoundedValue(Double val) {
        return new DecimalFormat("0.00").format(val);
    }
    public LoginsPage getLoginsPage() {
        if (loginsPage == null) {
            loginsPage = new LoginsPage();
        }
        return loginsPage;
    }
    public DashboardPage getDashboardPage() {

        if (dashboardPage == null) {
            dashboardPage = new DashboardPage();
        }
        return dashboardPage;
    }
    public ExchangePage getExchangePage() {

        if (exchangePage == null) {
            exchangePage = new ExchangePage();
        }
        return exchangePage;
    }
    public CustomersPage getCustomersPage() {
        if (customersPage == null) {
            customersPage = new CustomersPage();
        }
        return customersPage;
    }
    public CustomerOverviewPage getCustomerOverviewPage() {
        if (customerOverviewPage == null) {
            customerOverviewPage = new CustomerOverviewPage();
        }
        return customerOverviewPage;
    }
    public TransactionsPage getTransactionsPage() {

        if (transactionsPage == null) {
            transactionsPage = new TransactionsPage();
        }
        return transactionsPage;
    }
    public PaymentsPage getPaymentsPage() {
        if (paymentsPage == null) {
            paymentsPage = new PaymentsPage();
        }
        return paymentsPage;
    }
//
//    public ClientPageCRM getClientPage() {
//        if (clientPage == null) {
//            clientPage = new ClientPageCRM();
//        }
//        return clientPage;
//    }
//
//    public ProjectPageCRM getProjectPage() {
//        if (projectPage == null) {
//            projectPage = new ProjectPageCRM();
//        }
//        return projectPage;
//    }

}
