package corporate;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Random;
import java.util.Set;

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
	@Test(priority=2)
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
	@Test(priority=1)
	public void Signup() throws Exception
	{

		driver.get("https://qa.mypursu.com/sign-up");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		Random r=new Random();
		int random=r.nextInt(1000);
		//int num=r.nextInt(1000);
		//Name field
		driver.findElement(By.xpath("//input[@placeholder='Enter name']")).sendKeys("Name");
		//Company Name
		driver.findElement(By.xpath("//input[@placeholder='Enter company name']")).sendKeys("Company name");
		//Email id
		driver.findElement(By.xpath("(//input[@placeholder='Enter email ID'])[1]")).sendKeys("user"+random+"@yopmail.com");
		driver.findElement(By.xpath("(//input[@placeholder='Enter email ID'])[2]")).sendKeys("user"+random+"@yopmail.com");
		//Country dropdown
		Select s=new Select(driver.findElement(By.xpath("//select[@id='country']")));
		s.selectByVisibleText("UNITED STATES");
		//Mobile number
		driver.findElement(By.xpath("(//input[@placeholder='Enter number'])[1]")).sendKeys("7000000"+random);
		//terms and conditions
		driver.findElement(By.xpath("//input[@id='termsConditions']")).click();
		//privacy policy
		driver.findElement(By.xpath("//input[@id='privacyPolicy']")).click();
		//Continue
		driver.findElement(By.xpath("(//button[text()='Continue'])[1]")).click();
		Thread.sleep(3000);
		//Address
		driver.findElement(By.xpath("//input[@placeholder='Enter company address']")).sendKeys("Address");
		
		
		//State
		driver.findElement(By.xpath("//input[@placeholder='Enter State']")).sendKeys("State");
		//City
		driver.findElement(By.xpath("//input[@name='city']")).sendKeys("City");
		//zipcode
		driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys("520001");
		//registration number
		driver.findElement(By.xpath("//input[@id='registration']")).sendKeys("427845632584");
		//Upload doc
		driver.findElement(By.xpath("//span[@class='material-symbols-outlined']")).click();
		Robot rb=new Robot();
		rb.delay(3000);
		StringSelection ss=new StringSelection("Downloads\\nature-images.jpg");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		//Continue
		Thread.sleep(3000);
		WebElement ele=driver.findElement(By.xpath("(//button[text()='Continue'])[2]"));
		JavascriptExecutor jse=(JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click();", ele);
		WebDriver driver1 = new ChromeDriver();
        driver1.navigate().to("https://yopmail.com/");
        driver1.findElement(By.xpath("//input[@placeholder='Enter your inbox here']")).sendKeys("user"+random+"@yopmail.com");
        Thread.sleep(7000);
        driver1.findElement(By.xpath("//i[@class='material-icons-outlined f36']")).click();
        Thread.sleep(5000);
        driver1.findElement(By.id("refresh")).click();
        Thread.sleep(5000);
        WebElement iframe = driver1.findElement(By.xpath("//iframe[@id='ifmail']"));
        driver1.switchTo().frame(iframe);
        WebElement Path = driver1.findElement(By.xpath("//td[@align='left']//p[2]"));
        String OTP=Path.getText();
        String numericPart = OTP.replaceAll("[^0-9]","");
        System.out.println(numericPart);
//      long number = Long.parseLong(numericPart);
        driver1.quit();
        for (int i = 0; i < numericPart.length(); i++) {
            char digitChar = numericPart.charAt(i);
            //System.out.println(i);
            //System.out.println(digitChar);
            String digit = Character.toString(digitChar);
            int j=i+2;
            driver.findElement(By.xpath("//*[@class='otp-form-field']/div/input["+j+"]")).sendKeys(digit);
        }
        driver.findElement(By.xpath("//button[text()='Verify']")).click();
	}

	@Test(priority=3)
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
		WebElement Path = driver2.findElement(By.xpath("//td[@align='left']//p[4]"));
		
		String OTP = Path.getText();
		String numericParts = OTP.replaceAll("[^0-9]", "");
//	      long number = Long.parseLong(numericPart);
		driver2.quit();
		System.out.println(numericParts);
		WebDriverWait wait = new WebDriverWait(driver, 10);
	    WebElement otpInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Verification Code']")));

	    otpInput.sendKeys(numericParts);
	    System.out.println(otpInput.getText());
	    Thread.sleep(3000);
		//driver.findElement(By.xpath("//input[@id='otpWallet']")).sendKeys(numericParts);
		driver.findElement(By.xpath("(//button[text()='Submit'])[2]")).click();
	}

	@Test(priority=4)
	public void loadmoney() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='Load money']")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input[@name='emp_amount']")).sendKeys("200");
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

	@Test(priority=7)
	public void transaction() throws InterruptedException {
		driver.navigate().refresh();
	
	    Thread.sleep(5000);
        driver.findElement(By.xpath("//button[text()='Transaction']")).click();
        WebElement showDropDown=driver.findElement(By.name("TransactionListTable_length"));
        Select transactionDropDown=new Select(showDropDown);
        transactionDropDown.selectByVisibleText("All");
        Thread.sleep(3000);
        WebElement searchTxt=driver.findElement(By.xpath("//input[@type='search']"));
        searchTxt.sendKeys("l u t fr gb");
        Thread.sleep(3000);
        searchTxt.clear();
        Thread.sleep(3000);
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//a[@href='#transactionDetail']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[contains(text(),'OK')]")).click();
}

	@Test(priority=5)
	public void withdraw() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[text()='Withdraw']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@name='withdrow_amount']")).sendKeys("3");
		driver.findElement(By.xpath(""
				+ "/html/body/small/div/div/div/form/div/div[4]/small/textarea"))
				.sendKeys("This is send money");
		driver.findElement(By.xpath("//button[@class='btn btn-primary Withdrowempsubmitbtn text-uppercase']")).click();
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement ele = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-title']")));
		String b = ele.getText();
		Assert.assertEquals(b, "Success");
	}

	@Test(priority=6)
	public void sendmoney() throws InterruptedException {
		driver.navigate().refresh();
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
	
	@Test(priority = 8)
    public void allTransactions() throws InterruptedException {
	  driver.navigate().refresh();
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("//a[contains(text(),'All')]")).click();
       // WebElement showDropDown=driver.findElement(By.name("TransactionListTable_length"));
        //Select transactionDropDown=new Select(showDropDown);
       // transactionDropDown.selectByVisibleText("All");
        Thread.sleep(3000);
        WebElement searchTxt=driver.findElement(By.xpath("//input[@type='search']"));
        searchTxt.sendKeys("sot");
        Thread.sleep(3000);
        searchTxt.clear();
        Thread.sleep(3000); 
        driver.navigate().refresh();
       WebElement eledropdown=driver.findElement(By.xpath("//*[@id=\"navbarSupportedContent\"]/div/a"));
          eledropdown.click();
          Thread.sleep(3000);
      driver.findElement(By.xpath("//a[text()='Logout']")).click();
    }
	
	 @Test(priority = 9)
	    public void requestMoneyBack() throws InterruptedException {
		  Thread.sleep(5000);
	       WebElement ele1= driver.findElement(By.xpath("//*[@id=\"UserListTable\"]/tbody/tr[1]/td[6]/span/a[3]"));
	        JavascriptExecutor js=(JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", ele1);
			Thread.sleep(5000);
	      WebElement ele2=driver.findElement(By.xpath("(//input[@id='employee_req_amount'])[1]"));
	      ele2.sendKeys("10");
	      Thread.sleep(5000);
	      driver.findElement(By.xpath("(//button[contains(text(),'Request')])[2]")).click();
	      
	 }
	 
	 @Test(priority=10)
		public void profile() throws InterruptedException {
			driver.navigate().refresh();
	        Thread.sleep(5000);
	        driver.findElement(By.xpath("//a[contains(text(),'Profile')]")).click();
	        Thread.sleep(5000);
	        WebElement name=driver.findElement(By.xpath("//*[@id=\"name\"]"));
	        String actualInputValue = name.getAttribute("value");
	        String expectedValue = "kasinadha"; 
	        if (actualInputValue.equals(expectedValue)) {
	            System.out.println("Input verification passed: " + actualInputValue);
	        } else {
	            System.out.println("Input verification failed.");
	            System.out.println("Expected: " + expectedValue);
	            System.out.println("Actual: " + actualInputValue);
	        }
	        Thread.sleep(5000);
	        WebElement emailid=driver.findElement(By.xpath("//*[@id=\"emailID\"]"));
	        String actualInputValue1 = emailid.getAttribute("value");
	        String expectedValue1 = "kasinadha@yopmail.com";  
	        if (actualInputValue1.equals(expectedValue1)) {
	            System.out.println("Input verification passed: " + actualInputValue1);
	        } else {
	            System.out.println("Input verification failed.");
	            System.out.println("Expected: " + expectedValue1);
	            System.out.println("Actual: " + actualInputValue1);
	        }
	        Thread.sleep(5000);
	        WebElement phnum=driver.findElement(By.xpath("//*[@id=\"emailID\"]"));
	        String actualInputValue12 = phnum.getAttribute("value");
	        String expectedValue12 = "kasinadha@yopmail.com";  
	        if (actualInputValue1.equals(expectedValue1)) {
	            System.out.println("Input verification passed: " + actualInputValue1);
	        } else {
	            System.out.println("Input verification failed.");
	            System.out.println("Expected: " + expectedValue1);
	            System.out.println("Actual: " + actualInputValue1);
	        }
	        Thread.sleep(5000);
	        driver.findElement(By.xpath("//a[contains(text(),'support')]")).click();
	        Thread.sleep(5000);
	       // driver.findElement(By.xpath("//a[@href='https://api.whatsapp.com/send/?phone=9010009967&amp;text=Hi&amp;type=phone_number&amp;app_absent=0']")).click();
	        ((JavascriptExecutor) driver).executeScript("window.open('https://qa.mypursu.com/contact', '_blank');"); 
	        Set<String> windowHandles = driver.getWindowHandles();
	        String[] handles = windowHandles.toArray(new String[0]);
	        driver.switchTo().window(handles[1]);
	        driver.switchTo().window(handles[0]);
	        Thread.sleep(5000);
	       driver.findElement(By.xpath("//*[@id=\"v-pills-address-tab\"]")).click();
	       Thread.sleep(5000);
	      WebElement ele1= driver.findElement(By.xpath("//*[@id=\"addressLine1\"]"));
	       ele1.clear();
	       ele1.sendKeys("worn");
	       WebElement textElement = driver.findElement(By.xpath("//input[@name='country']"));
	       String actualInputValue11 = textElement.getAttribute("value");
	       String expectedValue11 = "United States";  // Replace with the expected text
	       if (actualInputValue11.equals(expectedValue11)) {
	           System.out.println("Input verification passed: " + actualInputValue11);
	       } else {
	           System.out.println("Input verification failed.");
	           System.out.println("Expected: " + expectedValue11);
	           System.out.println("Actual: " + actualInputValue11);
	       }
	       WebElement state=driver.findElement(By.xpath("//input[@id='state']"));
	       state.clear();
	       state.sendKeys("KARNATAKA");
	       Thread.sleep(5000);
	       WebElement city=driver.findElement(By.xpath("//input[@id='city']"));
	       city.clear();
	       city.sendKeys("hubli");
	       WebElement zipcode=driver.findElement(By.xpath("//input[@id='zipcode']"));
	       zipcode.clear();
	       zipcode.sendKeys("123456");
	       Thread.sleep(5000);
	       WebElement docname=driver.findElement(By.xpath("//input[@id='documentName']"));
	       String actualInputValue111 = docname.getAttribute("value");
	       String expectedValue111 = "Adhaar card";  
	       if (actualInputValue111.equals(expectedValue111)) {
	           System.out.println("Input verification passed: " + actualInputValue111);
	       } else {
	           System.out.println("Input verification failed.");
	           System.out.println("Expected: " + expectedValue111);
	           System.out.println("Actual: " + actualInputValue111);
	       }
	       Thread.sleep(5000);
	       WebElement docnum= driver.findElement(By.xpath("//input[@id='documentNumber']"));
	       String actualInputValue1111 =docnum.getAttribute("value");
	       String expectedValue1111 = "000000001";  
	       if (actualInputValue1111.equals(expectedValue1111)) {
	           System.out.println("Input verification passed: " + actualInputValue1111);
	       } else {
	           System.out.println("Input verification failed.");
	           System.out.println("Expected: " + expectedValue1111);
	           System.out.println("Actual: " + actualInputValue1111);
	       }
	       Thread.sleep(5000);
	       WebElement fileInput=driver.findElement(By.xpath("//input[@id='fileUpload']"));
	       fileInput.sendKeys("E:\\OneDrive\\Pictures\\Screenshots\\avila.png"); 
	       Thread.sleep(5000);
	       WebElement updatebutton=driver.findElement(By.xpath("//button[text()='Update']"));
	       updatebutton.click();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//*[@id=\"v-pills-privacyPolicy-tab\"]")).click();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("//*[@id=\"v-pills-terms-tab\"]")).click();
	       Thread.sleep(5000);
	       driver.findElement(By.xpath("(//*[@id=\"logoutlink\"])[2]")).click();
	}
	   
	 @Test(priority=11)
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