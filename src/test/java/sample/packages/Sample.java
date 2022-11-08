package sample.packages;

import java.awt.Desktop;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.github.javafaker.Faker;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Sample {
	static AppiumDriver<MobileElement> driver;
	static String reportlocation;
	static String reportlocation2;
	static ExtentReports extent;

	@BeforeSuite
	public static void report() {
		System.out.println("Started");
//		AppiumDriverLocalService service;
//		service = new AppiumServiceBuilder()
//		       .withIPAddress("0.0.0.0")
//		      // .withArgument(GeneralServerFlag., "/wd/hub")
//		       .usingPort(4723)
//		       .build();
//		service.start();
		


		reportlocation = "target/Spark.html";
		reportlocation2 = "target/failSpark.html";

		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(reportlocation);
		ExtentSparkReporter failedspark = new ExtentSparkReporter(reportlocation2).filter().statusFilter()
				.as(new Status[] { Status.FAIL }).apply();
		extent.attachReporter(spark);

		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("MyReport");
		spark.config().setReportName("sample");

		failedspark.config().setTheme(Theme.DARK);
		failedspark.config().setDocumentTitle("MyReport-Failed");
		failedspark.config().setReportName("sample fail");

		extent.attachReporter(spark, failedspark);

	}

	@AfterSuite
	public static void reportexit() throws Throwable {
		System.out.println("in report flush");
		extent.flush();
		// emailSend();
		Desktop.getDesktop().browse(new File(reportlocation).toURI());
		Desktop.getDesktop().browse(new File(reportlocation2).toURI());

	}
	public static void main(String[] args) throws Throwable {
		Faker fake = new Faker();
		String firstname =  fake.name().firstName();
		String lastName =  fake.name().lastName();
		String username =  fake.internet().emailAddress();
		String ext = fake.phoneNumber().extension();
		String phonenumber = fake.phoneNumber().cellPhone();
		String phn = fake.phoneNumber().subscriberNumber(5);
		
		//System.out.println("Time of execution: " + new Date().get);
		

		reportlocation = "target/Spark.html";
		reportlocation2 = "target/failSpark.html";

		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(reportlocation);
		ExtentSparkReporter failedspark = new ExtentSparkReporter(reportlocation2).filter().statusFilter()
				.as(new Status[] { Status.FAIL }).apply();
		extent.attachReporter(spark);

		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("MyReport");
		spark.config().setReportName("sample");

		failedspark.config().setTheme(Theme.DARK);
		failedspark.config().setDocumentTitle("MyReport-Failed");
		failedspark.config().setReportName("sample fail");

		extent.attachReporter(spark, failedspark);


		System.out.println(firstname);
		System.out.println(lastName);
		System.out.println(username);
		System.out.println(phonenumber);
		System.out.println(ext);
		System.out.println(phn);
		System.out.println();
		
		ExtentTest logintest = extent.createTest("login test").assignAuthor("Ajith").assignCategory("regression")
				.assignDevice("iphone 11");

		logintest.pass("login page");
		logintest.info(MarkupHelper.createLabel("just an info", ExtentColor.GREY));
//	logintest.fail("login fail");
		logintest.info("login info");
		logintest.pass("login screen",
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot()).build());
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("platformName", "ios");
		desiredCapabilities.setCapability("appium:platformVersion", "15.6.1");
		desiredCapabilities.setCapability("appium:deviceName", "Ajith’s iPhone");
		desiredCapabilities.setCapability("appium:udid", "ff3cf389c16dce03fb1ebbf138f3a4d0bece6ba7");
		desiredCapabilities.setCapability("appium:automationName", "XCUITest");
		desiredCapabilities.setCapability("appium:bundleId", "com.apple.AppStore");
		driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
		
		System.out.println("in report flush");
		extent.flush();
		// emailSend();
		Desktop.getDesktop().browse(new File(reportlocation).toURI());
		Desktop.getDesktop().browse(new File(reportlocation2).toURI());



	}

