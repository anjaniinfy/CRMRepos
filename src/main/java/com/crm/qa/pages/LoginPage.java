package com.crm.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class LoginPage extends TestBase {

	// Page Factory-OR

	@FindBy(xpath = "//input[@name='username']")
	WebElement username;

	@FindBy(xpath = "//input[@name='password']")
	WebElement password;

	@FindBy(xpath = "//input[@type='submit']")
	WebElement btnLogin;

	@FindBy(xpath = "//a[contains(text(),'Sign Up')]")
	WebElement btnSignUp;

	@FindBy(xpath = "//img[@src='https://classic.crmpro.com/img/logo.png']")
	WebElement crmlogo;

	// Initializing the driver
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}

	// Actions
	public String validateLoginPage() {
		return driver.getTitle();
	}

	public boolean validateCRMImage() {
		return crmlogo.isDisplayed();
	}

	public HomePage login(String usn, String pswd) throws InterruptedException {
		log.info("Inserting username");
		username.sendKeys(usn);
		log.info("Inserting password");
		password.sendKeys(pswd);
		Thread.sleep(2000);
		log.info("Click on login button for login to application");
		btnLogin.click();
		log.info("Home page should get open");
		return new HomePage();
	}
}
