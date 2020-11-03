package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;

public class ContactNotesPopUp {
	
	WebDriver driver;
	JavascriptExecutor js;
	
	utilityFunctions utlFn; 
	
	By searchContactPersonButton = By.id("btnSearchContactPerson");
	By searchText = By.id("txtSearch");
	By contactId = By.id("rdSearchById");
	By searchContactButton = By.id("btnSearch");
	By contactGrid = By.xpath("//table[@id='grdSearch']/tbody/tr/td[1]");
	By selectContact = By.id("btnShow");
	By authorizedCaller = By.id("chkAuthorizedCaller");
	By saveContact = By.id("btnSave");
	By closeButton = By.id("btnClose");
	
	
	public ContactNotesPopUp(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
	}
	
	public void switchToFrameContainer() {
		//driver.switchTo().frame(driver.findElement(frameContainer));
		driver.switchTo().frame(0);
	}
	
	public void clickSearchContactPersonButton() {
		driver.findElement(searchContactPersonButton).click();
	}
	
	public void setSearchText(String contactId) {
		//this.switchToMainFrame();
		driver.findElement(searchText).sendKeys(contactId);
	}
	
	public void selectSearchBy() {
		driver.findElement(contactId).click();
	}
	
	public void searchContactButton() {
		driver.findElement(searchContactButton).click();
	}	
	
	public void selectContactGrid(String contactId) {
		List<WebElement> ls = driver.findElements(contactGrid);
		for (WebElement we : ls) {
			if (contactId.equals(we.getText())) {
				we.click();
				break;
				}
			}
		}
	
	public void clickSelectContactButton() {

		driver.findElement(selectContact).click();
	}
	
	public void checkAuthorizedCaller() {

		driver.findElement(authorizedCaller).click();
	}

	public void clickSaveButtonContact() {

		driver.findElement(saveContact).click();
	}
	
	public void clickCloseButton() {
		driver.findElement(closeButton).click();
	}
	
}
