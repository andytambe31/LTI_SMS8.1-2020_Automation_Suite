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

public class AddContractLineItemPage {
	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;

	utilityFunctions utlFn;

	By frameContainer = By.id("frameContainer");

	By partialUnitNoXpath = By.xpath("//*[contains(@id,'_txtUnitNo')]");
	By UnitNoId;
	By ChkUnitId;
	By PriceId;
	By TenureId;
	By RolloverTenureId;
	By TenureInDaysId;
	By ExamIntervalId;
	By ExamCycleId;
	By ExamDayId;
	By BaseHrsExamId;
	By BaseHrsOtcbId;

	By examIntervalGrid = By.xpath("//table[@id='grdExamInterval']/tbody/tr/td[2]");
	By examDayOfWeekGrid = By.xpath("//table[@id='grdExamDayOfWeek']/tbody/tr/td[2]");
	By associateGrid = By.xpath("//table[@id='grdAssociate']/tbody/tr/td[2]");
	By typeGrid = By.xpath("//table[@id='grdType']/tbody/tr/td[1]");

	// Checkbox
	By allUnitsCheckbox = By.id("chkAllUnits");

	// Button
	By selectButton = By.id("btnSelect");
	By saveButton = By.id("btnSave");
	By okButton = By.id("btn1");
	By closeButton = By.id("btnClose");
	By typeButton = By.id("BUTTON2");

	String partialId = "";
	String partialUnitNoId = "txtUnitNo";
	String partialChkUnitId = "chkUnit";
	String partialPriceId = "txtPriceNew";
	String partialTenureId = "txtTenureInMonths";
	String partialRolloverTenureId = "txtRolloverTenureInMonths";
	String partialTenureInDaysId = "txtTenureInDays";
	String partialExamIntervalId = "btnExamInterval";
	String partialExamCycleId = "btnExamCycle";
	String partialExamDay = "btnExamDayOfWeek";
	String partialBaseHrsExamId = "txtBaseHrsExam";
	String partialBaseHrsOtcbId = "txtBaseHrsOtcb";

	String completeUnitNoId = "";
	String completeChkUnitId = "";
	String completePriceId = "";
	String completeTenureId = "";
	String completeRolloverTenureId = "";
	String completeTenureInDaysId = "";
	String completeExamIntervalId = "";
	String completeExamCycleId = "";
	String completeExamDayId = "";
	String completeBaseHrsExamId = "";
	String completeBaseHrsOtcbId = "";

