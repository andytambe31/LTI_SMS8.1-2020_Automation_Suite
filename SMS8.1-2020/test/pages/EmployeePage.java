package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import utility.utilityFunctions;

public class EmployeePage {
	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;

	utilityFunctions utlFn;

	By mainFrame = By.id("mainFrame");
	By associateFrame = By.id("associateFrame");

	// Buttons
	By roleBtn = By.id("btnRole");
	By searchSalesTerritoryAssociateButton = By.id("btnPayRollOfficeId");

	// Grids
	By roleGrid = By.xpath("//table[@id='grdRoleFilter']/tbody/tr/td[2]");
	By employeeGrid_Id = By.xpath("//table[@id = 'grdEmployee']/tbody/tr/td");
	By employeeSearchGrid = By.xpath("//table[@id = 'grdSearch']/tbody/tr/td[1]");

	// Frames
	By frameContainer = By.id("frameContainer");

	// WebElements
	By AssociateGrid = By.xpath("//table[@id='grdAssociate']//tr//td[2]");
	By employeeIdText = By.id("");
	By payrollNoTextbox = By.id("txtEmployeeNo");
	By payrollBranchButton = By.id("btnPayRollOfficeId");
	By empTitleButton = By.name("btnTitle");
	By empTitleGrid = By.xpath("//table[@id='grdTitle']/tbody/tr/td[2]");
	By firstNameTextbox = By.id("txtFirstName");
	By lastNameTextbox = By.id("txtLastName");
	By empRollButton = By.id("btnPayRoll");
	By empRollGrid = By.xpath("//table[@id='grdRole']/tbody/tr/td[2]");
	By commuTypeButton = By.id("grdCommunication__ctl2_btnCommType");
	By commuTypeGrid = By.xpath("//table[@id='grdCommType']/tbody/tr/td[2]");
	By commuTextTextbox = By.id("grdCommunication__ctl2_txtCommunicationText");
	By noteSideLinks = By.id("btnNote");
	By searchTextbox = By.id("txtSearch");
	By empIdRadioButton = By.id("rdSearchById");
	By searchButton = By.id("btnSearch");
	By searchSaveButton = By.id("btnShow");

	// Filter WebElements
	By statusFilterButton = By.id("btnStatus");
	By statusFilterGrid = By.xpath("//table[@id='grdStatus']/tbody/tr/td[2]");
	By branchFilterButton = By.id("btnBranch");
	By roleFilterButton = By.id("btnRole");
	By roleFilterGrid = By.xpath("//table[@id='grdRoleFilter']/tbody/tr/td[2]");
	By SearchpayrollNoButton = By.id("btnPayrollNo");
	By saveButton = By.id("btnSave");
	By deleteButton = By.id("btnDelete");
	By newButton = By.id("btnNew");
	By newRowButton = By.id("btnNewRow");
	By removeButton = By.id("btnRemove");

	By selectButton = By.id("btnSelect");

	By homeButton = By.id("btnHome");

	public EmployeePage(WebDriver driver) {
		this.driver = driver;
		utlFn = new utilityFunctions(driver);
		this.js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 30);
	}

	// Button functions
	public void clickOnRoleButton() {
		driver.findElement(roleBtn).click();
	}

	// Grid functions
	public void selectRole(String role) {
		List<WebElement> ls = driver.findElements(roleGrid);
		for (WebElement we : ls) {
			if (role.equals(we.getText())) {
				System.out.println("");
				we.click();
				break;
			}
		}
	}

	// Methods

	// Frame functions
	public void switchToMainFrame() {
		driver.switchTo().frame(0);
	}

	public void switchToAssociateFrame() {
		// driver.switchTo().frame(driver.findElement(associateFrame));
		driver.switchTo().frame(0);
	}

	public void switchToFrameContainer() {
		driver.switchTo().frame(0);
	}

// click Status Filter button
	public void clickStatusFilter() {
		driver.findElement(statusFilterButton).click();
	}

