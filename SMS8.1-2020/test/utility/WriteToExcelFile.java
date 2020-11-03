package utility;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteToExcelFile {

	public void writeToSpecificPositionNumber(String filePath, String sheetName, double dataToWrite[], int rowNo,
			int columnNo) throws IOException {

		// Create an object of File class to open xlsx file
		File file = new File(filePath);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);

		Workbook wb = new XSSFWorkbook(inputStream);

		// Read excel sheet by sheet name
		Sheet sheet = wb.getSheet(sheetName);

		// Set row and cell for writing data at desired location
		try {
			Row row1 = sheet.getRow(rowNo);
			Cell cell = row1.getCell(columnNo);
			cell.setCellType(1);
			cell.setCellValue(dataToWrite[0]);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// Close input stream
		inputStream.close();
		// Create an object of FileOutputStream class to create write data in excel file
		FileOutputStream outputStream = new FileOutputStream(file);
		// write data in the excel file
		wb.write(outputStream);
		// close output stream
		outputStream.close();
	}

	public void writeExcel(String filePath, String sheetName, String[] dataToWrite) throws IOException {

		int lengthOfData = dataToWrite.length;
		int dataPointer = 0;
		// Create an object of File class to open xlsx file
		File file = new File(filePath);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);

		Workbook wb = new XSSFWorkbook(inputStream);

		// Read excel sheet by sheet name
		Sheet sheet = wb.getSheet(sheetName);
		// Get the current count of rows in excel file
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		// System.out.println("Last Row"+sheet.getLastRowNum());
		// System.out.println("First Row"+sheet.getFirstRowNum());

		// Get the first row from the sheet
		Row row = sheet.getRow(0);
		// Create a new row and append it at last of sheet
		// Row newRow = sheet.createRow(rowCount+1);
		// Create a loop over the cell of newly created Row
		for (int i = 1; i <= rowCount; i++) {
			if (lengthOfData == 0) {
				lengthOfData = 0;
				break;
			}
			Row row1 = sheet.getRow(i);
			Cell cell;
			for (int j = 0; j < row.getLastCellNum() - 1; j++) {
				cell = row1.getCell(j);
				cell.setCellType(1);
				// System.out.print("Data write:"+dataToWrite[j]);
				if (cell.getStringCellValue().toString().equals("") & lengthOfData != 0) {
					// System.out.println(" in "+"("+i+","+j+")");
					cell.setCellValue(dataToWrite[dataPointer]);
					dataPointer++;
					lengthOfData = lengthOfData - 1;
				} else
					continue;
			}
		}
		// Close input stream
		inputStream.close();
		// Create an object of FileOutputStream class to create write data in excel file
		FileOutputStream outputStream = new FileOutputStream(file);
		// write data in the excel file
		wb.write(outputStream);
		// close output stream
		outputStream.close();
	}

	public void deleteDataFromExcel(String filePath, String sheetName, int rowNo, int columnNo) throws IOException {

		try {
			File file = new File(filePath);
			FileInputStream fs = new FileInputStream(file);

			Workbook wb = new XSSFWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			Row row = sh.getRow(0);

			/*
			 * for (int i= 1 ;i <= totalNoOfRows-1;i++) { row =
			 * wb.getSheet(sheetName).getRow(i); for (int j=0;j <= totalNoOfCols-1;j++) {
			 * row.removeCell(row.getCell(j)); } }
			 */
			// row = wb.getSheet(sheetName).getRow(rowNo);
			row.removeCell(row.getCell(columnNo));

		} catch (Exception e) {
			System.out.println("error");
		}
	}

	public void writeToSpecificPosition(String filePath, String sheetName, String dataToWrite[], int rowNo,
			int columnNo) throws IOException {

		// Create an object of File class to open xlsx file
		File file = new File(filePath);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);

		Workbook wb = new XSSFWorkbook(inputStream);

		// Read excel sheet by sheet name
		Sheet sheet = wb.getSheet(sheetName);

		// Set row and cell for writing data at desired location
		Row row1 = sheet.getRow(rowNo);
		Cell cell = row1.getCell(columnNo);
		cell.setCellType(1);

		cell.setCellValue(dataToWrite[0]);

		// Close input stream
		inputStream.close();
		// Create an object of FileOutputStream class to create write data in excel file
		FileOutputStream outputStream = new FileOutputStream(file);
		// write data in the excel file
		wb.write(outputStream);
		// close output stream
		outputStream.close();
	}

	public void writeBulkData(String filePath, String sheetName, List<List> dataToWrite, int rowNo, String colNos)
			throws IOException {

		/*
		 * Function can be used for writing a list
		 */

		// No of values
		int lengthOfData = dataToWrite.size();
		int dataPointer = 0;

		// Create an object of File class to open xlsx file
		File file = new File(filePath);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		Workbook wb = new XSSFWorkbook(inputStream);

		// Read excel sheet by sheet name
		Sheet sheet = wb.getSheet(sheetName);

		// Positions of last data to be entered
		Integer[] collim = this.processLine(colNos.split(" "));

		// Get the write row no.

		for (List<String> childList : dataToWrite) {

			// Row
			Row row = sheet.getRow(rowNo);
			rowNo++;

			// Cell variables
			Cell cell;
			dataPointer = 0;

			for (Integer col : collim) {
				cell = row.getCell(col);
				cell.setCellType(1);

				// Write data
				cell.setCellValue(childList.get(dataPointer));
				dataPointer++;
			}

		}

		// Close input stream
		inputStream.close();
		// Create an object of FileOutputStream class to create write data in excel file
		FileOutputStream outputStream = new FileOutputStream(file);
		// write data in the excel file
		wb.write(outputStream);
		// close output stream
		outputStream.close();
	}

	private Integer[] processLine(String[] strings) {
		Integer[] intarray = new Integer[strings.length];
		int i = 0;
		for (String str : strings) {
			intarray[i] = Integer.parseInt(str);// Exception in this line
			i++;
		}
		return intarray;
	}
}