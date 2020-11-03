package pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.utilityFunctions;

public class EditContractAgreementPage {
	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;
	utilityFunctions utlFn;

	//frame container
	By frameContainer = By.id("frameContainer");
	
	By closeButton = By.id("btnClose");
	
	public EditContractAgreementPage(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		utlFn = new utilityFunctions(driver);
		wait = new WebDriverWait(driver, 10);
	}

	public void switchToFrameContainer() {
		// driver.switchTo().frame(driver.findElement(frameContainer));
		// driver.switchTo().frame(driver.findElement(frameContainer));
		driver.switchTo().frame(0);
	}

	public void clickCloseButton() {
		//this.switchToFrameContainer();
		driver.findElement(closeButton).click();
	}
	
	
}