// Select status 	
	public void setStatusFilter(String status) {
		this.clickStatusFilter();
		List<WebElement> ls = driver.findElements(statusFilterGrid);
		for (WebElement we : ls) {
			if (status.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

	public void setSearchText(String empID) {
		// driver.findElement(searchTextbox).sendKeys(empID);
		js.executeScript("arguments[0].value='" + empID + "';", driver.findElement(searchTextbox));
	}

	public void clickEmployeeIdRadioButton() {
		driver.findElement(empIdRadioButton).click();
	}

	public void clickSearchButton() {

		driver.findElement(searchButton).click();
	}

	public void clickSearchEmployeeButton() {
		driver.findElement(SearchpayrollNoButton).click();
	}

	public void setEmployeeId(String empID) throws InterruptedException {
		this.switchToFrameContainer();
		this.setSearchText(empID);
		this.clickEmployeeIdRadioButton();
		this.clickSearchButton();
		Thread.sleep(1000);
		this.selectSearchEmployee(empID);
		this.clickSearchSelect();
	}

	public void clickSearchSelect() {
		driver.findElement(searchSaveButton).click();
	}

	public void selectSearchEmployee(String empID) {

		List<WebElement> ls = driver.findElements(employeeSearchGrid);
		for (WebElement we : ls) {
			if (we.getText().equals(empID)) {
				we.click();
				break;
			}
		}

	}

	// Click Branch Filter
	public void clickBranchFilter() {
		driver.findElement(branchFilterButton).click();
	}

//Set Branch filter value
	public void setBranchFilter(String branchFilter) {
		List<WebElement> ls = driver.findElements(AssociateGrid);
		for (WebElement we : ls) {
			if (branchFilter.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

//click Role Drop-down button
	public void clickRoleFilter() {
		driver.findElement(roleFilterButton).click();
	}

//Select Role from drop-down
	public void setRoleFilter(String roleFilter) {
		this.clickRoleFilter();
		List<WebElement> ls = driver.findElements(roleFilterGrid);
		for (WebElement we : ls) {
			if (roleFilter.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}
	/*----------------------------------------------------------------------*/

//click Payroll Branch Drop-down button
	public void clickPayrollBranch() {
		driver.findElement(payrollBranchButton).click();
	}

//Select Payroll Branch from drop-down
	public void setPayrollBranch(String branch) {
		List<WebElement> ls = driver.findElements(AssociateGrid);
		for (WebElement we : ls) {
			if (branch.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

//Click Employee Title	
	public void clickEmpTitle() {
		driver.findElement(empTitleButton).click();
	}

//Select Employee Title
	public void setEmpTitle(String empTitle) {
		this.clickEmpTitle();
		List<WebElement> ls = driver.findElements(empTitleGrid);
		for (WebElement we : ls) {
			if (empTitle.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

//Set FirstName		
	public void setFirstName(String firstName) {
		js.executeScript("arguments[0].value='" + firstName + "';", driver.findElement(firstNameTextbox));
	}

//Set LastName
	public void setLastName(String lastName) {
		js.executeScript("arguments[0].value='" + lastName + "';", driver.findElement(lastNameTextbox));
	}

//Set Communication Text
	public void setCommuText(String commuText) {
		js.executeScript("arguments[0].value='" + commuText + "';", driver.findElement(commuTextTextbox));
	}

//Click Employee Role
	public void clickEmpRole() {
		driver.findElement(empRollButton).click();
	}

//Set Employee Role
	public void setEmpRole(String empRole) {
		this.clickEmpRole();
		List<WebElement> ls = driver.findElements(empRollGrid);
		for (WebElement we : ls) {
			if (empRole.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

//Click Communication Type
	public void clickCommuType() {
		driver.findElement(commuTypeButton).click();
	}

//Set Communication Type
	public void setCommuType(String commuType) {
		this.clickCommuType();
		List<WebElement> ls = driver.findElements(commuTypeGrid);
		for (WebElement we : ls) {
			if (commuType.equals(we.getText())) {
				we.click();
				break;
			}
		}
	}

//get Employee ID
	public String[] getEmpId() {
		String[] empId = { driver.findElement(employeeIdText).getAttribute("value") };
		return empId;
	}

//Get Payroll No
	public String[] getPayrollNo() {
		String[] payrollNo = { driver.findElement(payrollNoTextbox).getAttribute("value") };
		return payrollNo;
	}

//Click on Save Button
	public void clickSaveButton() {
		driver.findElement(saveButton).click();
	}

//Click on Delete Button
	public void clickDeleteButton() {
		driver.findElement(deleteButton).click();
	}

//Click on New Button		
	public void clickNewButton() {
		driver.findElement(newButton).click();
	}

//click select button  
	public void clickSelectButton() {
		driver.findElement(selectButton).click();
	}

//click NewRow button 	
	public void clickNewRowButton() {
		driver.findElement(newRowButton).click();
	}

//click Remove button 
	public void clickRemoveButton() {
		driver.findElement(removeButton).click();
	}

//Click on EmployeeName name from EmplyeeGridTable 			

	public void clickTerritoryID(String empId) {
		List<WebElement> Row_table = driver.findElements(By.xpath("//table[@id='grdEmployee']/tbody/tr"));
		int rowSize = Row_table.size();
		List<WebElement> Column_table = driver.findElements(By.xpath("//table[@id='grdEmployee']/tbody/tr[1]/td"));
		int colSize = Column_table.size();
		System.out.println("Total number of rows = " + rowSize);
		System.out.println("Total number of columns = " + colSize);

		WebElement rclick;
		for (int i = 0; i < rowSize; i++) {
			List<WebElement> colVals = Row_table.get(i).findElements(By.tagName("td"));
			int columns_count = colVals.size();
			// System.out.println("Number of cells In Row " + i + " are " + columns_count);
			for (int j = 0; j < colSize; j++) {
				String cellText = colVals.get(j).getText();
				System.out.println("Cell Value of row number " + i + " and column number " + j + " Is " + cellText);
				if (cellText.equals(empId)) {
					// Thread.sleep(2000);
					rclick = Row_table.get(i).findElement(By.tagName("td"));
					rclick.click();
					System.out.println("You have Clicked On employeeID - " + empId);
					i = rowSize + 1;
					// break;
				}

			}
		}

	}

	public void clickTerritoryID2(String empId) {

		List<WebElement> ls = driver.findElements(employeeGrid_Id);
		String xpathForEmployeeFirstName = "//table[@id = 'grdEmployee']/tbody/tr";
		int i = 1;
		for (WebElement we : ls) {
			String temp = we.getText();
			if (temp.equals(empId)) {
				break;
			}
			i++;
		}

		driver.findElement(By.xpath(xpathForEmployeeFirstName + "[" + i + "]" + "/td[3]")).click();

	}

	public void clickOnNotes() {

		// TODO Auto-generated method stub
		driver.findElement(noteSideLinks).click();
	}

}
