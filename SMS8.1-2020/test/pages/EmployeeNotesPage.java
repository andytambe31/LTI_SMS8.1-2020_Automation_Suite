package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.utilityFunctions;

public class EmployeeNotesPage {

	WebDriver driver;
	JavascriptExecutor js;
	utilityFunctions utlFn;

	// Frames
	By frameContainer = By.id("frameContainer");

	// Textboxes
	By subjectTextBox = By.id("txtSubject");
	By noteTextBox = By.id("txtaNote");
	By expiryDateTextBox = By.id("txtExpirationDate");

	// Buttons
	By saveButton = By.id("btnSave");
	By closeButton = By.id("btnClose");

	// Grids
	By notesGrid = By.xpath("//table[@id = 'grdNotes']/tbody");

	// Constructor
	public EmployeeNotesPage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
	}

	// Functions

	// ******** Frames functions
	public void switchToFrameContainer() {
		driver.switchTo().frame(0);
	}

	// ******** TextBox functions
	public void setSubject(String subject) {
		driver.findElement(subjectTextBox).sendKeys(subject);
	}

	public void setNote(String note) {
		driver.findElement(noteTextBox).sendKeys(note);
	}

	public void setExpiryDate(String expiryDate) {
		driver.findElement(expiryDateTextBox).sendKeys(expiryDate);
	}

	// ******** Button functions
	public void clickOnSave() {
		driver.findElement(saveButton).click();
	}

	public void clickOnClose() {
		driver.findElement(closeButton).click();
	}

	// ******** Grid functions
	public boolean noteSaved() {
		return utlFn.waitForDetailsToLoad(driver.findElement(notesGrid), "text");
	}
}
