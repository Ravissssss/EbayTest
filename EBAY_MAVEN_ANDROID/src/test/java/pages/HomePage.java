package pages;

import static org.testng.Assert.fail;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utilities.FailCase;
import utilities.Log;
import utilities.MobileActions;
import utilities.Screenshots;

public class HomePage extends MobileActions {

	WebDriverWait exwait;
	StringBuffer verificationErrors;

	public HomePage(MobileDriver driver) {
		exwait = new WebDriverWait(driver, 30);
		PageFactory.initElements(new AppiumFieldDecorator(driver, 40, TimeUnit.SECONDS), this);
	}

	@SuppressWarnings("unchecked")
	public <T> T navigateTo(MobileDriver driver, String Menu_item,String validAccount) throws Exception {

		verificationErrors=new StringBuffer();

		System.out.println("return null");
		return null;
	}

	public void homePage(MobileDriver driver, HashMap<String, String> data) throws Exception {
		try 
		{
			verificationErrors = new StringBuffer();
			Screenshots.takeScreenshot(driver, screenshotFilePath + "AppLaunchScreen.png");

			Log.info("Searching for TV");	
			sendkeyTotextbox(driver, verificationErrors, btnSearch, data.get("TV"));
			Screenshots.takeScreenshot(driver, screenshotFilePath + "SearchingProdctScreen.png");
			driver.hideKeyboard();

			Wait wait = new FluentWait(driver)
					.withTimeout(30, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.SECONDS)
					.ignoring(NoSuchElementException.class);

			clickElement(driver, verificationErrors, selectTv);
			Screenshots.takeScreenshot(driver, screenshotFilePath + "SelectProduct.png");

			Assert.assertEquals(tvPrice.getText(),data.get("Tv Price"),"Tv Price is mismatched" );
			Screenshots.takeScreenshot(driver, screenshotFilePath + "SelectProduct1.png");

			//Clicking on TV
			clickElement(driver, verificationErrors, tvPrice);

			driver.rotate(org.openqa.selenium.ScreenOrientation.LANDSCAPE);
			System.out.println(" Now screen orientation Is : "+driver.getOrientation());
			driver.rotate(org.openqa.selenium.ScreenOrientation.PORTRAIT);
			System.out.println(" Now screen orientation Is : "+driver.getOrientation());
			Screenshots.takeScreenshot(driver, screenshotFilePath + "RotatingScreen.png");

			//Registering for Sign into application 
			Log.info("Clicking on Register button");	
			Screenshots.takeScreenshot(driver, screenshotFilePath + "EbayHomeScreen.png");
			clickElement(driver, verificationErrors, btn_Register);
			Screenshots.takeScreenshot(driver, screenshotFilePath + "LoginDetailsScreen.png");

			sendkeyTotextbox(driver, verificationErrors, val_FirstName, data.get("FirstName"));
			clickElement(driver, verificationErrors, lbl_FirstName);

			//Just hiding the keypad
			driver.hideKeyboard();

			Log.info("Verifying First Name");
			verifyElementPresent(driver, verificationErrors, lbl_FirstName);

			Log.info("Verifying Last Name");
			Assert.assertEquals(lbl_LastName.getText(),data.get("LabelLastName"),"Last Name label is mismatched" );

			sendkeyTotextbox(driver, verificationErrors, val_LastName, data.get("SecondName"));

			sendkeyTotextbox(driver, verificationErrors, val_Email, data.get("Email"));
			verifyElementPresent(driver, verificationErrors, lbl_Email);

			sendkeyTotextbox(driver, verificationErrors, val_Password, data.get("Password"));
			Assert.assertEquals(lbl_Password.getText(),data.get("LabelPassword"),"Password label is mismatched" );

			Log.info("Submitting Register button");	
			clickElement(driver, verificationErrors, submitBtn);

			Log.info("Provide contact info");
			verifyElementPresent(driver, verificationErrors, lbl_contactInfo);

			Log.info("Selecting the Country");	
			dropdownselect(driver, verificationErrors, data.get("Country"));

			Log.info("Entering the Address one");
			sendkeyTotextbox(driver, verificationErrors, address, data.get("Address"));

			Log.info("Entering the Address two");
			sendkeyTotextbox(driver, verificationErrors, secAddress, data.get("Second Address"));

			Log.info("Entering the City");
			sendkeyTotextbox(driver, verificationErrors, city, data.get("City"));

			Log.info("Selecting the State");	
			dropdownselect(driver, verificationErrors, data.get("State"));

			Log.info("Entering Zip code");	
			sendkeyTotextbox(driver, verificationErrors, pincode, data.get("PoinCode"));

			Log.info("Entering Mobile Number");	
			sendkeyTotextbox(driver, verificationErrors, mobNo, data.get("Mobile"));

			Log.info("Agree Terms and Condtions");	
			clickElement(driver, verificationErrors, chkBox);
			Screenshots.takeScreenshot(driver, screenshotFilePath + "LoginDetailsScreen1.png");

			Log.info("Scrool down the page");	
			scrollTotext(driver, verificationErrors, "I only have a landline");

			Log.info("Clicking on Continue button");	
			clickElement(driver, verificationErrors, btn_Continue);
			Screenshots.takeScreenshot(driver, screenshotFilePath + "SecondScreenDetails.png");
		} 
		catch (Exception e) 
		{
			verificationErrors.append(e.toString());
		} 
		finally {
			String verificationErrorString = verificationErrors.toString();
			if (!"".equals(verificationErrorString))
			{
				try 
				{
					Screenshots.takeScreenshot(driver, screenshotFilePath + "HomeScreenErr.png");
					Screenshots.movetoFolder(screenshotFilePath);
					FailCase.failresult(failfilepath, tc_details, verificationErrorString);
					fail(verificationErrorString);
				}
				catch (Exception e) 
				{
					Log.fatal(e.getMessage());
				}
			}
		}
	}
	public void app_logout(MobileDriver driver) throws InterruptedException 
	{
		verificationErrors=new StringBuffer();
		if (driver != null) 
		{
			driver.closeApp();
			driver.quit();
		}

	}

