package utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;

public class MobileActions {

	static WebDriverWait exwait;
	protected String screenshotFilePath = null;
	protected String failfilepath = null;
	protected String tc_details = null;
	String Errormessage;

	public void setScreenshotFilePath(String filepath)
	{
		this.screenshotFilePath = filepath;
	}

	public void failcase(String failfilepath, String tc_details)
	{
		this.failfilepath = failfilepath;
		this.tc_details = tc_details;
	}

	public static void scrollTotext(MobileDriver driver,StringBuffer verificationErrors,String ElementText) throws IOException
	{
		try
		{
			driver.context("NATIVE_APP");
			String driverType=driver.toString();

			if(driverType.contains("AndroidDriver"))
			{
				System.out.println("To print driver :"+driver.toString());
				driver.scrollTo(ElementText);
			}
			if(driverType.contains("IOSDriver"))
			{

				System.out.println("To print driver :"+driver.toString());
				JavascriptExecutor js = (JavascriptExecutor) driver;
				HashMap <String, String> scrollObject = new HashMap <String, String>();
				scrollObject.put("direction", "up");
				js.executeScript("mobile: scroll", scrollObject);

			}
			Log.info("scrolled to text :"+ElementText);
		}
		catch(Exception e)
		{
			String Errormessage="Unable to scroll to text :"+ElementText+"\n"+e.toString();
			verificationErrors.append(Errormessage);
			Log.fatal(Errormessage);

		}
		finally
		{
			Set<String> context = driver.getContextHandles();
			System.out.println("contexts available for scroll to :" +context);
			for (String contextName : context) {

				driver.context(contextName);
				Log.info(contextName);

			}
		}
	}

	public static void sendkeyTotextbox(MobileDriver driver,StringBuffer verificationErrors,MobileElement element,String Value)
	{
		exwait =new WebDriverWait(driver, 20);
		try
		{
			exwait.until(ExpectedConditions.visibilityOf(element)).sendKeys(Value);
			Log.info("Entered text inside :"+element);
		}
		catch(NoSuchElementException e)
		{
			verificationErrors.append("Element is not present to enter text - Element Name : "+e.toString());
			Log.fatal("Element is not present to enter text - Element Name : "+e.toString());
		}
		catch(Exception e)
		{
			verificationErrors.append("Unable to enter text inside :"+"\n"+e.toString());
			Log.fatal("Unable to enter text inside :"+e.toString());
		}

	}
	public static void sendkeyTotextbox(MobileDriver driver,StringBuffer verificationErrors,String locatorType,String locator,String Value)
	{
		exwait =new WebDriverWait(driver, 20);
		try
		{
			if(locatorType.equalsIgnoreCase("xpath"))
			{
				exwait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locator)))).sendKeys(Value);
				Log.info("Entered text inside with attributes :"+locatorType+":"+locator);
			}
		}
		catch(NoSuchElementException e)
		{
			verificationErrors.append("Element is not present to enter text - Element Name :"+locatorType+":"+locator);
			Log.fatal("Element is not present to enter text - Element Name :"+locatorType+":"+locator);
		}
		catch(Exception e)
		{
			String Errormessage="Unable to enter text inside with attributes :"+locatorType+":"+locator+"\n"+e.toString();
			verificationErrors.append(Errormessage);
			Log.fatal(Errormessage);

		}

	}

	public static void verifyElementPresent(MobileDriver driver,StringBuffer verificationErrors,MobileElement element) 
	{
		exwait =new WebDriverWait(driver, 20);
		try
		{
			exwait.until(ExpectedConditions.visibilityOf(element));
			Log.info("Element Verified using visibility of :  "+element);
		}
		catch(NoSuchElementException e)
		{
			verificationErrors.append("Element is not loaded in the page - Element Name : "+e.toString());
			Log.fatal("Element not present - Element :"+e.toString());
		}
		catch(Exception e)
		{
			verificationErrors.append("Element is not loaded in the page - Element Name : "+e.toString());
			Log.fatal("Element is not loaded in the page - Element Name : "+e.toString()+e.getCause());
		}

	}
	public static void clickElement(MobileDriver driver, StringBuffer verificationErrors, MobileElement element)
	{
		exwait =new WebDriverWait(driver, 20);
		try
		{
			//exwait.until(ExpectedConditions.visibilityOf(element)).click();
			element.click();
			//Log.info("Clicked :"+element);

		}
		catch(NoSuchElementException e)
		{
			verificationErrors.append("Element is not loaded to click :"+e.toString());
			Log.fatal("Element is not loaded to click :"+e.toString());
		}
		catch(Exception e)
		{

			verificationErrors.append("Unable to click :"+e.toString());
			Log.fatal("Unable to click :"+e.toString()+e.getCause());

		}
	}
	public static void dropdownselect(MobileDriver driver, StringBuffer verificationErrors, String value){
		exwait =new WebDriverWait(driver, 20);
		MobileElement ddelement;
		try {

			Thread.sleep(3000);
			ddelement = (MobileElement) driver.findElement(By.xpath("//android.widget.Spinner[contains(@resource-id,'countryId')]"));

			exwait.until(ExpectedConditions.visibilityOf(ddelement)).click();
			Log.info("Drop down textbox is found, so value = "+value + " is selected");
		} 
		catch (NoSuchElementException e) 
		{
			verificationErrors.append("Drop down textbox is not found so value = "+value + "is not selected" +e.toString());
			Log.fatal("Drop down textbox is not found so value = "+value + "is not selected" +e.getMessage());
		}catch (WebDriverException e) 
		{
			verificationErrors.append("Webdriver Exception "+e.toString());
			Log.fatal("Webdriver Exception "+e.getMessage());
		}
		catch (Exception e) 
		{
			verificationErrors.append("Common Exception "+e.toString());
			Log.fatal("Common Exception "+e.getMessage());
		}

	}

	public static void dropdownselect1(MobileDriver driver, StringBuffer verificationErrors, String value){
		exwait =new WebDriverWait(driver, 20);
		MobileElement ddelement;
		try {

			Thread.sleep(3000);
			ddelement = (MobileElement) driver.findElement(By.xpath("//android.widget.Spinner[contains(@resource-id,'state') and @text='Select your state']"));

			exwait.until(ExpectedConditions.visibilityOf(ddelement)).click();
			Log.info("Drop down textbox is found, so value = "+value + " is selected");
		} 
		catch (NoSuchElementException e) 
		{
			verificationErrors.append("Drop down textbox is not found so value = "+value + "is not selected" +e.toString());
			Log.fatal("Drop down textbox is not found so value = "+value + "is not selected" +e.getMessage());
		}catch (WebDriverException e) 
		{
			verificationErrors.append("Webdriver Exception "+e.toString());
			Log.fatal("Webdriver Exception "+e.getMessage());
		}
		catch (Exception e) 
		{
			verificationErrors.append("Common Exception "+e.toString());
			Log.fatal("Common Exception "+e.getMessage());
		}

	}



	public static void hideKeyboard(MobileDriver driver, StringBuffer verificationErrors) {
		exwait =new WebDriverWait(driver, 20);
		try
		{
			driver.hideKeyboard();

		}

		catch(Exception e)
		{
			Log.info("No keyboard instance found");
		}

	}


}
