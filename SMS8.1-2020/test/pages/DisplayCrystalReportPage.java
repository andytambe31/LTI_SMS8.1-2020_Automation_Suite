package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class DisplayCrystalReportPage {

	WebDriver driver;
	Actions builder;
	String CrystalReportId;
	JavascriptExecutor js;

	By exportButton = By.id("CRViewer_toptoolbar_export");
	By dropDown = By.xpath("//table[contains(@id,'iconMenu_arrow_bobjid_')]");
	By mainFrame = By.name("mainFrame");
	By frame = By.xpath("//iframe[contains(text(),'bobjid_')]");
	By selectPDF = null;// By.xpath("//span[contains(@title,'PDF']");//("//td[contains(@id,'iconMenu_menu_bobjid')
						// AND contains(@id,'dialog_combo_it_1')]");
	By exportBtn = By.xpath("//td[contains(@id,'theBttnCenterImgbobjid') AND contains(@id,'dialog_submitBtn')]");

	// theBttnCenterImgbobjid_1577794964943_dialog_submitBtn
	// iconMenu_menu_bobjid_1577794964943_dialog_combo_text_bobjid_1577794964943_dialog_combo_it_1

	// Constructor
	public DisplayCrystalReportPage(WebDriver driver) {
		this.driver = driver;
		builder = new Actions(driver);
		this.js = (JavascriptExecutor) driver;
	}

	// Button functions
	public void clickOnExportButton() {
		driver.findElement(exportButton).click();
		String temp = driver.findElement(By.xpath("//table[contains(@id,'iconMenu_icon_bobjid_')]")).getAttribute("id");
		temp = temp.replace("iconMenu_icon_bobjid_", "");
		temp = temp.replace("_dialog_combo", "");
		this.setCrystalReportId(temp);
	}

	public void clickOnDropDown() {
		driver.findElement(dropDown).click();
	}

	public void clickOnPDF() {
		this.setSelectPDF();
		driver.findElement(this.selectPDF).click();
	}

	public void clickOnExport() {
		this.setExportBtn();
		try {
			//builder.moveToElement(driver.findElement(exportBtn)).click().build().perform();
			js.executeScript("arguments[0].click();", driver.findElement(exportBtn));
			//driver.findElement(exportBtn).click();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}

	// Frame functions
	public void switchToMainFrame() {
		driver.switchTo().frame(0);
	}

	public void setCrystalReportId(String CrystalReportId) {
		this.CrystalReportId = CrystalReportId;
	}

	public void switchTo() {
		System.out.println(driver.getCurrentUrl());
		driver.switchTo().frame("mainFrame");
		System.out.println(driver.getCurrentUrl());
		// driver.switchTo().window("Form1");
	}

	public void switchToFrame() {
		// driver.switchTo().window("DisplayCrystalReport");
		// driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(frame));
	}

	// Misc

	public void setSelectPDF() {
		String temp = "iconMenu_menu_bobjid_" + this.CrystalReportId + "_dialog_combo_span_text_bobjid_"
				+ this.CrystalReportId + "_dialog_combo_it_1";
		selectPDF = By.id(temp);
	}

	public void setExportBtn() {
		String temp = "theBttnbobjid_" + this.CrystalReportId + "_dialog_submitBtn";
		exportBtn = By.id(temp);
	}
}
