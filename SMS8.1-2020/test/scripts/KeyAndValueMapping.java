package scripts;

import java.util.HashMap;


import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.Test;
import org.junit.Assert;

//import pages.BuildingNotesPopUp;
//import pages.BuildingPage;

//import pages.ContactNotesPopUp;

//import pages.SMSConfirmPopUp;
import utility.ReadFromExcel;

import java.io.IOException;
import org.openqa.selenium.WebDriver;

public class KeyAndValueMapping {

	String driverPath = "./test/resources/drivers/";

	WebDriver driver;

	String pathToExcelFile = "./test/resources/data/";
	String fileName = "pdfData.xlsx";

	String[][] retObjArr1;
	String[][] retObjArr2;



	public HashMap<String, String> setKeyValue() throws IOException, InterruptedException {

		// Class reference
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		retObjArr1 = readFromeExcel.getExcelData(pathToExcelFile + fileName, "UAEKey");
		retObjArr2 = readFromeExcel.getExcelData(pathToExcelFile + fileName, "UAEValue");

		// Misc
		int x = retObjArr1.length;
		String key = null, value = null;

		// HashMaps
		Map<String, String> numberMapping = new HashMap<>();

		// Set values
		for (int i = 0; i < x; i++) {
			key = retObjArr1[i][0];
			value = retObjArr2[i][0];
			numberMapping.put(key, value);
		}

		//System.out.println(numberMapping);

		return (HashMap<String, String>) numberMapping;
	}

}
