package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import scripts.IsHomePage;

public class CommonElements {
	
	WebDriver driver;
	
	By homeButton = By.id("btnHome");
	By mainFrame = By.id("mainFrame");
	
	public CommonElements(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickOnHomeButton() {
		IsHomePage isHomePage = new IsHomePage(driver);
		if(isHomePage.isElementPresent(By.id("lblEntity")) == false) {
			driver.findElement(By.id("btnHome")).click();
		}
	}
	
	//Switch to Main Frame
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}
}
