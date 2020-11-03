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
 * This is an example on how to get the x/y coordinates and size of each
 * character in PDF
 */
public class PDFLocation extends PDFTextStripper {
	static List<String> lines = new ArrayList<String>();
	List<String> PDFList1;// = new ArrayList<String>();

	static List<String> lines2 = new ArrayList<String>();
	static List<String> PDFList2 = new ArrayList<String>();

	public PDFLocation() throws IOException {

	}

	public void returnLocation(String fileName, int pageNo, List<String> keyList) throws IOException {
		PDDocument document = null;
		String temp = "";
		PDFList1 = new ArrayList<String>();
		try {
			document = PDDocument.load(new File(fileName));
			PDFTextStripper stripper = new PDFLocation();

			lines = new ArrayList<String>();
			stripper.setSortByPosition(true);
			stripper.setStartPage(pageNo);
			stripper.setEndPage(pageNo);
			Writer testdata = new OutputStreamWriter(new ByteArrayOutputStream());
			stripper.writeText(document, testdata);

			// Only add location of keys
			int endPoint = 0;
			boolean flag = false;
			for (String keyString : keyList) {
				List<Integer> pointer = new ArrayList<Integer>();

				for (int i = endPoint; i < lines.size(); i++) {

					String t = lines.get(i).substring(0, 1);
					if (keyString.substring(0, 1).contains(t)) {
						flag = true;
					}

					if (flag == true) {
						temp = temp + lines.get(i).substring(0, 1);
						pointer.add(i);

						if (keyString.equals(temp)) {
							int startPoint = pointer.get(0);
							endPoint = pointer.get(pointer.size() - 1);
							;
							while (startPoint <= endPoint) {
								PDFList1.add(lines.get(startPoint));
								startPoint++;
							}
							temp = "";
							pointer = new ArrayList<Integer>();
							endPoint++;
							flag = false;
							break;
						}
					}

					if (flag == false) {
						flag = false;
						continue;
					}
				}
			}

			for (String pdfdata : PDFList1) {
				System.out.println(pdfdata);
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	public void matchLocation(String fileName, int pageNo) throws IOException {

		PDDocument document = null;
		// String fileName = "./test/resources/data/QatarQC.pdf";
		// "D:\\OTIS_ApplicationStuff\\SMS\\Invoices\\Invoices\\QatarQC.pdf";
		PDFList1 = new ArrayList<String>();
		try {
			document = PDDocument.load(new File(fileName));
			PDFTextStripper stripper = new PDFLocation();
			// PDFTextStripper stripper = new GetLinesFromPDF();
			lines = new ArrayList<String>();
			stripper.setSortByPosition(true);
			stripper.setStartPage(pageNo);
			stripper.setEndPage(pageNo);
			// stripper.setEndPage(document.getNumberOfPages());
			Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
			stripper.writeText(document, dummy);
			for (String line : lines) {
				PDFList2.add(line);
			}
			// PDFList2.add(lines.toString());
			for (String pdfdata : PDFList2) {
				for (String pdfdata1 : PDFList1) {
					if (pdfdata1.equals(pdfdata) || pdfdata.equals(pdfdata1)) {
						System.out.println("Matching Location : " + pdfdata);
					} else {
						System.out.println("UnMatched Location : " + pdfdata);
					}
				}
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}
	}

	public int returnNumOfPages(String fileName) {

		PDDocument document = null;
		String temp = "";
		PDFList1 = new ArrayList<String>();
		try {
			document = PDDocument.load(new File(fileName));
			PDFTextStripper stripper = new PDFLocation();
			PDFLocation pdfLoc = new PDFLocation();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return document.getNumberOfPages();
	}

	public List<String> returnLoc(String fileName, int pageNo, List<String> keyList) throws IOException {
		PDDocument document = null;
		String temp = "";
		PDFList1 = new ArrayList<String>();

		// Hashmap
		Map<String, List> PDFLocations = new HashMap<String, List>();

		// Lists
		List<String> PDFLocationsList = new ArrayList<String>();
		try {
			document = PDDocument.load(new File(fileName));
			PDFTextStripper stripper = new PDFLocation();
			PDFLocation pdfLoc = new PDFLocation();

			lines = new ArrayList<String>();
			stripper.setSortByPosition(true);
			stripper.setStartPage(pageNo);
			stripper.setEndPage(pageNo);
			Writer testdata = new OutputStreamWriter(new ByteArrayOutputStream());
			stripper.writeText(document, testdata);

			// Only add location of keys
			boolean flag = false;
			int keyPointer = 0;
			for (String keyString : keyList) {
				PDFLocationsList = new ArrayList<String>();
				List<Integer> pointer = new ArrayList<Integer>();
				List<Integer> skipPointer = new ArrayList<Integer>();

				for (int i = 0; i < lines.size(); i++) {

					// Check if value of i is not present in skip pointer
					if (skipPointer.contains(i)) {
						continue;
					}

					// Store each character one after the other
					temp = temp + lines.get(i).substring(0, 1);
					pointer.add(i);

					// Match each character of keyString with lines
					String keyCharacter = pdfLoc.getStringAt(keyString, keyPointer);
					String lineCharacter = lines.get(i).substring(0, 1);
					if (keyCharacter.contains(lineCharacter)) {
						keyPointer++;
						// Check if consecutive characters are present in keyString
						if (keyString.contains(temp)) {

							// Check if consecutive characters are completely present in keyString
							if (keyString.equals(temp)) {
								flag = true;
							}

							// Flag is true then add to list
							if (flag == true) {
								int startPoint = pointer.get(0);
								int endPoint = pointer.get(pointer.size() - 1);

								while (startPoint <= endPoint) {
									PDFList1.add(lines.get(startPoint));
									PDFLocationsList.add(lines.get(startPoint));
									startPoint++;
								}
								PDFLocations.put(keyString, PDFLocationsList);
								temp = "";
								skipPointer.addAll(pointer);
								pointer = new ArrayList<Integer>();
								keyPointer = 0;
								flag = false;
								break;

							}
						} else {
							continue;
						}

					} else {
						temp = "";
						pointer = new ArrayList<Integer>();
						keyPointer = 0;
						continue;
					}

					if (i == lines.size() && flag == false) {
						break;
					}
				}
			}

			for (String pdfdata : PDFList1) {
				// System.out.println(pdfdata);
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}
		return PDFList1;
	}

	public Map returnLocMap(String fileName, int pageNo, List<String> keyList) throws IOException {
		PDDocument document = null;
		String temp = "";
		PDFList1 = new ArrayList<String>();

		// Hashmap
		Map<String, List> PDFLocations = new HashMap<String, List>();

		// Lists
		List<String> PDFLocationsList = new ArrayList<String>();
		try {
			document = PDDocument.load(new File(fileName));
			PDFTextStripper stripper = new PDFLocation();
			PDFLocation pdfLoc = new PDFLocation();

			lines = new ArrayList<String>();
			stripper.setSortByPosition(true);
			stripper.setStartPage(pageNo);
			stripper.setEndPage(pageNo);
			Writer testdata = new OutputStreamWriter(new ByteArrayOutputStream());
			stripper.writeText(document, testdata);

			// Only add location of keys
			boolean flag = false;
			int keyPointer = 0;
			for (String keyString : keyList) {
				PDFLocationsList = new ArrayList<String>();
				List<Integer> pointer = new ArrayList<Integer>();
				List<Integer> skipPointer = new ArrayList<Integer>();

				for (int i = 0; i < lines.size(); i++) {

					// Check if value of i is not present in skip pointer
					if (skipPointer.contains(i)) {
						continue;
					}

					// Store each character one after the other
					temp = temp + lines.get(i).substring(0, 1);
					pointer.add(i);

					// Match each character of keyString with lines
					String keyCharacter = pdfLoc.getStringAt(keyString, keyPointer);
					String lineCharacter = lines.get(i).substring(0, 1);
					if (keyCharacter.contains(lineCharacter)) {
						keyPointer++;
						// Check if consecutive characters are present in keyString
						if (keyString.contains(temp)) {

							// Check if consecutive characters are completely present in keyString
							if (keyString.equals(temp)) {
								flag = true;
							}

							// Flag is true then add to list
							if (flag == true) {
								int startPoint = pointer.get(0);
								int endPoint = pointer.get(pointer.size() - 1);

								while (startPoint <= endPoint) {
									PDFList1.add(lines.get(startPoint));
									PDFLocationsList.add(lines.get(startPoint));
									startPoint++;
								}
								PDFLocations.put(keyString, PDFLocationsList);
								temp = "";
								skipPointer.addAll(pointer);
								pointer = new ArrayList<Integer>();
								keyPointer = 0;
								flag = false;
								break;

							}
						} else {
							continue;
						}

					} else {
						temp = "";
						pointer = new ArrayList<Integer>();
						keyPointer = 0;
						continue;
					}

					if (i == lines.size() && flag == false) {
						break;
					}
				}
			}

			for (String pdfdata : PDFList1) {
				// System.out.println(pdfdata);
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}
		return PDFLocations;
	}

	public String getStringAt(String str, int location) {

		if (location == 0) {
			return str.substring(0, location + 1);
		} else
			return str.substring(location, location + 1);
	}

	/**
	 * Override the default functionality of PDFTextStripper.writeString()
	 */
	/*
	 * @Override protected void writeString(String str, List<TextPosition>
	 * textPositions) throws IOException { lines.add(str); // you may process the
	 * line here itself, as and when it is obtained }
	 */
	/**
	 * Override the default functionality of PDFTextStripper.writeString()
	 */
	@Override
	protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
		// lines.add(string);
		// textPositions.get(0);

		for (TextPosition text : textPositions) {
			/*
			 * System.out.println( text.getUnicode()+ " [(X=" + text.getXDirAdj() + ",Y=" +
			 * text.getYDirAdj() + ") height=" + text.getHeightDir() + " width="
			 * +text.getWidthDirAdj() + "]" );
			 */

			String xLoc = String.format("%.2f", text.getXDirAdj());
			String yLoc = String.format("%.2f", text.getYDirAdj());
			String heightLoc = String.format("%.2f", text.getHeightDir());
			String widthLoc = String.format("%.2f", text.getWidthDirAdj());
			String fontLoc = String.format("%.2f", text.getFontSize());
			// .format("%.2f")
			lines.add(text.getUnicode() + " [(X=" + xLoc + ",Y=" + yLoc + ") height=" + heightLoc + " width=" + widthLoc
					+ " Font=" + fontLoc + "]");
		}
	}
}