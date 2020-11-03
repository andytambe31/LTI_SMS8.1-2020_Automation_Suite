package utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import scripts.TestParameters;

public class GetLoginIdForMethodName {

	public List<String> getLogindId(String methodName) throws IOException {

		// Class references
		ReadFromExcel read = new ReadFromExcel();

		// Paths and fileNames
		String pathToExcel = "./test/resources/data/";
		String fileName = "preSmokeTestData.xlsx";
		String sheetName = "LoginId";

		// Get all excel data
		String[][] loginIdData = read.getExcelData(pathToExcel + fileName, sheetName);

		// Get login id of passed methodName
		int j = 2; // Column
		for (int i = 0; i < loginIdData.length; i++) {// Row
			if (methodName.equals(loginIdData[i][j])) {

				List<String> loginPass = new ArrayList<String>();
				String login = loginIdData[i][0];
				String pass = loginIdData[i][1];

				// Add login and pass
				loginPass.add(login);
				loginPass.add(pass);
				return loginPass;
			}
		}

		return null;
	}

	public boolean checkIfLoginMatches(String methodName) throws IOException {

		// Class references
		ReadFromExcel read = new ReadFromExcel();
		TestParameters tstParameters = new TestParameters();

		// Paths and fileNames
		String pathToExcel = "./test/resources/data/";
		String fileName = "preSmokeTestData.xlsx";
		String sheetName = "LoginId";

		// Get all excel data
		String[][] loginIdData = read.getExcelData(pathToExcel + fileName, sheetName);

		// Get login id of passed methodName
		int j = 2; // Column
		for (int i = 0; i < loginIdData.length; i++) {// Row
			if (methodName.equals(loginIdData[i][j])) {
				if (tstParameters.getLogin().equals(loginIdData[i][0].toString())) {
					return true;
				}
			}
		}

		return false;
	}

}
