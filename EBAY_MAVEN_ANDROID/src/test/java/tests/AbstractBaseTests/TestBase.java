package tests.AbstractBaseTests;

import java.io.File;
import java.net.MalformedURLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.github.genium_framework.appium.support.server.AppiumServer;
import com.github.genium_framework.server.ServerArguments;

import utilities.Log;


public abstract class TestBase {

	protected String moduleName = this.getClass().getSimpleName();
	protected AppiumServer _appiumServer;
		
	@BeforeSuite
	public void setUpAppium() throws MalformedURLException{
	try
		{
			Log.info("Before suite Started");
			ServerArguments serverArguments = new ServerArguments();
			serverArguments.setArgument("--address", "0.0.0.0");
			serverArguments.setArgument("--port", 4723);
			serverArguments.setArgument("--no-reset", true);
			serverArguments.setArgument("--session-override", true);
			serverArguments.setArgument("--local-timezone", true);

			File nodeExecutableFilePath = new File("/Applications/Appium.app/Contents/Resources/node/bin/node");
			File appiumJavaScriptFilePath = new File("/Applications/Appium.app/Contents/Resources/node_modules/appium/build/lib/main.js");

			_appiumServer = new AppiumServer(nodeExecutableFilePath, appiumJavaScriptFilePath, serverArguments);
			_appiumServer.startServer(90000);
						
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Log.fatal(e.getMessage());
			if(_appiumServer!=null&&_appiumServer.isServerRunning())
			{
				System.out.println("appium null ");
				_appiumServer.stopServer();
			}
		}
	}
	
	@AfterSuite
	public void tearDownAppium(){
		Log.info(" After Suite");
		if(_appiumServer!=null&&_appiumServer.isServerRunning())
		{
			_appiumServer.stopServer();
		}

	}




}

