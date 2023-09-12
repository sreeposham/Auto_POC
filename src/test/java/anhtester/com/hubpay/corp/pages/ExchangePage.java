package anhtester.com.hubpay.corp.pages;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import anhtester.com.common.CommonPage;
import anhtester.com.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


public class ExchangePage extends CommonPage {
	WebDriver driver;
	public Map<String, Map<String, String>> exchangeData=new HashMap<String,Map<String, String>>();
	public ExchangePage() {
//		super(driver);
//		this.driver = driver;
////		this.dynamicDataMap=
//		PageFactory.initElements(driver, this);
	}
	

	private By title_Exchange=By.xpath("//h1[text()='Exchange']");

	private By titleDesc_Exchange=By.xpath("//p[text()='Convert money between your wallets']");

	private By label_ReviewConfirmStep=By.xpath("//span[text()='Review & confirm']/preceding-sibling::span[text()='2']/parent::span");
	private By label_Payments_PaymentsRecipients=By.xpath("//span[text()='Payments & Recipients']/preceding-sibling::span[text()='2']/parent::span");
	private By label_Payments_SubTitle=By.xpath("//h1[text()='Add the recipients & the amounts you wish to send']");
	private By label_Payments_ReviewConfirmStep=By.xpath("//span[text()='Review & confirm']/preceding-sibling::span[text()='3']/parent::span");

	private By label_AmountStep=By.xpath("//span[text()='Amount']/preceding-sibling::span[text()='1']/parent::span");

	private By title_AmountStep=By.xpath("//h1[text()='Amount']");
	private By title_ReviewStep=By.xpath("//h1[text()='Review & confirm']");

	private By titleDesc_AmountStep=By.xpath("//h1[text()='Amount']/following-sibling::p[text()='Select the wallet you want to exchange to and define the amount']");

	private By buySelectBtn=By.xpath("//button[@role='combobox']/following-sibling::select");

	private By buySelectDropDown=By.xpath("//button[@role='combobox']/following-sibling::select");

	private By defaultSellCurrency=By.xpath("//label[text()='Sell']/following-sibling::div/input[@placeholder='0.00']/following-sibling::div[.='AED']");

	private By buyAmountInput=By.xpath("//input[@placeholder='Enter amount']");

	private By buyCurrency=By.xpath("//button/span");

	private By sellCurrency=By.xpath("//input[@id='sell']/following-sibling::div");

	private By sellAmountInput=By.xpath("//input[@id='sell']");

	private By liveRateValue=By.xpath("//div[text()='Live rate']/preceding-sibling::div//div");

	private By processingTimeValue=By.xpath("//div[text()='Processing time']/preceding-sibling::div");
	private By review_ProcessingTimeValue=By.xpath("//div[contains(.,'Processing time')][@class='flex items-center']/preceding-sibling::div");

	private By settlementDateVal=By.xpath("//div[contains(text(),'Settlement date')]/span");
	

	private By btnNext=By.xpath("//button[.='Next']");
	private By btnConfirmExchange=By.xpath("//button[.='Confirm Exchange']");


	public void selectWallet(String wallet) {
		WebUI.selectOptionByValue(buySelectDropDown, wallet);
	}

