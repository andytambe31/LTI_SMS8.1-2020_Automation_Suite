package scripts;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

public class TableChecker {

	static List<String> ls;

	@SuppressWarnings("rawtypes")
	public List<String> returnTableContent(String FILENAME, List<String> keyList, int pageNum) throws IOException {

		// PDDocument pd = PDDocument.load(new
		// File("D:\\OTIS_ApplicationStuff\\SMS\\Invoices\\Sigma SG_OInvoice.pdf"));
		// TODO Auto-generated method stub

		ls = new ArrayList<String>();

		// final String FILENAME= "./test/resources/data/QatarQC.pdf";

		// "D:\\OTIS_ApplicationStuff\\SMS\\Invoices\\Sigma SG_OInvoice.pdf";

		PDDocument pd = PDDocument.load(new File(FILENAME));

		int totalPages = pd.getNumberOfPages();

		ObjectExtractor oe = new ObjectExtractor(pd);
		SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
		Page page = oe.extract(pageNum);

		// extract text from the table after detecting
		List<Table> table = sea.extract(page);

		String temp = "";
		for (Table tables : table) {
			for (String key : keyList) {
				List<List<RectangularTextContainer>> rows = tables.getRows();
				for (int i = 0; i < rows.size(); i++) {
					List<RectangularTextContainer> cells = rows.get(i);
					String cellValue = null;
					temp = "";
					for (int j = 0; j < cells.size(); j++) {
						if (!cells.get(j).getText().isEmpty()) {
							cellValue = cells.get(j).getText().toString().trim();
							cellValue = cellValue.replaceAll("\r", " ");

							// System.out.println(cellValue);
							if (cellValue.contains(key)) {
								temp = temp + cellValue;
								if (temp.equals(temp)) {
									ls.add(key);
									temp = "";
									// System.out.println("Tabular : "+ ls);
								} else
									continue;
							}
						}
					}
				}
			}
		}

		return ls;
	}

	public List pdfTabularData(String FILENAME, int pageNum) throws InvalidPasswordException, IOException {

		ls = new ArrayList<String>();

		PDDocument pd = PDDocument.load(new File(FILENAME));

		int totalPages = pd.getNumberOfPages();

		ObjectExtractor oe = new ObjectExtractor(pd);
		SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
		Page page = oe.extract(pageNum);

		// extract text from the table after detecting
		List<Table> table = sea.extract(page);

		String temp = "";
		for (Table tables : table) {
			List<List<RectangularTextContainer>> rows = tables.getRows();
			for (int i = 0; i < rows.size(); i++) {
				List<RectangularTextContainer> cells = rows.get(i);
				String cellValue = null;
				temp = "";
				for (int j = 0; j < cells.size(); j++) {
					if (!cells.get(j).getText().isEmpty()) {
						cellValue = cells.get(j).getText().toString().trim();
						cellValue = cellValue.replaceAll("\r", " ");
						ls.add(cellValue);

					}
				}
			}
		}

		return ls;
	}

	public int returnNoOfTables(String filename, int pageNum) throws InvalidPasswordException, IOException {

		PDDocument pd = PDDocument.load(new File(filename));
		ObjectExtractor oe = new ObjectExtractor(pd);
		SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
		Page page = oe.extract(pageNum);

		// extract text from the table after detecting
		List<Table> table = sea.extract(page);

		return table.size();

	}

	public int returnNumOfPages(String fileName) {

		PDDocument document = null;
		String temp = "";
		List<String> PDFList1 = new ArrayList<String>();
		try {
			document = PDDocument.load(new File(fileName));
			PDFTextStripper stripper = new PDFLocation();
			PDFLocation pdfLoc = new PDFLocation();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return document.getNumberOfPages();
	}

}