	/************************** Object Repository for Ebay App ********************************/

	@FindBy(xpath="//android.widget.Button[contains(@resource-id,'com.ebay.mobile:id/button_register')]")
	private MobileElement btn_Register;

	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'firstname')]")
	private MobileElement val_FirstName;

	@FindBy(xpath="//android.view.View[contains(@resource-id,'lblfirstname') and @text='First name']")
	private MobileElement lbl_FirstName;

	@FindBy(xpath="//android.view.View[contains(@resource-id,'lbllastname') and @text='Last name']")
	private MobileElement lbl_LastName;

	@FindBy(xpath="android.widget.EditText[contains(@resource-id,'lastname')]")
	private MobileElement val_LastName;

	@FindBy(xpath="android.widget.EditText[contains(@resource-id,'email')]")
	private MobileElement val_Email;

	@FindBy(xpath="//android.view.View[contains(@resource-id,'lblemail') and @text='Email address']")
	private MobileElement lbl_Email;

	@FindBy(xpath="android.widget.EditText[contains(@resource-id,'PASSWORD')]")
	private MobileElement val_Password;

	@FindBy(xpath="//android.view.View[contains(@resource-id,'lblPASSWORD') and @text='Password']")
	private MobileElement lbl_Password;

	@FindBy(xpath="//android.widget.Button[contains(@resource-id,'ppaFormSbtBtn') and @text='Register']")
	private MobileElement submitBtn;

	@FindBy(xpath="//android.view.View[@text='Provide your contact info']	")
	private MobileElement lbl_contactInfo;

	@FindBy(xpath="//android.widget.Spinner[contains(@resource-id,'countryId')]")
	private String drpdownIndia;

	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'address1')]")
	private MobileElement address;

	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'address2')]")
	private MobileElement secAddress;

	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'city')]")
	private MobileElement city;

	@FindBy(xpath="//android.widget.Spinner[contains(@resource-id,'state') and @text='Select your state']")
	private MobileElement drpdownState;

	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'zip')]")
	private MobileElement pincode;

	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'phoneFlagComp1')]")
	private MobileElement mobNo;

	@FindBy(xpath="//android.view.View[@text='5' and @index='0']")
	private MobileElement chkBox;

	@FindBy(xpath="//android.widget.Button[contains(@resource-id,'sbtBtn') and @text='Continue']")
	private MobileElement btn_Continue;

	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'com.ebay.mobile:id/search_box') and @text='Search for anything']")
	private MobileElement btnSearch;

	@FindBy(xpath="android.widget.EditText[contains(@resource-id,'com.ebay.mobile:id/search_src_text') and @text='Search for anything']")
	private MobileElement sendkeys_Search;

	@FindBy(xpath="android.widget.TextView[contains(@resource-id,'com.ebay.mobile:id/text') and @text='65 inch tvLED Televisions']")
	private MobileElement selectTv;

	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'com.ebay.mobile:id/textview_item_price')]")
	private MobileElement tvPrice;

	@FindBy(xpath="//android.widget.EditText[contains(@resource-id,'com.ebay.mobile:id/search_src_text')]")
	private MobileElement tab;


}
