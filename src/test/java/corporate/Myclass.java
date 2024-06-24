package corporate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.listeners.BaseTest;
import com.qa.listeners.MyListeners;
@Listeners(MyListeners.class)

public class Myclass extends BaseTest {
	@Test
	public void login() throws Exception{
		ChromeDriver driver1; 
			driver.navigate().to("https://qa.mypursu.com/");
			driver.manage().window().maximize();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//a[text()='Corporate Login']")).click();
			driver.findElement(By.xpath("//input[@id='emailID']")).sendKeys("kasinadha@yopmail.com");
			driver.findElement(By.xpath("//input[@id='termsConditions']")).click();
			driver.findElement(By.xpath("//button[text()='Continueee']")).click();
			// ChromeOptions opt=new ChromeOptions();
			// opt.addArguments("--headless=new");
			driver1 = new ChromeDriver();
			driver1.navigate().to("https://yopmail.com/");
			driver1.findElement(By.xpath("//input[@placeholder='Enter your inbox here']"))
					.sendKeys("kasinadha@yopmail.com");
			Thread.sleep(5000);
			driver1.findElement(By.xpath("//i[@class='material-icons-outlined f36']")).click();
			Thread.sleep(5000);
			driver1.findElement(By.id("refresh")).click();
			Thread.sleep(10000);
			WebElement iframe = driver1.findElement(By.xpath("//iframe[@id='ifmail']"));
			driver1.switchTo().frame(iframe);
			WebElement Path = driver1.findElement(By.xpath("//td[@align='left']//p[2]"));
			String OTP = Path.getText();
			String numericPart = OTP.replaceAll("[^0-9]", "");
//		      long number = Long.parseLong(numericPart);
			driver1.quit();
			for (int i = 0; i < numericPart.length(); i++) {
				char digitChar = numericPart.charAt(i);
				String digit = Character.toString(digitChar);
				int j = i + 2;
				driver.findElement(By.xpath("//*[@class='otp-form-field']/div/input[" + j + "]")).sendKeys(digit);
			}
			driver.findElement(By.xpath("//button[text()='Verify']")).click();
		}

		
	}


