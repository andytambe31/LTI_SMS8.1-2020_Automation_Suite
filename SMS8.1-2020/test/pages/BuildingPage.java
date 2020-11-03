package pages;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;

import utility.utilityFunctions;

public class BuildingPage {

	WebDriver driver;
	JavascriptExecutor js;
	utilityFunctions utlFn;

	By mainFrame = By.id("mainFrame");
	By associateFrame = By.id("associateFrame");

	By searchBuildingTextBox = By.id("txtBuildingId");

	By searchBuildingButton = By.id("btnSearchBuilding");
	By associateSearchTextBox = By.id("txtSearch");
	By searchButton = By.id("btnSearch");
	By buildingIdRadioButton = By.id("rdSearchById");
	By buildingGrid = By.xpath("//table[@id='grdSearch']/tbody/tr/td[1]");
	By searchGrid = By.xpath("//table[@id='grdSearch']");
	By selectButton = By.id("btnSelect");
	By arabicNameTextBox = By.id("txtArabicname");

	By searchBranchButton = By.id("btnSearchBranch");
	By buildingTypeButton = By.id("btnBuildingType");
	By GSTNumberButton = By.id("btnGSTNumber");
	By buildingNameTextBox = By.id("txtBuildingName");
	By GSTNoTextBox = By.id("txtGSTNumber");
	By buildingTypeGrid = By.xpath("//table[@id='grdBuildingType']/tbody/tr/td[2]");
	By GSTNumberGrid = By.xpath("//table[@id='grdGSTNumber']/tbody/tr/td[2]");
	By ownerTypeButton = By.id("btnOwnerType");
	By ownerTypeGrid = By.xpath("//table[@id='grdOwnerType']/tbody/tr/td[2]");
	By addressLine1TextBox = By.id("txtAddressLine1");
	By countryButton = By.id("btnCountry");
	By stateButton = By.id("btnState");
	By cityButton = By.id("btnCity");
	By countryGrid = By.xpath("//table[@id='grdCountry']/tbody/tr/td[2]");
	By stateGrid = By.xpath("//table[@id='grdState']/tbody/tr/td[2]");
	By saveButton = By.id("btnSave");
	By deleteButton = By.id("btnDelete");
	By searchSaveButton = By.id("btnShow");
	By associateSearchButton = By.id("btnSearch");

	By frameContainer = By.id("frameContainer");

	By sideLinksSubSection = By.xpath("//tbody[@id='subSection']//tr//td[1]");

	By associateGrid = By.xpath("//table[@id='grdAssociate']//tr//td[2]");
	By linkContacts = By.id("btnLinkContacts");
	By txtBuildingType = By.id("txtBuildingType");

	public BuildingPage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
	}

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

	public void clickSearchBuildingButton() {
		driver.findElement(searchBuildingButton).click();
	}

	public void setAssociateSearch(String searchText) {
		js.executeScript("arguments[0].value='" + searchText + "';", driver.findElement(associateSearchTextBox));
	}

	public void clickBuildingIdRadioButton() {
		driver.findElement(buildingIdRadioButton).click();
	}

	public void clickSearchButton() {

		driver.findElement(searchButton).click();
	}

	public void selectBuildling(String buildingId) {

		// Wait for search results to populate
		utlFn.waitForDetailsToLoad(driver.findElement(searchGrid), "text");

		List<WebElement> ls = driver.findElements(buildingGrid);
		for (WebElement we : ls) {
			if (buildingId.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void setBuildingId(String buildingId) throws InterruptedException {
		this.switchToFrameContainer();
		this.setAssociateSearch(buildingId);
		this.clickBuildingIdRadioButton();
		this.clickSearchButton();
		this.selectBuildling(buildingId);
		this.clickSearchSaveButton();

	}

	public void clickSearchBranchButton() {
		driver.findElement(searchBranchButton).click();
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

	public void setArabicName(String arabicName) {
		js.executeScript("arguments[0].value='" + arabicName + "';", driver.findElement(arabicNameTextBox));

	}

	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

	public void clickSearchSaveButton() {
		driver.findElement(searchSaveButton).click();
	}

	public void setBuildingName(String buildingName) {
		js.executeScript("arguments[0].value='" + buildingName + "';", driver.findElement(buildingNameTextBox));
	}

	public void setGSTNumber(String GSTNo) {
		js.executeScript("arguments[0].value='" + GSTNo + "';", driver.findElement(GSTNoTextBox));
	}

	public void clickBuildingTypeButton() {
		driver.findElement(buildingTypeButton).click();
	}

	public void clickGSTNumberButton() {
		driver.findElement(GSTNumberButton).click();
	}

	public void setBuildingType(String buildingType) {
		this.clickBuildingTypeButton();
		List<WebElement> ls = driver.findElements(buildingTypeGrid);
		for (WebElement we : ls) {

			//if (buildingType.equals(we.getText())) {
				we.click();
				break;
			//}
		}
	}

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

	public String getBuildingType() {
		String buildingType = driver.findElement(txtBuildingType).getAttribute("value");
		return buildingType;
	}

	public void setAddressLine1(String addressLine1) {
		utlFn.fastType(driver.findElement(addressLine1TextBox), addressLine1);
	}

	public void clearAddressLine1() {
		driver.findElement(addressLine1TextBox).clear();
	}

	public String getAddressLine1() {
		return driver.findElement(addressLine1TextBox).getAttribute("value");
	}

	public void clickOwnerTypeButton() {
		driver.findElement(ownerTypeButton).click();
	}

	public void setOwnerType(String ownerType) {
		this.clickOwnerTypeButton();
		List<WebElement> ls = driver.findElements(ownerTypeGrid);
		for (WebElement we : ls) {
			if (ownerType.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void clickCountryButton() {
		driver.findElement(countryButton).click();
	}

	public void clickStateButton() {
		driver.findElement(stateButton).click();
	}

	public void clickCityButton() {
		driver.findElement(cityButton).click();
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

	public void setCountry(String country) {
		this.clickCountryButton();
		List<WebElement> ls = driver.findElements(countryGrid);
		for (WebElement we : ls) {
			if (country.equals(we.getText())) {
				we.click();
				break;
			}
		}
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

	public void clickSaveButton() {
		driver.findElement(saveButton).click();
	}

	public String[] getBuildingId() {
		utlFn.retryingFindClick(searchBuildingTextBox);
		String buildingId[] = { driver.findElement(searchBuildingTextBox).getAttribute("value") };
		return buildingId;
	}

	public void setSearchBuildingNumber(String buildingNumber) {
		this.switchToMainFrame();
		driver.findElement(searchBuildingTextBox).sendKeys(buildingNumber + Keys.ENTER);
	}

	public void clickDeleteButton() {
		driver.findElement(deleteButton).click();
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

	public void clickContacts() {
		driver.switchTo().defaultContent();
		driver.findElement(linkContacts).click();
	}

	// Wait functions
	public void waitForBuildingDetailsToLoad() {
		utlFn.waitForDetailsToLoad(driver.findElement(searchBuildingTextBox), "value");
	}

}
