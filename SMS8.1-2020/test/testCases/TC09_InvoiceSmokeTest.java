package testCases;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

//import scripts.ExcelCreator; // Uncomment
import scripts.GetLinesFromPDF;
import scripts.ImageChecker;
import scripts.KeyList;
import scripts.PDFLocation;
//import scripts.ResultWriter;
import scripts.StoreDataWithKeyFromInvoice;
import scripts.StoreDataWithoutKeyFromInvoice;
import scripts.TableChecker;
import scripts.TestParameters;
import utility.ReadFromExcel;
import utility.utilityFunctions;
import scripts.CheckMismatch;
import scripts.ExcelCreator;
import scripts.GetLineFromPDFstripper;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class TC11_InvoiceSmokeTest {

	// Class references
	PDFLocation pdfLoc;// = new PDFLocation();
	TestParameters tstParameters = new TestParameters();
	KeyList klist = new KeyList();

	// WebDriver
	WebDriver driver;

	// Class references
	// KeyAndValueMapping hMap = new KeyAndValueMapping();
	StoreDataWithoutKeyFromInvoice storeSQLData = new StoreDataWithoutKeyFromInvoice();
	StoreDataWithKeyFromInvoice storeSQLWithData = new StoreDataWithKeyFromInvoice();

	// Hashmaps
	Map<String, List> dbDataWithoutKey = new HashMap<>();
	Map<String, List> dbDataWithKey = new HashMap<>();
	Map<Integer, List> pdfData1 = new HashMap<>();
	Map<Integer, List> failResult = new HashMap<>();
	Map<Integer, List> invoicePageDetails;
	Map<String, List> reportResult = new HashMap<>();

	// Lists
	List<String> v = new ArrayList();
	List<String> listKeys = new ArrayList();
	List<List> pdfData = new ArrayList();
	List<List> pdfData_WithoutKey = new ArrayList();
	List<String> reportData = new ArrayList();
	List<List> allReportData = new ArrayList();
	List<String> invoiceValidationData = new ArrayList();
	List<List> invoiceValidationReport = new ArrayList();
	List<String> testTypePassFailKeyCount = new ArrayList<String>();
	List<List> testTypePassFailKeyCountList = new ArrayList<List>();
	List<List> allInvoiceValidationDetails = new ArrayList<List>();
	List<String> invoiceValidationDetails = new ArrayList<String>();
	List<String> passFailPages = new ArrayList<String>();
	List<List> allPassFailPages = new ArrayList<List>();

	// Integer
	int noOfPages;
	int noOfFailPages;
	int noOfPassPages;
	int allPassCounter;
	int allFailCounter;

	// Strings
	String value;
	String pathToExcelFile = "./test/resources/data/";
	String testPDF;
	String refPDF;
	String methodName;

	// long
	long endTime;
	long startTime;
	long timeElapsed;

	// Result flag
	boolean dataVerfication = true;
	boolean locationVerfication = false;
	boolean tableVerfication = false;
	boolean imageVerfication = false;

	@DataProvider(name = "DP1")
	public String[][] getTestPDF() throws IOException {
		int pdfCount = 2;
		boolean OInvoiceId = true;
		boolean TInvoiceId = true;
		// Column
		int j = 8;
		ReadFromExcel readFromeExcel = new ReadFromExcel();
		Object[][] retObjArr = readFromeExcel.getExcelData(pathToExcelFile + "preSmokeTestData.xlsx", "Data");

		// Rotate over preSmokeTestSheet data to get testPDF count
		for (int i = 0; i < retObjArr.length; i++) {
			if (retObjArr[i][j].equals("Yes")) {
				if (retObjArr[i][9].equals("null")) {
					pdfCount--;
					OInvoiceId = false;
				}
				if (retObjArr[i][11].equals("null")) {
					pdfCount--;
					TInvoiceId = false;
				}
			}

		}

		// Two PDFs O and T invoice
		String[][] retObjArr2 = new String[pdfCount][1];

		// Counter
		int pdfCounter = 0;

		// Rotate over preSmokeTestSheet data to get testPDF names
		for (int i = 0; i < retObjArr.length; i++) {
			if (retObjArr[i][j].equals("Yes")) {
				if (OInvoiceId) {
					retObjArr2[pdfCounter][0] = (String) retObjArr[i][9];
					pdfCounter++;
				}

				// System.out.println(retObjArr[i][9]);
				if (TInvoiceId) {
					retObjArr2[pdfCounter][0] = (String) retObjArr[i][11];
				}
				// System.out.println(retObjArr[i][11]);
			}

		}
		return retObjArr2;
	}

	@BeforeClass
	public void testCasesSetUp() {

		System.out.println("*******\tPDF smoke Test\t*******");

		driver = tstParameters.getDriver();
		testPDF = tstParameters.getOBillingTestPDF();
		refPDF = tstParameters.getOBillingReferencePDF();// ;
	}

	@Test(priority = 0)
	public void atestSetUp() throws IOException, ClassNotFoundException, SQLException {

		// method name
		methodName = "dataWithoutKey";

		// tstParameters.setEntity("India");
		GetLinesFromPDF pdf = new GetLinesFromPDF();
		GetLineFromPDFstripper strippPdf = new GetLineFromPDFstripper();
		utilityFunctions utFln = new utilityFunctions(driver);

		// returns hashmap for without key Data
		dbDataWithoutKey = new HashMap<>();
		dbDataWithoutKey = storeSQLData.runQuery();

		// returns invoice data
		pdfData1 = new HashMap<>();
		pdfData = new ArrayList();

		pdfData1 = // pdf.GetPDFDataMap(pathToExcelFile + testPDF);
				strippPdf.GetPDFDataStripperMap(pathToExcelFile + testPDF);
		pdfData = // pdf.GetPDFDataList(pathToExcelFile + testPDF);
				strippPdf.GetPDFDataStripperList(pathToExcelFile + testPDF);
		pdfData_WithoutKey = strippPdf.GetPDFDataStripperList(pathToExcelFile + testPDF);

		// Get all keys
		Set<String> keys = dbDataWithoutKey.keySet();
		for (String k : keys) {
			// System.out.println("Key: " + k);
		}

		// code to compare the data without key with pdf data pagewise

		Iterator itr = keys.iterator();

		allPassCounter = 0;
		allFailCounter = 0;
		noOfFailPages = 0;
		noOfPassPages = 0;

		// stores all the keys
		listKeys = new ArrayList();
		while (itr.hasNext()) {
			listKeys.add((String) itr.next());
		}

		List<String> invoicePageData = new ArrayList<String>();
		invoicePageDetails = new HashMap();
		int keyLength = listKeys.size();
		int counter = 0;
		noOfPages = 0;
		boolean invoiceNumMatched = false;

		// Check if number of pages in PDF are equal invoices in DB
		if (pdfData1.size() == dbDataWithoutKey.size()) { // Uncomment
			// if (true) { // Uncomment
			System.out.println("Pages are equal");
			// First invoice
			// ************HashMap for storing PDF page details************

			// Invoice no. from DB loop

			for (String kys : listKeys) {

				// Get Page No from Invoice and associate it with Invoice Type,Invoice No. and
				// Manual Invoice flag loop
				for (int i = 1; i <= pdfData1.size(); i++) {
					for (List pageWiseData : (List<List>) pdfData1.get(i)) {
						// System.out.println("For loop count: "+Forcounter+ " and nofOfRecords:
						// "+noOfrecords);

						// Check if invoice no. from DB

						for (String str : (List<String>) pageWiseData) {
							if (str.contains(kys)) {
								List<String> pdfIndvValues = dbDataWithoutKey.get(kys);
								int len = pdfIndvValues.size();
								int dataCount = 0;
								// System.out.println(pdfIndvValues);

								for (String indValue : pdfIndvValues) {
									dataCount++;
									// Store page level data

									// System.out.println("Value" + indValue);

									if (dataCount <= 2) {
										invoicePageData.add(indValue);
										if (dataCount == 2) {
											invoicePageData.add(kys);
											invoicePageDetails.put(i, invoicePageData);
											invoicePageData = new ArrayList<String>();
											break;
										}

									}

								}
							}
						}

					}
				}
			}

			System.out.println("Invoice page details :" + invoicePageDetails);
		}

	}

	@Test(priority = 1, dataProvider = "DP1")
	public void checkDataWithoutKey(String testPDF)
			throws IOException, InterruptedException, ClassNotFoundException, SQLException {

		System.out.println("*************************WithoutKey**************************");
		// method name
		methodName = "dataWithoutKey";

		// tstParameters.setEntity("India");
		GetLinesFromPDF pdf = new GetLinesFromPDF();
		utilityFunctions utFln = new utilityFunctions(driver);

		// returns hashmap for without key Data
		dbDataWithoutKey = new HashMap<>();
		dbDataWithoutKey = storeSQLData.runQuery();

		/*
		 * // returns invoice data pdfData1 = new HashMap<>(); pdfData = new
		 * ArrayList(); try { pdfData1 = pdf.GetPDFDataMap(pathToExcelFile + testPDF);
		 * pdfData = pdf.GetPDFDataList(pathToExcelFile + testPDF); } catch (Exception
		 * e) { pdfData1 = pdf.GetPDFDataMap(pathToExcelFile + "UAE_OInvoice_bkp.pdf");
		 * pdfData = pdf.GetPDFDataList(pathToExcelFile + "UAE_OInvoice_bkp.pdf"); }
		 */

		// Get all keys
		Set<String> keys = dbDataWithoutKey.keySet();
		for (String k : keys) {
			// System.out.println("Key: " + k);
		}

		// code to compare the data without key with pdf data pagewise

		Iterator itr = keys.iterator();

		allPassCounter = 0;
		allFailCounter = 0;
		noOfFailPages = 0;
		noOfPassPages = 0;

		// stores all the keys
		listKeys = new ArrayList();
		while (itr.hasNext()) {
			listKeys.add((String) itr.next());
		}

		List<String> invoicePageData = new ArrayList<String>();
		List matchedPage = new ArrayList();
		// invoicePageDetails = new HashMap();
		int keyLength = listKeys.size();
		int counter = 0;
		noOfPages = 0;
		boolean invoiceNumMatched = false;
		boolean pageVerification = false;
		boolean invoiceFlagForQatar = false;

		// Check if number of pages in PDF are equal invoices in DB
		if (pdfData.size() == dbDataWithoutKey.size()) { // Uncomment
			// if (true) { // Uncomment
			System.out.println("Pages are equal");
			int pageNo = 0;
			// **************Matching Logic********************
			for (String invoiceNo : keys) {

				pageVerification = false;
				invoiceFlagForQatar = false;

				List<String> expectedActualValue = new ArrayList<String>();

				// Check Invoice in PDF
				for (int i = 0; i < pdfData.size(); i++) { // Loop through the pdf pages
					invoiceFlagForQatar = false;

					// Skip matched pages
					if (matchedPage.contains(i)) {
						continue;
					}

					// Get pagewise list of the pdf
					List<String> ls = (List<String>) pdfData.get(i);
					List<String> ls2 = (List<String>) pdfData_WithoutKey.get(i);

					if (!tstParameters.getEntity().contains("QATAR")) {
						for (String lin : ls2) {
							if (lin.contains(invoiceNo)) {
								invoiceFlagForQatar = true;
								break;
							}
						}
					} else {
						for (String lin : ls) {
							if (lin.contains(invoiceNo)) {
								invoiceFlagForQatar = true;
								break;
							}
						}
					}

					// Check if invoiceNo is found in the page
					if (ls.contains(invoiceNo) || invoiceFlagForQatar == true) {
						System.out.println("************* Page " + (i + 1) + " *************");

						// Store i value of matched page
						matchedPage.add(i);

						pageNo = i + 1;

						// Get data to be verified
						List<String> values = dbDataWithoutKey.get(invoiceNo);

						// Compare
						int vCounter = 0;

						// Loop through values of data to be verified
						int passCounter = 0;
						boolean flagForIntegerMatch = false;
						System.out.println("Database data: " + values);
						for (String value : values) {
							vCounter++;
							boolean matched = false;

							// Skip first 2 values
							if (vCounter < 3) {
								continue;
							}

							int lineCounter = 0;
							boolean linematcherflag = false;

							if (tstParameters.getEntity().equals("India")) {
								for (String line : ls) {
									linematcherflag = false;
									lineCounter++;

									// Compare
									if (line.contains(value.replaceAll("\\s+", " ")) || line.contains(value)) {

										flagForIntegerMatch = true;

										// Check if the matched value is float or integer, for completely matching
										String temp = value.replaceAll(",", "");

										if (utFln.isInteger(temp) || utFln.isDouble(temp)) {

											// Create an array of words from the matched PDF line
											String[] wordList = line.split(" ");

											int wordListCounter = 0;
											for (String word : wordList) {

												wordListCounter++;
												if (word.replaceAll(" ", "").equals(value)) {
													line = word;
													matched = true;
													break;
												}
												if (wordListCounter == wordList.length) {
													flagForIntegerMatch = false;
													continue;
												}
											}
										} else {
											matched = true;
										}

										if (!flagForIntegerMatch) {
											continue;
										}

										passCounter++;

										if (matched) {
											linematcherflag = true;
											// get data for Invoice Validation report
											System.out.println(" Passed - " + value);
											invoiceValidationDetails = new ArrayList<String>();
											invoiceValidationDetails.add(String.valueOf(pageNo));
											invoiceValidationDetails.add(String.valueOf(invoiceNo));
											invoiceValidationDetails.add(testPDF);
											invoiceValidationDetails.add(methodName);
											invoiceValidationDetails.add("DB Data");
											invoiceValidationDetails.add(value);
											invoiceValidationDetails.add(line);
											invoiceValidationDetails.add("Pass");
											allInvoiceValidationDetails.add(invoiceValidationDetails);
											invoiceValidationDetails = new ArrayList<String>();
											linematcherflag = true;
										} else {

											/*passCounter--;
											expectedActualValue.add(" Mismatch: " + v);
											failResult.put(pageNo, expectedActualValue);

											// get the Invoice Validation report
											System.out.println(" Failed - " + value);
											invoiceValidationDetails = new ArrayList<String>();
											invoiceValidationDetails.add(String.valueOf(pageNo));
											invoiceValidationDetails.add(String.valueOf(invoiceNo));
											invoiceValidationDetails.add(testPDF);
											invoiceValidationDetails.add(methodName);
											invoiceValidationDetails.add("DB Data");
											invoiceValidationDetails.add(value);
											invoiceValidationDetails.add("Value mismatch");
											invoiceValidationDetails.add("Fail");
											allInvoiceValidationDetails.add(invoiceValidationDetails);
											invoiceValidationDetails = new ArrayList<String>();

											passFailPages = new ArrayList<String>();
											passFailPages.add(String.valueOf(pageNo));
											allPassFailPages.add(passFailPages);*/
										}

										break;
									}

									/*if (lineCounter == ls.size()) {
										// Assert.assertTrue(false);
										expectedActualValue.add(" Mismatch: " + v);
										failResult.put(pageNo, expectedActualValue);

										// get the Invoice Validation report
										System.out.println(" Failed - " + value);
										invoiceValidationDetails = new ArrayList<String>();
										invoiceValidationDetails.add(String.valueOf(pageNo));
										invoiceValidationDetails.add(String.valueOf(invoiceNo));
										invoiceValidationDetails.add(testPDF);
										invoiceValidationDetails.add(methodName);
										invoiceValidationDetails.add("DB Data");
										invoiceValidationDetails.add(value);
										invoiceValidationDetails.add("Value mismatch");
										invoiceValidationDetails.add("Fail");
										allInvoiceValidationDetails.add(invoiceValidationDetails);
										invoiceValidationDetails = new ArrayList<String>();

										passFailPages = new ArrayList<String>();
										passFailPages.add(String.valueOf(pageNo));
										allPassFailPages.add(passFailPages);
									}*/

								}
							}

							if (linematcherflag == false) {

								// Class references
								TableChecker tbCheck = new TableChecker();

								// Get page tabular data
								List<String> keyTableContentListActual = new ArrayList<String>();
								keyTableContentListActual
										.addAll(tbCheck.pdfTabularData(pathToExcelFile + testPDF, pageNo));

								int tableCount = 0;
								for (String tableContent : keyTableContentListActual) {
									tableCount++;

									if (tableContent.contains(value.replaceAll("\r", " "))
											|| tableContent.contains(value)) {

										passCounter++;

										// get data for Invoice Validation report
										System.out.println(" Passed - " + value);
										invoiceValidationDetails = new ArrayList<String>();
										invoiceValidationDetails.add(String.valueOf(pageNo));
										invoiceValidationDetails.add(String.valueOf(invoiceNo));
										invoiceValidationDetails.add(testPDF);
										invoiceValidationDetails.add(methodName);
										invoiceValidationDetails.add("DB Data");
										invoiceValidationDetails.add(value);
										invoiceValidationDetails.add(tableContent);
										invoiceValidationDetails.add("Pass");
										allInvoiceValidationDetails.add(invoiceValidationDetails);
										invoiceValidationDetails = new ArrayList<String>();
										break;
									}

									if (tableCount == keyTableContentListActual.size()) {
										// Assert.assertTrue(false);
										expectedActualValue.add(" Mismatch: " + v);
										failResult.put(pageNo, expectedActualValue);

										// get the Invoice Validation report
										System.out.println(" Failed - " + value);
										invoiceValidationDetails = new ArrayList<String>();
										invoiceValidationDetails.add(String.valueOf(pageNo));
										invoiceValidationDetails.add(String.valueOf(invoiceNo));
										invoiceValidationDetails.add(testPDF);
										invoiceValidationDetails.add(methodName);
										invoiceValidationDetails.add("DB Data");
										invoiceValidationDetails.add(value);
										invoiceValidationDetails.add("Value mismatch");
										invoiceValidationDetails.add("Fail");
										allInvoiceValidationDetails.add(invoiceValidationDetails);
										invoiceValidationDetails = new ArrayList<String>();

										passFailPages = new ArrayList<String>();
										passFailPages.add(String.valueOf(pageNo));
										allPassFailPages.add(passFailPages);
										break;
									}

								}

							}

						}

						if (vCounter == values.size()) {
							if (passCounter == values.size() - 2) { // 2 values are not tested
								System.out.println("Page " + pageNo + " passed!");
								pageVerification = true;
							} else {
								System.out.println("Page " + pageNo + " failed!");
								pageVerification = true;
							}
						}

					}

					if (pageVerification) {
						break;
					}

				}

			}
		}

		else
			System.out.println("Pages are not equal");
		// Uncomment

		// System.out.println("Page details: " + invoicePageDetails);
		dataVerfication = true;
		testTypePassFailKeyCount = new ArrayList<String>();
		testTypePassFailKeyCount.add(methodName);
		testTypePassFailKeyCount.add(String.valueOf(allFailCounter));
		testTypePassFailKeyCount.add(String.valueOf(allPassCounter));
		testTypePassFailKeyCountList.add(testTypePassFailKeyCount);

		reportData.add(testPDF);
		reportData.add(String.valueOf(noOfPages));
		reportData.add(String.valueOf(noOfFailPages));
		reportData.add(String.valueOf(noOfPassPages));
		allReportData.add(reportData);
		reportData = new ArrayList();

		System.out.println(failResult);

	}

	@Test(priority = 0, dataProvider = "DP1")
	public void checkDataWithKey(String testPDF)
			throws IOException, InterruptedException, ClassNotFoundException, SQLException {

		System.out.println("***********************WithKeyNew****************************");

		// System.out.println("DB");
		// method name
		methodName = "dataWithKey";

		// tstParameters.setEntity("India");
		GetLinesFromPDF pdf = new GetLinesFromPDF();

		// returns hashmap for without key Data
		dbDataWithKey = storeSQLWithData.runQueryWithKey();

		// returns invoice data
		// pdfData1 = new HashMap<>();
		// pdfData = pdf.GetPDFDataList(pathToExcelFile + testPDF);
		// pdfData1 = pdf.GetPDFDataMap(pathToExcelFile + testPDF);

		Set<String> keys = dbDataWithKey.keySet();
		for (String k : keys) {
			// System.out.println("Key: " + k);
		}

		Iterator itr = keys.iterator();
		listKeys = new ArrayList<String>();
		// stores all the keys
		while (itr.hasNext()) {
			listKeys.add((String) itr.next());
		}

		// HashMap
		Map<Integer, List> pdfPassDetails = new HashMap<Integer, List>();
		Map<Integer, List> pdfFailDetails = new HashMap<Integer, List>();

		// Lists
		List<String> passDetails = new ArrayList<String>();

		// allInvoiceValidationDetails = new ArrayList<List>();
		List<String> failDetails = new ArrayList<String>();
		List<String> keyList = new ArrayList<String>();

		// Variable
		keyList = klist.returnListOfKeys("dataVerfication");

		// Counters
		int passCounter = 0; // for counting the no of DB values matched in PDF
		int failCounter = 0; // for counting the no of DB values matched in PDF
		int countColumn = 0;
		int matchedKeyCounter = 0;
		noOfPages = 0;
		allPassCounter = 0;
		allFailCounter = 0;
		noOfFailPages = 0;
		noOfPassPages = 0;
		boolean invoiceFlagForQatar = false;

		for (String kys : listKeys) {

			if (listKeys.size() != pdfData.size()) {
				System.out.println("Page not equal");
				break;
			} else
				System.out.println("Pages are equal");

			if (matchedKeyCounter == listKeys.size()) {
				System.out.println("Breaked");
				break;
			}
			for (int i = 0; i < pdfData.size(); i++) {

				List<String> pdfPage = (List<String>) pdfData.get(i);
				String k = kys;
				boolean invoiceNumFound = false;
				invoiceFlagForQatar = false;

				if (tstParameters.getEntity().equals("Qatar") || tstParameters.getEntity().equals("Bahrain")) {
					for (String lin : pdfPage) {
						if (lin.contains(k)) {
							invoiceFlagForQatar = true;
							break;
						}
					}
				}

				if (pdfPage.contains(k) || invoiceFlagForQatar == true) {

					System.out.println("************* Page " + (i + 1) + " *************");

					invoiceNumFound = true;
					List<String> dataBaseColumns = dbDataWithKey.get(k);

					// Lists
					passDetails = new ArrayList<String>();
					failDetails = new ArrayList<String>();

					// Counters
					passCounter = 0; // for counting the no of DB values matched in PDF
					failCounter = 0; // for counting the no of DB values matched in PDF
					countColumn = 0; // for counting the no of columns

					System.out.println("Page " + (i + 1) + " data: \n" + dataBaseColumns);
					// Rotate over invoice DB values
					int keyPointer = 0;
					for (String dataBaseCol : dataBaseColumns) {

						// Increment column counter for each db value
						countColumn++;

						// Rotate over each line of pdf
						for (int line = 0; line < pdfPage.size(); line++) {

							if (dataBaseCol.equals("NULL") && tstParameters.getEntity().equals("UAE")) {
								passCounter++;
								allPassCounter++;

								// Remove passed values from list
								pdfPage.get(line).replace(pdfPage.get(line).toString(),
										pdfPage.get(line).toString().replaceAll(dataBaseCol, ""));

								// get the Invoice Validation report
								System.out.println("  NULL value");
								invoiceValidationDetails = new ArrayList<String>();
								invoiceValidationDetails.add(String.valueOf(i + 1));
								invoiceValidationDetails.add(String.valueOf(k));
								invoiceValidationDetails.add(testPDF);
								invoiceValidationDetails.add(methodName);
								invoiceValidationDetails.add(keyList.get(keyPointer));
								invoiceValidationDetails.add("Doesn't Exist");
								invoiceValidationDetails.add("Doesn't Exist");
								invoiceValidationDetails.add("Pass");
								allInvoiceValidationDetails.add(invoiceValidationDetails);
								break;
							}
							if (line > 0) {
								if ((pdfPage.get(line).toString() + pdfPage.get(line - 1).toString()).trim()
										.contains(dataBaseCol)
										|| (pdfPage.get(line).toString() + pdfPage.get(line - 1).toString())
												.replaceAll(" ", "").contains(dataBaseCol.replaceAll(" ", ""))
										|| (pdfPage.get(line).toString() + pdfPage.get(line - 1).toString())
												.replaceAll("  ", "").contains(dataBaseCol.replaceAll(" ", ""))) {

									// If passes then increment passCounter and add to list of passed values
									passCounter++;
									allPassCounter++;
									passDetails.add(dataBaseCol);

									// Remove passed values from list
									String val1 = pdfPage.get(line).toString();
									String val2 = pdfPage.get(line - 1).toString();
									String keyValue = null;
									String dbData;
									for (String withKey : keyList) {
										if (val1.contains(withKey) || val2.contains(withKey)
												|| (val1 + val2).contains(withKey) || dataBaseCol.contains(withKey)) {
											keyValue = withKey;
										}
									}
									dbData = dataBaseCol.replaceAll(keyValue, "");

									if (val1.contains(dataBaseCol)) {
										pdfPage.set(line, pdfPage.get(line).replace(dataBaseCol, ""));
									} else {
										pdfPage.set(line, pdfPage.get(line).replace(keyValue, ""));
										pdfPage.set(line - 1, pdfPage.get(line - 1).replace(dbData, ""));
									}

									// get the Invoice Validation report
									System.out.println("  Passed - " + dataBaseCol);
									invoiceValidationDetails = new ArrayList<String>();
									invoiceValidationDetails.add(String.valueOf(i + 1));
									invoiceValidationDetails.add(String.valueOf(k));
									invoiceValidationDetails.add(testPDF);
									invoiceValidationDetails.add(methodName);
									invoiceValidationDetails.add(keyList.get(keyPointer));
									invoiceValidationDetails.add(dataBaseCol);
									invoiceValidationDetails.add(dataBaseCol);
									invoiceValidationDetails.add("Pass");
									allInvoiceValidationDetails.add(invoiceValidationDetails);
									break;
								}
							}

							if (pdfPage.get(line).toString().contains(dataBaseCol)
									|| pdfPage.get(line).toString().replaceAll(" ", "")
											.contains(dataBaseCol.replaceAll(" ", ""))
									|| pdfPage.get(line).toString().replaceAll("  ", "")
											.contains(dataBaseCol.replaceAll(" ", ""))) { //

								// If passes then increment passCounter and add to list of passed values
								passCounter++;
								allPassCounter++;
								passDetails.add(dataBaseCol);

								// Remove passed values from list
								String val1 = pdfPage.get(line).toString();
								String val2 = pdfPage.get(line + 1).toString();
								String keyValue = null;
								String dbData;
								for (String withKey : keyList) {
									if (val1.contains(withKey) || val2.contains(withKey)
											|| (val1 + val2).contains(withKey) || dataBaseCol.contains(withKey)) {
										keyValue = withKey;
									}
								}
								dbData = dataBaseCol.replaceAll(keyValue, "");
								if (val1.contains(dataBaseCol)) {
									pdfPage.set(line, pdfPage.get(line).replace(dataBaseCol, ""));
								} else {
									pdfPage.set(line, pdfPage.get(line).replace(keyValue, ""));
									pdfPage.set(line + 1, pdfPage.get(line + 1).replace(dbData, ""));
								}

								// get the Invoice Validation report
								System.out.println("  Passed - " + dataBaseCol);
								invoiceValidationDetails = new ArrayList<String>();
								invoiceValidationDetails.add(String.valueOf(i + 1));
								invoiceValidationDetails.add(String.valueOf(k));
								invoiceValidationDetails.add(testPDF);
								invoiceValidationDetails.add(methodName);
								invoiceValidationDetails.add(keyList.get(keyPointer));
								invoiceValidationDetails.add(dataBaseCol);
								invoiceValidationDetails.add(dataBaseCol);
								invoiceValidationDetails.add("Pass");
								allInvoiceValidationDetails.add(invoiceValidationDetails);

								break;
							}

							else if (line < pdfPage.size() - 3) {
								if ((pdfPage.get(line).toString() + pdfPage.get(line + 1).toString()).trim()
										.contains(dataBaseCol)
										|| (pdfPage.get(line).toString() + pdfPage.get(line + 1).toString())
												.replaceAll(" ", "").contains(dataBaseCol.replaceAll(" ", ""))
										|| (pdfPage.get(line).toString() + pdfPage.get(line + 1).toString())
												.replaceAll("  ", "").contains(dataBaseCol.replaceAll(" ", ""))) {

									// If passes then increment passCounter and add to list of passed values
									passCounter++;
									allPassCounter++;
									passDetails.add(dataBaseCol);

									// Remove passed values from list
									String val1 = pdfPage.get(line).toString();
									String val2 = pdfPage.get(line + 1).toString();
									String keyValue = null;
									String dbData;
									for (String withKey : keyList) {
										if (val1.contains(withKey) || val2.contains(withKey)
												|| (val1 + val2).contains(withKey) || dataBaseCol.contains(withKey)) {
											keyValue = withKey;
										}
									}
									dbData = dataBaseCol.replaceAll(keyValue, "");

									if (val1.contains(dataBaseCol)) {
										pdfPage.set(line, pdfPage.get(line).replace(dataBaseCol, ""));
									} else {
										pdfPage.set(line, pdfPage.get(line).replace(keyValue, ""));
										pdfPage.set(line + 1, pdfPage.get(line + 1).replace(dbData, ""));
									}

									// get the Invoice Validation report
									System.out.println("  Passed - " + dataBaseCol);
									invoiceValidationDetails = new ArrayList<String>();
									invoiceValidationDetails.add(String.valueOf(i + 1));
									invoiceValidationDetails.add(String.valueOf(k));
									invoiceValidationDetails.add(testPDF);
									invoiceValidationDetails.add(methodName);
									invoiceValidationDetails.add(keyList.get(keyPointer));
									invoiceValidationDetails.add(dataBaseCol);
									invoiceValidationDetails.add(dataBaseCol);
									invoiceValidationDetails.add("Pass");
									allInvoiceValidationDetails.add(invoiceValidationDetails);
									break;
								}

								if ((pdfPage.get(line + 1).toString() + pdfPage.get(line + 2).toString()).trim()
										.contains(dataBaseCol)

										|| (pdfPage.get(line + 1).toString() + pdfPage.get(line + 2).toString())
												.replaceAll(" ", "").contains(dataBaseCol.replaceAll(" ", ""))
										|| (pdfPage.get(line + 1).toString() + pdfPage.get(line + 2).toString())
												.replaceAll("  ", "").contains(dataBaseCol.replaceAll(" ", ""))) {
									// If passes then increment passCounter and add to list of passed values
									passCounter++;
									allPassCounter++;
									passDetails.add(dataBaseCol);

									// Remove passed values from list
									String val1 = pdfPage.get(line + 1).toString();
									String val2 = pdfPage.get(line + 2).toString();
									String keyValue = null;
									String dbData;
									for (String withKey : keyList) {
										if (val1.contains(withKey) || val2.contains(withKey)
												|| (val1 + val2).contains(withKey) || dataBaseCol.contains(withKey)) {
											keyValue = withKey;
										}
									}
									dbData = dataBaseCol.replaceAll(keyValue, "");

									if (val1.contains(dataBaseCol)) {
										pdfPage.set(line, pdfPage.get(line + 1).replace(dataBaseCol, ""));
									} else {
										pdfPage.set(line + 1, pdfPage.get(line + 1).replace(keyValue, ""));
										pdfPage.set(line + 2, pdfPage.get(line + 2).replace(dbData, ""));
									}

									// get the Invoice Validation report
									System.out.println("  Passed - " + dataBaseCol);
									invoiceValidationDetails = new ArrayList<String>();
									invoiceValidationDetails.add(String.valueOf(i + 1));
									invoiceValidationDetails.add(String.valueOf(k));
									invoiceValidationDetails.add(testPDF);
									invoiceValidationDetails.add(methodName);
									invoiceValidationDetails.add(keyList.get(keyPointer));
									invoiceValidationDetails.add(dataBaseCol);
									invoiceValidationDetails.add(dataBaseCol);
									invoiceValidationDetails.add("Pass");
									allInvoiceValidationDetails.add(invoiceValidationDetails);
									break;
								}

								// Check if last line has reached, since last reached column is not matched

							}

							if (line == (pdfPage.size() - 1)) {
								// If fails then increment failCounter and add to list of failed values
								failCounter++;
								allFailCounter++;
								failDetails.add(dataBaseCol);

								// get the Invoice Validation report
								System.out.println("  Failed - " + dataBaseCol);
								invoiceValidationDetails = new ArrayList<String>();
								invoiceValidationDetails.add(String.valueOf(i + 1));
								invoiceValidationDetails.add(String.valueOf(k));
								invoiceValidationDetails.add(testPDF);
								invoiceValidationDetails.add(methodName);
								invoiceValidationDetails.add(keyList.get(keyPointer));
								invoiceValidationDetails.add(dataBaseCol);
								invoiceValidationDetails.add("Value Mismatch");
								invoiceValidationDetails.add("Fail");
								allInvoiceValidationDetails.add(invoiceValidationDetails);

								passFailPages = new ArrayList<String>();
								passFailPages.add(String.valueOf(i + 1));
								allPassFailPages.add(passFailPages);
								break;

							}

						}
						keyPointer++;

					}

				}
				/*
				 * if(i+1 == 4) { System.out.println(); }
				 */
				// Calculate results pagewise
				pdfPassDetails.put(i + 1, passDetails);
				pdfFailDetails.put(i + 1, failDetails);

				// Pagewise console sysouts
				if (passCounter == countColumn && invoiceNumFound == true) {
					System.out.println("Page " + (i + 1) + " passed!");
					matchedKeyCounter++;
					noOfPassPages++;
					noOfPages++;
					break;
				} else if (passCounter != countColumn && invoiceNumFound == true) {

					System.out.println("Page " + (i + 1) + " failed!");
					noOfFailPages++;
					noOfPages++;
					break;
				}

				// noOfpages++;

			}

			// Passed and Failed Values sysouts

		}
		// System.out.println("Passed values:\n" + pdfPassDetails);
		// System.out.println("Failed values:\n" + pdfFailDetails);

		testTypePassFailKeyCount = new ArrayList<String>();
		testTypePassFailKeyCount.add(methodName);
		testTypePassFailKeyCount.add(String.valueOf(allFailCounter));
		testTypePassFailKeyCount.add(String.valueOf(allPassCounter));
		testTypePassFailKeyCountList.add(testTypePassFailKeyCount);
		// System.out.println(testTypePassFailKeyCountList);
		// System.out.println("PASS FAIL" + allInvoiceValidationDetails);

		// System.out.println("Total no of pages" + noOfPages);
		// System.out.println("Total no of Pass pages" + noOfPassPages);
		// System.out.println("Total no of Fail pages" + noOfFailPages);
		/*
		 * reportData.add(testPDF); reportData.add(String.valueOf(noOfPages));
		 * reportData.add(String.valueOf(noOfFailPages));
		 * reportData.add(String.valueOf(noOfPassPages)); allReportData.add(reportData);
		 * reportData = new ArrayList();
		 */
		/*
		 * passFailPages = new ArrayList<String>();
		 * passFailPages.add(String.valueOf(noOfFailPages));
		 * passFailPages.add(String.valueOf(noOfPassPages));
		 * allPassFailPages.add(passFailPages);
		 */
		System.out.println(failDetails);

	}

	@Test(priority = 1, dataProvider = "DP1")
	public void formatVerifcationImage(String testPDF) throws InvalidPasswordException, IOException {

		System.out.println("*************Image****************");

		// method name
		methodName = "formatImage";

		// Check whether data verification is passed
		if (dataVerfication == false) {
			Assert.fail("Data Verfication failed");
		}

		// Class references
		ImageChecker imgCheck = new ImageChecker();

		// Variable
		List<String> refImageDetails = new ArrayList<String>();
		List<String> testImageDetails = new ArrayList<String>();

		// Step 1. - Compare ref image count for with each page of test PDF
		int pdfPageCount = imgCheck.noOfPages(pathToExcelFile + testPDF);
		int pageCounter = 0;
		allPassCounter = 0;
		allFailCounter = 0;
		noOfFailPages = 0;
		noOfPassPages = 0;
		while (pageCounter < pdfPageCount) {
			// Increment counter for Page 1
			pageCounter++;

			String invoiceNumber = (String) invoicePageDetails.get(pageCounter).get(2);

			// Invoice or CN
			if (invoicePageDetails.get(pageCounter).get(0).equals("1")) {
				if (invoicePageDetails.get(pageCounter).get(1).equals("0")) {
					// O invoice
					refImageDetails = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceOInvoice();
					refImageDetails.addAll(imgCheck.getImageDetails(pathToExcelFile + refPDF, 1));
				} else {
					// Manual Invoice
					refImageDetails = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceManualOInvoice();
					refImageDetails.addAll(imgCheck.getImageDetails(pathToExcelFile + refPDF, 1));
				}

			} else {
				if (invoicePageDetails.get(pageCounter).get(1).equals("0")) {
					// O CN
					refImageDetails = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceOCN();
					refImageDetails.addAll(imgCheck.getImageDetails(pathToExcelFile + refPDF, 1));
				} else {
					// Manual CN
					refImageDetails = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceManualOCN();
					refImageDetails.addAll(imgCheck.getImageDetails(pathToExcelFile + refPDF, 1));
				}
			}

			// Step 1. - Get image count for reference PDF
			int refPDFImageCount = imgCheck.noOfImages(pathToExcelFile + refPDF, 1);

			// Store no of images of test PDF
			int testPDFImageCount = imgCheck.noOfImages(pathToExcelFile + testPDF, pageCounter);

			/*
			 * allPassCounter = 0; allFailCounter = 0;
			 */

			if (refPDFImageCount == 0 && testPDFImageCount == 0) {
				System.out.println("Image is not present in page " + pageCounter);
				allPassCounter++;

				// get the Invoice Validation report
				invoiceValidationDetails = new ArrayList<String>();
				invoiceValidationDetails.add(String.valueOf(pageCounter));
				invoiceValidationDetails.add(invoiceNumber);
				invoiceValidationDetails.add(testPDF);
				invoiceValidationDetails.add(methodName);
				invoiceValidationDetails.add("Image");
				invoiceValidationDetails.add(String.valueOf("No images"));
				invoiceValidationDetails.add(String.valueOf("No images"));
				invoiceValidationDetails.add("Pass");
				allInvoiceValidationDetails.add(invoiceValidationDetails);

			} else if (refPDFImageCount == testPDFImageCount) {
				System.out.println("Image Count passed for Page " + pageCounter);
				refImageDetails = new ArrayList<String>();
				testImageDetails = new ArrayList<String>();

				// Get image details for reference PDF
				refImageDetails.addAll(imgCheck.getImageDetails(pathToExcelFile + refPDF, 1));

				// Get image details for test PDF
				testImageDetails.addAll(imgCheck.getImageDetails(pathToExcelFile + testPDF, pageCounter));

				// Start matching
				int testImageDetailsLength = testImageDetails.size();

				// Counters
				int counterForTest = 0;
				// int counterForRef = 0;
				int passCounter = 0;
				int failCounter = 0;

				for (String imgTestDetail : testImageDetails) {
					counterForTest++;
					int counterForRef = 0;
					for (String imgRefDetail : refImageDetails) {
						counterForRef++;
						if (imgTestDetail.equals(imgRefDetail)) {
							passCounter++;
							allPassCounter++;

							// get the Invoice Validation report
							invoiceValidationDetails = new ArrayList<String>();
							invoiceValidationDetails.add(String.valueOf(pageCounter));
							invoiceValidationDetails.add(invoiceNumber);
							invoiceValidationDetails.add(testPDF);
							invoiceValidationDetails.add(methodName);
							invoiceValidationDetails.add("Image");
							invoiceValidationDetails.add(imgRefDetail);
							invoiceValidationDetails.add(imgTestDetail);
							invoiceValidationDetails.add("Pass");
							allInvoiceValidationDetails.add(invoiceValidationDetails);
							break;
						}

						if (counterForRef == refImageDetails.size()) {
							failCounter++;
							allFailCounter++;

							// get the Invoice Validation report
							invoiceValidationDetails = new ArrayList<String>();
							invoiceValidationDetails.add(String.valueOf(pageCounter));
							invoiceValidationDetails.add(invoiceNumber);
							invoiceValidationDetails.add(testPDF);
							invoiceValidationDetails.add(methodName);
							invoiceValidationDetails.add("Image");
							invoiceValidationDetails.add(imgRefDetail);
							invoiceValidationDetails.add(imgTestDetail);
							invoiceValidationDetails.add("Fail");
							allInvoiceValidationDetails.add(invoiceValidationDetails);

							passFailPages = new ArrayList<String>();
							passFailPages.add(String.valueOf(pageCounter));
							allPassFailPages.add(passFailPages);
							break;
						}
					}

				}

				if (counterForTest == testImageDetailsLength) {
					if (passCounter == testImageDetailsLength) {
						System.out.println("Image details verified for Page " + pageCounter);
						noOfPassPages++;

					} else {
						System.out.println("Image details not verified for Page " + pageCounter);
						noOfFailPages++;
					}
				}

			} else {
				System.out.println("Image Count failed for Page " + pageCounter);
				allFailCounter++;

				// get the Invoice Validation report
				invoiceValidationDetails = new ArrayList<String>();
				invoiceValidationDetails.add(String.valueOf(pageCounter));
				invoiceValidationDetails.add(invoiceNumber);
				invoiceValidationDetails.add(testPDF);
				invoiceValidationDetails.add(methodName);
				invoiceValidationDetails.add("Image");
				invoiceValidationDetails.add(String.valueOf("Number of images : " + refPDFImageCount));
				invoiceValidationDetails.add(String.valueOf("Number of images : " + testPDFImageCount));
				invoiceValidationDetails.add("Fail");
				allInvoiceValidationDetails.add(invoiceValidationDetails);

			}
			/*
			 * reportData.add(testPDF); reportData.add(String.valueOf(noOfPages));
			 * reportData.add(String.valueOf(noOfFailPages));
			 * reportData.add(String.valueOf(noOfPassPages)); allReportData.add(reportData);
			 * reportData = new ArrayList();
			 */

		}
		testTypePassFailKeyCount = new ArrayList<String>();
		testTypePassFailKeyCount.add(methodName);
		testTypePassFailKeyCount.add(String.valueOf(allFailCounter));
		testTypePassFailKeyCount.add(String.valueOf(allPassCounter));
		testTypePassFailKeyCountList.add(testTypePassFailKeyCount);

		/*
		 * passFailPages = new ArrayList<String>();
		 * passFailPages.add(String.valueOf(noOfFailPages));
		 * passFailPages.add(String.valueOf(noOfPassPages));
		 * allPassFailPages.add(passFailPages);
		 */

	}

	@Test(priority = 3, dataProvider = "DP1")
	public void formatVerificationLocation(String testPDF) throws IOException {

		System.out.println("*************Location****************");

		// method name
		methodName = "formatLocation";

		// Check whether data verification is passed
		if (dataVerfication == false) {
			Assert.fail("Data Verfication failed");
		}

		// Variable
		List<String> keyList = new ArrayList<String>();
		List<String> keyLocationListRefInvoice = new ArrayList<String>();
		List<String> keyLocationListRefCredit = new ArrayList<String>();
		List<String> keyLocationListRef = new ArrayList<String>();
		List<String> keyLocationListActual = new ArrayList<String>();
		List<String> keyLocationListRef1 = new ArrayList<String>();
		List<String> keyLocationListActual1 = new ArrayList<String>();

		// HashMaps
		Map<String, List> keyLocationMapActual = new HashMap<String, List>();
		Map<String, List> keyLocationMapRef = new HashMap<String, List>();

		// Step 1 - Get key list
		keyList = klist.returnListOfKeys("locationVerfication");

		// Step 2 - Match with existing data
		int noOfPages = pdfLoc.returnNumOfPages(pathToExcelFile + testPDF);
		int cnt = 0;
		allFailCounter = 0;
		allPassCounter = 0;
		while (cnt != noOfPages) {
			// Page counter
			cnt++;
			int refPageNo = 1;

			String invoiceNumber = (String) invoicePageDetails.get(cnt).get(2);

			// For UAE all invoices are shifted up from page 2
			if (cnt >= 2 && tstParameters.getEntity().equals("UAE")) {
				refPageNo = 2;
			}
			// Invoice or CN
			if (invoicePageDetails.get(cnt).get(0).equals("1")) {
				if (invoicePageDetails.get(cnt).get(1).equals("0")) {
					// O invoice
					keyLocationListRef = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceOInvoice();
					// keyLocationListRef.addAll(pdfLoc.returnLoc(pathToExcelFile + refPDF,
					// refPageNo, keyList));
					keyLocationMapRef = pdfLoc.returnLocMap(pathToExcelFile + refPDF, refPageNo, keyList);
				} else {
					// Manual Invoice
					keyLocationListRef = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceManualOInvoice();
					// keyLocationListRef.addAll(pdfLoc.returnLoc(pathToExcelFile + refPDF,
					// refPageNo, keyList));
					keyLocationMapRef = pdfLoc.returnLocMap(pathToExcelFile + refPDF, refPageNo, keyList);
				}

			} else {
				if (invoicePageDetails.get(cnt).get(1).equals("0")) {
					// O CN
					keyLocationListRef = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceOCN();
					// keyLocationListRef.addAll(pdfLoc.returnLoc(pathToExcelFile + refPDF,
					// refPageNo, keyList));
					keyLocationMapRef = pdfLoc.returnLocMap(pathToExcelFile + refPDF, refPageNo, keyList);
				} else {
					// Manual CN
					keyLocationListRef = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceManualOCN();
					// keyLocationListRef.addAll(pdfLoc.returnLoc(pathToExcelFile + refPDF,
					// refPageNo, keyList));
					keyLocationMapRef = pdfLoc.returnLocMap(pathToExcelFile + refPDF, refPageNo, keyList);
				}
			}

			// Clear list of test invoice for new pages
			keyLocationListActual = new ArrayList<String>();

			// Get test pdf keylist page wise
			keyLocationListActual.addAll(pdfLoc.returnLoc(pathToExcelFile + testPDF, cnt, keyList));
			keyLocationMapActual = pdfLoc.returnLocMap(pathToExcelFile + testPDF, cnt, keyList);

			// Step 3 - Match reference and Actual

			// Flags & counters
			int count = 0;
			int match = 0;
			int passCounter = 0;

			// --------------------------------
			for (String key : keyLocationMapActual.keySet()) {

				// Lists
				List<String> valueMismatch = new ArrayList<String>();

				// Get Location list for reference PDF
				try {
					keyLocationListRef1 = new ArrayList<String>();
					keyLocationListRef1.addAll(keyLocationMapRef.get(key));

					// Get Location list for test PDF
					keyLocationListActual1 = new ArrayList<String>();
					keyLocationListActual1.addAll(keyLocationMapActual.get(key));
				} catch (Exception e) {
					System.out.println(e);
					System.out.println(key + "****************");
				}

				int refListLen = keyLocationListRef1.size();
				int actualListLen = keyLocationListActual1.size();
				int countForActual = 0;

				for (String strA : keyLocationListActual1) {
					countForActual++;
					count = 0;// Counting test ref values

					for (String strR : keyLocationListRef1) {
						count++;
						if (strA.equals(strR)) {
							// match = 1; // pass
							passCounter++;
							break;
						} else if (count == refListLen) {
							match = 2;
							System.out.println("Mismatch: " + strA);
							// Get possible mismatching values
							CheckMismatch chMis = new CheckMismatch();
							String expectedStr = chMis.retMismatch(strA, keyLocationListRef1);
							allFailCounter++;

							// Note all failed values
							valueMismatch.add(expectedStr + "~" + strA);

							passFailPages = new ArrayList<String>();
							passFailPages.add(String.valueOf(cnt));
							allPassFailPages.add(passFailPages);

							break;
						}

						if (passCounter == keyLocationListRef1.size()) {
							match = 1;
						}
					}
					if (match == 2) {
						break;
					}
				}
				if (match == 1 && valueMismatch.isEmpty()) {
					// get the Invoice Validation report
					invoiceValidationDetails = new ArrayList<String>();
					allPassCounter++;
					invoiceValidationDetails.add(String.valueOf(cnt));
					invoiceValidationDetails.add(invoiceNumber);
					invoiceValidationDetails.add(testPDF);
					invoiceValidationDetails.add(methodName);
					invoiceValidationDetails.add("Location");
					invoiceValidationDetails.add(key);
					invoiceValidationDetails.add(key);
					invoiceValidationDetails.add("Pass");
					allInvoiceValidationDetails.add(invoiceValidationDetails);// Uncomment

				}
				if (!valueMismatch.isEmpty()) {

					for (String mismatchVal : valueMismatch) {

						String[] val = mismatchVal.split("~");

						// get the Invoice Validation report
						invoiceValidationDetails = new ArrayList<String>();
						invoiceValidationDetails.add(String.valueOf(cnt));
						invoiceValidationDetails.add(invoiceNumber);
						invoiceValidationDetails.add(testPDF);
						invoiceValidationDetails.add(methodName);
						invoiceValidationDetails.add("Location");
						invoiceValidationDetails.add("Key -" + key + "-" + val[0]);
						invoiceValidationDetails.add("Key -" + key + "-" + val[1]);
						invoiceValidationDetails.add("Fail");
						allInvoiceValidationDetails.add(invoiceValidationDetails); // Uncomment
					}

				}

			}

			// --------------------------------

			if (match == 1) {
				System.out.println("Page " + cnt + " matched!");
				noOfPassPages++;
				locationVerfication = true;
			} else if (match == 2) {
				locationVerfication = false;
				noOfFailPages++;
				System.out.println("Page " + cnt + " not matched!");
			} else {
				locationVerfication = false;
				System.out.println("Page " + cnt + " Skipped");
			}

		}
		testTypePassFailKeyCount = new ArrayList<String>();
		testTypePassFailKeyCount.add(methodName);
		testTypePassFailKeyCount.add(String.valueOf(allFailCounter));
		testTypePassFailKeyCount.add(String.valueOf(allPassCounter));
		testTypePassFailKeyCountList.add(testTypePassFailKeyCount);

		/*
		 * passFailPages = new ArrayList<String>();
		 * passFailPages.add(String.valueOf(noOfFailPages));
		 * passFailPages.add(String.valueOf(noOfPassPages));
		 * allPassFailPages.add(passFailPages);
		 */
	}

	@Test(priority = 4, dataProvider = "DP1")
	public void formatVerificationTable(String testPDF) throws IOException {

		System.out.println("*************Table****************");

		// method name
		methodName = "formatTable";

		// Check whether data verification is passed
		if (dataVerfication == false) {
			Assert.fail("Data Verfication failed");
		}

		// Class references
		TableChecker tbCheck = new TableChecker();

		// Variable
		List<String> keyList = new ArrayList<String>();
		List<String> keyTableContentListRef = new ArrayList<String>();
		List<String> keyTableContentListActual = new ArrayList<String>();

		// Set reference and test pdfs
		// String refPDF = "./test/resources/data/QatarQC.pdf";
		// String testPDF = "./test/resources/data/Qatar_OInvoice.pdf";
		// TestParameters.setEntity("Qatar");
		// TestParameters.setEntity("");

		// Step 1 - Get key list
		keyList = klist.returnListOfKeys("tabularVerfication");

		// Counter
		int pageCount = tbCheck.returnNumOfPages(pathToExcelFile + testPDF);
		int cnt = 0;
		allPassCounter = 0;
		allFailCounter = 0;

		// Loop through each page of test PDF
		while (cnt != pageCount) {
			// Page counter
			cnt++;

			String invoiceNumber = (String) invoicePageDetails.get(cnt).get(2);

			// Step 2. - Set reference PDF values
			if (invoicePageDetails.get(cnt).get(0).equals("1")) {
				if (invoicePageDetails.get(cnt).get(1).equals("0")) {
					// O invoice
					keyTableContentListRef = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceOInvoice();
					keyTableContentListRef.addAll(tbCheck.returnTableContent(pathToExcelFile + refPDF, keyList, 1));
				} else {
					// Manual Invoice
					keyTableContentListRef = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceManualOInvoice();
					keyTableContentListRef.addAll(tbCheck.returnTableContent(pathToExcelFile + refPDF, keyList, 1));
				}

			} else {
				if (invoicePageDetails.get(cnt).get(1).equals("0")) {
					// O CN
					keyTableContentListRef = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceOCN();
					keyTableContentListRef.addAll(tbCheck.returnTableContent(pathToExcelFile + refPDF, keyList, 1));
				} else {
					// Manual CN
					keyTableContentListRef = new ArrayList<>();
					refPDF = tstParameters.getOBillingReferenceManualOCN();
					keyTableContentListRef.addAll(tbCheck.returnTableContent(pathToExcelFile + refPDF, keyList, 1));
				}
			}

			// Step 3 - Check for count of Table
			if (tbCheck.returnNoOfTables(pathToExcelFile + refPDF, 1) == 0
					&& tbCheck.returnNoOfTables(pathToExcelFile + testPDF, cnt) == 0) {
				System.out.println("Table is not present in Page " + cnt);
				allPassCounter++;

				// get the Invoice Validation report
				invoiceValidationDetails = new ArrayList<String>();
				invoiceValidationDetails.add(String.valueOf(cnt));
				invoiceValidationDetails.add(invoiceNumber);
				invoiceValidationDetails.add(testPDF);
				invoiceValidationDetails.add(methodName);
				invoiceValidationDetails.add("Table");
				invoiceValidationDetails.add(String.valueOf("No tables"));
				invoiceValidationDetails.add(String.valueOf("No tables"));
				invoiceValidationDetails.add("Pass");
				allInvoiceValidationDetails.add(invoiceValidationDetails);

			} else if (tbCheck.returnNoOfTables(pathToExcelFile + refPDF, 1) == tbCheck
					.returnNoOfTables(pathToExcelFile + testPDF, cnt)) {

				System.out.println("Table count matched for Page " + cnt);

				// Step 4 - Form list of Table content for Test PDF page wise
				keyTableContentListActual = new ArrayList<String>();
				keyTableContentListActual.addAll(tbCheck.returnTableContent(pathToExcelFile + testPDF, keyList, cnt));

				// compare both list
				int refListLen = keyTableContentListRef.size();
				int actualListLen = keyTableContentListActual.size();

				// Flags & counters
				int count = 0;
				int match = 0;
				int passCounter = 0;

				if (refListLen == actualListLen) {

					// Matching Loop
					for (String strA : keyTableContentListActual) {
						count = 0;
						for (String strR : keyTableContentListRef) {
							count++;
							if (strA.equals(strR)) {
								match = 1; // pass
								passCounter++;
								allPassCounter++;

								// get the Invoice Validation report
								invoiceValidationDetails = new ArrayList<String>();
								invoiceValidationDetails.add(String.valueOf(cnt));
								invoiceValidationDetails.add(invoiceNumber);
								invoiceValidationDetails.add(testPDF);
								invoiceValidationDetails.add(methodName);
								invoiceValidationDetails.add("Table");
								invoiceValidationDetails.add(strR);
								invoiceValidationDetails.add(strA);
								invoiceValidationDetails.add("Pass");
								allInvoiceValidationDetails.add(invoiceValidationDetails);
								break;
							} else if (count == actualListLen) {
								match = 2; // false
								allFailCounter++;

								// get the Invoice Validation report
								invoiceValidationDetails = new ArrayList<String>();
								invoiceValidationDetails.add(String.valueOf(cnt));
								invoiceValidationDetails.add(invoiceNumber);
								invoiceValidationDetails.add(testPDF);
								invoiceValidationDetails.add(methodName);
								invoiceValidationDetails.add("Table");
								invoiceValidationDetails.add(strR);
								invoiceValidationDetails.add("Mismatch");
								invoiceValidationDetails.add("Fail");
								allInvoiceValidationDetails.add(invoiceValidationDetails);

								passFailPages = new ArrayList<String>();
								passFailPages.add(String.valueOf(cnt));
								allPassFailPages.add(passFailPages);
								break;
							}
						}

					}
				} else
					match = 2;

				if (passCounter != actualListLen || actualListLen == 0) {
					match = 2;
				}

				if (match == 1) {
					System.out.println("Table content matched! For page " + cnt);
					noOfPassPages++;
					tableVerfication = true;
				} else if (match == 2) {
					System.out.println("Table content not matched.. for page " + cnt);
					noOfFailPages++;
					tableVerfication = false;
				} else {
					System.out.println("Page Skipped");
					tableVerfication = false;
				}

			} else {
				System.out.println("Table Count not matched for Page " + cnt);
				allFailCounter++;

				// get the Invoice Validation report
				invoiceValidationDetails = new ArrayList<String>();
				invoiceValidationDetails.add(String.valueOf(cnt));
				invoiceValidationDetails.add(testPDF);
				invoiceValidationDetails.add(methodName);
				invoiceValidationDetails.add("Table");
				invoiceValidationDetails.add("No of Tables: " + tbCheck.returnNoOfTables(pathToExcelFile + refPDF, 1));
				invoiceValidationDetails.add("No of Tables: " + tbCheck.returnNoOfTables(pathToExcelFile + testPDF, 1));
				invoiceValidationDetails.add("Fail");
				allInvoiceValidationDetails.add(invoiceValidationDetails);
			}

			tableVerfication = false;
		}
		testTypePassFailKeyCount = new ArrayList<String>();
		testTypePassFailKeyCount.add(methodName);
		testTypePassFailKeyCount.add(String.valueOf(allFailCounter));
		testTypePassFailKeyCount.add(String.valueOf(allPassCounter));
		testTypePassFailKeyCountList.add(testTypePassFailKeyCount);
		/*
		 * passFailPages = new ArrayList<String>();
		 * passFailPages.add(String.valueOf(noOfFailPages));
		 * passFailPages.add(String.valueOf(noOfPassPages));
		 * allPassFailPages.add(passFailPages);
		 */

	}

	@BeforeClass
	public void setUp() throws IOException {

		pdfLoc = new PDFLocation();
	}

	@BeforeClass
	public void startTime() throws IOException {

		// ... the code being measured starts ...

		startTime = Instant.now().toEpochMilli();

	}

	@AfterClass
	public void endTime() throws IOException {

		// ... the code being measured ends ...

		endTime = Instant.now().toEpochMilli();

		// get difference of two time values
		timeElapsed = endTime - startTime;
		System.out.println("*************ExecutionTime****************");
		System.out.println("Execution time in milliseconds: " + timeElapsed);
		// Class reference
		TestParameters tstPar = new TestParameters();

		String pathToExcel = "./test/resources/data/";
		String resultSheet = "SmokeTestResultSheet.xlsx";

		// System.out.println(allPassFailPages);
		List<String> page = new ArrayList<String>();

		for (List<String> lsStr : allPassFailPages) {
			String temp = lsStr.get(0);
			if (!page.contains(temp)) {
				page.add(temp);
			}
		}

		// System.out.println(page);

		// Test Pdf list
		List<List> testallPDFPassFailDetails = new ArrayList<List>();
		List<String> testPDFPassFailDetails = new ArrayList<String>();
		List<String> keyTestTypeDetails = new ArrayList<String>();
		List<List> allKeyTestTypeDetailsMap = new ArrayList<List>();
		List<List> consolidated = new ArrayList<List>();
		List<String> consolidated_details = new ArrayList<String>();

		// PDF 1
		testallPDFPassFailDetails.addAll(allReportData);
		testPDFPassFailDetails = new ArrayList<String>();

		for (List<String> testPDFData : testallPDFPassFailDetails) {
			String allPass = String.valueOf((Integer.parseInt(testPDFData.get(1))) - page.size());
			testPDFData.set(2, String.valueOf(page.size()));
			testPDFData.set(3, allPass);
		}

		// Hashmap
		Map<String, List> testPageTestTypeDetailsMap = new HashMap<String, List>();

		// Consolidated

		// Test type list

		allKeyTestTypeDetailsMap.add(testTypePassFailKeyCountList);

		ExcelCreator ec = new ExcelCreator(testallPDFPassFailDetails, testTypePassFailKeyCountList, allReportData,
				allInvoiceValidationDetails, String.valueOf(timeElapsed) + "ms"); // Uncomment

		ec.createOBillingSheet(); // Uncomment //
		ec.createInvoiceValidationSheet();// Uncomment //
		ec.createOBillingDataSheet();// Uncomment

	}

}
