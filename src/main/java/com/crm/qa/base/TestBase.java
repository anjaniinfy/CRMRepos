package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.crm.qa.tests.ContactPageTest;
import com.crm.qa.utils.TestUtil;
import com.crm.qa.utils.WebEventListener;

public class TestBase {
	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static Logger log = Logger.getLogger(ContactPageTest.class);

	public TestBase() {
		try {
			prop = new Properties();
			//log.info("Fetching url,credentiles from properties file");
			FileInputStream io = new FileInputStream(
					"C:\\Users\\Anjani\\eclipse-workspace\\CRM\\src\\main\\java\\com\\crm\\qa\\config\\config.properties");
			prop.load(io);
		} catch (FileNotFoundException e) {
			e.getStackTrace();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static void intialization() {
		String browserName = prop.getProperty("browser");
		log.info("Checking for which browser we are running our script");
		if (browserName.equals("chrome")) {
			log.info("Chrome driver is getting invoked");
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\Anjani\\eclipse-workspace\\CRM\\src\\main\\resources\\drivers-exe\\chromedriver.exe");
		//ChromeOptions options= new ChromeOptions();
		//	options.addArguments("disable-info");		
		// = new ChromeDriver(options);
			log.info("Chrome Browser is starting");
			driver = new ChromeDriver();
			
		} 
		else if (browserName.equals("IE")) {
			log.info("IE  driver is getting invoked");
			System.setProperty("webdriver.ie.driver",
					"C:\\Users\\Anjani\\eclipse-workspace\\CRM\\src\\main\\resources\\drivers-exe\\IEDriverServer.exe");
			log.info("IE Browser is starting");
			driver = new InternetExplorerDriver();
		}
		log.info("Invoking the Event listener class for console action");
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIME, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		log.info("Url is getting fetched");
		driver.get(prop.getProperty("url"));
	}
}
