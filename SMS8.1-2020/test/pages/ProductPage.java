package pages;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;

import utility.utilityFunctions;

public class ProductPage {

	WebDriver driver;
	JavascriptExecutor js;
	utilityFunctions utlFn;

	By mainFrame = By.id("mainFrame");
	By associateFrame = By.id("associateFrame");
	By associateSearchTextBox = By.id("txtSearch");
	By frameContainer = By.id("frameContainer"); 
	By specificationGroup = By.id("txtCol1Value"); 
	By specificationGroupShortName = By.id("txtCol2Value"); 
	By saveButton = By.id("btnSave"); 
	By specificationGroupGrid = By.xpath("//table[@id='grdReferences']/tbody/tr/td[2]");
	By deleteButton = By.id("btnDelete");
	By specificationGroupBtn = By.id("btnSpec"); 
	By specificationGroupBtnCombo = By.id("btnSpecCombo"); 
	By specificationGroupBtnGrid = By.xpath("//table[@id='Table4']/tbody/tr/td[2]");
	By specificationGroupBtnComboGrid = By.xpath("//table[@id='Table5']/tbody/tr/td[2]");
	By specificationName = By.id("txtSpecificationName"); 
	By specificationValue = By.xpath("//input[@name='grdSpecificationItem:_ctl2:txtSpecificationValue']");
	By specificationGrid = By.xpath("//table[@id='grdSpecification']/tbody/tr/td[3]");
	By productGroupGrid = By.xpath("//table[@id='grdProductGroup']/tbody/tr/td[1]");
	By productGroupName = By.id("txtProductGroupName"); 
	By productShortName = By.id("txtShortName"); 
	By productType = By.id("txtProductTypeName"); 
	By productTypeGrid = By.xpath("//table[@id='grdProductType']/tbody/tr/td[1]");
	By modelType = By.id("txtModelTypeName"); 
	By modelTypeGrid = By.xpath("//table[@id='grdModelType']/tbody/tr/td[1]");
	By searchModelButton = By.id("btnModelType"); 
	By associateGrid = By.xpath("//table[@id='grdAssociate']//tr//td[2]");
	By selectButton = By.id("btnSelect");
	By searchProductButton = By.id("btnProductType");
	By searchProductGroupButton = By.id("btnProductGroup");
	By selectProductGroup = By.xpath("//table[@id='Table4']/tbody/tr/td[2]");
	By productGrid = By.xpath("//table[@id='grdProduct']/tbody/tr/td[4]");
	By searchProductNameTextBox = By.id("txtProductName");
	

	

