package com.crm.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.crm.qa.base.TestBase;

public class ContactPage extends TestBase {

	@FindBy(xpath = "//td[contains(text(),'Contacts')]")
	// used to store value in cache memory so no need to go to UI again and again
	// @CacheLookup
	WebElement contactLable;

	@FindBy(xpath = "//a[text()='Ashwini Raj']//parent::td[@class='datalistrow']//preceding-sibling::td[@class='datalistrow']")
	WebElement checkBoxcontact;

	@FindBy(xpath = "//input[@id='first_name']")
	WebElement firstname;

	@FindBy(xpath = "//input[@id='surname']")
	WebElement lastname;

	@FindBy(xpath = "//input[@name='client_lookup']")
	WebElement companyname;

	@FindBy(xpath = "//*[@id='contactForm']/table/tbody/tr[1]/td/input[2]")
	WebElement savebutn;

	public ContactPage() {
		PageFactory.initElements(driver, this);
	}

	public boolean verifyContactLabel() {
		return contactLable.isDisplayed();
	}

	public void selectContactByName(String  name) {
		driver.findElement(By.xpath("//a[text()='"+name+"']//parent::td[@class='datalistrow']//preceding-sibling::td[@class='datalistrow']")).click();
	}

	public void createNewContact(String title, String fstname, String lstname, String compy) {

		log.info("Selecting values from dropdown with help of action command");
		Select select = new Select(driver.findElement(By.name("title")));
		select.selectByVisibleText(title);
		log.info("Fetching firstName from excel data");
		firstname.sendKeys(fstname);
		log.info("Fetching lastName from excel data");
		lastname.sendKeys(lstname);
		log.info("Fetching company name from excel data");
		companyname.sendKeys(compy);
		savebutn.click();

	}
}