package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.utilityFunctions;

public class UnitPage {

	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;

	utilityFunctions utlFn;

	By mainFrame = By.id("mainFrame");
	By associateFrame = By.id("associateFrame");

	// Textboxs
	By buildingIdTextBox = By.id("txtSearchBuildingId");
	By gridBuildingId = By.id("txtBuildingId");
	By searchUnitTextBox = By.id("txtSearchUnitNo");
	By unitNumberTextBox = By.id("txtUnitNumber");
	By unitNameTextBox = By.id("txtUnitName");
	By customerUnitNameTextbox = By.id("txtCustomerUnitName");
	By productButton = By.id("btnProduct");
	By unitTypeButton = By.id("btnUnitType");
	By maintenanceTerritoryButton = By.id("btnTerritory");
	By buildingName = By.id("txtBuildingName");

	// Grids
	By sideLinksSubSection = By.xpath("//tbody[@id='subSection']//tr//td[1]");
	By associateGrid = By.xpath("//table[@id='grdAssociate']//tbody//tr");
	By productGrid = By.xpath("//table[@id='grdAssociate']//tbody/tr[1]");
	By unitGrid = By.xpath("//*[@id='grdUnits']//tbody//tr//td[2]");
	By unitTypeGrid = By.xpath("//table[@id='grdUnitType']//tbody//tr//td[2]");
	By verticalGrid = By.xpath("//table[@id='grdVertical']//tbody//tr//td[2]");
	By tierGrid = By.xpath("//table[@id='grdAssociate']//tbody/tr[1]");

	// Buttons
	By selectButton = By.id("btnSelect");
	By associateSearchTextBox = By.id("txtSearch");
	By associateSearchButton = By.id("btnSearch");
	By tierButton = By.id("btnTier");
	By verticalButton = By.id("btnVertical");

	By newButton = By.id("btnNew");
	By saveButton = By.id("btnSave");
	By deleteButton = By.id("btnDelete");

	public UnitPage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 30);
	}

	// Frame functions
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}

	public void switchToAssociateFrame() {
		// driver.switchTo().frame(driver.findElement(associateFrame));
		driver.switchTo().frame(0);
	}

	// Textbox functions
	public void setBuildingId(String buildingId) {
		this.switchToMainFrame();
		utlFn.fastType(driver.findElement(buildingIdTextBox), buildingId);
	}

	public String getBuildingId() {
		return driver.findElement(gridBuildingId).getAttribute("value");
	}

	public void setCustomerUnitName(String customerUnitName) {
		js.executeScript("arguments[0].value='" + customerUnitName + "';", driver.findElement(customerUnitNameTextbox));
	}

	public void setAssociateSearch(String searchText) {
		driver.findElement(associateSearchTextBox).clear();
		js.executeScript("arguments[0].value='" + searchText + "';", driver.findElement(associateSearchTextBox));
	}

	public void setSearchUnitNumber(String unitNumber) {
		this.switchToMainFrame();
		utlFn.fastType(driver.findElement(searchUnitTextBox), unitNumber);
	}

	public void setUnitNumber(String unitNumber) {
		WebElement unitNumberWE = wait.until(ExpectedConditions.visibilityOfElementLocated(unitNumberTextBox));
		js.executeScript("arguments[0].value='" + unitNumber + "';", driver.findElement(unitNumberTextBox));
	}

	public void setUnitName(String unitName) {
		utlFn.fastType(driver.findElement(unitNameTextBox), unitName);
	}

	// Button functions
	public void clickAssociateSearchButton() {
		driver.findElement(associateSearchButton).click();
	}

	public void clickProductButton() {
		driver.findElement(productButton).click();
	}

	public void clickTierButton() {
		driver.findElement(tierButton).click();
	}

	public void clickUnitTypeButton() {
		driver.findElement(unitTypeButton).click();
	}

	public void clickVerticalButton() {
		driver.findElement(verticalButton).click();
	}

	public void clickSalesTerritoryButton() {
		driver.findElement(maintenanceTerritoryButton).click();
	}

	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

	public void clickSaveButton() {
		driver.findElement(saveButton).sendKeys("" + Keys.TAB);
		driver.findElement(saveButton).click();
	}

	public void clickDeleteButton() {
		driver.findElement(deleteButton).click();
	}

	public void clickNewButton() {
		driver.findElement(newButton).click();
	}

	// Misc functions
	public void selectUnitFromGrid(String unitNumber) {
		List<WebElement> ls = driver.findElements(unitGrid);
		for (WebElement we : ls) {
			if (unitNumber.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void selectTierFromGrid() {
		List<WebElement> ls = driver.findElements(tierGrid);
		for (WebElement we : ls) {
			we.click();
			break;
		}
	}

	public void setUnitType(String unitType) {
		this.clickUnitTypeButton();
		List<WebElement> ls = driver.findElements(unitTypeGrid);
		for (WebElement we : ls) {
			if (unitType.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void setVertical() throws InterruptedException {
		this.clickVerticalButton();
		Thread.sleep(1000);
		List<WebElement> ls = driver.findElements(verticalGrid);
		for (WebElement we : ls) {
			we.click();
			break;
		}
	}

	public void setUnitTypeEdit() {
		this.clickUnitTypeButton();
		List<WebElement> ls = driver.findElements(unitTypeGrid);
		for (WebElement we : ls) {
			we.click();
			break;
		}
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

	public void setSalesTerritory(String salesTerritory) throws InterruptedException {
		this.setAssociateSearch(salesTerritory);
		this.clickAssociateSearchButton();
		Thread.sleep(1000);
		driver.findElement(associateGrid).findElement(By.xpath("td[2]")).click();
	}

	public void setProduct(String product) throws InterruptedException {

		this.setAssociateSearch(product);
		// this.clickAssociateSearchButton();
		Thread.sleep(1000);
		// driver.findElement(associateGrid).findElement(By.xpath("td[2]")).click();
		driver.findElement(associateGrid).findElement(By.xpath("td[2]")).click();

	}

	public String[] getUnitNumber() {
		utlFn.retryingFindClick(unitNumberTextBox);
		return new String[] { driver.findElement(unitNumberTextBox).getAttribute("value") };
	}

	public void waitForBuildingDetailsToLoad() {
		utlFn.waitForDetailsToLoad(driver.findElement(buildingName), "value");
	}

	public void waitForBuildingIdToLoad() {
		utlFn.waitForDetailsToLoad(driver.findElement(buildingIdTextBox), "value");
	}

}
