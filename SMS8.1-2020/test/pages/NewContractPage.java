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

public class NewContractPage {

	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;

	utilityFunctions utlFn;

	By mainFrame = By.id("mainFrame");
	By contractNumberTextbox = By.id("txtContractNo");
	By dateTextbox = By.id("txtTransactionDate");
	By unitNoTextbox = By.id("txtFromUnitNo");
	By scheduleBillingCheckbox = By.id("ChkIsScheduleContract");

	By searchSalesTerritoryAssociateButton = By.id("btnSearchTerritory");
	By associateGrid = By.xpath("//table[@id='grdAssociate']/tbody/tr/td[2]");
	By associateSearchTextBox = By.id("txtSearch");
	By associateSearchButton = By.id("btnSearch");
	By selectButton = By.id("btnSelect");
	By contractGroupButton = By.id("btnContractGroup");
	By customerTextbox = By.id("txtCustomerId");

	By newButton = By.id("btnNew");
	By saveButton = By.id("btnSave");
	By deleteButton = By.id("btnDelete");

	By okButton = By.id("btn1");
	By taxButton = By.id("btnTaxCode");
	By taxCodeGrid = By.xpath("//table[@id='grdTaxCode']/tbody/tr/td[2]");
	By contractGroupGrid = By.xpath("//table[@id='grdContractGroup']/tbody/tr/td[2]");
	By contractGroupGrid_forLoad = By.xpath("//table[@id='grdContractGroup']/tbody/tr[1]/td[2]");

	// Data fields
	By buildigIdDetails = By.id("txtBuildingId");

	By checkTaxable = By.id("chkTaxable");

	public NewContractPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		utlFn = new utilityFunctions(driver);
		wait = new WebDriverWait(driver, 10);
	}

	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}

	public void switchToAssociateFrame() {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
		// driver.switchTo().frame(0);
	}

	public void setContractNumber(String contractNumber) {
		this.switchToMainFrame();
		utlFn.fastType(driver.findElement(contractNumberTextbox), contractNumber);
	}

	public void setDate(String date) {
		// js.executeScript("arguments[0].value='"+date+"';",
		// driver.findElement(dateTextbox));
		// utlFn.fastType(driver.findElement(dateTextbox), date);
		driver.findElement(dateTextbox).sendKeys(date + Keys.TAB);
	}

	public void setUnitNo(String unitNo) {
		utlFn.fastType(driver.findElement(unitNoTextbox), unitNo);
	}

	public void clickContractGroupButton() {
		driver.findElement(contractGroupButton).click();
	}

	public void selectContractGroup(String contractGroup) {
		while (true) {
			try {
				if (utlFn.waitForDetailsToLoad(driver.findElement(contractGroupGrid_forLoad), "text")) {
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		List<WebElement> ls = driver.findElements(contractGroupGrid);
		for (WebElement we : ls) {
			if (we.getText().contains(contractGroup)) {
				we.click();
				break;
			}
		}
	}

	public void waitFoDetailsToLoad() {
		// utlFn.waitForDetailsToLoad(driver.findElement(arg0), attribute)
	}

	public void setScheduleBilling() {
		driver.findElement(scheduleBillingCheckbox).click();
	}

	public void clickSalesTerritoryButton() {
		driver.findElement(searchSalesTerritoryAssociateButton).click();
	}

	public void setAssociateSearch(String searchText) {
		js.executeScript("arguments[0].value='" + searchText + "';", driver.findElement(associateSearchTextBox));
	}

	public void clickAssociateSearchButton() {
		driver.findElement(associateSearchButton).click();
	}

	public int returnTransactionType() {
		String[] str = new String[] { driver.findElement(By.id("txtTransactionType")).getAttribute("value") };
		return str.length;
	}

	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

	public void setSalesTerritory(String salesTerritory) {
		List<WebElement> ls = driver.findElements(associateGrid);
		for (WebElement we : ls) {
			if (salesTerritory.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void setCustomer(String customer) {
		utlFn.fastType(driver.findElement(customerTextbox), customer);
	}

	public void clickSaveButton() {
		driver.findElement(saveButton).click();
	}

	public void clickDeleteButton() {
		driver.findElement(deleteButton).click();
	}

	public void clickOKButton() {
		driver.findElement(okButton).click();
	}

	public void checkTaxable() {
		driver.findElement(checkTaxable).click();
	}

	public boolean checkTaxableCheckBox() {
		try {
			return driver.findElement(checkTaxable).isSelected();
		} catch (Exception e) {
			return true;
		}

	}

	public void clickTaxButton() {
		driver.findElement(taxButton).click();
	}

	public void selectTax(String taxCode) {
		List<WebElement> ls = driver.findElements(taxCodeGrid);
		for (WebElement we : ls) {
			if (taxCode.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	// Data fields functions
	// Wait for builfing details to load
	public void waitForBuildingDetails() {
		utlFn.waitForDetailsToLoad(driver.findElement(buildigIdDetails), "value");
	}
}
