package com.qa.listeners;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;

public class BaseTest {

	public static WebDriver driver;
	@BeforeClass
	public void setup() throws InterruptedException 
	{
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
	}
	public void failed(String methodname) throws Exception
	{
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File dest=new File(".\\screen\\"+methodname+".png");
		try
		{
		FileUtils.copyFile(src,dest);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}