	public ProductPage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
	}

	public void switchToMainFrame() {
		driver.switchTo().frame(driver.findElement(mainFrame));
	}

	public void switchToAssociateFrame() {
		// driver.switchTo().frame(driver.findElement(associateFrame));
		driver.switchTo().frame(0);
	}
	
	public void switchToFrameContainer(){
		driver.switchTo().frame(driver.findElement(frameContainer));
	}

	
	public void setAssociateSearch(String searchText) {
		js.executeScript("arguments[0].value='" + searchText + "';", driver.findElement(associateSearchTextBox));
	}
	
	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}
	
	public void setProductGroup(String groupName) {

		driver.findElement(productGroupName).sendKeys(groupName);
	}
	public void setProductGroupShortName(String shortName) {

		driver.findElement(productShortName).sendKeys(shortName);
	}
	
	public void setProductType(String groupName) {

		driver.findElement(productType).sendKeys(groupName);
	}
	
	public void setModelType(String groupName) {

		driver.findElement(modelType).sendKeys(groupName);
	}
	
	public void clearProductType() {

		driver.findElement(productType).clear();
	}
	
	public void clearModelType() {

		driver.findElement(modelType).clear();
	}
	
	public void clearProductGroup() {

		driver.findElement(productGroupName).clear();
	}
	public void clearProductGroupShortName() {

		driver.findElement(productShortName).clear();
	}
	
	public void setSpecificationGroup(String groupName) {

		driver.findElement(specificationGroup).sendKeys(groupName);
	}
	public void setSpecificationGroupShortName(String shortName) {

		driver.findElement(specificationGroupShortName).sendKeys(shortName);
	}
	
	public void clearSpecificationGroup() {

		driver.findElement(specificationGroup).clear();
	}
	public void clearSpecificationGroupShortName() {

		driver.findElement(specificationGroupShortName).clear();
	}
	
	public void clickSaveButton() {

		driver.findElement(saveButton).click();
	}
	public void clickDeleteButton() {

		driver.findElement(deleteButton).click();
	}
	public void clickSpecificationGroupButton() {

		driver.findElement(specificationGroupBtn).click();
	}
	
	public void clickSpecificationGroupButtonCombo() {

		driver.findElement(specificationGroupBtnCombo).click();
	}
	public void setSpecificationName(String specName) {

		driver.findElement(specificationName).sendKeys(specName);
	}
	public void setSpecificationValue(String specValue) {

		driver.findElement(specificationValue).sendKeys(specValue);
	}
	public void clearProductName() {

		driver.findElement(searchProductNameTextBox).clear();
	}
	
	public void setProductName(String productName) {

		driver.findElement(searchProductNameTextBox).sendKeys(productName);
	}
	
	
	public String[] getProductName() {
		utlFn.retryingFindClick(searchProductNameTextBox);
		String productName[] = { driver.findElement(searchProductNameTextBox).getAttribute("value")};
		return productName;
	}
	public void selectSpecificationGroup(String groupName) {
		this.clickSpecificationGroupButton();
		List<WebElement> ls = driver.findElements(specificationGroupBtnGrid);
		for (WebElement we : ls) {
			if (groupName.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}
	public void selectSpecificationGroupCombo(String groupName) {
		this.clickSpecificationGroupButtonCombo();
		List<WebElement> ls = driver.findElements(specificationGroupBtnComboGrid);
		for (WebElement we : ls) {
			if (groupName.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}
	
	public void selectSpecificationGroupGrid(String groupName) {
		List<WebElement> ls = driver.findElements(specificationGroupGrid);
		for (WebElement we : ls) {
			if (groupName.equals(we.getText())) {
				we.click();
				break;
				}
			}
		}
	
	public void selectProductGroupGrid(String groupName) {
		List<WebElement> ls = driver.findElements(productGroupGrid);
		for (WebElement we : ls) {
			if (groupName.equals(we.getText())) {
				we.click();
				break;
				}
			}
		}
	
	public void selectProductTypeGrid(String groupName) {
		List<WebElement> ls = driver.findElements(productTypeGrid);
		for (WebElement we : ls) {
			if (groupName.equals(we.getText())) {
				we.click();
				break;
				}
			}
		}
	
	public void selectModelTypeGrid(String groupName) {
		List<WebElement> ls = driver.findElements(modelTypeGrid);
		for (WebElement we : ls) {
			if (groupName.equals(we.getText())) {
				we.click();
				break;
				}
			}
		}
	
	public void selectProductGrid(String productName) {
		List<WebElement> ls = driver.findElements(productGrid);
		for (WebElement we : ls) {
			if (productName.equals(we.getText())) {
				we.click();
				break;
				}
			}
		}
	
	public void selectSpecificationGrid(String groupName) {
		List<WebElement> ls = driver.findElements(specificationGrid);
		for (WebElement we : ls) {
			if (groupName.equals(we.getText())) {
				we.click();
				break;
				}
			}
		}
	
	public void clickSearchModelTypeButton() {
		driver.findElement(searchModelButton).click();
	}

	public void selectModelType(String modelType) {
		//this.clickSearchModelTypeButton();
		List<WebElement> ls = driver.findElements(associateGrid);
		for (WebElement we : ls) {

			if (modelType.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}
	
	public void clickSearchProductTypeButton() {
		driver.findElement(searchProductButton).click();
	}

	public void selectProductType(String productType) {
		//this.clickSearchModelTypeButton();
		List<WebElement> ls = driver.findElements(associateGrid);
		for (WebElement we : ls) {

			if (productType.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}
	
	public void clickSearchProductGroupButton() {
		driver.findElement(searchProductGroupButton).click();
	}
	
	public void selectProductGroup(String productGroup) {
		this.clickSearchProductGroupButton();
		List<WebElement> ls = driver.findElements(selectProductGroup);
		for (WebElement we : ls) {
			if (productGroup.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}
	
	}







