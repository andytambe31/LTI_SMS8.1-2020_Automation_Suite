package pages;

import java.util.List;

import javax.swing.text.DefaultEditorKit.CutAction;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;

public class CustomerPage {

	WebDriver driver;
	JavascriptExecutor js;
	utilityFunctions utlFn;

	public CustomerPage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
	}

	// Frames
	By mainFrame = By.id("mainFrame");
	By associateFrame = By.id("associateFrame");
	By frameContainer = By.id("frameContainer");

	// Textbox
	By searchCustomerTextBox = By.id("txtCustomerId");
	By associateSearchTextBox = By.id("txtSearch");
	By customerNameTextBox = By.id("txtCustomerName");
	By addressLine1TextBox = By.id("txtAddress1");
	By arabicNameTextBox = By.id("txtArabicname");
	By GSTNoTextBox = By.id("txtGSTNumber");
	By PANNoTextBox = By.id("txtTaxNumber");
	By branchTextBox = By.id("txtBranch");

	// Button
	By searchButton = By.id("btnSearch");
	By selectButton = By.id("btnSelect");
	By searchBranchButton = By.id("btnSearchBranch");
	By countryButton = By.id("btnCountry");
	By stateButton = By.id("btnState");
	By saveButton = By.id("btnSave");
	By deleteButton = By.id("btnDelete");
	By searchSaveButton = By.id("btnShow");
	By searchCustomerButton = By.id("btnSearchCustomer");
	By GSTNumberButton = By.id("btnGSTNumber");
	By PANNumberButton = By.id("btnTaxNumber");
	By cityButton = By.id("btnCity");
	By associateSearchButton = By.id("btnSearch");

	// Radio buttons
	By customerIdRadioButton = By.id("rdSearchById");

	// Grids
	By customerGrid = By.xpath("//table[@id='grdSearch']/tbody/tr/td[1]");
	By sideLinksSubSection = By.xpath("//tbody[@id='subSection']//tr//td[1]");
	By associateGrid = By.xpath("//table[@id='grdAssociate']//tr//td[2]");
	By countryGrid = By.xpath("//table[@id='grdCountry']/tbody/tr/td[2]");
	By stateGrid = By.xpath("//table[@id='grdState']/tbody/tr/td[2]");
	By GSTNumberGrid = By.xpath("//table[@id='grdGSTNumber']/tbody/tr/td[2]");
	By PANNumberGrid = By.xpath("//table[@id='grdPANNumber']/tbody/tr/td[2]");

	// Links
	By linkContacts = By.id("btnLinkContacts");

	// Frame functions
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}

	public void switchToAssociateFrame() {
		// driver.switchTo().frame(driver.findElement(associateFrame));
		driver.switchTo().frame(0);
	}

	public void switchToFrameContainer() {
		driver.switchTo().frame(driver.findElement(frameContainer));
	}

	// Textbox function
	public void setCustomerName(String customerName) {
		js.executeScript("arguments[0].value='" + customerName + "';", driver.findElement(customerNameTextBox));
	}

	public void setAddressLine1(String addressLine1) {
		utlFn.fastType(driver.findElement(addressLine1TextBox), addressLine1);
	}

	public void setArabicName(String arabicName) {
		js.executeScript("arguments[0].value='" + arabicName + "';", driver.findElement(arabicNameTextBox));

	}

	public String[] getCustomerId() {
		String[] customerId = { driver.findElement(searchCustomerTextBox).getAttribute("value") };
		return customerId;
	}

	// Button functions
	public void clickSearchBranchButton() {
		driver.findElement(searchBranchButton).click();
	}

	public void clickCountryButton() {
		driver.findElement(countryButton).click();
	}

	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

	public void clickSaveButton() {
		driver.findElement(saveButton).click();
	}

	public void clickDeleteButton() {
		driver.findElement(deleteButton).click();
	}

	public void clickSearchCustomerButton() {
		try {
			driver.findElement(searchCustomerButton).click();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void clickCustomerIdRadioButton() {
		driver.findElement(customerIdRadioButton).click();
	}

	public void clickSearchButton() {
		driver.findElement(searchButton).click();

	}

	public void clickCityButton() {
		driver.findElement(cityButton).click();
	}

	public void clickSearchSaveButton() {
		driver.findElement(searchSaveButton).click();
	}

	public void setGSTNumber(String GSTNo) {
		js.executeScript("arguments[0].value='" + GSTNo + "';", driver.findElement(GSTNoTextBox));
	}

	public void setPANNumber(String PANNo) {
		js.executeScript("arguments[0].value='" + PANNo + "';", driver.findElement(PANNoTextBox));
	}

	public void clickGSTNumberButton() {
		driver.findElement(GSTNumberButton).click();
	}

	public void clickPANNumberButton() {
		driver.findElement(PANNumberButton).click();
	}

	// Grid functions

	public void setGSTNumberDropDown(String GSTNumber) {
		this.clickGSTNumberButton();
		List<WebElement> ls = driver.findElements(GSTNumberGrid);
		for (WebElement we : ls) {

			if (GSTNumber.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void setPANNumberDropDown(String PANNumber) {
		this.clickPANNumberButton();
		List<WebElement> ls = driver.findElements(PANNumberGrid);
		for (WebElement we : ls) {

			if (PANNumber.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void setBranch(String branch) {
		List<WebElement> ls = driver.findElements(associateGrid);
		for (WebElement we : ls) {
			if (branch.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	// By branchTextBox = By.id("txtBranch");
	public void getBranch() {
		utlFn.waitForDetailsToLoad(driver.findElement(branchTextBox), "value");
	}

	public void setCountry(String country) {
		this.clickCountryButton();
		List<WebElement> ls = driver.findElements(countryGrid);
		for (WebElement we : ls) {
			//if (country.equals(we.getText())) {
				we.click();
				break;
			//}
		}
	}

	public void clickStateButton() {
		driver.findElement(stateButton).click();

	}

	public void setState(String state) {
		this.clickStateButton();
		List<WebElement> ls = driver.findElements(stateGrid);
		for (WebElement we : ls) {
			if (state.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void clickAssociateSearchButton() {
		driver.findElement(associateSearchButton).click();
	}

	public void setCity(String city) throws InterruptedException {
		this.setAssociateSearch(city);
		this.clickAssociateSearchButton();
		Thread.sleep(1000);
		driver.findElement(associateGrid).click();
	}

	public void setAssociateSearch(String searchText) {
		js.executeScript("arguments[0].value='" + searchText + "';", driver.findElement(associateSearchTextBox));
	}

	public void clickOnSideLink(String sideLink) {
		driver.switchTo().defaultContent();
		List<WebElement> ls = driver.findElements(sideLinksSubSection);
		for (WebElement we : ls) {
			if (sideLink.equals(we.getText())) {
				we.click();
			}
		}
	}

	// Custom functions
	public void setCustomerId(String customerId) {
		this.switchToFrameContainer();
		this.setAssociateSearch(customerId);
		this.clickCustomerIdRadioButton();
		this.clickSearchButton();
		this.selectCustomer(customerId);
		this.clickSearchSaveButton();
	}

	public void selectCustomer(String customerId) {

		// Wait for search results to populate
		utlFn.waitForDetailsToLoad(driver.findElement(customerGrid), "text");

		List<WebElement> ls = driver.findElements(customerGrid);
		for (WebElement we : ls) {
			if (customerId.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void clickContacts() {
		driver.switchTo().defaultContent();
		driver.findElement(linkContacts).click();
	}

}
