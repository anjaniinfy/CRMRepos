package com.crm.qa.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.crm.qa.pages.ContactPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.base.TestBase;
import com.crm.qa.utils.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class HomePageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	ContactPage contactPage;
	TestUtil testUtil;
	public ExtentReports extentReport;
	public ExtentTest extentTest;

	public HomePageTest() {
		super();
	}
	

	// test case should be executed--independent with each other
	// before each test case -- launch the browser and login application
	// @Test--Execute test cases
	// after each test case close the browser
	@BeforeTest
	public void setExtent() {
		extentReport=new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html",true) ; 
		extentReport.addSystemInfo("Host Name", "Anjani Window");
		extentReport.addSystemInfo("User Name", "Anjani Automation Labs");
		extentReport.addSystemInfo("Environment", "QA");
		
	}
	
	@BeforeMethod
	public void setup() throws InterruptedException {
		intialization();
		loginPage = new LoginPage();
		contactPage = new ContactPage();
		testUtil = new TestUtil();
		log.info("Login into FREECRM application");
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test(priority = 1)
	public void verifyHomePageTitleTest() {
		extentTest=extentReport.startTest("verifyHomePageTitleTest");
		log.info("Validate Homepage Logo TC4");
		String hometitle = homePage.verifyHomePageTitle();
		Assert.assertEquals(hometitle, "CRMPRO", "Home Page title not matching");
	}

	@Test(priority = 2)
	public void verifyUserNameTest() {
		extentTest=extentReport.startTest("verifyUserNameTest");
		log.info("Validate UserName  TC5");
		testUtil.switchToFrame();
		boolean name = homePage.verifyUserName();
		Assert.assertTrue(name);
	}

	@Test(priority = 1)
	public void verifyContactLinkTest() {
		extentTest=extentReport.startTest("verifyContactLinkTest");
		log.info("Validate contact link is working TC6");
		log.info("USer hould able to Switch the frame");
		testUtil.switchToFrame();
		log.info("User is clicking on Contact Link tab/hyperlink");
		contactPage = homePage.clickingOnContactLink();
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		log.info("Close the Browser ");
		if (result.getStatus() == ITestResult.FAILURE) {
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in  extent report																					/
			String screenshotPath = TestUtil.getScreenshot(driver, result.getName());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(screenshotPath)); // to add screenshot in extent report																				// report
			// extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath));//to add screencast/video in extent report
		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());
		}
		extentReport.endTest(extentTest); // ending test and ends the current test and prepare to create html report
		driver.quit();
// Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");

	}
@AfterTest
public void endReport() {
	extentReport.flush();
	extentReport.close();
	
}

	
}
