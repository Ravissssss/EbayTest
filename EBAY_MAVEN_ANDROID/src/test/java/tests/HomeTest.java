package tests;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.MobileDriver;
import pages.HomePage;
import pages.LoginPage;
import tests.AbstractBaseTests.TestBase;
import utilities.ExcelLib;
import utilities.Log;

public class HomeTest extends TestBase {

	MobileDriver driver = null;
	private HomePage homepage;
	private LoginPage loginpage;

	ExcelLib Home_testData = new ExcelLib();
	HashMap<String, String> dataset = new HashMap<String, String>();

	@DataProvider(name = "HomeNgDataprovider")
	public Object[][] testDataprovider(Method m) throws Exception {
		Object[][] tabArray = null;
		String testScen = m.getName();
		tabArray = Home_testData.getTestcases(testScen);
		return tabArray;

	}

	@BeforeClass
	public void setExcel() throws Exception {
		Log.info("Data files set for :" + this.getClass().getSimpleName());
		Home_testData.setExcelFile("DataEngine/Home.xls");
	}

	@Parameters({"MobileType","Ebay_username","Ebay_password" })
	@BeforeMethod
	public void App_login(String mobileType, String userName, String password) throws Exception {

		loginpage = new LoginPage();
		driver = loginpage.launch_mobile(mobileType);
		homepage = loginpage.loginIn(driver, userName, password);
		
	}

	@Test(dataProvider = "HomeNgDataprovider")
	public void H001_HomePageDetails(String testcase) throws Exception 
	{
		String ScenarioName = new Object(){}.getClass().getEnclosingMethod().getName();
		Log.startTestCase(ScenarioName + "---" + testcase);
		String sheetName = "HomePage";
		dataset = Home_testData.getDatafortc(testcase, sheetName);
		homepage.setScreenshotFilePath("screenshots//Pass//" + moduleName + "//" + testcase + "//");
		homepage.failcase("FailCases.txt//", "Module Name: " + moduleName + "\t TC#: " + testcase);
	    homepage.homePage(driver, dataset);
		Log.endTestCase(ScenarioName + "---" + testcase);

	}
	
	@AfterMethod
	public void App_logout() throws Exception {
		Thread.sleep(5000);
		homepage.app_logout(driver);
		if (driver != null) {
			driver.quit();
		}
		Log.info("Logged Off");
		Log.info("Completed!!!!!");
	}	

}
