package scripts;

import scripts.TestParameters;

public class ReturnEntitySpecificInvoiceNo {

	public String invoiceTypeId(String OInvoiceId, String InvoiceTypeId) {
		String oupt = OInvoiceId;
		TestParameters tstPar = new TestParameters();

		//tstPar.setEntity("UAE");

		switch (tstPar.getEntity()) {

		case "UAE":

			if (InvoiceTypeId.equals("1")) {

				String invoice = "M4-";

				oupt = invoice + OInvoiceId;

			}

			else if (InvoiceTypeId.equals("2")) {

				String creditNote = "RM-";

				oupt = creditNote + OInvoiceId;

			} else
				System.out.println("InvoiceTypeId not found");

			break;

		default:

			//System.out.println("Entity not found");

		}

		return oupt;
	}

}
