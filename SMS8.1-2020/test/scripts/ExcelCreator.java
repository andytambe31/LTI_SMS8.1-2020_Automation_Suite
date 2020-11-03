package scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.HyperlinkEvent;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IgnoredErrorType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utility.WriteToExcelFile;

public class ExcelCreator {

	List<List> allPDFPassFailDetails = new ArrayList<List>();
	List<List> allKeyTestTypeDetailsMap = new ArrayList<List>();
	List<List> consolidated = new ArrayList<List>();
	List<List> invoiceValidationDetails = new ArrayList<List>();
	String timeElapsed;

	public ExcelCreator(List allPDFPassFailDetails, List allKeyTestTypeDetailsMap, List consolidated,
			List invoiceValidationDetails, String timeElapsed) {
		this.allPDFPassFailDetails.addAll(allPDFPassFailDetails);
		this.allKeyTestTypeDetailsMap.addAll(allKeyTestTypeDetailsMap);
		this.consolidated.addAll(consolidated);
		this.invoiceValidationDetails.addAll(invoiceValidationDetails);
		this.timeElapsed = timeElapsed;
	}

	public void createSheet(String filePath, String sheetName) throws IOException {
		// Create an object of File class to open xlsx file
		File file = new File(filePath);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);

		Workbook wb = new XSSFWorkbook(inputStream);

		Sheet sheet = (XSSFSheet) wb.createSheet(sheetName);

		// Close input stream
		inputStream.close();

		// Create an object of FileOutputStream class to create write data in excel file
		FileOutputStream outputStream = new FileOutputStream(file);

		// write data in the excel file
		wb.write(outputStream);

