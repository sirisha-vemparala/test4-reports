package corporate;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.listeners.BaseTest;

public class InsideFeatures extends BaseTest{
	ChromeDriver driver1;
	//WebDriver driver = new ChromeDriver();
	@Test(priority=1)
	public void login() throws InterruptedException {
		driver.navigate().to("https://qa.mypursu.com/");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[text()='Corporate Login']")).click();
		driver.findElement(By.xpath("//input[@id='emailID']")).sendKeys("kasinadha@yopmail.com");
		driver.findElement(By.xpath("//input[@id='termsConditions']")).click();
		driver.findElement(By.xpath("//button[text()='Continue']")).click();
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
//	      long number = Long.parseLong(numericPart);
		driver1.quit();
		for (int i = 0; i < numericPart.length(); i++) {
			char digitChar = numericPart.charAt(i);
			String digit = Character.toString(digitChar);
			int j = i + 2;
			driver.findElement(By.xpath("//*[@class='otp-form-field']/div/input[" + j + "]")).sendKeys(digit);
		}
		driver.findElement(By.xpath("//button[text()='Verify']")).click();
	}

	@Test(priority=2)
	public void Adduser() throws Exception {
		Random r = new Random();
		int random = r.nextInt(1000);
		driver.findElement(By.xpath("(//button[@class='btn btn-outline-secondary'])[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@placeholder='Enter name']")).sendKeys("walletuser");
		driver.findElement(By.xpath("(//input[@placeholder='Enter email ID'])[1]"))
				.sendKeys("walletuser" + random + "@yopmail.com");
		driver.findElement(By.xpath("//input[@id='mobile_number']")).sendKeys("8000000" + random);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//select[@id='countries']")).click();
		Robot rb = new Robot();
		rb.delay(3000);
		rb.keyPress(KeyEvent.VK_DOWN);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		driver.findElement(By.xpath("(//button[text()='Submit'])[1]")).click();
		WebDriver driver2 = new ChromeDriver();
		driver2.navigate().to("https://yopmail.com/");
		driver2.findElement(By.xpath("//input[@placeholder='Enter your inbox here']"))
				.sendKeys("walletuser" + random + "@yopmail.com");
		Thread.sleep(7000);
		driver2.findElement(By.xpath("//i[@class='material-icons-outlined f36']")).click();
		Thread.sleep(5000);
		driver2.findElement(By.id("refresh")).click();
		Thread.sleep(5000);
		WebElement iframe = driver2.findElement(By.xpath("//iframe[@id='ifmail']"));
		driver2.switchTo().frame(iframe);
		WebElement Path = driver2.findElement(By.xpath("//td[@align='left']//p[2]"));
		String OTP = Path.getText();
		String numericParts = OTP.replaceAll("[^0-9]", "");
//	      long number = Long.parseLong(numericPart);
		driver2.quit();
		driver.findElement(By.xpath("//input[@id='otpWallet']")).sendKeys(numericParts);
		driver.findElement(By.xpath("(//button[text()='Submit'])[2]")).click();
	}

