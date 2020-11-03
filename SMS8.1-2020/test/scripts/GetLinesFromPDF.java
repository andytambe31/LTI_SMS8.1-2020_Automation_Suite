package scripts;

import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is an example on how to extract text line by line from pdf document
 */
public class GetLinesFromPDF extends PDFTextStripper {

	static List<String> lines = new ArrayList<String>();
	static List<List> PDFList = new ArrayList<List>();
	static Map<Integer, List> pdfpage;

	public GetLinesFromPDF() throws IOException {

	}

	/**
	 * @throws IOException If there is an error parsing the document.
	 */
	public Map<Integer, List> GetPDFDataMap(String fileName) throws IOException {

		String key = "";
		String value = "";
		PDDocument document = null;
		lines = new ArrayList<String>();
		PDFList = new ArrayList<List>();
		

		try {
			pdfpage = new HashMap<>();
			document = PDDocument.load(new File(fileName));
			PDFTextStripper stripper = new GetLinesFromPDF();
			int cnt = 0;
			int finalpagecnt = document.getNumberOfPages();

			for (int i = 1; i <= finalpagecnt; i++) {
				lines = new ArrayList<String>();
				PDFList = new ArrayList<List>();
				stripper.setSortByPosition(true);
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				Writer testdata = new OutputStreamWriter(new ByteArrayOutputStream());
				stripper.writeText(document, testdata);
				// print lines

				PDFList.add(lines);
				pdfpage.put(i, PDFList);

			}
			for (List pdfdata : PDFList) {
				 //System.out.println(pdfdata);
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}

		// return PDFList;
		//System.out.println("PDF Data : " + pdfpage);
		return pdfpage;
	}

	public List<List> GetPDFDataList(String fileName) throws IOException {

		String key = "";
		String value = "";
		PDDocument document = null;
		lines = new ArrayList<String>();
		PDFList = new ArrayList<List>();
		// String fileName =
		// "./test/resources/data/UAE_Invoice.pdf";//"C:\\Users\\binali.patel\\eclipse-workspace\\ComparePDFDataWithExcel\\test\\resources\\data\\UAE_Invoice.pdf";
		/*
		 * try { document = PDDocument.load(new File(fileName)); PDFTextStripper
		 * stripper = new GetLinesFromPDF(); stripper.setSortByPosition(true);
		 * stripper.setStartPage(0); stripper.setEndPage(document.getNumberOfPages());
		 * Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
		 * stripper.writeText(document, dummy);
		 * 
		 * // print lines // for(String line:lines){
		 * 
		 * for(int i=0; i<lines.size();i++){ resultList.add(lines.get(i));
		 * 
		 * System.out.println(lines.get(i));
		 * 
		 * if(lines.get(i).contains("Contract Value : 1009574.76")) {
		 * System.out.println("PASS"); }else if(lines.get(i).contains("Period To")) {
		 * lines.get(i+1).contains("28-Feb-19"); } else
		 * System.out.println("FAIL value is" + lines.get(i));
		 * 
		 * }
		 * 
		 * 
		 * int cnt = 0; int finalpagecnt = document.getNumberOfPages();
		 * 
		 * for (int i = 1; i <= finalpagecnt; i++) { lines = new ArrayList<String>();
		 * stripper.setSortByPosition(true); stripper.setStartPage(i);
		 * stripper.setEndPage(i); Writer testdata = new OutputStreamWriter(new
		 * ByteArrayOutputStream()); stripper.writeText(document, testdata); // print
		 * lines for (String line : lines) { // System.out.println(line); }
		 * PDFList.add(lines.toString()); } for (String pdfdata : PDFList) {
		 * //System.out.println(pdfdata); } } }
		 */

		/*
		 * finally { if (document != null) { document.close(); } } for(String str:
		 * PDFList) { System.out.println(str); }
		 */

		try {
			pdfpage = new HashMap<>();
			document = PDDocument.load(new File(fileName));
			PDFTextStripper stripper = new GetLinesFromPDF();
			int cnt = 0;
			int finalpagecnt = document.getNumberOfPages();

			for (int i = 1; i <= finalpagecnt; i++) {
				lines = new ArrayList<String>();
				stripper.setSortByPosition(true);
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				Writer testdata = new OutputStreamWriter(new ByteArrayOutputStream());
				stripper.writeText(document, testdata);
				// print lines

				for (String line : lines) {
					// System.out.println(lines);
				}
				PDFList.add(lines);
				pdfpage.put(i, PDFList);

			}
			for (List pdfdata : PDFList) {
				// System.out.println(pdfdata);
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}

		return PDFList;
		// return pdfpage;
	}

	/**
	 * Override the default functionality of PDFTextStripper.writeString()
	 */
	@Override
	protected void writeString(String str, List<TextPosition> textPositions) throws IOException {

		lines.add(str);
		// you may process the line here itself, as and when it is obtained
	}
}