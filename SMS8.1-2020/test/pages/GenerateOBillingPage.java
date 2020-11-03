package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class GenerateOBillingPage {

	WebDriver driver;
	Actions builder;

	// Buttons
	By generateBilling = By.id("btnGenerateBilling");
	By discardBilling = By.id("btnDiscardBilling");
	By invoiceList = By.id("btnInvoiceList");
	By printInvoice = By.id("btnPrintInvoice");

	// Frames
	By mainFrame = By.id("mainFrame");

	// disabled
	public GenerateOBillingPage(WebDriver driver) {
		this.driver = driver;
		builder = new Actions(driver);
	}

	// Click on button functions
	public void clickOnGenerateBilling() {
		driver.findElement(generateBilling).click();
	}

	public void clickOnDiscardBilling() {
		driver.findElement(discardBilling).click();
	}

	public void clickOnInvoiceList() {
		try {
			builder.moveToElement(driver.findElement(invoiceList)).doubleClick().build().perform();
		} catch (Exception e) {
			// TODO: handle exception
			driver.findElement(invoiceList).click();
		}
	}
	

	public void clickOnPrintInvoice() {
		try {
			builder.moveToElement(driver.findElement(printInvoice)).doubleClick().build().perform();
			//driver.findElement(printInvoice).click();
		} catch (Exception e) {
			// TODO: handle exception
			//builder.moveToElement(driver.findElement(printInvoice)).click().build().perform();

		}

		
	}
	

	// Frame functions
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}

	// Misc function

	public boolean checkIfDiscardButtonIsEnabled() {

		WebElement discardBtn = driver.findElement(discardBilling);
		try {
			if (discardBtn.getAttribute("disabled").toString().trim().equals("true")) {
				return false;// Button is disabled
			}
			return true; // button is enabled
		} catch (Exception e) {
			// System.out.println("Catch");
			return true;
		}

	}

}