	@Test(priority=3)
	public void loadmoney() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='Load money']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//input[@placeholder='Enter Amount'])[2]")).sendKeys("200");
		Thread.sleep(3000);
		driver.findElement(By.name("description")).sendKeys("This is load Money");
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//button[text()='Submit Request'])[1]")).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement ele = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-title']")));
		String b = ele.getText();
		System.out.println(b);
	}

	@Test(priority=6)
	public void transaction() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[contains(text(),'Transaction')]")).click();
		WebElement showDropDown = driver.findElement(By.name("TransactionListTable_length"));
		Select transactionDropDown = new Select(showDropDown);
		transactionDropDown.selectByVisibleText("All");
		Thread.sleep(3000);
		WebElement searchTxt = driver.findElement(By.xpath("//input[@type='search']"));
		searchTxt.sendKeys("mama");
		Thread.sleep(3000);
		searchTxt.clear();
		Thread.sleep(3000);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("(//a[@href='#transactionDetail'])[1]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
	}

	@Test(priority=4)
	public void withdraw() throws InterruptedException {
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='Withdraw']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//input[@placeholder='Enter Amount'])[3]")).sendKeys("3");
		driver.findElement(By.xpath("(//textarea[@placeholder='Enter a note in 100 characters'])[2]"))
				.sendKeys("This is send money");
		driver.findElement(By.xpath("(//button[@type='submit'])[5]")).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement ele = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-title']")));
		String b = ele.getText();
		Assert.assertEquals(b, "Success");
	}

	@Test(priority=5)
	public void sendmoney() throws InterruptedException {
		Thread.sleep(3000);
		WebElement a = driver.findElement(By.xpath("(//a[@title='Send Money'])[2]"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", a);
		Thread.sleep(3000);
		driver.findElement(By.id("employee_amount")).sendKeys("10");
		Thread.sleep(3000);
		WebElement b = driver.findElement(By.xpath("//button[text()='Submit Request']"));
		jse.executeScript("arguments[0].click();", b);
	}

	@Test(priority=7)
	public void profile() throws InterruptedException {
		Thread.sleep(3000);
		 WebElement profilePage=driver.findElement(By.xpath("//a[contains(text(),'Profile')]"));
	     profilePage.click();
	     Thread.sleep(2000);
	     WebElement nameElement=driver.findElement(By.id("name"));
	     nameElement.clear();
	     nameElement.sendKeys("kasinadha");
	     Thread.sleep(3000);
	     WebElement addressDetails=driver.findElement(By.id("v-pills-address-tab"));
	     addressDetails.click();
	     Thread.sleep(3000);
	     WebElement addressL1=driver.findElement(By.id("addressLine1"));
	     addressL1.clear();
	     addressL1.sendKeys("opposite Highway");
	     Thread.sleep(3000);
	     WebElement addressL2=driver.findElement(By.id("addressLine2"));
	      addressL2.clear();
	      addressL2.sendKeys("opposite canara bank");
	     Thread.sleep(3000);
//	     WebElement profileDropDown=driver.findElement(By.id("countries"));
//			Select countryDropDown=new Select(profileDropDown);
//			countryDropDown.selectByVisibleText("CANADA");
//			Thread.sleep(3000);
			WebElement stateElement=driver.findElement(By.id("state"));
			stateElement.clear();
			stateElement.sendKeys("Andhra Pradesh");
			Thread.sleep(3000);
			WebElement cityElement=driver.findElement(By.id("city"));
			cityElement.clear();
			cityElement.sendKeys("Visakhapatnam");
			Thread.sleep(3000);
			WebElement zipElement=driver.findElement(By.id("zipcode"));
			zipElement.clear();
			zipElement.sendKeys("533401");
			Thread.sleep(3000);
			WebElement documentName=driver.findElement(By.id("documentName"));
			documentName.clear();
			documentName.sendKeys("Adhaar card");
			Thread.sleep(3000);
			WebElement documentNumber=driver.findElement(By.id("documentNumber"));
			documentNumber.clear();
			documentNumber.sendKeys("000000001");
			Thread.sleep(3000);
			WebElement fileInput = driver.findElement(By.id("fileUpload"));
	     String filePath = "C:\\Users\\DELL\\Downloads\\premalu.jpg";
	     fileInput.sendKeys(filePath);
	     Thread.sleep(3000);
	     driver.findElement(By.xpath("//button[text()='Update']")).click();
	     Thread.sleep(3000);
	     WebElement privacyPolicy=driver.findElement(By.id("v-pills-privacyPolicy-tab"));
	     privacyPolicy.click();
	     driver.findElement(By.xpath("//div[@class='col-12 col-md-5 col-lg-4']"));
	     WebElement termsConditions=driver.findElement(By.xpath("/html/body/main/section/div/div[2]/div/div[1]/div/div[2]/div/button[4]"));
	     termsConditions.click();
	     Thread.sleep(3000);
		driver.quit();
	}
	@Test(priority=8)
	public void mainpage() throws Throwable {
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://qa.mypursu.com/");
		driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.xpath("//a[@type='button']")).click();
		Thread.sleep(1000);
		WebElement el=driver.findElement(By.xpath("/html/body/section[3]/div/div/div[2]/a[1]"));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", el);
        driver.navigate().back();
        WebElement el2=driver.findElement(By.xpath("/html/body/section[3]/div/div/div[2]/a[2]"));
        jsExecutor.executeScript("arguments[0].click();", el2);
        driver.navigate().back();
		WebElement ele=driver.findElement(By.xpath("/html/body/section[1]/div/div/div/div[1]/div"));
		    jsExecutor.executeScript("arguments[0].click();", ele);
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement ele1=driver.findElement(By.linkText("About us"));
        jsExecutor.executeScript("arguments[0].click();", ele1);
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement ele2=driver.findElement(By.xpath("/html/body/footer/div[1]/div/div[2]/div/ul/li[4]/a"));
        jsExecutor.executeScript("arguments[0].click();", ele2);
        driver.findElement(By.xpath("/html/body/div/section/div/div[2]/div/div/div/div[1]/h5/button")).click();
             driver.findElement(By.xpath("/html/body/nav/div/div/a")).click();
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement ele5=driver.findElement(By.linkText("Privacy Policy"));
        jsExecutor.executeScript("arguments[0].click();", ele5);
        WebElement ele6=driver.findElement(By.linkText("Terms & Conditions"));
        jsExecutor.executeScript("arguments[0].click();", ele6);
        WebElement ele7=driver.findElement(By.xpath("/html/body/footer/div[1]/div/div[3]/div/ul/li[3]/a"));
        jsExecutor.executeScript("arguments[0].click();", ele7);
        WebElement ele8=driver.findElement(By.xpath("/html/body/footer/div[1]/div/div[3]/div/ul/li[4]/a"));
        jsExecutor.executeScript("arguments[0].click();", ele8);
        WebElement ele3= driver.findElement(By.linkText("Contact Us"));
        jsExecutor.executeScript("arguments[0].click();", ele3);
        driver.findElement(By.cssSelector("input[id='name']")).sendKeys("Geeta");
        driver.findElement(By.cssSelector("input[name='email']")).sendKeys("tod@yopmail.com");
        driver.findElement(By.cssSelector("input[id='mobile']")).sendKeys("1234567891");
        driver.findElement(By.cssSelector("input[placeholder='Subject']")).sendKeys("asdfghj");
        driver.findElement(By.xpath("/html/body/div/section/div/div[2]/div/div/div[2]/form/div[5]/textarea")).sendKeys("qwerqwertyuiqwertyuioqwertyuio");
        WebElement ele4=driver.findElement(By.xpath("/html/body/div/section/div/div[2]/div/div/div[2]/form/div[6]/button"));
        jsExecutor.executeScript("arguments[0].click();", ele4);
        jsExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement ele10=driver.findElement(By.xpath("/html/body/footer/div[1]/div/div[1]/div/div[2]/a[1]"));
         jsExecutor.executeScript("arguments[0].click();", ele10);
       //  driver.navigate().back();
        WebElement ele11=driver.findElement(By.xpath("/html/body/footer/div[1]/div/div[1]/div/div[2]/a[2]"));
        jsExecutor.executeScript("arguments[0].click();", ele11);
        WebElement ele12=driver.findElement(By.xpath("/html/body/footer/div[1]/div/div[1]/div/div[2]/a[3]"));
        jsExecutor.executeScript("arguments[0].click();", ele12);
        WebElement ele13=driver.findElement(By.xpath("/html/body/footer/div[1]/div/div[1]/div/div[2]/a[4]"));
        jsExecutor.executeScript("arguments[0].click();", ele13);
        WebElement ele14=driver.findElement(By.xpath("/html/body/footer/div[1]/div/div[1]/div/div[2]/a[5]"));
        jsExecutor.executeScript("arguments[0].click();", ele14);  
        Thread.sleep(5000);
        driver.quit();
	}
}  