package scripts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utility.ReadFromExcel;

public class KeyList {
	TestParameters tstParameter = new TestParameters();

	// Excel paths
	String filePath = "./test/resources/data/";

	public List<String> returnListOfKeys(String verficationType) throws IOException {

		// Variables
		List<String> keyList = new ArrayList<String>();

		// Class references
		ReadFromExcel read = new ReadFromExcel();
		int colNum = 0;
		switch (verficationType) {
		case "dataVerfication":
			colNum = 3;
			break;
		case "locationVerfication":
			colNum = 1;
			break;
		case "tabularVerfication":
			colNum = 2;
			break;
		}

		// Step 1 - Get data from excel into an Object
		String sheetName = "Format" + tstParameter.getEntity() + "Key";
		Object[][] retArray = read.getExcelData(filePath + "pdfData.xlsx", "Format" + tstParameter.getEntity() + "Key");

		// Step 2 - Add object data in list
		for (int i = 0; i < retArray.length; i++) {
			if (((String) retArray[i][colNum]).equals("1")) {
				keyList.add((String) retArray[i][0]);
			}

		}

		// Step 3 - Return the list
		return keyList;
	}
}
