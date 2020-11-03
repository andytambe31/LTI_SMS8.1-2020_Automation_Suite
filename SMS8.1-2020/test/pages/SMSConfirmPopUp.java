package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SMSConfirmPopUp {
	WebDriver driver;
	JavascriptExecutor executor;
	Actions builder;
	WebDriverWait wait;

	By okButton = By.id("btn1");
	By yesButton = By.id("btn2");
	By noButton = By.id("btn3");
	By cancelButton = By.id("btn4");

	By message = By.id("lblMsg");

	public SMSConfirmPopUp(WebDriver driver) {
		this.driver = driver;
		executor = (JavascriptExecutor) driver;
		builder = new Actions(driver);
		wait = new WebDriverWait(driver, 10);
	}

	public void clickOKButton() {
		driver.findElement(okButton).click();
	}

	public void clickYesButton() {
		// driver.findElement(yesButton).click();
		// executor.executeScript("arguments[0].click();",
		// driver.findElement(yesButton));
		try {
			builder.moveToElement(driver.findElement(yesButton)).click(driver.findElement(yesButton));
			builder.perform();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void clickYesButton2() {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(yesButton)));
		driver.findElement(yesButton).click();
		// executor.executeScript("arguments[0].click();",
		// driver.findElement(yesButton));
		// builder.moveToElement(driver.findElement(yesButton)).click(driver.findElement(yesButton));
		// builder.perform();
	}

	public void clickNoButton() {
		driver.findElement(noButton).click();
	}

	public void clickCancelButton() {
		driver.findElement(cancelButton).click();
	}

	public String getMessage() {
		return driver.findElement(message).getText();
	}

}
