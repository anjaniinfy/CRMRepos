package com.crm.qa.tests;


import java.io.IOException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.crm.qa.pages.ContactPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;
import com.crm.qa.base.TestBase;
import com.crm.qa.utils.TestUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ContactPageTest2 extends TestBase {
	LoginPage loginPage;
	HomePage homePage;
	ContactPage contactPage;
	TestUtil testUtil;
	String sheetName = "Contacts";
	public ExtentReports extent;
	public ExtentTest logger;

	public ContactPageTest2() {
		super();
	}

	@BeforeTest
	public void setExtent() {
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentReport.html", true);
		//extent.loadConfig(new File(System.getProperty("user.dir") + "extent-config.xml"));
	}

	@BeforeMethod
	public void setup() throws InterruptedException {
		intialization();
		loginPage = new LoginPage();
		contactPage = new ContactPage();
		testUtil = new TestUtil();
		log.info("Login into CRM application");

		homePage = loginPage.login("anjani18", "anjani@20");
		log.info("Switching the frame");
		testUtil.switchToFrame();
		log.info("User is clicking on Contact hyperlink/tab");
		contactPage = homePage.clickingOnContactLink();
	}

	@Test(priority = 1)
	public void verifyContactLevelTest() {
		logger = extent.startTest("verifyContactLevelTest");
		log.info("**********************Contact Label TestCase TC7*****************************");
		Assert.assertTrue(contactPage.verifyContactLabel(), "contact label is missing on the page");
		logger.log(LogStatus.PASS, "Contact Label TestCase TC7");
		extent.endTest(logger);
	}

	@Test(priority = 2)
	public void selectsingleContactTest() {
		logger = extent.startTest("selectsingleContactTest");
		log.info("********************** select single Contact TC8*****************************");
		contactPage.selectContactByName("aakhil Raj");
		logger.log(LogStatus.PASS, " select single Contact TC8 ");
		extent.endTest(logger);
	}

	@Test(priority = 3)
	public void selectMultipleContactTest() {
		logger = extent.startTest("selectMultipleContactTest");
		log.info("********************** select Multiple Contact TC9 *****************************");
		contactPage.selectContactByName("aacs Joshi");
		contactPage.selectContactByName("aakhil Raj");
		logger.log(LogStatus.PASS, "select Multiple Contact from contacts tab newly opened");
		// extent.endTest(logger);
	}

	@DataProvider
	public Object[][] getDataFromDataSheet() {
		log.info("User is fetching data from excel through data provider");
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}

	@Test(priority = 4, dataProvider = "getDataFromDataSheet")
	public void validateCreateNewContact(String title, String firstName, String lastName, String Company) {
		logger = extent.startTest("validateCreateNewContact");
		log.info("**********************Validate Creat New Contact TC10/TC11*****************************");
		homePage.clickOnNewContactLink();
		// contactPage.createNewContact("Mr.", "Ram", "Kumar", "Google");
		contactPage.createNewContact(title, firstName, lastName, Company);
		logger.log(LogStatus.PASS, "Creating new contact from excel data is completed");
		// extent.endTest(logger);
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		log.info("Close the Browser ");
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
			logger.log(LogStatus.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in //
																						// extent report /
			String screenshotPath = TestUtil.getScreenshot(driver, result.getName());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenshotPath)); // to add screenshot in extent //
																					// report // report
			// extentTest.log(LogStatus.FAIL, extentTest.addScreencast(screenshotPath));
			// //to add screencast/video in extent report
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());
		}
		extent.endTest(logger); // ending test and ends the current test and prepare to create html report*/
		// Runtime.getRuntime().exec("taskkill /F /IM ChromeDriver.exe");
		driver.quit();
	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}
}
