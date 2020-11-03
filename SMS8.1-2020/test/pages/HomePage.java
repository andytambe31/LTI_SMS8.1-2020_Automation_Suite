package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

	WebDriver driver;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	// HomePage elements
	By logoutButton = By.xpath("//table[@id='Table4']/tbody/tr[1]/td[4]/label/b");

	// Menu
	By adminMenu = By.id("Admin");
	By organizationMenu = By.id("Organization");
	// By productMenu = By.id("Product");
	By productMenu = By.xpath("//td[@menuId='38']");
	By inquiryMenu = By.id("Inquiry");
	By contractMenu = By.id("MenuContract");
	By otislineMenu = By.id("Otisline");
	By oBillingMenu = By.id("O Billing");
	By tBillingMenu = By.id("TManagement");
	By oscaMenu = By.id("OSCA");
	By neTimeTicketMenu = By.id("NE TimeTicket");
	By jdeInterfaceMenu = By.id("JDE Interface");
	By revenueMenu = By.id("Revenue");
	By smsReportMenu = By.id("SMSReport");
	By systemMenu = By.id("System");

	// Inquiry Menu
	By unitsForContractMenu = By.id("UnitsForContract");
	By unitTransactionsMenu = By.id("UnitTransactions");
	By unitsForBuildingMenu = By.id("UnitsForBuilding");
	By contractsForCustomerMenu = By.id("ContractsForCustomer");
	By contractPriceHistoryMenu = By.id("ContractYearlyPriceHistory");
	By invoicesForCustomerMenu = By.id("InvoiceByCustomer");
	By invoicesForContractMenu = By.id("InvoiceByContract");
	By invoicesForUnitMenu = By.id("InvoiceByUnit");

	// Application
	By applicationParameter = By.id("ApplicationParameter");
	By manageUser = By.id("ManageUser");
	By menuPermission = By.id("MenuPermission");
	By smsReportPermission = By.id("SMS Report Permission");

	// Organization Sub-Menus

	By companyMenu = By.id("Company");
	By branchMenu = By.id("Branch");
	By employeeMenu = By.id("Employee");
	By territoryMenu = By.id("RouteInquiry");
	By routeMenu = By.id("Route");

	// Contract Menu
	By unitMenu = By.id("Unit");
	By customerMenu = By.id("Customer");
	By newContractMenu = By.id("NewContract");
	By editContract = By.id("Edit Contract");
	By addUnitsToContractLineItem = By.id("Add Units to Contract");
	By contactMenu = By.id("Contacts");
	By buildingMenu = By.id("Building");
	By suspendMenu = By.id("Suspend");
	By cancellationMenu = By.id("Cancellation");
	By resumeMenu = By.id("Resume");
	By renegotiateMenu = By.id("Renegotiate");
	By priceAdjustmentMenu = By.id("Price Adjustment");

	// O Billing menu
	By generateOBilling = By.id("Generate ");
	By manualOInvoice = By.id("ManualInvoice");
	By postOInvoice = By.id("Post Invoice");

	// Inquiry Menu
	public void clickUnitsForContract() {
		driver.findElement(unitsForContractMenu).click();
	}

	public void clickUnitTransactions() {
		driver.findElement(unitTransactionsMenu).click();
	}

	public void clickUnitsForBuilding() {
		driver.findElement(unitsForBuildingMenu).click();
	}

	public void clickContractsForCustomer() {
		driver.findElement(contractsForCustomerMenu).click();
	}

	public void clickContractPriceHistory() {
		driver.findElement(contractPriceHistoryMenu).click();
	}

	public void clickInvoicesForCustomer() {
		driver.findElement(invoicesForCustomerMenu).click();
	}

	public void clickInvoicesForContract() {
		driver.findElement(invoicesForContractMenu).click();
	}

	public void clickInvoicesForUnit() {
		driver.findElement(invoicesForUnitMenu).click();
	}

	// Click on Homepage elements
	public void clickLogout() {
		driver.findElement(logoutButton).click();
	}

	// Click on Main menu
	public void clickAdminMenu() {
		driver.findElement(adminMenu).click();
	}

	public void clickOrganizationMenu() {
		driver.findElement(organizationMenu).click();
	}

	public void clickProductMenu() {
		driver.findElement(productMenu).click();
	}

	public void clickInquiryMenu() {
		driver.findElement(inquiryMenu).click();
	}

	public void clickContractMenu() {
		driver.findElement(contractMenu).click();
	}

	public void clickOtislineMenu() {
		driver.findElement(otislineMenu).click();
	}

	public void clickOBillingMenu() {
		driver.findElement(oBillingMenu).click();
	}

	public void clickTBillingMenu() {
		driver.findElement(tBillingMenu).click();
	}

	public void clickOSCAMenu() {
		driver.findElement(oscaMenu).click();
	}

	public void clickNETimeTicketMenu() {
		driver.findElement(neTimeTicketMenu).click();
	}

	public void clickJDEInterfaceMenu() {
		driver.findElement(jdeInterfaceMenu).click();
	}

	public void clickRevenueMenu() {
		driver.findElement(revenueMenu).click();
	}

	public void clickSMSReportMenu() {
		driver.findElement(smsReportMenu).click();
	}

	public void systemMenu() {
		driver.findElement(systemMenu).click();
	}

	// Click on Organization menu
	public void clickCompanyMenu() {
		driver.findElement(companyMenu).click();
	}

	public void clickBranchMenu() {
		driver.findElement(branchMenu).click();
	}

	public void clickEmployeeMenu() {
		driver.findElement(employeeMenu).click();
	}

	public void clickTerritoryMenu() {
		driver.findElement(territoryMenu).click();
	}

	public void clickRouteyMenu() {
		driver.findElement(routeMenu).click();
	}

	// Click on Contract Menu's
	public void clickUnitMenu() {
		driver.findElement(unitMenu).click();
	}

	public void clickCustomerMenu() {
		driver.findElement(customerMenu).click();
	}

	public void clickNewContractMenu() {
		driver.findElement(newContractMenu).click();
	}

	public void clickAddUnitsToContractLineItem() {
		driver.findElement(addUnitsToContractLineItem).click();
	}

	public void clickEditContract() {
		driver.findElement(editContract).click();
	}

	public void clickContacts() {
		driver.findElement(contactMenu).click();
	}

	public void clickBuildingMenu() {
		driver.findElement(buildingMenu).click();
	}

	public void clickSuspendContract() {
		driver.findElement(suspendMenu).click();
	}

	public void clickCancellationContract() {
		driver.findElement(cancellationMenu).click();
	}

	public void clickResumeContract() {
		driver.findElement(resumeMenu).click();
	}

	public void clickRenegotiateContract() {
		driver.findElement(renegotiateMenu).click();
	}

	public void clickPriceAdjustmentContract() {
		driver.findElement(priceAdjustmentMenu).click();
	}

	// Click on Admin Menu's
	public void clickApplicationParameter() {
		driver.findElement(applicationParameter).click();
	}

	public void clickManagerUser() {
		driver.findElement(manageUser).click();
	}

	public void clickMenuPermission() {
		driver.findElement(menuPermission).click();
	}

	public void clickSMSReportPermission() {
		driver.findElement(smsReportPermission).click();
	}

	// Product Menu
	By SpecificationGroup = By.id("ProductSpecificationGroup");
	By Specifications = By.id("Specifications");
	By ProductGroup = By.id("Product Group");
	By ProductType = By.id("Product Type");
	By ModelType = By.id("Model Type");
	By Product = By.xpath("//td[@menuId='45']");

	// Click on Product Menu

	public void clickSpecificationGroup() {
		driver.findElement(SpecificationGroup).click();
	}

	public void clickSpecifications() {
		driver.findElement(Specifications).click();
	}

	public void clickProductGroup() {
		driver.findElement(ProductGroup).click();
	}

	public void clickProductType() {
		driver.findElement(ProductType).click();
	}

	public void clickModelType() {
		driver.findElement(ModelType).click();
	}

	public void clickProduct() {
		driver.findElement(Product).click();
	}

	// Click on O Billing menus

	public void clickOnGenerateOBilling() {
		driver.findElement(generateOBilling).click();
	}

	public void clickOnManualOInvoice() {
		driver.findElement(manualOInvoice).click();
	}

	public void clickOnPostOBilling() {
		driver.findElement(postOInvoice).click();
	}

}
