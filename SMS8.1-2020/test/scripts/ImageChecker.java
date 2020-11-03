package scripts;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.PDFStreamEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.pdfbox.contentstream.operator.state.Concatenate;
import org.apache.pdfbox.contentstream.operator.state.Restore;
import org.apache.pdfbox.contentstream.operator.state.Save;
import org.apache.pdfbox.contentstream.operator.state.SetGraphicsStateParameters;
import org.apache.pdfbox.contentstream.operator.state.SetMatrix;

/**
 * This is an example on how to get the x/y coordinates of image location and
 * size of image.
 */
public class ImageChecker extends PDFStreamEngine {

	static int imageCount;
	static List<String> imageDetails = new ArrayList<String>();// = null;

	/**
	 * @throws IOException If there is an error loading text stripper properties.
	 */
	public ImageChecker() throws IOException {
		// preparing PDFStreamEngine
		addOperator(new Concatenate());
		addOperator(new DrawObject());
		addOperator(new SetGraphicsStateParameters());
		addOperator(new Save());
		addOperator(new Restore());
		addOperator(new SetMatrix());
	}

	/**
	 * @throws IOException If there is an error parsing the document.
	 */
	public HashMap<Integer, List<String>> getImageDetails() throws IOException {
		PDDocument document = null;
		// String fileName =
		// "D:\\OTIS_ApplicationStuff\\SMS\\Invoices\\QATAR_OInvoice.pdf";
		String fileName = "./test/resources/data/QatarQC.pdf";
		try {
			document = PDDocument.load(new File(fileName));
			ImageChecker printer = new ImageChecker();
			int pageNum = 0;
			for (PDPage page : document.getPages()) {
				pageNum++;
				System.out.println("\n\nProcessing page: " + pageNum + "\n---------------------------------");
				printer.processPage(page);
			}
		} finally {
			if (document != null) {
				document.close();
			}
		}
		return null;
	}

	public int noOfImages(String fileName, int pageNum) throws InvalidPasswordException, IOException {
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(fileName));
			ImageChecker printer = new ImageChecker();
			imageCount = 0;
			printer.processPage(document.getPage(pageNum - 1));
		} finally {
			if (document != null) {
				document.close();
			}
		}
		
		return imageCount;
	}

	public int noOfPages(String fileName) throws InvalidPasswordException, IOException {
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(fileName));
			ImageChecker printer = new ImageChecker();
			return document.getPages().getCount();

		} finally {
			if (document != null) {
				document.close();
			}
		}
	}
	
	public List<String> getImageDetails(String fileName,int pageNum) throws InvalidPasswordException, IOException {
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(fileName));
			ImageChecker printer = new ImageChecker();
			imageCount = 0;
			printer.processPage(document.getPage(pageNum - 1));
			
		} finally {
			if (document != null) {
				document.close();
			}
		}
		return imageDetails;
	}

	/**
	 * @param operator The operation to perform.
	 * @param operands The list of arguments.
	 *
	 * @throws IOException If there is an error processing the operation.
	 */
	@Override
	protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
		String operation = operator.getName();

		
		if ("Do".equals(operation)) {
			imageDetails = new ArrayList<String>();
			COSName objectName = (COSName) operands.get(0);
			// get the PDF object
			PDXObject xobject = getResources().getXObject(objectName);
			// check if the object is an image object
			if (xobject instanceof PDImageXObject) {
				PDImageXObject image = (PDImageXObject) xobject;
				int imageWidth = image.getWidth();
				int imageHeight = image.getHeight();
				
				//image found
				if(imageWidth != 0 && imageHeight != 0) {
					//System.out.println(++this.imageCount);
					imageCount++;
				}
				//Add details in list
				imageDetails.add(Integer.toString(imageWidth));
				imageDetails.add(Integer.toString(imageWidth));
				
				// System.out.println("\nImage [" + objectName.getName() + "]");

				Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
				float imageXScale = ctmNew.getScalingFactorX();
				float imageYScale = ctmNew.getScalingFactorY();
				
				//Add details in list
				imageDetails.add(Float.toString(imageXScale));
				imageDetails.add(Float.toString(imageYScale));
				
			} else if (xobject instanceof PDFormXObject) {
				PDFormXObject form = (PDFormXObject) xobject;
				showForm(form);
			}
		} else {
			super.processOperator(operator, operands);
		}
	}

}