	public AddContractLineItemPage(WebDriver driver) {
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

	public void selectUnit(String unitNo) throws InterruptedException {
		// this.switchToFrameContainer();
		List<WebElement> lt = driver.findElements(partialUnitNoXpath);
		partialId = "";
		for (WebElement we : lt) {
			if (unitNo.equals(we.getAttribute("value"))) {
				partialId = we.getAttribute("id");
				// grdContractLines__ctl2_txtUnitNo
				partialId = partialId.replace(partialUnitNoId, "");
				this.completeUnitIDs(partialId);
				this.checkUnit();
				break;
			}
		}
	}

	public void selectType(String type) {
		List<WebElement> ls = driver.findElements(typeGrid);
		for (WebElement we : ls) {
			if (type.equals(we.getText())) {
				we.click();
				break;
			}
		}

	}

	public void completeUnitIDs(String partialId) {
		completeUnitNoId = partialId + partialUnitNoId;
		completeChkUnitId = partialId + partialChkUnitId;
		completePriceId = partialId + partialPriceId;
		completeTenureId = partialId + partialTenureId;
		completeRolloverTenureId = partialId + partialRolloverTenureId;
		completeTenureInDaysId = partialId + partialTenureInDaysId;
		completeExamIntervalId = partialId + partialExamIntervalId;
		completeExamCycleId = partialId + partialExamCycleId;
		completeExamDayId = partialId + partialExamDay;
		completeBaseHrsExamId = partialId + partialBaseHrsExamId;
		completeBaseHrsOtcbId = partialId + partialBaseHrsOtcbId;

		UnitNoId = By.id(completeUnitNoId);
		ChkUnitId = By.id(completeChkUnitId);
		PriceId = By.id(completePriceId);
		TenureId = By.id(completeTenureId);
		RolloverTenureId = By.id(completeRolloverTenureId);
		TenureInDaysId = By.id(completeTenureInDaysId);
		ExamIntervalId = By.id(completeExamIntervalId);
		ExamCycleId = By.id(completeExamCycleId);
		ExamDayId = By.id(completeExamDayId);
		BaseHrsExamId = By.id(completeBaseHrsExamId);
		BaseHrsOtcbId = By.id(completeBaseHrsOtcbId);
	}

	public void checkUnit() {
		driver.findElement(ChkUnitId).click();
	}

	public void setPrice(String price) {
		driver.findElement(PriceId).clear();
		// js.executeScript("arguments[0].value='" + price + "';",
		// driver.findElement(PriceId));
		// driver.findElement(PriceId).sendKeys(price);
		utlFn.fastType(driver.findElement(PriceId), price);
	}

	public void setTenure(String tenure) {
		driver.findElement(TenureId).clear();
		// js.executeScript("arguments[0].value='" + tenure + "';",
		// driver.findElement(TenureId));
		utlFn.fastType(driver.findElement(TenureId), tenure);
	}

	public void setRolloverTenure(String roTenure) {
		driver.findElement(RolloverTenureId).clear();
		// js.executeScript("arguments[0].value='" + roTenure + "';",
		// driver.findElement(RolloverTenureId));
		utlFn.fastType(driver.findElement(RolloverTenureId), roTenure);
	}

	public void setTenureInDays(String tenureInDays) {
		driver.findElement(TenureInDaysId).clear();
		// js.executeScript("arguments[0].value='" + tenureInDays + "';",
		// driver.findElement(TenureInDaysId));
		utlFn.fastType(driver.findElement(TenureInDaysId), tenureInDays);
	}

	public void clickExamInterval() {
		driver.findElement(ExamIntervalId).click();
	}

	public void onClickExamIntervalShiftLeft() {
		driver.findElement(ExamIntervalId).sendKeys(Keys.ARROW_LEFT);
	}

	public void setExamInterval(String examInterval) {

		this.clickExamInterval();
		this.onClickExamIntervalShiftLeft();
		this.clickExamInterval();
		selectExamInterval(examInterval);
	}
	

	public void clickExamCycleButton() {
		// this.switchToFrameContainer();
		driver.findElement(ExamCycleId).click();
	}

	public void selectExamInterval(String examInterval) {
		List<WebElement> ls = driver.findElements(examIntervalGrid);
		for (WebElement we : ls) {
			if (examInterval.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void setExamCycle(String examCycle) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
		// driver.switchTo().frame(0);
		List<WebElement> ls = driver.findElements(associateGrid);
		for (WebElement we : ls) {
			if (examCycle.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

	public void clickExamDay() {
		driver.findElement(ExamDayId).click();
	}

	public void setExamDay(String examDay) {
		this.switchToFrameContainer();
		this.clickExamDay();
		selectExamDay(examDay);
	}

	public void selectExamDay(String examDay) {
		List<WebElement> ls = driver.findElements(examDayOfWeekGrid);
		for (WebElement we : ls) {
			if (examDay.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void setExamHours(String examHours) {
		driver.findElement(BaseHrsExamId).clear();
		// js.executeScript("arguments[0].value='" + examHours + "';",
		// driver.findElement(BaseHrsExamId));
		utlFn.fastType(driver.findElement(BaseHrsExamId), examHours);
	}

	public void setOTCBHours(String otcbHours) {
		driver.findElement(BaseHrsOtcbId).clear();
		// js.executeScript("arguments[0].value='" + otcbHours + "';",
		// driver.findElement(BaseHrsOtcbId));
		utlFn.fastType(driver.findElement(BaseHrsOtcbId), otcbHours);
	}

//Checkbox functions
	public void checkAllUnits() {
		while (true) {
			try {
				if (utlFn.waitForDetailsToLoad(driver.findElement(partialUnitNoXpath), "value")) {
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		driver.findElement(allUnitsCheckbox).click();
	}

//Button functions
	public void clickSaveButton() {
		driver.findElement(saveButton).click();
	}

	public void clickOKButton() {
		driver.findElement(okButton).click();
	}

	public boolean retryingFindClick(By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	public void clickCloseButton() {
		this.switchToFrameContainer();
		driver.findElement(closeButton).click();
	}

	public void clickTypeButton() {
		driver.findElement(typeButton).click();
	}

}
