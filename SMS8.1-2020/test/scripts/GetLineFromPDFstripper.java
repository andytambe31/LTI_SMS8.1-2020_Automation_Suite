package scripts;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class GetLineFromPDFstripper extends PDFTextStripper {

	public GetLineFromPDFstripper() throws IOException {
		super();
	}

	static List<String> lines = new ArrayList<String>();
	static List<List> PDFList = new ArrayList<List>();
	static List<List> PDFList2 = new ArrayList<List>();
	static Map<Integer, List> pdfpage;


	// GetPDFDataStripperMap
	public Map<Integer, List> GetPDFDataStripperMap(String fileName) throws InvalidPasswordException, IOException {
		PDDocument document = null;
		// String fileName = "./test/resources/data/UAE_OInvoice.pdf"; // pdf 1 path

		document = PDDocument.load(new File(fileName));
		// PDFTextStripper stripper = new GetPDFCharLocationAndSize();
		// PDFTextStripper stripper = new GetLinesFromPDF(); //recent
		GetLineFromPDFstripper stripper = new GetLineFromPDFstripper(); // new

		int cnt = 0;
		int finalpagecnt = document.getNumberOfPages();
		pdfpage = new HashMap<>();

		// Map
		for (int i = 1; i <= finalpagecnt; i++) {
			lines = new ArrayList<String>();
			stripper.setSortByPosition(true);
			stripper.setStartPage(i);
			stripper.setEndPage(i);
			// new
			String parsedText = stripper.getText(document);
			/*
			 * System.out.println(
			 * "----------------------------------------------------------------------");
			 * System.out.println("Page " + stripper.getStartPage() +
			 * " UAE_OInvoice pdf 1 "); System.out.println(
			 * "----------------------------------------------------------------------");
			 * System.out.println(parsedText);
			 */
			String str[] = parsedText.split("\r\n");
			lines = new ArrayList<String>();
			PDFList = new ArrayList<List>();
			for (int j = 0; j < str.length; j++) {
				lines.add(str[j]);
			}

			PDFList.add(lines);
			pdfpage.put(i, PDFList);

		}
		return pdfpage;
	}

	// GetPDFDataStripperList
	public List<List> GetPDFDataStripperList(String fileName) throws IOException {

		PDDocument document = null;
		// String fileName = "./test/resources/data/UAE_OInvoice.pdf"; // pdf 1 path

		document = PDDocument.load(new File(fileName));
		// PDFTextStripper stripper = new GetPDFCharLocationAndSize();
		// PDFTextStripper stripper = new GetLinesFromPDF(); //recent
		GetLineFromPDFstripper stripper = new GetLineFromPDFstripper(); // new
		PDFList2 = new ArrayList<List>();

		int cnt = 0;
		int finalpagecnt = document.getNumberOfPages();
		pdfpage = new HashMap<>();

		// List
		for (int i = 1; i <= finalpagecnt; i++) {
			lines = new ArrayList<String>();
			stripper.setSortByPosition(true);
			stripper.setStartPage(i);
			stripper.setEndPage(i);
			// new
			String parsedText = stripper.getText(document);
			/*
			 * System.out.println(
			 * "----------------------------------------------------------------------");
			 * System.out.println("Page " + stripper.getStartPage() +
			 * " UAE_OInvoice pdf 1 "); System.out.println(
			 * "----------------------------------------------------------------------");
			 * System.out.println(parsedText);
			 */
			String str[] = parsedText.split("\r\n");
			lines = new ArrayList<String>();
			for (int j = 0; j < str.length; j++) {
				lines.add(str[j]);
			}

			PDFList2.add(lines);
		}
		return PDFList2;
	}

}
