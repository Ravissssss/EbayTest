package pages;

import static org.testng.Assert.fail;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import utilities.Log;
import utilities.MobileActions;
import utilities.Screenshots;

public class LoginPage extends MobileActions {

	StringBuffer verificationErrors;
	MobileDriver driver = null;
	protected URL url;
	protected DesiredCapabilities capabilities;

	public MobileDriver launch_mobile(String mobileType) throws Exception {
		final String URL_STRING = "http://0.0.0.0:4723/wd/hub";

		url = new URL(URL_STRING);

		File classpathRoot= new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot,"app");
		File app;

		if(mobileType.equalsIgnoreCase("ANDROID"))
		{
			Log.info("Android Mobile");
			capabilities = new DesiredCapabilities();
			app = new File(appDir, "eBay.apk");
			capabilities.setCapability("deviceName","Android");
			capabilities.setCapability("app", app.getAbsolutePath());
			capabilities.setCapability("appPackage","com.ebay.mobile");
			capabilities.setCapability("appActivity","com.ebay.mobile.activities.MainActivity");
			capabilities.setCapability("sessionOverride", true);
			capabilities.setCapability("noReset",true);
			capabilities.setCapability("platformVersion", "6.0.1");
			driver = new AndroidDriver<MobileElement>(url,capabilities);
		}
		else if(mobileType.equalsIgnoreCase("IOS"))
		{
			Log.info("iOS Mobile");
			capabilities = new DesiredCapabilities();
			app = new File(appDir, "");
			capabilities.setCapability("deviceName", "iPhone 6s Plus");
			capabilities.setCapability("app", app.getAbsolutePath());
			//capabilities.setCapability("udid", "");
			capabilities.setCapability("bundleId", "");
			capabilities.setCapability("platformVersion", "9.3");
			driver = new IOSDriver<MobileElement>(url,capabilities);
		}

		/*	
		Set<String> context = driver.getContextHandles();
		System.out.println("contexts available for login:" +context);
		for (String contextName : context) {

			driver.context(contextName);
			Log.info(contextName);

		}*/

		PageFactory.initElements(new AppiumFieldDecorator(driver, 30, TimeUnit.SECONDS), this);
		return driver;
	}
	public HomePage loginIn(MobileDriver driver,String userName, String password){

		try
		{
			verificationErrors = new StringBuffer();
			Screenshots.takeScreenshot(driver, screenshotFilePath + "HomeScreen.png");

			//Sign In screen is not detecting via uiautomator, so I amn't able to capture Object Repositories.

			/*clickElement(driver, verificationErrors, signIn);

			sendkeyTotextbox(driver,verificationErrors,signIn_Email, userName);

			sendkeyTotextbox(driver, verificationErrors,signIn_Pwd, password);
			driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
	        Screenshots.takeScreenshot(driver, screenshotFilePath + "SignIn Screen.png");

			clickElement(driver, verificationErrors, btnSignIn);
			Screenshots.takeScreenshot(driver, screenshotFilePath + "ProductsScreen.png");

			 */	

		}
		catch(Exception e)
		{
			verificationErrors.append(e.toString());
			Log.fatal(e.getMessage());
		}

		finally
		{
			String verificationErrorString = verificationErrors.toString();
			if (!"".equals(verificationErrorString))
			{
				try {
					fail(verificationErrorString);
				} 
				catch (Exception e) 
				{
					Log.fatal(e.getMessage());
				}
			}

		}
		return new HomePage(driver);
	}


	/************************** Object Repository for perform_MenuClick() ********************************/
	@FindBy(xpath="//android.widget.Button[contains(@resource-id,'com.ebay.mobile:id/button_sign_in') and @text='Sign in']")
	private MobileElement signIn;

	@FindBy(id="com.ebay.mobile:edit_text_username")
	private MobileElement signIn_Email;

	@FindBy(id="com.ebay.mobile:edit_text_password")
	private MobileElement signIn_Pwd;

	@FindBy(id="com.ebay.mobile:id/button_sign_in")
	private MobileElement btnSignIn;

	@FindBy(xpath="//android.widget.Button[contains(@resource-id,'com.ebay.mobile:id/button_register')]")
	private MobileElement btn_Register;

	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'com.ebay.mobile:id/search_box') and @text='Search for anything']")
	private MobileElement btnSearch;

	@FindBy(xpath="android.widget.EditText[contains(@resource-id,'com.ebay.mobile:id/search_src_text') and @text='Search for anything']")
	private MobileElement sendkeys_Search;

	@FindBy(xpath="android.widget.TextView[contains(@resource-id,'com.ebay.mobile:id/text') and @text='65 inch tvLED Televisions']")
	private MobileElement selectTv;

	@FindBy(xpath="//android.widget.TextView[contains(@resource-id,'com.ebay.mobile:id/textview_item_price')]")
	private MobileElement tvPrice;
}