	public void clickConfirmExchangeBtn() {
		WebUI.clickElement(btnConfirmExchange);
	}
	public void clickBtn(String btn, String trxnType) {
		By paymentBtn = By.xpath("//button[contains(.,'" + btn + "')][contains(@class,'text-primary-foreground')]");
		if (!(btn.equalsIgnoreCase("Next")|btn.equalsIgnoreCase("Add individual or business"))) {
			WebUI.waitForElementVisible(paymentBtn);
			WebUI.clickElement(paymentBtn);
		} else if(btn.equalsIgnoreCase("Add individual or business")){
			paymentBtn = By.xpath("//button[contains(.,'" + btn + "')][contains(@class,'ring-offset-background bg-violet')]");
			WebUI.waitForElementVisible(paymentBtn);
			WebUI.clickElement(paymentBtn);
		}
		else {
			WebUI.waitForElementClickable(paymentBtn,5000);
			WebUI.clickElementWithJs(paymentBtn);
			try {
				if (trxnType.equalsIgnoreCase("Exchange") | trxnType.equalsIgnoreCase("Foreign Exchange")) {
					WebUI.waitForElementVisible(title_ReviewStep);
				} else if (trxnType.equalsIgnoreCase("Payments") | trxnType.equalsIgnoreCase("International Payments")) {

					WebUI.waitForElementVisible(label_Payments_SubTitle);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void verifyStepStatusExchangePage(String stepName, String stepStatus,String trxnType) {

		if(trxnType.equalsIgnoreCase("Exchange")|trxnType.equalsIgnoreCase("Foreign Exchange")){
		switch (stepName.toUpperCase()) {
		case "AMOUNT":
			WebUI.verifyElementVisible(label_AmountStep,30,"Amount breadcrumb is not available");
			validateStepStatus(label_AmountStep, stepStatus);
			break;
		case "REVIEW & CONFIRM":
			WebUI.smartWait();
			WebUI.verifyElementVisible(label_ReviewConfirmStep,30,"Review & Confirm breadcrumb is not available");
			validateStepStatus(label_ReviewConfirmStep, stepStatus);
			break;
		}}else if(trxnType.equalsIgnoreCase("Payments")|trxnType.equalsIgnoreCase("International Payments")){
			switch (stepName.toUpperCase()) {
				case "AMOUNT":
					WebUI.verifyElementVisible(label_AmountStep,30,"Amount breadcrumb is not available");
					validateStepStatus(label_AmountStep, stepStatus);
					break;
				case "PAYMENTS & RECIPIENTS":
					WebUI.verifyElementVisible(label_Payments_PaymentsRecipients,30,"Payments & Recipients breadcrumb is not available");
					validateStepStatus(label_Payments_PaymentsRecipients, stepStatus);
					break;

				case "REVIEW & CONFIRM":
					WebUI.smartWait();
					WebUI.verifyElementVisible(label_Payments_ReviewConfirmStep,30,"Review & Confirm breadcrumb is not available");
					validateStepStatus(label_Payments_ReviewConfirmStep, stepStatus);
					break;
			}
		}
	}

	public boolean checkStepIsActive(By by) {
		boolean flag = false;
		WebUI.waitForElementVisible(by);
		if(WebUI.getAttributeElement(by,"class").contains("opacity-50"))
		{
			flag = false;
		} else
			flag = true;
		return flag;
	}

	public void validateStepStatus(By ele, String status) {
		if (status.equalsIgnoreCase("Active")) {
			WebUI.verifyTrue(checkStepIsActive(ele),"Breadcrumb is not active");
		} else if (status.equalsIgnoreCase("Inactive")) {
			WebUI.verifyTrue(!checkStepIsActive(ele),"Breadcrumb is not Inactive");
		}

	}
	public void defaultDetailsExchangePage() {
		WebUI.verifyElementVisible(title_Exchange,"Exchange title is not visible");
		WebUI.verifyElementVisible(titleDesc_Exchange,"Exchange title description is not visible");
		WebUI.verifyElementVisible(label_AmountStep,"Amount breadcrumb is not visible");
		WebUI.verifyElementVisible(label_ReviewConfirmStep,"Review & confirm breadcrumb is not visible");
		WebUI.verifyElementVisible(title_AmountStep,"Amount section title is not visible");
		WebUI.verifyElementVisible(titleDesc_AmountStep,"Amount section title description is not visible");
		WebUI.verifyElementVisible(buySelectBtn,"Buy currency default value is not 'Select'");
		WebUI.verifyElementVisible(defaultSellCurrency,"Sell currency default value is not AED");
		WebUI.verifyTrue(!WebUI.getWebElement(btnNext).isEnabled(),"Next button is enabled");
	}

	
	
	public void verifyCompanyUserDetails(String company, String user) {
		By companytitle=By.xpath("//span[.='"+company+"']");
		WebUI.verifyElementVisible(companytitle,"Company name '"+company+"' is not visible");
		By by=By.xpath("//p[.='Hello, "+user+"']");
		WebUI.verifyElementVisible(by,"User name '"+user+"' is not visible");
	}

	public void selectAndEnterBuyAmount(String corridor, String amount) {
		selectWallet(corridor);
		WebUI.smartWait();
		WebUI.clearAndFillText(buyAmountInput,amount);
		if(corridor.equalsIgnoreCase("eur")) {
			WebUI.clearText(buyAmountInput);
			WebUI.setText(buyAmountInput,amount);
		}
		
	}

	public void verifyLiveRateDisplayed(String corridor) {
		WebUI.waitForElementVisible(liveRateValue,5);
		WebUI.verifyElementVisible(liveRateValue,"Live rate is not displayed");
		switch(corridor.toUpperCase()){
			case "AUD":
			case "CAD":
			case "CHF":
			case "EUR":
			case "GBP":
			case "NZD":
			case "NOK":
			case "SEK":
			case "SGD":
				WebUI.verifyElementTextContains(liveRateValue,"1 "+corridor+" = ");
				break;
			case "DKK":
			case "JPY":
			case "CNY":
			case "INR":
			case "ZAR":
				WebUI.verifyElementTextContains(liveRateValue,"1 AED = ");
				break;
		}

	}
	public String getCalculatedFromAmountValue(String corridor, Double toAmount) {
		Double fromAmount=null;
		Double rateValue=Double.parseDouble(WebUI.getTextElement(review_RateVal).split(" ")[3]);
		switch(corridor.toUpperCase()){
			case "AUD":
			case "CAD":
			case "CHF":
			case "EUR":
			case "GBP":
			case "NZD":
			case "NOK":
			case "SEK":
			case "SGD":
				fromAmount=Math.round((rateValue*toAmount) * 100.0) / 100.0;
				break;
			case "DKK":
			case "CNY":
			case "INR":
			case "ZAR":
			case "JPY":
				fromAmount=Math.round((toAmount/rateValue) * 100.0) / 100.0;
				break;
		}
		return String.format("%.2f",fromAmount);

	}
	public void verifyProcessingTime(String procTime, String corridor) {
		WebUI.verifyElementTextEquals(processingTimeValue, procTime);
	}
	public void review_verifyProcessingTime(String procTime) {
		WebUI.verifyElementTextEquals(review_ProcessingTimeValue, procTime);
	}
	public Map<String,Map<String,String>> getDetailsForReview(Map<String,Map<String,String>> temp_exchange_data){
		String buyAmount="";
//		exchangeData.put("corridor",WebUI.getAttributeElement(buyCurrency,"innerText").trim());
//		WebUI.verifyTrue(exchangeData.put("toCurrencyName", getCurrencyName(WebUI.getAttributeElement(buyCurrency,"innerText").trim()));
//		exchangeData.put("toCurrencyCode", WebUI.getAttributeElement(buyCurrency,"innerText").trim());
		buyAmount=WebUI.getAttributeElement(buyAmountInput,"value").toString().trim();
		if(WebUI.getAttributeElement(buyCurrency,"innerText").trim().contentEquals("EUR")){

			buyAmount=buyAmount.substring(0,buyAmount.length()-1);
//			System.out.println("after replace:'"+buyAmount+"'");
//			System.out.println("getText:'"+buyAmountInput.getText().trim()+"'");
//			System.out.println("After append"+buyAmount);
		}
		if(!buyAmount.contains(".")){
			buyAmount=buyAmount+".00";
		}
//		exchangeData.put("buyAmount",buyAmount);
//		exchangeData.put("sellCurrencyName", getCurrencyName(WebUI.getAttributeElement(sellCurrency,"innerText").trim()));
//		exchangeData.put("sellCurrencyCode", WebUI.getAttributeElement(sellCurrency,"innerText").trim());
//		System.out.println("Retrieve Sell amount:"+sellAmountInput.getAttribute("value").toString().replaceFirst("\\s", ""));
		temp_exchange_data.get("COMMON").put("liveRate", WebUI.getTextElement(liveRateValue));
		temp_exchange_data.get("EXCHANGE").put("fromAmount", temp_exchange_data.get("COMMON").get("fromCurrencyCode")+" "+calculatedSellAmount(buyAmount));
		temp_exchange_data.get("COMMON").put("settlementDate", WebUI.getTextElement(settlementDateVal));
//		exchangeData.get("COMMON").put("processingTime",WebUI.getTextElement(processingTimeValue));
//		String formatted_settlementDate="";
//		String dd_settlementDate=WebUI.getTextElement(settlementDateVal).split(" ")[0];
//		if (dd_settlementDate.contains("rd")) {
//			dd_settlementDate = dd_settlementDate.replace("rd", "");
//		} else if (dd_settlementDate.contains("st")) {
//			dd_settlementDate = dd_settlementDate.replace("st", "");
//		} else if (dd_settlementDate.contains("nd")) {
//			dd_settlementDate = dd_settlementDate.replace("nd", "");
//		} else if (dd_settlementDate.contains("th")) {
//			dd_settlementDate = dd_settlementDate.replace("th", "");
//		}
//		formatted_settlementDate=dd_settlementDate+" "+WebUI.getTextElement(settlementDateVal).split(" ")[1].trim()+" "+WebUI.getTextElement(settlementDateVal).split(" ")[2].trim();
//		System.out.println("formatted_settlementDate"+formatted_settlementDate);
//		temp_exchange_data.get("COMMON").put("formatted_settlementDate", formatted_settlementDate);
		return temp_exchange_data;
	}
	public Map<String,Map<String,String>> calculateExchangeSummaryOnReviewConfirm(Map<String,Map<String,String>> temp_exchange_data){
		Double buyAmount=null;
		buyAmount=Double.parseDouble(temp_exchange_data.get("EXCHANGE").get("toAmount"));
		temp_exchange_data.get("COMMON").put("liveRate", WebUI.getTextElement(review_RateVal));
		temp_exchange_data.get("EXCHANGE").put("fromAmount", temp_exchange_data.get("COMMON").get("fromCurrencyCode")+" "+getCalculatedFromAmountValue(temp_exchange_data.get("COMMON").get("toCurrencyCode"),buyAmount));
		temp_exchange_data.get("COMMON").put("totalPayable", temp_exchange_data.get("EXCHANGE").get("fromAmount"));
//		temp_exchange_data.get("EXCHANGE").put("balanceDue", temp_exchange_data.get("EXCHANGE").get("fromAmount"));
		return temp_exchange_data;
	}
	public String calculatedSellAmount(String buyAmount) {
		double buyAmountDouble=Double.parseDouble(buyAmount);
		double rateVal=Double.parseDouble(WebUI.getTextElement(liveRateValue).split(" ")[3]);
		System.out.println("calculated sell amount:"+getRoundedValue(buyAmountDouble*rateVal));
		return getRoundedValue(buyAmountDouble*rateVal);
	}

	private By review_BuyVal=By.xpath("//div[text()='Buy']/following-sibling::div");

	private By review_RateVal=By.xpath("//div[contains(text(),' Rate:')]/following-sibling::div");
	private By review_OfferRateLabel=By.xpath("//div[text()='x Rate:']");
	private By review_BidRateLabel=By.xpath("//div[text()='÷ Rate:']");
	private By review_SellVal=By.xpath("//div[contains(text(),' Sell:')]/following-sibling::div");

	private By review_TotalpayableVal=By.xpath("//div[.='Total payable:']/following-sibling::div");

	private By review_AvailableFromWalletBalanceVal=By.xpath("//div[contains(.,'Available from ') and contains(.,'allet balance:')]/following-sibling::div");

	private By review_BalanceDueVal=By.xpath("//div[.='Balance due:']/following-sibling::div");
	private By review_BalanceDueDate=By.xpath("//div[.='Balance due date']/preceding-sibling::div");
	private By review_CheckboxLabel=By.xpath("//button[@id='confirm']/following-sibling::label");

	private By review_Checkbox=By.xpath("//button[@id='confirm']");
	private By review_CheckboxText=By.xpath("//button[@id='confirm']/following-sibling::label");
	private By review_ToCurrencySection=By.xpath("//*[@data-icon='arrowRight']/following-sibling::span//div[contains(@class,'items-start')]");
	private By review_FromCurrencySection=By.xpath("//*[@data-icon='arrowRight']/preceding-sibling::span//div[contains(@class,'items-start')]");
	public void verifyStaticDetailsOnReviewExchangeSummary(Map<String, Map<String, String>> temp_exchange_map) {
		verifyStaticDetailsOnReviewScreen(temp_exchange_map);
		WebUI.verifyElementTextEquals(review_BuyVal,temp_exchange_map.get("COMMON").get("toCurrencyCode").trim()+" "+temp_exchange_map.get("EXCHANGE").get("toAmount").trim());
		WebUI.verifyElementTextEquals(review_CheckboxText, "I confirm & agree to pay the total balance due to Hubpay Limited before "+temp_exchange_map.get("COMMON").get("settlementDate"));
	}
	public void verifyStaticDetailsOnReviewInternationPaymentsSummary(Map<String, Map<String, String>> temp_exchange_map) {
		verifyStaticDetailsOnReviewScreen(temp_exchange_map);
		WebUI.verifyElementTextContains(review_CheckboxText, "I confirm & accept this rate. I agree to pay the total pending funds to Hubpay Limited before");
		WebUI.verifyElementTextContains(review_CheckboxText, temp_exchange_map.get("COMMON").get("settlementDate").trim());
	}
	public void verifyStaticDetailsOnReviewScreen(Map<String, Map<String, String>> temp_exchange_map) {
		String actText=WebUI.getTextElement(review_ToCurrencySection);
		WebUI.verifyTrue(actText.contains(temp_exchange_map.get("COMMON").get("toCurrencyCode"))&actText.contains(temp_exchange_map.get("COMMON").get("toCurrencyName")));
		actText=WebUI.getTextElement(review_FromCurrencySection);
		WebUI.verifyTrue(actText.contains(temp_exchange_map.get("COMMON").get("fromCurrencyCode"))&actText.contains(temp_exchange_map.get("COMMON").get("fromCurrencyName")));
		WebUI.verifyElementTextEquals(review_ProcessingTimeValue, temp_exchange_map.get("COMMON").get("processingTime"));
		WebUI.verifyElementTextEquals(review_BalanceDueDate, temp_exchange_map.get("COMMON").get("settlementDate"));
		}
	public void verifyExchangeType(String corridor){
		switch(corridor.toUpperCase()){
			case "AUD":
			case "CAD":
			case "CHF":
			case "EUR":
			case "GBP":
			case "NZD":
			case "NOK":
			case "SEK":
			case "SGD":
WebUI.verifyElementVisible(review_OfferRateLabel);
				break;
			case "DKK":
			case "CNY":
			case "INR":
			case "ZAR":
			case "JPY":
				WebUI.verifyElementVisible(review_BidRateLabel);
				break;
		}
	}
	public void verifyCalculatedValuesOnReviewExchangeSummary(Map<String, Map<String, String>> temp_exchange_map) {
//		WebUI.verifyElementTextEquals(review_RateVal,temp_exchange_map.get("COMMON").get("liveRate"));
		WebUI.verifyElementTextEquals(review_SellVal,temp_exchange_map.get("EXCHANGE").get("fromAmount"));
		WebUI.verifyElementTextEquals(review_TotalpayableVal,temp_exchange_map.get("EXCHANGE").get("fromAmount"));
		verifyAvailableAndBalanceDueOnReviewScreen(temp_exchange_map);
	}
	public void reviewExchangeSummaryBeforeExpired(Map<String, Map<String, String>> temp_exchange_map) {
		System.out.println(temp_exchange_map);
		//verify currency indication section

		String actText=WebUI.getTextElement(review_ToCurrencySection);
		WebUI.verifyTrue(actText.contains(temp_exchange_map.get("COMMON").get("toCurrencyCode"))&actText.contains(temp_exchange_map.get("COMMON").get("toCurrencyName")));
		actText=WebUI.getTextElement(review_FromCurrencySection);
		WebUI.verifyTrue(actText.contains(temp_exchange_map.get("COMMON").get("fromCurrencyCode"))&actText.contains(temp_exchange_map.get("COMMON").get("fromCurrencyName")));
		WebUI.verifyElementTextEquals(review_BuyVal,temp_exchange_map.get("COMMON").get("toCurrencyCode").trim()+" "+temp_exchange_map.get("EXCHANGE").get("toAmount").trim());
		WebUI.verifyElementTextEquals(review_RateVal,temp_exchange_map.get("COMMON").get("liveRate"));
		WebUI.verifyElementTextEquals(review_SellVal,temp_exchange_map.get("EXCHANGE").get("fromAmount"));
		WebUI.verifyElementTextEquals(review_TotalpayableVal,temp_exchange_map.get("EXCHANGE").get("fromAmount"));
		calculateAvailableBalanceLogic(temp_exchange_map.get("COMMON").get("fromCurrencyWalletBalance"));
		WebUI.verifyElementTextEquals(review_ProcessingTimeValue, temp_exchange_map.get("COMMON").get("processingTime"));
		WebUI.verifyElementTextEquals(review_BalanceDueDate, temp_exchange_map.get("COMMON").get("settlementDate"));
		WebUI.verifyElementTextEquals(review_CheckboxText, "I confirm & agree to pay the total balance due to Hubpay Limited before "+temp_exchange_map.get("COMMON").get("settlementDate"));
	}
	public void verifyAvailableAndBalanceDueOnReviewScreen(Map<String, Map<String, String>> temp_exchange_map) {
		String currencyCode=temp_exchange_map.get("COMMON").get("fromCurrencyCode");
		String balValue=temp_exchange_map.get("COMMON").get("fromCurrencyWalletBalance").split(" ")[1].trim().replace(",", "");
		Double totalPayableVal=Double.parseDouble(temp_exchange_map.get("EXCHANGE").get("fromAmount").split(" ")[1].trim());
		if(totalPayableVal < Double.valueOf(balValue)) {
			WebUI.verifyTrue(WebUI.getTextElement(review_AvailableFromWalletBalanceVal).contentEquals("-"+temp_exchange_map.get("EXCHANGE").get("fromAmount")));
			WebUI.verifyTrue(WebUI.getTextElement(review_BalanceDueVal).contentEquals(currencyCode+" 0.00"));
		}else if(totalPayableVal > Double.valueOf(balValue)) {
			WebUI.verifyTrue(WebUI.getTextElement(review_AvailableFromWalletBalanceVal).contentEquals("-"+temp_exchange_map.get("COMMON").get("fromCurrencyWalletBalance")));
			Double balDue=Double.valueOf(temp_exchange_map.get("EXCHANGE").get("fromAmount").split(" ")[1]) - Double.valueOf(balValue);
			WebUI.verifyTrue(WebUI.getTextElement(review_BalanceDueVal).contentEquals(currencyCode+" "+String.format("%.2f",balDue)));
		}
	}
	public void calculateAvailableBalanceLogic(String preBalance) {
		String currencyCode=preBalance.split(" ")[0].trim();;
		String balValue=preBalance.split(" ")[1].trim().replace(",", "");
		Double totalPayableVal=Double.valueOf(WebUI.getTextElement(review_TotalpayableVal).split(" ")[1]);
		String str_totalPayableVal =String.format("%.2f",totalPayableVal);
		if(totalPayableVal < Double.valueOf(balValue)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("act:'"+WebUI.getTextElement(review_AvailableFromWalletBalanceVal)+"'");
			System.out.println("Exp:'"+"-"+currencyCode+" "+str_totalPayableVal+"'");
			System.out.println(String.format("%.2f",totalPayableVal));
			WebUI.verifyTrue(WebUI.getTextElement(review_AvailableFromWalletBalanceVal).contentEquals("-"+currencyCode+" "+str_totalPayableVal));
			System.out.println("act:"+WebUI.getTextElement(review_BalanceDueVal)+"'");
			System.out.println("Exp:'"+currencyCode+" 0.00");
			WebUI.verifyTrue(WebUI.getTextElement(review_BalanceDueVal).contentEquals(currencyCode+" 0.00"));
		}else if(totalPayableVal > Double.valueOf(balValue)) {
			WebUI.verifyTrue(WebUI.getTextElement(review_AvailableFromWalletBalanceVal).contentEquals("-"+preBalance));
			Double balDue=Double.valueOf(WebUI.getTextElement(review_TotalpayableVal).split(" ")[1]) - Double.valueOf(balValue);
			WebUI.verifyTrue(WebUI.getTextElement(review_BalanceDueVal).contentEquals(currencyCode+" "+String.format("%.2f",balDue)));
		}
	}
	public Map<String, Map<String, String>> getAvailableBalanceDetails(Map<String, Map<String, String>> temp_exchange_map) {
		String currencyCode=temp_exchange_map.get("COMMON").get("fromCurrencyCode");
		Double balValue=Double.valueOf(temp_exchange_map.get("COMMON").get("fromCurrencyWalletBalance").split(" ")[1].trim().replace(",", ""));
		Double totalPayableVal=Double.valueOf(temp_exchange_map.get("COMMON").get("totalPayable").split(" ")[1].trim().replace(",", ""));
		String str_totalPayableVal =String.format("%.2f",totalPayableVal);
		if(totalPayableVal < balValue) {
			temp_exchange_map.get("COMMON").put("availableFromWalletBalance",currencyCode+" "+str_totalPayableVal);
			temp_exchange_map.get("COMMON").put("balanceDue",currencyCode+" 0.00");
		}else if(totalPayableVal > balValue) {
			temp_exchange_map.get("COMMON").put("availableFromWalletBalance",temp_exchange_map.get("COMMON").get("fromCurrencyWalletBalance"));
			Double balDue=totalPayableVal - balValue;
			temp_exchange_map.get("COMMON").put("balanceDue",currencyCode+" "+String.format("%.2f",balDue));
		}
		return temp_exchange_map;
	}
	public void clickReviewConfirmCheckbox(String status) {
		WebUI.clickElement(review_Checkbox);
	}
	public void verifyConsentCheckboxStatus(String strStatus){
		WebUI.verifyTrue(WebUI.getAttributeElement(review_CheckboxLabel,"data-state").equalsIgnoreCase(strStatus),"Consent checkbox is not "+strStatus);
	}
	public void verifyTextConsentInfoBox(String settlementDate){
		WebUI.verifyTrue(WebUI.getAttributeElement(review_CheckboxLabel,"innerText").contentEquals("I confirm & agree to pay the total balance due to Hubpay Limited before "+settlementDate));
	}
	private By modal_SuccessTitle=By.xpath("//div[@role='dialog']//h2[text()='Success!']");

	private By modal_SuccessTitleDesc=By.xpath("//div[@role='dialog']//p[text()='Please ensure you add sufficient funds to your AED wallet by the settlement date']");

	private By modal_TotalAmtConvertedVal=By.xpath("//div[@role='dialog']//p[text()='Total amount converted']/following-sibling::p");

	private By modal_SettlementDateVal=By.xpath("//div[@role='dialog']//p[text()='Settlement due date']/following-sibling::p");

	private By modal_TransactionReferenceVal=By.xpath("//div[@role='dialog']//p[text()='Transaction reference']/following-sibling::p");

	private By modal_DoneBtn=By.xpath("//div[@role='dialog']//button[text()='Done']");
	public void verifyDetailsOnSuccessModal(Map<String, Map<String, String>> temp_exchange_map) {
		try {
			WebUI.waitForElementVisible(modal_SuccessTitle);
			WebUI.waitForElementVisible(modal_SuccessTitle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebUI.verifyElementVisible(modal_SuccessTitle,"Success tile is not visible on Modal");
		WebUI.verifyElementVisible(modal_SuccessTitleDesc,"Success tile description is not visible on Modal");
		WebUI.verifyElementTextEquals(modal_TotalAmtConvertedVal,temp_exchange_map.get("EXCHANGE").get("fromAmount")+" = "+temp_exchange_map.get("COMMON").get("toCurrencyCode").trim()+" "+temp_exchange_map.get("EXCHANGE").get("toAmount").trim());
//		WebUI.verifyTrue(WebUI.getTextElement(modal_TotalAmtConvertedVal).contentEquals(temp_exchange_map.get("sellCurrencyCode")+" "+temp_exchange_map.get("sellAmount")+" = "+temp_exchange_map.get("buyCurrencyCode").trim()+" "+temp_exchange_map.get("buyAmount").trim()));
		WebUI.verifyElementTextEquals(modal_SettlementDateVal,temp_exchange_map.get("COMMON").get("settlementDate"));
		WebUI.verifyTrue(WebUI.getTextElement(modal_TransactionReferenceVal).contains("AF-"));
	}
	public String getReferenceFromExchangeSuccessModal(){
		return WebUI.getTextElement(modal_TransactionReferenceVal);
	}
	public void modal_ClickBtn() {
		WebUI.clickElement(modal_DoneBtn);
	}
	private By selectTenor=By.xpath("//div[contains(text(),'Settlement date')]/preceding-sibling::div//select");
	public void selectTenorDateOnExchange(String tenor) {
//		WebUI.waitForElementClickable(selectTenor,5000);
		WebUI.sleep(1);
		WebUI.waitForElementVisible(selectTenor);
		WebUI.selectOptionByText(selectTenor,tenor);
	}
private By refreshRateBtn=By.xpath("//button[contains(@class,'bg-primary text-primary-foreground')][text()='Refresh rate']");
	public void refreshLiveRateOnReviewScreen() {
		WebUI.waitForElementVisible(refreshRateBtn,20);
		WebUI.waitForElementClickable(refreshRateBtn,20);
		WebUI.clickElement(refreshRateBtn);
		WebUI.sleep(1);
//		WebUI.verifyElementNotVisible(refreshRateBtn);
	}
}