		// close output stream
		outputStream.close();

	}

	public void createCells(String filePath, String sheetName, String dataToWrite, int rowNum, int colNum)
			throws IOException {

		// Class reference
		WriteToExcelFile wteF = new WriteToExcelFile();

		// Create an object of File class to open xlsx file
		// File file = new File(filePath);

		// Create an object of FileInputStream class to read excel file
		// FileInputStream inputStream = new FileInputStream(file);

		// Workbook wb = new XSSFWorkbook(inputStream);

		// Read excel sheet by sheet name
		// Sheet sheet = wb.getSheet(sheetName);

		// Write data
		// Row row = sheet.createRow(rowNum);
		// Cell cell = row.createCell(colNum);
		// cell.setCellType(1);
		// cell.setCellValue(dataToWrite);
		wteF.writeToSpecificPosition(filePath, sheetName, new String[] { dataToWrite }, rowNum, colNum);

	}

	public void createNumberCells(String filePath, String sheetName, double dataToWrite, int rowNum, int colNum)
			throws IOException {

		// Class reference
		WriteToExcelFile wteF = new WriteToExcelFile();
		wteF.writeToSpecificPositionNumber(filePath, sheetName, new double[] { dataToWrite }, rowNum, colNum);

	}

	public void hyperlink(String filePath, String sheetName, int row, int col, String data, String hyperLinkReference)
			throws IOException {
		try {

			// Create an object of File class to open xlsx file
			File file = new File(filePath);

			// Create an object of FileInputStream class to read excel file
			FileInputStream inputStream = new FileInputStream(file);

			// Workbook
			Workbook wb = new XSSFWorkbook(inputStream);

			CreationHelper createHelper = wb.getCreationHelper();

			// cell style for hyperlinks
			CellStyle hlink_style = wb.createCellStyle();
			Font hlink_font = wb.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);

			Cell cell;
			Sheet sheet = wb.getSheet(sheetName);

			// link to a place in this workbook

			// create a target sheet and cell
			cell = sheet.getRow(row).getCell(col);
			cell.setCellValue(data);
			Hyperlink link2 = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
			link2.setAddress(hyperLinkReference);
			cell.setHyperlink(link2);
			cell.setCellStyle(hlink_style);

			// Close input stream
			inputStream.close();
			// Create an object of FileOutputStream class to create write data in excel file
			FileOutputStream outputStream = new FileOutputStream(file);
			// write data in the excel file
			wb.write(outputStream);
			// close output stream
			outputStream.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void setFilters(String filePath, String sheetName, int row1, int col1, int row2, int col2)
			throws IOException {
		/* Create Workbook and Worksheet - Add Input Rows */
		// Create an object of File class to open xlsx file
		File file = new File(filePath);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(filePath);

		// Workbook
		Workbook wb = new XSSFWorkbook(inputStream);

		Sheet sheet = wb.getSheet(sheetName);

		CellReference ref1 = new CellReference(row1, col1);
		CellReference ref2 = new CellReference(row2, col2);

		String cell1 = ref1.formatAsString();
		String cell2 = ref2.formatAsString();
		System.out.println(cell1 + ":" + cell2);
		sheet.setAutoFilter(CellRangeAddress.valueOf(cell1 + ":" + cell2));

		FileOutputStream outputStream = new FileOutputStream(file);
		// write data in the excel file
		wb.write(outputStream);
		// close output stream
		outputStream.close();
	}

	public void formatCell(String filePath, String sheetName, int rowNo, int colNo, short boldWeight, byte underline,
			String borderType, short fontHeight, String cellType) throws IOException {
		// Create an object of File class to open xlsx file
		File file = new File(filePath);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(filePath);

		// Workbook
		Workbook wb = new XSSFWorkbook(inputStream);

		Sheet sheet = wb.getSheet(sheetName);

		// We create a simple cell, set its value and apply the cell style.
		Row row = sheet.getRow(rowNo);
		Cell cell = row.getCell(colNo);

		// Create sytle
		XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();

		DataFormat fmt = wb.createDataFormat();

		/*
		 * switch (cellType) { case "string": style.setDataFormat(fmt.getFormat("@"));
		 * cell.setCellStyle(style); break;
		 * 
		 * case "number": style.setDataFormat(fmt.getFormat("General"));
		 * cell.setCellStyle(style); break;
		 * 
		 * default: System.out.println("Invalid cellType"); break; }
		 */

		switch (borderType) {
		case "thin":
			style.setBorderTop(XSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			style.setBorderRight(XSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			break;
		case "thick":
			style.setBorderTop(XSSFCellStyle.BORDER_THICK);
			style.setBorderLeft(XSSFCellStyle.BORDER_THICK);
			style.setBorderRight(XSSFCellStyle.BORDER_THICK);
			style.setBorderBottom(XSSFCellStyle.BORDER_THICK);
		default:
			break;
		}

		System.out.println(style.getFillBackgroundColorColor());

		// Font
		Font font = wb.createFont();
		font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
		font.setFontHeightInPoints((short) fontHeight);// 20
		// font.setItalic(true);
		font.setBoldweight((short) boldWeight);// 2
		font.setUnderline((byte) underline);// 1
		style.setFont(font);

		// We create a simple cell, set its value and apply the cell style.
		cell.setCellStyle(style);
		sheet.autoSizeColumn((short) 1);

		// Color
		style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(style);

		// Close input stream
		inputStream.close();
		// Create an object of FileOutputStream class to create write data in excel file
		FileOutputStream outputStream = new FileOutputStream(file);
		// write data in the excel file
		wb.write(outputStream);
		// close output stream
		outputStream.close();

	}

	public void createBorder(String filePath, String sheetName) {
		XSSFWorkbook workbook = null;
		FileOutputStream fileOutputStream = null;
		try {

			fileOutputStream = new FileOutputStream(new File(filePath));

			// Create a Workbook
			workbook = new XSSFWorkbook();

			// Create an Empty Sheet
			XSSFSheet sheet = workbook.getSheet(sheetName);

			// Cell border
			int[] rows = { 0, 2, 4, 6 };
			int[] columns = { 1, 2, 6, 3 };
			for (int row1 : rows) {
				for (int col1 : columns) {
					Row row = sheet.createRow(row1);
					Cell cell = row.createCell((short) col1);
					CellStyle style = workbook.createCellStyle();
					style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
					style.setBorderRight(XSSFCellStyle.BORDER_THIN);
					style.setBorderTop(XSSFCellStyle.BORDER_THIN);

					style.setBottomBorderColor(IndexedColors.BLUE.getIndex());
					style.setLeftBorderColor(IndexedColors.YELLOW.getIndex());
					style.setRightBorderColor(IndexedColors.BLACK.getIndex());
					style.setTopBorderColor(IndexedColors.BROWN.getIndex());

					cell.setCellStyle(style);
					workbook.write(fileOutputStream);
				}
			}

		} catch (Exception e) {
			System.out.println("An Exception occured while writing Excel" + e);
		}
	}

	public static void colorCells(String filePath, String sheetName, int rowNo, int colNo, String color)
			throws IOException {

		// Create an object of File class to open xlsx file
		File file = new File(filePath);

		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(filePath);

		// Workbook
		Workbook wb = new XSSFWorkbook(inputStream);

		Sheet sheet = wb.getSheet(sheetName);

		// We create a simple cell, set its value and apply the cell style.
		Row row = sheet.getRow(rowNo);
		Cell cell = row.getCell(colNo);

		// Create sytle
		XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();

		DataFormat fmt = wb.createDataFormat();

		// Color
		if (color.equals("Green")) {
			style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		} else
			style.setFillForegroundColor(IndexedColors.RED.getIndex());

		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(style);

		// Close input stream
		inputStream.close();
		// Create an object of FileOutputStream class to create write data in excel file
		FileOutputStream outputStream = new FileOutputStream(file);
		// write data in the excel file
		wb.write(outputStream);
		// close output stream
		outputStream.close();

	}

	// *********************Sheets***************************************
	public void createOBillingSheet() throws IOException {

		// Class reference
		TestParameters tstPar = new TestParameters();
		WriteToExcelFile wteF = new WriteToExcelFile();

		String pathToExcel = "./test/resources/data/";
		String resultSheet = "SmokeTestResultSheet.xlsx";

		// Create Sheet
		// this.createSheet(pathToExcel + resultSheet, "O Billing Smoke Test");

		// Create borders
		// this.createBorder(pathToExcel + resultSheet, "O Billing Smoke Test");

		// *****************Create cells for O Billing Sheet**********************
		/*
		 * // O Billing Smoke Test this.createCells(pathToExcel + resultSheet,
		 * "O Billing Smoke Test", "O Billing Smoke Test", 0, 1);
		 * this.formatCell(pathToExcel + resultSheet, "O Billing Smoke Test", 0, 1,
		 * (short) 1, (byte) 0, "thin", (short) 20, "string");
		 * 
		 * // Company: UAE this.createCells(pathToExcel + resultSheet,
		 * "O Billing Smoke Test", "Company: " + tstPar.getEntity(), 2, 1);
		 * this.formatCell(pathToExcel + resultSheet, "O Billing Smoke Test", 2, 1,
		 * (short) 1, (byte) 0, "thin", (short) 15, "string");
		 * 
		 * // Test Execution Time this.createCells(pathToExcel + resultSheet,
		 * "O Billing Smoke Test", "Test Execution Time", 4, 2);
		 * this.formatCell(pathToExcel + resultSheet, "O Billing Smoke Test", 4, 2,
		 * (short) 1, (byte) 0, "thick", (short) 16, "string");
		 * 
		 * // Test Execution Time : this.createCells(pathToExcel + resultSheet,
		 * "O Billing Smoke Test", "<<Total Time>>", 4, 6); this.formatCell(pathToExcel
		 * + resultSheet, "O Billing Smoke Test", 4, 6, (short) 1, (byte) 0, "thick",
		 * (short) 16, "string");
		 * 
		 * // Test PDF this.createCells(pathToExcel + resultSheet,
		 * "O Billing Smoke Test", "Test PDF", 6, 1); this.formatCell(pathToExcel +
		 * resultSheet, "O Billing Smoke Test", 6, 1, (short) 1, (byte) 0, "thin",
		 * (short) 15, "string");
		 * 
		 * // Total pages this.createCells(pathToExcel + resultSheet,
		 * "O Billing Smoke Test", "Total pages", 6, 2); this.formatCell(pathToExcel +
		 * resultSheet, "O Billing Smoke Test", 6, 2, (short) 1, (byte) 0, "thin",
		 * (short) 15, "string");
		 * 
		 * // Fail this.createCells(pathToExcel + resultSheet, "O Billing Smoke Test",
		 * "Fail", 6, 3); this.formatCell(pathToExcel + resultSheet,
		 * "O Billing Smoke Test", 6, 3, (short) 1, (byte) 0, "thin", (short) 15,
		 * "string");
		 * 
		 * // Pass this.createCells(pathToExcel + resultSheet, "O Billing Smoke Test",
		 * "Pass", 6, 6); this.formatCell(pathToExcel + resultSheet,
		 * "O Billing Smoke Test", 6, 6, (short) 1, (byte) 0, "thin", (short) 15,
		 * "string");
		 */

		// ************Test PDFs********************
		String sheetName = "O Billing Smoke Test";

		// Row and column for writing PDF pass fail details in report
		int rowNum = 8, lastRow = rowNum;
		int colNumforPDFName = 1;
		int colNumforTotalPages = 2;
		int colNumforTotalFailPages = 3;
		int colNumforTotalPassPages = 4;
		int colNumforTotalSkippedPages = 5;

		// Loop for rotating over all tested PDFs
		for (List<String> pdfDetails : this.allPDFPassFailDetails) {

			// PDF Name
			this.createCells(pathToExcel + resultSheet, sheetName, pdfDetails.get(0), rowNum, colNumforPDFName);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforPDFName, (short) 1, (byte) 0, "thin", (short) 15, "string");
			 */

			// PDF page count
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(1)), rowNum,
					colNumforTotalPages);

			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(2)), rowNum,
					colNumforTotalFailPages);
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(3)), rowNum,
					colNumforTotalPassPages);
			// Skipped
			/*
			 * this.createNumberCells(pathToExcel + resultSheet, sheetName,
			 * Double.parseDouble(pdfDetails.get(4)), rowNum, colNumforTotalSkippedPages);
			 */

			/*
			 * // Summary this.hyperlink(pathToExcel + resultSheet, "O Billing Smoke Test",
			 * rowNum, colNumforTotalFailPages + 1, "Summary", "'Mismatched'!A1");
			 * this.hyperlink(pathToExcel + resultSheet, "O Billing Smoke Test", rowNum,
			 * colNumforTotalPassPages + 1, "Summary", "'Matched'!A1");
			 * 
			 * // Graph this.hyperlink(pathToExcel + resultSheet, "O Billing Smoke Test",
			 * rowNum, colNumforTotalFailPages + 2, "Graph", "'Graphical Analysis'!A1");
			 * this.hyperlink(pathToExcel + resultSheet, "O Billing Smoke Test", rowNum,
			 * colNumforTotalPassPages + 2, "Graph", "'Graphical Analysis'!A1");
			 */

			rowNum++;
			lastRow = rowNum;
		}

		// Time of Exection
		this.createCells(pathToExcel + resultSheet, sheetName, timeElapsed, 5, 4);

		/*
		 * // Color consolidated this.formatCell(pathToExcel + resultSheet, sheetName,
		 * lastRow, colNumforPDFName, (short) 1, (byte) 0, "thin", (short) 18,
		 * "string");
		 */
		// ****************Quick Links*******************
		// Quick links
		// this.createCells(pathToExcel + resultSheet, "O Billing Smoke Test", "Quick
		// Links", rowNum + 1, 1);

		// Click here for graphical analysis
		// this.createCells(pathToExcel + resultSheet, "O Billing Smoke Test", "Click
		// here for graphical analysis",
		// rowNum + 2, 1);

		// Click here for Smoke test result sheet
		// this.createCells(pathToExcel + resultSheet, "O Billing Smoke Test", "Click
		// here for Smoke test result sheet",
		/// rowNum + 3, 1);

		/*
		 * // ***************HyperLinks************************
		 * this.hyperlink(pathToExcel + resultSheet, "O Billing Smoke Test", rowNum + 2,
		 * 1, "Click here for graphical analysis", "'Graphical Analysis'!A1");
		 * this.hyperlink(pathToExcel + resultSheet, "O Billing Smoke Test", rowNum + 3,
		 * 1, "Click here for Smoke test result sheet", "'Smoke test suite'!A1");
		 */

	}

	public void createInvoiceValidationSheet() throws IOException {

		// Class reference
		TestParameters tstPar = new TestParameters();
		WriteToExcelFile wteF = new WriteToExcelFile();

		String pathToExcel = "./test/resources/data/";
		String resultSheet = "SmokeTestResultSheet.xlsx";

		// *****************Create cells Matched Sheet**********************

		/*
		 * // Invoice Data Validation this.createCells(pathToExcel + resultSheet,
		 * "Invoice Validation", "Invoice Validation", 4, 4);
		 * this.formatCell(pathToExcel + resultSheet, "Invoice Validation", 4, 4,
		 * (short) 1, (byte) 0, "thick", (short) 20, "string");
		 * 
		 * // UAE_O_Invoice this.createCells(pathToExcel + resultSheet,
		 * "Invoice Validation", "UAE_O_Invoice", 6, 2); this.formatCell(pathToExcel +
		 * resultSheet, "Invoice Validation", 6, 2, (short) 1, (byte) 0, "thin", (short)
		 * 14, "string"); // PageNo: this.createCells(pathToExcel + resultSheet,
		 * "Invoice Validation", "PageNo:", 8, 2); this.formatCell(pathToExcel +
		 * resultSheet, "Invoice Validation", 8, 2, (short) 1, (byte) 0, "thin", (short)
		 * 12, "string"); // Test Type this.createCells(pathToExcel + resultSheet,
		 * "Invoice Validation", "Test Type", 8, 4); this.formatCell(pathToExcel +
		 * resultSheet, "Invoice Validation", 8, 4, (short) 1, (byte) 0, "thin", (short)
		 * 12, "string"); // Result this.createCells(pathToExcel + resultSheet,
		 * "Invoice Validation", "Result", 8, 6); this.formatCell(pathToExcel +
		 * resultSheet, "Invoice Validation", 8, 6, (short) 1, (byte) 0, "thin", (short)
		 * 12, "string"); // Page No. this.createCells(pathToExcel + resultSheet,
		 * "Invoice Validation", "Page No.", 10, 2); this.formatCell(pathToExcel +
		 * resultSheet, "Invoice Validation", 10, 2, (short) 1, (byte) 0, "thin",
		 * (short) 12, "string"); // Test Type this.createCells(pathToExcel +
		 * resultSheet, "Invoice Validation", "Test Type", 10, 3);
		 * this.formatCell(pathToExcel + resultSheet, "Invoice Validation", 10, 3,
		 * (short) 1, (byte) 0, "thin", (short) 12, "string"); // Comment
		 * this.createCells(pathToExcel + resultSheet, "Invoice Validation", "Comment",
		 * 10, 4); this.formatCell(pathToExcel + resultSheet, "Invoice Validation", 10,
		 * 4, (short) 1, (byte) 0, "thin", (short) 12, "string"); // Expected
		 * this.createCells(pathToExcel + resultSheet, "Invoice Validation", "Expected",
		 * 10, 5); this.formatCell(pathToExcel + resultSheet, "Invoice Validation", 10,
		 * 5, (short) 1, (byte) 0, "thin", (short) 12, "string"); // Actual
		 * this.createCells(pathToExcel + resultSheet, "Invoice Validation", "Actual",
		 * 10, 6); this.formatCell(pathToExcel + resultSheet, "Invoice Validation", 10,
		 * 6, (short) 1, (byte) 0, "thin", (short) 12, "string"); // Result
		 * this.createCells(pathToExcel + resultSheet, "Invoice Validation", "Result",
		 * 10, 7); this.formatCell(pathToExcel + resultSheet, "Invoice Validation", 10,
		 * 7, (short) 1, (byte) 0, "thin", (short) 12, "string");
		 */
		// **********Data Entry***************
		int dataRow = 6, initRow = dataRow - 1;
		int colPageNo = 2, colInvoiceNo = colPageNo + 1, colInvoiceType = colInvoiceNo + 1,
				colTestType = colInvoiceType + 1, colKey = colTestType + 1, colExpected = colKey + 1,
				colActual = colExpected + 1, colResult = colActual + 1;

		String colNos = Integer.toString(colPageNo) + " " + Integer.toString(colInvoiceNo) + " "
				+ Integer.toString(colInvoiceType) + " " + Integer.toString(colTestType) + " "
				+ Integer.toString(colKey) + " " + Integer.toString(colExpected) + " " + Integer.toString(colActual)
				+ " " + Integer.toString(colResult) + " ";

		wteF.writeBulkData(pathToExcel + resultSheet, "Invoice Validation", invoiceValidationDetails, dataRow, colNos);

		for (List<String> invoiceValidationdetails : invoiceValidationDetails) {

			/*
			 * this.createCells(pathToExcel + resultSheet, "Invoice Validation",
			 * invoiceValidationdetails.get(0), dataRow, colPageNo);
			 * this.createCells(pathToExcel + resultSheet, "Invoice Validation",
			 * invoiceValidationdetails.get(1), dataRow, colInvoiceType);
			 * this.createCells(pathToExcel + resultSheet, "Invoice Validation",
			 * invoiceValidationdetails.get(2), dataRow, colTestType);
			 * this.createCells(pathToExcel + resultSheet, "Invoice Validation",
			 * invoiceValidationdetails.get(3), dataRow, colKey);
			 * this.createCells(pathToExcel + resultSheet, "Invoice Validation",
			 * invoiceValidationdetails.get(4), dataRow, colExpected);
			 * this.createCells(pathToExcel + resultSheet, "Invoice Validation",
			 * invoiceValidationdetails.get(5), dataRow, colActual);
			 * this.createCells(pathToExcel + resultSheet, "Invoice Validation",
			 * invoiceValidationdetails.get(6), dataRow, colResult);
			 */

			dataRow++;
		}

		this.setFilters(pathToExcel + resultSheet, "Invoice Validation", initRow, colPageNo, dataRow, colResult);

		// *********Hyperlink****************

		// this.hyperlink(pathToExcel + resultSheet, "Matched", 1, 7, "Obilling result
		// sheet",
		// "'O Billing Smoke Test'!A1");
		// this.hyperlink(pathToExcel + resultSheet, "Matched", 6, 7, "Graphical
		// Analysis", "'Graphical Analysis'!A1");

	}

	public void createOBillingDataSheet() throws IOException {
		// Class reference
		TestParameters tstPar = new TestParameters();
		WriteToExcelFile wteF = new WriteToExcelFile();

		String pathToExcel = "./test/resources/data/";
		String resultSheet = "SmokeTestResultSheet.xlsx";
		String sheetName = "OBilling_data";

		// *****************Create cells OBilling_data Sheet**********************

		// Row and column for writing PDF pass fail details in report
		int rowNum = 3;
		int colNumforPDFName = 1;
		int colNumforTotalPages = 2;
		int colNumforTotalFailPages = 3;
		int colNumforTotalPassPages = 4;
		int colNumforTotalSkippedPages = 5;

		// Loop for rotating over all tested PDFs
		for (List<String> pdfDetails : this.allPDFPassFailDetails) {

			// PDF Name
			this.createCells(pathToExcel + resultSheet, sheetName, pdfDetails.get(0), rowNum, colNumforPDFName);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforPDFName, (short) 1, (byte) 0, "thin", (short) 15, "string");
			 */

			// PDF page count
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(1)), rowNum,
					colNumforTotalPages);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
			 */

			// Failed
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(2)), rowNum,
					colNumforTotalFailPages);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalFailPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
			 */

			// Passed
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(3)), rowNum,
					colNumforTotalPassPages);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalPassPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
			 */
			// Skipped
			/*
			 * this.createNumberCells(pathToExcel + resultSheet, sheetName,
			 * Double.parseDouble(pdfDetails.get(4)), rowNum, colNumforTotalSkippedPages);
			 */
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalSkippedPages, (short) 1, (byte) 0, "thin", (short) 15,
			 * "number");
			 */

			rowNum++;
		}

		// Row and column for writing testType pass fail details in report
		rowNum = 3;
		int colNumforTestTypeName = 7;
		colNumforTotalFailPages = 8;
		colNumforTotalPassPages = 9;
		colNumforTotalSkippedPages = 10;
		/*
		 * for (List<String> pdfDetails : this.consolidated) {
		 * 
		 * // PDF Name this.createCells(pathToExcel + resultSheet, sheetName,
		 * pdfDetails.get(0), rowNum, colNumforTestTypeName);
		 * 
		 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
		 * colNumforTestTypeName, (short) 1, (byte) 0, "thin", (short) 15, "string");
		 * 
		 * 
		 * // Failed this.createNumberCells(pathToExcel + resultSheet, sheetName,
		 * Double.parseDouble(pdfDetails.get(1)), rowNum, colNumforTotalFailPages);
		 * 
		 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
		 * colNumforTotalFailPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
		 * 
		 * 
		 * // Passed this.createNumberCells(pathToExcel + resultSheet, sheetName,
		 * Double.parseDouble(pdfDetails.get(2)), rowNum, colNumforTotalPassPages);
		 * 
		 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
		 * colNumforTotalPassPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
		 * 
		 * 
		 * // Skipped
		 * 
		 * this.createNumberCells(pathToExcel + resultSheet, sheetName,
		 * Double.parseDouble(pdfDetails.get(3)), rowNum, colNumforTotalSkippedPages);
		 * 
		 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
		 * colNumforTotalSkippedPages, (short) 1, (byte) 0, "thin", (short) 15,
		 * "number");
		 * 
		 * 
		 * rowNum++; }
		 */

		// Row and column for writing consolidated PDF pass fail details in report
		rowNum = 3;
		colNumforPDFName = 12;
		colNumforTotalPages = 13;
		colNumforTotalFailPages = 14;
		colNumforTotalPassPages = 15;
		colNumforTotalSkippedPages = 16;

		// Loop for rotating over all tested PDFs
		for (List<String> pdfDetails : this.allPDFPassFailDetails) {

			// PDF Name
			this.createCells(pathToExcel + resultSheet, sheetName, pdfDetails.get(0), rowNum, colNumforPDFName);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforPDFName, (short) 1, (byte) 0, "thin", (short) 15, "string");
			 */

			// PDF page count
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(1)), rowNum,
					colNumforTotalPages);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
			 */

			// Failed
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(2)), rowNum,
					colNumforTotalFailPages);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalFailPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
			 */

			// Passed
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(3)), rowNum,
					colNumforTotalPassPages);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalPassPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
			 */
			// Skipped
			/*
			 * this.createNumberCells(pathToExcel + resultSheet, sheetName,
			 * Double.parseDouble(pdfDetails.get(4)), rowNum, colNumforTotalSkippedPages);
			 */
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalSkippedPages, (short) 1, (byte) 0, "thin", (short) 15,
			 * "number");
			 */

			rowNum++;
		}

		// Row and column for writing consolidated PDF pass fail details in report
		rowNum = 3;
		colNumforPDFName = 18;
		int colNumforTotalFailKeys = 19;
		int colNumforTotalPassKeys = 20;

		// Loop for rotating over all tested PDFs
		for (List<String> pdfDetails : this.allKeyTestTypeDetailsMap) {

			// PDF Name
			this.createCells(pathToExcel + resultSheet, sheetName, pdfDetails.get(0), rowNum, colNumforPDFName);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforPDFName, (short) 1, (byte) 0, "thin", (short) 15, "string");
			 */

			// Failed
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(1)), rowNum,
					colNumforTotalFailKeys);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
			 */

			// Passed
			this.createNumberCells(pathToExcel + resultSheet, sheetName, Double.parseDouble(pdfDetails.get(2)), rowNum,
					colNumforTotalPassKeys);
			/*
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalFailPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
			 */

			// Passed
			/*
			 * this.createNumberCells(pathToExcel + resultSheet, sheetName,
			 * Double.parseDouble(pdfDetails.get(3)), rowNum, colNumforTotalPassPages);
			 * 
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalPassPages, (short) 1, (byte) 0, "thin", (short) 15, "number");
			 * 
			 * // Skipped this.createNumberCells(pathToExcel + resultSheet, sheetName,
			 * Double.parseDouble(pdfDetails.get(4)), rowNum, colNumforTotalSkippedPages);
			 * 
			 * this.formatCell(pathToExcel + resultSheet, sheetName, rowNum,
			 * colNumforTotalSkippedPages, (short) 1, (byte) 0, "thin", (short) 15,
			 * "number");
			 */

			rowNum++;
		}

	}
	// *********************Sheets End***************************************

}
