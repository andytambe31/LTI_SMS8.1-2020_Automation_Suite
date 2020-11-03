package scripts;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import utility.ReadFromExcel;
import utility.WriteToExcelFile;

public class ResultWriter {

	// WebDriver
	WebDriver driver;
	String mainWindowHandle;

	// Class objects
	WriteToExcelFile write;// = new WriteToExcelFile();
	ReadFromExcel readFromExcel;// = new ReadFromExcel();
	ResultSet rs;// = new ResultSet();
	FailHandle failHndl;// = new FailHandle(driver);

	// Excel paths and filenames
	String pathToExcelFile = "./test/resources/data/";
	String fileResult = "SmokeTestResultSheet.xlsx";
	String sheetName = "Smoke test suite";

	// Misc
	String contractNumber = "";
	String temp = "";

	// Constructor

	public ResultWriter(WebDriver driver, String mainWindowHandle) {
		this.driver = driver;
		this.mainWindowHandle = mainWindowHandle;
		write = new WriteToExcelFile();
		readFromExcel = new ReadFromExcel();
		rs = new ResultSet();
		failHndl = new FailHandle(driver);
	}

	public void setResults(ITestResult result, String methodName) throws IOException {

		write = new WriteToExcelFile();
		String timeStamp = null;
		if (methodName.contains("TS: ")) {
			timeStamp = methodName.substring(methodName.length() - 21, methodName.length());
			methodName = methodName.replace(timeStamp, "");
		}

		int rowRead = rs.getReadRow(methodName);

		if (this.setExecutionResult(result).equals("PASS")) {
			// Mark as Pass if no failed
			if (!(readFromExcel.readDataFromSpecificPosition(pathToExcelFile + fileResult, sheetName, rowRead, 1))
					.equals("FAIL")) {
				write.writeToSpecificPosition(pathToExcelFile + fileResult, sheetName, new String[] { "PASS" },
						rs.getResultRow(methodName), 1);
			}

			// Write data
			if (methodName.equals("createContracts") || methodName.equals("editContracts")
					|| methodName.equals("taxedContracts")) {
				this.writeForContract(methodName, rowRead, timeStamp);
			} else {
				if (methodName.equals("contractBillingVerfication")) {

				} else {

					// Write timeStamp
					write.writeToSpecificPosition(pathToExcelFile + fileResult, sheetName, new String[] { timeStamp },
							rs.getResultRow(methodName), 3);

					// Write data
					write.writeToSpecificPosition(pathToExcelFile + fileResult, sheetName,
							new String[] { readFromExcel.readDataFromSpecificPosition(
									pathToExcelFile + rs.getExcel(methodName), rs.getSourceSheetName(methodName),
									rowRead, rs.getReadColumn(methodName)) },
							rs.getResultRow(methodName), 2);
				}
			}
		}

		else if (this.setExecutionResult(result).equals("FAIL"))
			// Marks as Fail
			write.writeToSpecificPosition(pathToExcelFile + fileResult, sheetName, new String[] { "FAIL" },
					rs.getResultRow(methodName), 1);
		else
			// Marks as SKIP
			write.writeToSpecificPosition(pathToExcelFile + fileResult, sheetName, new String[] { "SKIP" },
					rs.getResultRow(methodName), 1);

	}

	public String setExecutionResult(ITestResult result) {

		try {

			if (result.getStatus() == ITestResult.SUCCESS) {
				return "PASS";
			} else if (result.getStatus() == ITestResult.FAILURE) {
				failHndl.failHandler(mainWindowHandle);
				return "FAIL";

			} else if (result.getStatus() == ITestResult.SKIP) {
				return "SKIP";

			}
		} catch (Exception e) {
			System.out.println("\nLog Message::@AfterMethod: Exception caught");
			e.printStackTrace();
		}
		return "FAIL";

	}

	public void writeForContract(String methodName, int rowRead, String timeStamp) throws IOException {
		// Count of contract
		int contractCount = rowRead;

		if ((methodName.equals("taxedContracts") && contractCount == 1)) {
			contractNumber = "";
		}
		if ((methodName.equals("editContracts") && contractCount == 1)) {
			contractNumber = "";
		}
		if ((methodName.equals("createContracts") && contractCount == 4)
				|| (methodName.equals("editContracts") && contractCount == 4)
				|| (methodName.equals("taxedContracts") && contractCount == 3)) {
			temp = readFromExcel.readDataFromSpecificPosition(pathToExcelFile + rs.getExcel(methodName),
					rs.getSourceSheetName(methodName), contractCount, rs.getReadColumn(methodName));
			contractNumber = contractNumber + temp;

			// Write in excel file
			write.writeToSpecificPosition(pathToExcelFile + fileResult, sheetName, new String[] { contractNumber },
					rs.getResultRow(methodName), 2);

			// Write timeStamp
			write.writeToSpecificPosition(pathToExcelFile + fileResult, sheetName, new String[] { timeStamp },
					rs.getResultRow(methodName), 3);

		} else {
			temp = readFromExcel.readDataFromSpecificPosition(pathToExcelFile + rs.getExcel(methodName),
					rs.getSourceSheetName(methodName), contractCount, rs.getReadColumn(methodName));
			contractNumber = contractNumber + temp + ",";
		}

	}

	/*
	 * public void writeForTaxedContract(String methodName) throws IOException { //
	 * Count of contract int contractCount = rs.getReadRow(methodName);
	 * 
	 * if (contractCount == 4) { temp =
	 * readFromExcel.readDataFromSpecificPosition(pathToExcelFile +
	 * rs.getExcel(methodName), rs.getSourceSheetName(methodName), contractCount,
	 * rs.getReadColumn(methodName)); contractNumber = contractNumber + temp;
	 * 
	 * // Write in excel file write.writeToSpecificPosition(pathToExcelFile +
	 * fileResult, sheetName, new String[] { contractNumber },
	 * rs.getResultRow(methodName), 2);
	 * 
	 * } else { temp = readFromExcel.readDataFromSpecificPosition(pathToExcelFile +
	 * rs.getExcel(methodName), rs.getSourceSheetName(methodName), contractCount,
	 * rs.getReadColumn(methodName)); contractNumber = contractNumber + temp + ",";
	 * }
	 * 
	 * }
	 */

}