//	public static void emailSend() throws EmailException {
//		System.out.println("in email send");
//		Email email = new SimpleEmail();
//		email.setHostName("smtp.gmail.com");
//		email.setSmtpPort(465);
//		email.setAuthenticator(new DefaultAuthenticator("ajithkumar454win@gmail.com", "Icandoit@2022"));
//		email.setSSLOnConnect(true);
//		email.setFrom("ajithkumar454win@gmail.com");
//		email.setSubject("TestMail");
//		email.setMsg("This is a test mail");
//		email.addTo("ajithkumar454win@gmail.com");
//		email.send();
//	}

	@Test
	public static void first() throws Throwable {

		ExtentTest logintest = extent.createTest("login test").assignAuthor("Ajith").assignCategory("regression")
				.assignDevice("iphone 11");

		logintest.pass("login page");
		logintest.info(MarkupHelper.createLabel("just an info", ExtentColor.GREY));
//	logintest.fail("login fail");
		logintest.info("login info");

		ExtentTest logintest2 = extent.createTest("menu test").assignAuthor("kumar").assignCategory("smoke")
				.assignDevice("iphone 12");
		logintest2.pass("menu page");
		logintest.pass(MarkupHelper.createLabel("just an pass", ExtentColor.GREEN));
		// logintest2.fail("menu fail");
		logintest2.info(" menu info");

		ExtentTest logintest3 = extent.createTest("scroll test").assignCategory("retest").assignDevice("iphone 13");
		logintest3.pass("scroll page");
		logintest3.fail("scroll fail");
		logintest3.info("scroll info");
		logintest3.fail(MarkupHelper.createLabel("just an fail", ExtentColor.RED));

		// DesiredCapabilities capabilites = new DesiredCapabilities();
		System.out.println("in dc");
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("platformName", "ios");
		desiredCapabilities.setCapability("appium:platformVersion", "15.5");
		desiredCapabilities.setCapability("appium:deviceName", "Ajith’s iPhone");
		desiredCapabilities.setCapability("appium:udid", "ff3cf389c16dce03fb1ebbf138f3a4d0bece6ba7");
		desiredCapabilities.setCapability("appium:automationName", "XCUITest");
		// desiredCapabilities.setCapability(MobileCapabilityType.APP,
		// "/Applications/AnswerForce.app");

		desiredCapabilities.setCapability("appium:bundleId", "com.saucelabs.SwagLabsMobileApp");
		// AppiumDriver driver = new IOSDriver(new URL("http://0.0.0.0:4723/wd/hub"),
		// desiredCapabilities);

		driver = new IOSDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);

		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement email = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//XCUIElementTypeTextField[@name=\"test-Username\"]")));
		email.sendKeys("standard_user");

		WebElement password = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//XCUIElementTypeSecureTextField[@name=\"test-Password\"]")));
		password.sendKeys("secret_sauce");

		WebElement loginbutton = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//XCUIElementTypeOther[@name=\"test-LOGIN\"]")));
		loginbutton.click();

		logintest.pass("login screen",
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot()).build());

		HashMap<String, Object> scrollobject = new HashMap<String, Object>();
		JavascriptExecutor js = driver;
		scrollobject.put("direction", "down");
		scrollobject.put("xpath", "//XCUIElementTypeStaticText[@name=\"© 2022 Sauce Labs. All Rights Reserved.\"]");
		js.executeScript("mobile:scroll", scrollobject);
		driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name=\"© 2022 Sauce Labs. All Rights Reserved.\"]"))
				.click();

		WebElement menu = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//XCUIElementTypeOther[@name=\"test-Menu\"]")));
		menu.click();

		TouchAction swipe = new TouchAction(driver).press(PointOption.point(762, 372))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(20, 372))
				.release().perform();

//		MobileElement firstField = 	 (MobileElement) driver.findElementsByAccessibilityId("test-Username");
//				wait.until(ExpectedConditions
//				.visibilityOf(firstField));

		// IOSTouchAction iosTouchAction = new IOSTouchAction(driver);
		// iosTouchAction.doubleTap(element(firstField));

// 
		MobileElement AddCart = driver.findElementByXPath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[1]");
	String value =	AddCart.getText();
	System.out.println(value);
		
		MobileElement firstField = driver.findElementByXPath("(//XCUIElementTypeOther[@name=\"test-ADD TO CART\"])[5]");

		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		HashMap<String, Object> tapObject = new HashMap<String, Object>();
		tapObject.put("x", firstField.getSize().getWidth() / 2);
		tapObject.put("y", firstField.getSize().getHeight() / 2);
		tapObject.put("element", firstField.getId());
		js1.executeScript("mobile:tap", tapObject);

		System.out.println("double tap");
//	
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		HashMap<String, Object> doubleTap = new HashMap<String, Object>();
		doubleTap.put("x", firstField.getSize().getWidth() / 2);
		doubleTap.put("y", firstField.getSize().getHeight() / 2);
		doubleTap.put("element", firstField.getId());
		js2.executeScript("mobile:doubleTap", doubleTap);
		
		MobileElement remove  = driver.findElementByXPath("//XCUIElementTypeOther[@name=\"test-REMOVE\"]");
	String removeFromCart =	remove.getText();
		
		System.out.println(removeFromCart);
		
//		JavascriptExecutor js3 = (JavascriptExecutor) driver;
//		HashMap<String, Object> longClick = new HashMap<String, Object>();
//		longClick.put("x", firstField.getSize().getWidth() / 2);
//		longClick.put("y", firstField.getSize().getHeight() / 2);
//		longClick.put("element", firstField.getId());
//		js3.executeScript("mobile:longClick", longClick);
//		
		
		
		// URL remoteUrl = new URL("http://127.0.0.1:4723/wd/hub");

		// capabilites.setCapability (MobileCapabilityType. PLATFORM_VERSION, "16.0");
		// capabilites.setCapability (MobileCapabilityType.DEVICE_NAME, "iPhone 14");
		// capabilites.setCapability (MobileCapabilityType.AUTOMATION_NAME,
		// AutomationName.IOS_XCUI_TEST);
		// System.out.print("aji2");
		// // capabilites.setCapability (IOSMobileCapabilityType.LAUNCH_TIMEOUT,
		// 500000);
		// capabilites.setCapability ("commandTimeouts", "12000"); //.app
		// System.out.print("aji3");
		// // capabilites.setCapability(MobileCapabilityType.UDID,
		// "4AB6329D-9740-4351-A8E2-4CE4CAA45A96");
		// // capabilites.setCapability(IOSMobileCapabilityType.BUNDLE_ID,
		// "com.saucelabs.SwagLabsMobileApp");
		// System.out.print("aji4");
		// capabilites.setCapability(MobileCapabilityType.UDID,
		// "4AB6329D-9740-4351-A8E2-4CE4CAA45A96");
		// capabilites.setCapability(IOSMobileCapabilityType.BUNDLE_ID,
		// "com.saucelabs.SwagLabsMobileApp");
		// // driver = new IOSDriver<>(new URL("http://localhost:4723/wd/hub"), dc);
		// // IOSDriver driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"),
		// capabilites);
		// IOSDriver driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"),
		// capabilites);
		// System.out.print("aji5");

		// desiredCapabilities.setCapability("appium:includeSafariInWebviews", true);
		// desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
		// desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

		/////////////

		// IOS_XCUI_Test
	}

	public static String base64Screenshot() {

		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	}
}
