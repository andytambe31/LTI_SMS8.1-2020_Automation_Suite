package scripts;

import java.util.ArrayList;
import java.util.List;

public class CheckMismatch {

	public String retMismatch(String strA, List<String> keyLocationListRef) {

		List<String> possibleMismatch = new ArrayList<String>();

		for (String strR : keyLocationListRef) {
			String temp = "";
			int keyPointer = 0;

			for (int i = 0; i < strR.length(); i++) {
				String character = this.getStringAt(strR, i);
				temp = temp + strR.substring(0, 1);

				String keyCharacter = this.getStringAt(strA, keyPointer);
				String lineCharacter = strR.substring(0, 1);

				if (this.getStringAt(strA, 0).equals(this.getStringAt(strR, 0))) {
					if (keyCharacter.contains(character)) {
						keyPointer++;
						// Check when consecutive characters mismatch
						if (strA.contains(temp)) {
							possibleMismatch.add(strR);
							break;
						}
					} else {
						temp = "";
						keyPointer = 0;
						continue;
					}
				}

			}

		}
		//System.out.println(possibleMismatch);
		String retVal = this.retBestMismatch(strA, possibleMismatch);
		return retVal;

	}

	public String getStringAt(String str, int location) {

		if (location == 0) {
			return str.substring(0, location + 1);
		} else
			return str.substring(location - 1, location);
	}

	public String retBestMismatch(String strA, List<String> possibleMismatch) {

		int passCounter = 0;
		int refCounter = 0;
		int temp = 0;
		int maxPass = 0;

		for (String strR : possibleMismatch) {
			refCounter++;
			passCounter = 0;
			for (int i = 0; i < strR.length(); i++) {

				if (this.getStringAt(strA, i).equals(this.getStringAt(strR, i))) {
					passCounter++;
				}

			}
			if (passCounter > maxPass) {
				maxPass = passCounter;
				temp = refCounter - 1;
			}

		}

		//System.out.println("Best Match: " + possibleMismatch.get(temp));
		return possibleMismatch.get(temp);

	}
}
