package scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateContractNumberIndia {

	public static String contractNumber() {
		
			Random r = new Random();
			int first = r.nextInt((9 - 1) + 1) + 1;
			char second = (char) (r.nextInt(26) + 'A');
			int third = r.nextInt((899 - 100) + 100) + 100;
			String id = String.valueOf(first) +String.valueOf(second)+ String.valueOf(third) ;
			return id;

		
	}
}
