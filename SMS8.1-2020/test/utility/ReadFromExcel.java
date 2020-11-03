package utility;

import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFromExcel {
	
	String[][] arrayExcelData = null;         

	Workbook wb = null;
	
	public  String[][] getExcelData(String fileName, String sheetName) throws IOException {    
			
	//System.out.println(fileName+" "+sheetName);
	//try {
		File file = new File(fileName);         
		FileInputStream fs = new FileInputStream(file); 
		//System.out.println("File Created");
		
		wb = new XSSFWorkbook(fs); 
		
		Sheet sh = wb.getSheet(sheetName);        
		int totalNoOfRows = sh.getPhysicalNumberOfRows();         
		int totalNoOfCols = sh.getRow(0).getPhysicalNumberOfCells();         
		
		arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];         

		for (int i= 1 ;i <= totalNoOfRows-1;i++) {
			//System.out.println("1st for loop");
			for (int j=0;j <= totalNoOfCols-1;j++) {
				sh.getRow(i).getCell(j).setCellType(1);
				arrayExcelData[i-1][j] = sh.getRow(i).getCell(j).getStringCellValue().toString();
				}
			}
		
		//Close inputstream
		fs.close();
	/*} 
			catch (Exception e) {
				System.out.println("error in getExcelData()");         
				}*/
			return arrayExcelData;         
	}
	
	public String readDataFromSpecificPosition(String fileName, String sheetName,int rowNo,int columnNo) {
		
		String arrayExcelData1 = null; 
		try {
			File file = new File(fileName);         
			FileInputStream fs = new FileInputStream(file); 
			
			wb = new XSSFWorkbook(fs); 
			
			
			Sheet sh = wb.getSheet(sheetName);        
			int totalNoOfRows = sh.getPhysicalNumberOfRows();         
			int totalNoOfCols = sh.getRow(0).getPhysicalNumberOfCells();         
			
			arrayExcelData = new String[totalNoOfRows-1][totalNoOfCols];         

			sh.getRow(rowNo).getCell(columnNo).setCellType(1);
			arrayExcelData1 = sh.getRow(rowNo).getCell(columnNo).getStringCellValue().toString();
			
			fs.close();
		} 
				catch (Exception e) {
					System.out.println("error in getExcelData()*");         
					}
				return arrayExcelData1; 
	}

}