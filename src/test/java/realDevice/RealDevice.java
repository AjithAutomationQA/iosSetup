package realDevice;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

public class RealDevice {

	static AppiumDriver<MobileElement> driver;
	static String reportlocation;
	static String reportlocation2;
	static ExtentReports extent;

	@BeforeSuite
	public static void report() {
		System.out.println("Started");

		reportlocation = "target/RealIOSdevice.html";
		reportlocation2 = "target/FailedCases.html";

		extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter(reportlocation);
		ExtentSparkReporter failedspark = new ExtentSparkReporter(reportlocation2).filter().statusFilter()
				.as(new Status[] { Status.FAIL }).apply();
		extent.attachReporter(spark);

		spark.config().setTheme(Theme.DARK);
		spark.config().setDocumentTitle("AppStore");
		spark.config().setReportName("Search in the AppStore");

		failedspark.config().setTheme(Theme.DARK);
		failedspark.config().setDocumentTitle("Report-Failed Testcases");
		failedspark.config().setReportName("Failed Cases");

		extent.attachReporter(spark, failedspark);
		
		//String a =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

	}

	@AfterSuite
	public static void reportexit() throws Throwable {
		System.out.println("in report flush");
		extent.flush();

		Desktop.getDesktop().browse(new File(reportlocation).toURI());
		Desktop.getDesktop().browse(new File(reportlocation2).toURI());

	}
	
	@BeforeMethod
	public static void  appLaunch() throws Throwable{
		
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("platformName", "ios");
		desiredCapabilities.setCapability("appium:platformVersion", "15.6.1");
		desiredCapabilities.setCapability("appium:deviceName", "Ajith’s iPhone");
		desiredCapabilities.setCapability("appium:udid", "ff3cf389c16dce03fb1ebbf138f3a4d0bece6ba7");
		desiredCapabilities.setCapability("appium:automationName", "XCUITest");
		desiredCapabilities.setCapability("appium:bundleId", "com.apple.AppStore");
		driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);

	
	}

	@Test()
	public static  String base64Screenshot () throws Throwable {

		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

	}

	@Test
	public static void appStore() throws Throwable {

		ExtentTest appStoreIOS = extent.createTest("Real Device Setup").assignAuthor("Ajith Kumar")
				.assignCategory("Smoke Test").assignDevice("Iphone 7");

		appStoreIOS.pass("Home screen",
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot()).build());
		
		WebDriverWait wait = new WebDriverWait(driver, 90);

		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//XCUIElementTypeButton[@name=\"UIA.AppStore.TabBar.search\"]")));

		MobileElement SearchButton = driver
				.findElementByXPath("//XCUIElementTypeButton[@name=\"UIA.AppStore.TabBar.search\"]");

		SearchButton.click();
		
		appStoreIOS.pass("Search button",
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot()).build());

		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//XCUIElementTypeNavigationBar[@name=\"Search\"]/XCUIElementTypeSearchField")));

		MobileElement SearchBar = driver
				.findElementByXPath("//XCUIElementTypeNavigationBar[@name=\"Search\"]/XCUIElementTypeSearchField");

		String search = "answerconnect";

		SearchBar.sendKeys(search);
		appStoreIOS.pass("Text entered is: " + search);
		appStoreIOS.pass("Value entered in search bar",
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot()).build());
		
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//XCUIElementTypeButton[@name=\"Clear text\"]")));

		MobileElement ClearButton = driver
				.findElementByXPath("//XCUIElementTypeButton[@name=\"Clear text\"]");


	

		ClearButton.clear();
		
		appStoreIOS.pass("Clear button clicked",
				MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot()).build());

		appStoreIOS.fail("scroll fail");
		appStoreIOS.info("scroll info");
		appStoreIOS.fail(MarkupHelper.createLabel("just an fail", ExtentColor.RED));

	}

}
