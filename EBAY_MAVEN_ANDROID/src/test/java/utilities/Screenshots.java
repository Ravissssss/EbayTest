package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.appium.java_client.MobileDriver;

public class Screenshots 
{	
	public static void takeScreenshot(MobileDriver driver, String filename) throws IOException
	{
		driver.context("NATIVE_APP");
		
		File ChangeLangFill = (File)((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(ChangeLangFill, new File(filename));

		Set<String> context = driver.getContextHandles();
		for (String contextName : context) {

			driver.context(contextName);
			Log.info(contextName);

		}
		
	}
	
	public static void movetoFolder(String folder) throws IOException
	{
		String destFolder = folder.replace("Pass", "Fail");
	
		String source = folder;
		File srcDir = new File(source);

		String destination = destFolder;
		File destDir = new File(destination);
		
		if (destDir.exists())
		{
			FileUtils.deleteDirectory(destDir);
		}
		FileUtils.moveDirectory(srcDir, destDir);
	}
	
}