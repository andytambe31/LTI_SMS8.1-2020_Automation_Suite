package scripts;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HyperLink {
	public static void main(String[] args) throws IOException {
		/* Create Workbook and Worksheet - Add Input Rows */
		XSSFWorkbook my_workbook = new XSSFWorkbook();
		XSSFSheet my_sheet = my_workbook.createSheet("Autofilter");
		/* Create Test Data */
		/* Add header rows */
		Row row0 = my_sheet.createRow(0);
		row0.createCell(0).setCellValue("Quarter");
		row0.createCell(1).setCellValue("Brand");
		row0.createCell(2).setCellValue("Count");
		/* Add test data */
		Row row1 = my_sheet.createRow(1);
		row1.createCell(0).setCellValue("Q1");
		row1.createCell(1).setCellValue("A");
		row1.createCell(2).setCellValue(new Double(10));

		Row row2 = my_sheet.createRow(2);
		row2.createCell(0).setCellValue("Q1");
		row2.createCell(1).setCellValue("B");
		row2.createCell(2).setCellValue(new Double(15));

		Row row3 = my_sheet.createRow(3);
		row3.createCell(0).setCellValue("Q2");
		row3.createCell(1).setCellValue("A");
		row3.createCell(2).setCellValue(new Double(23));

		Row row4 = my_sheet.createRow(4);
		row4.createCell(0).setCellValue("Q2");
		row4.createCell(1).setCellValue("C");
		row4.createCell(2).setCellValue(new Double(24));

		my_sheet.setAutoFilter(CellRangeAddress.valueOf("A1:C5"));
		/* Write changes to the workbook */
		FileOutputStream out = new FileOutputStream(new File("excel_auto_filter.xlsx"));
		my_workbook.write(out);
		out.close();

	}

}
