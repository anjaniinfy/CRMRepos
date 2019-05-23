package com.crm.qa.tests;


import java.io.IOException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.utils.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.crm.qa.base.TestBase;

public class LoginPageTest extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	TestUtil util;
	public ExtentReports extent;
	public ExtentTest extentTest;

	public LoginPageTest() {
		super();
	}

	@BeforeTest
	public void setExtent() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
		extent.addSystemInfo("Host Name", "Anjani Window");
		extent.addSystemInfo("User Name", "Anjani Automation Labs");
		extent.addSystemInfo("Environment", "QA");
	}

	@BeforeMethod
	public void setup() {
		intialization();
		loginPage = new LoginPage();
	}

	/*@Test(priority = 3)
	public void loginPageValidateTitleTest() {
		extentTest = extent.startTest("loginPageValidateTitleTest");
		log.info("Validate Login page Logo TC1");
		String title = loginPage.validateLoginPage();
		Assert.assertEquals(title, "CRMPRO - CRM software for customer relationship management, sales, and support.");
		log.info("loginPage title is present");
		log.warn("loginPage title is having Warning message");
		log.fatal("loginPage title is having  fatal message");
		log.error("loginPage title is having  error message");
		log.debug("loginPage title is having  debug message");
	}

	@Test(priority = 2)
	public void crmLogoImgageTest() {
		extentTest = extent.startTest("crmLogoImgageTest");
		log.info("Validate Login page Image TC2");
		boolean crmimg = loginPage.validateCRMImage();
		Assert.assertTrue(crmimg);
	}*/

	@Test(priority = 1)
	public void loginTest() throws InterruptedException {
		extentTest = extent.startTest("loginTest");
		log.info("Validate Login to CRM application is scuccessful TC3");
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));

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
		extent.endTest(extentTest); // ending test and ends the current test and prepare to create html report
		driver.quit();
// Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");

	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}
}
