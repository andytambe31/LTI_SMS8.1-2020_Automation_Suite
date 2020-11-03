package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utility.utilityFunctions;

public class SearchContractPage {
	WebDriver driver;
	JavascriptExecutor js;
	utilityFunctions utlFn;

	public SearchContractPage(WebDriver driver) {
		this.driver = driver;
		this.utlFn = new utilityFunctions(driver);
	}

	By frameContainer = By.id("frameContainer");

	// Inputs
	By searchTextbox = By.id("txtSearch");
	By wholeWordRadioButton = By.id("rdWholeWord");
	By anyPartButton = By.id("rdAnyPart");
	By startWordRadioButton = By.id("rdStartWord");
	By contractRadioButton = By.id("rdSearchText");
	By buildingRadioButton = By.id("rdBuilding");
	By customerRadioButton = By.id("rdCustomer");

	// Buttons
	By searchButton = By.id("btnSearch");
	By selectButton = By.id("btnShow");

	// Grid xpaths
	By searchGrid = By.xpath("//table[@id='grdSearch']/tbody/tr/td[2]");

	public void switchToFrameContainer() {
		driver.switchTo().frame(driver.findElement(frameContainer));
	}

	// Input functions
	public void setSearchText(String searchText) {
		utlFn.fastType(driver.findElement(searchTextbox), searchText);
		// js.executeScript("arguments[0].value='"+searchText+"';",
		// driver.findElement(searchTextbox));
	}

	public void setWholeWord() {
		driver.findElement(wholeWordRadioButton).click();
	}

	public void setAnyPart() {
		driver.findElement(anyPartButton).click();
	}

	public void setStartWord() {
		driver.findElement(startWordRadioButton).click();
	}

	public void setContractRadioButton() {
		driver.findElement(contractRadioButton).click();
	}

	public void setBuildingRadioButton() {
		driver.findElement(buildingRadioButton).click();
	}

	public void setCustomerRadioButton() {
		driver.findElement(customerRadioButton).click();
	}

	// Button functions
	public void clickSearchButton() {
		driver.findElement(searchButton).click();
	}

	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

	// Other functions
	public void selectContract(String contractNo) {

		//Wait for contract search results to load
		utlFn.waitForDetailsToLoad(driver.findElement(searchGrid), "text");

		List<WebElement> ls = driver.findElements(searchGrid);
		for (WebElement we : ls) {
			if (contractNo.equals(we.getText())) {
				we.click();
				break;
			}
		}

	}

	public void searchAndSelectContract(String contractNo) {
		this.setSearchText(contractNo);
		this.setWholeWord();
		this.setContractRadioButton();
		this.clickSearchButton();
		this.selectContract(contractNo);
		this.clickSelectButton();
	}
}
