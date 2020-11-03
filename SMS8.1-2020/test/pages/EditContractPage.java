package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;

public class EditContractPage {

	WebDriver driver;
	JavascriptExecutor js;
	utilityFunctions utlFn;

	public EditContractPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		this.utlFn = new utilityFunctions(driver);
	}

	// Frame
	By mainFrame = By.id("mainFrame");

	// Inputs
	By contractNumberTextbox = By.id("txtContractNo");
	By customerInvoiceAttention = By.id("txtCustomerInvAttn");

	// Buttons
	By contractSearchButton = By.id("btnSearch");
	By paymentTypeButton = By.id("btnPaymentType");
	By saveButton = By.id("btnSave");

	// Grids
	By paymentTypeGrid = By.xpath("//table[@id='grdPaymentType']/tbody/tr/td[2]");
	By subSectionGrid = By.xpath("//tbody[@id='subSection']/tr/td[1]");

	// Links
	By billingSchedule = By.xpath("//tbody[@id='subSection']/tr[9]/td[1]/button");

	// Frame functions
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}

	// Input functions
	public void setContractNo(String contractNo) {
		utlFn.fastType(driver.findElement(contractNumberTextbox), contractNo);
	}

	public void setCustomerInvoiceAttention(String customerInvoiceAttentn) {
		utlFn.fastType(driver.findElement(customerInvoiceAttention), customerInvoiceAttentn);
	}

	// Button functions
	public void clickContractSearchButton() {
		driver.findElement(contractSearchButton).click();
	}

	public void clickPaymentTypeButton() {
		driver.findElement(paymentTypeButton).click();
	}

	public void clickSaveButton() {
		driver.findElement(saveButton).click();
	}

	// Grid functions
	public void selectPaymentTypeGrid(String paymentType) {
		List<WebElement> ls = driver.findElements(paymentTypeGrid);
		for (WebElement we : ls) {
			if (paymentType.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void selectSideLink(String sideLink) {
		List<WebElement> ls = driver.findElements(subSectionGrid);
		for (WebElement we : ls) {
			if (sideLink.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	// Link functions
	public void clickOnScheduleBilling() {
		driver.findElement(billingSchedule).click();
	}
}
