package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import scripts.TestParameters;
import utility.ReadFromExcel;
import utility.utilityFunctions;

public class ManualOInvoice {

	// Driver
	WebDriver driver;
	WebDriverWait wait;

	// Class references
	utilityFunctions utlFn;
	TestParameters tstParameters = new TestParameters();
	ReadFromExcel read = new ReadFromExcel();

	// paths
	String pathToExcel = "./test/resources/data/";
	String fileName = "BuildingData.xlsx";

	// test data
	String _buildingId = read.readDataFromSpecificPosition(pathToExcel + fileName, "Data", 0, 0);

	public ManualOInvoice(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		wait = new WebDriverWait(driver, 10);
	}

	// Textboxes
	By contractNumberTextbox = By.id("txtContractNo");
	By billAmount = By.id("txtBillAmount");
	By billFrom = By.id("txtBillFrom");
	By billTo = By.id("txtBillTo");
	By description = By.id("txtDescription");

	// Field
	By buildingId = By.id("txtBuildingId");

	// Dropdown
	By invoiceGroupDDBtn = By.id("btnInvoiceGroup");

	// Grid
	By invoiceGroupGrid = By.xpath("//table[@id='grdInvoiceGroup']/tbody/tr[1]/td[2]");
	By manualInvoiceGrid = By.xpath("//tr[contains(@class,'nETable')]");

	// Buttons
	By saveButton = By.id("btnSave");

	// **************Functions**************

	// Frame function
	public void switchToMainFrame() {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
	}

	// Texbox functions

	public void setcontractNumber(String contractNumber) {
		this.switchToMainFrame();
		// utlFn.fastType(driver.findElement(contractNumberTextbox), contractNumber);
		driver.findElement(contractNumberTextbox).sendKeys(contractNumber + Keys.TAB);

		// Wait for details to be loaded
		this.waitForContractDetails();
	}

	public void setBillAmount(String _billAmount) {
		utlFn.fastType(driver.findElement(billAmount), _billAmount);
	}

	public void setBillFrom(String _billFrom) {
		utlFn.fastType(driver.findElement(billFrom), _billFrom);
	}

	public void setBillTo(String _billTo) {
		utlFn.fastType(driver.findElement(billTo), _billTo);
	}

	public void setDescription(String _description) {
		utlFn.fastType(driver.findElement(description), _description);
	}

	// Button functions
	public void clickSaveButton() {
		driver.findElement(saveButton).click();
	}

	public void clickInvoiceGroupBtn() {
		driver.findElement(invoiceGroupDDBtn).click();
	}

	// Grid functions
	public void selectInvoiceGroup() throws InterruptedException {
		this.clickInvoiceGroupBtn();
		Thread.sleep(1000);
		driver.findElement(invoiceGroupGrid).click();
	}

	public boolean checkIfManualInvoiceCreated(String contractNumber, String amount) {
		List<WebElement> ls = driver.findElements(manualInvoiceGrid);
		for (WebElement we : ls) {
			String temp = we.findElement(By.xpath("td[4]")).getText();
			if (contractNumber.equals(temp)) {

				// Verification
				temp = we.findElement(By.xpath("td[6]")).getText();
				System.out.println(temp);
				if (temp.contains(amount)) {
					return true;
				}
			}
		}

		return false;
	}

	// Misc functions
	public void waitForContractDetails() {
		// wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(buildingId),
		// _buildingId));
		utlFn.waitForDetailsToLoad(driver.findElement(buildingId), "value");
	}

}
