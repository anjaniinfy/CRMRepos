package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class HomePage extends TestBase {

	// Initializing the driver
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	// Page Factory-OR
	@FindBy(xpath = "//td[contains(text(),'User: Anjani kumar')]")
	WebElement userNameLabel;

	@FindBy(xpath = "//td[contains(text(),'CRMPRO')]")
	WebElement titleHome;

	@FindBy(xpath = "//a[@title='Contacts']")
	WebElement contactsLink;

	//@FindBy(xpath = "//*[@id='navmenu']/ul/li[4]/ul/li[1]/a")
	//WebElement newcontactsLink;
	
	@FindBy(linkText="New Contact")
	WebElement newcontactsLink;

	@FindBy(xpath = "//a[contains(text(),'Deals')]")
	WebElement dealsLink;

	@FindBy(xpath = "//a[contains(text(),'Tasks')]")
	WebElement tasksLink;

	// Actions

	public String verifyHomePageTitle() {
		return driver.getTitle();
	}

	public boolean verifyUserName() {
		return userNameLabel.isDisplayed();

	}

	public ContactPage clickingOnContactLink() {
		contactsLink.click();
		return new ContactPage();
	}

	public DealPage clickOnDealPage() {
		dealsLink.click();
		return new DealPage();
	}

	public DealPage clickOnTasklPage() {
		tasksLink.click();
		return new DealPage();
	}

	public void clickOnNewContactLink() {
		log.info("invoking action command for moving to subtab");
		Actions action = new Actions(driver);
		action.moveToElement(contactsLink).build().perform();
		newcontactsLink.click();

	}

}
