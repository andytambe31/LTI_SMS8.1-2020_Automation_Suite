package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import utility.utilityFunctions;

public class ContactNotePage {
	WebDriver driver;
	JavascriptExecutor js;

	utilityFunctions utlFn;

	By frameContainer = By.id("frameContainer");

	By subjectTextBox = By.id("txtSubject");
	By noteTextBox = By.id("txtNotes");

	By saveButton = By.id("btnSave");
	By closeButton = By.id("btnClose");

	By contractNote = By.id("divgrdNotes");

	public ContactNotePage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
	}

	public void switchToFrameContainer() {
		driver.switchTo().frame(driver.findElement(frameContainer));
	}

	public void clickOnSave() {
		driver.findElement(saveButton).click();
		;
	}

	public void clickOnClose() {
		driver.findElement(closeButton).click();
	}

	public void setSubject(String subject) {
		js.executeScript("arguments[0].value='" + subject + "';", driver.findElement(subjectTextBox));
	}

	public void setNote(String note) {
		utlFn.fastType(driver.findElement(noteTextBox), note);
		// js.executeScript("arguments[0].value='"+note+"';",
		// driver.findElement(noteTextBox));
	}

	public void waitForContactNoteTobeSaved() {
		utlFn.waitForDetailsToLoad(driver.findElement(contractNote), "text");
	}
}
