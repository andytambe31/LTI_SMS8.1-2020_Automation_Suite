package scripts;

import java.io.File;

public class RenameFile {

	static String fileName;

	public RenameFile() {
		TestParameters tstParameters = new TestParameters();
		fileName = tstParameters.getOBillingTestPDF();
	}

	public void renameAndMove() {
		File destFile = new File("./test/resources/data/" + fileName);
		File sourceFile = new File("C:\\Users\\ATA\\Downloads\\CRViewer.pdf");

		if (sourceFile.renameTo(destFile)) {
			System.out.println("File moved successfully");
		} else {
			System.out.println("Failed to move file");
		}

	}

	public boolean delete() {
		File file = new File("./test/resources/data/" + fileName);

		if (file.delete()) {
			System.out.println("File deleted successfully");
			return true;
		} else {
			System.out.println("Failed to delete the file");
			return false;
		}
	}

	public boolean fileFound() {
		File sourceFile = new File("C:\\Users\\ATA\\Downloads\\CRViewer.pdf");

		return sourceFile.exists();
	}

}
