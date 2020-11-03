package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SMSReportPermissionPage {
	WebDriver driver;
	
	By mainFrame = By.id("mainFrame");
	By reportGrid = By.xpath("//table[@id = 'grdReportList']//tbody//tr");
	
	
	public SMSReportPermissionPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}
	
	public boolean verifyReport(String reportName) {
		List<WebElement> ls = driver.findElements(reportGrid);
		for(WebElement we:ls) {
			if(reportName.equals(we.findElement(By.xpath("td[2]")).getText())) {
				return true;
			}
		}
		return false;
	}
}
