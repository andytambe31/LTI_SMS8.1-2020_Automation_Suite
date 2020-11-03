package scripts;

public class ConvertToWord {

	// Java program to print a given number in words. The
	// program handles numbers from 0 to 9999

	// A function that prints
	// given number in words
	public static String convert_to_words(char[] num) {
		// Get number of digits
		// in given number
		int len = num.length;

		// Base cases
		if (len == 0) {
			System.out.println("empty string");
			//return;
		}
		if (len > 4) {
			System.out.println("Length more than 4 is not supported");
			//return;
		}

		/*
		 * The first string is not used, it is to make array indexing simple
		 */
		String[] single_digits = new String[] { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight",
				"nine" };

		/*
		 * The first string is not used, it is to make array indexing simple
		 */
		String[] two_digits = new String[] { "", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
				"sixteen", "seventeen", "eighteen", "nineteen" };

		/* The first two string are not used, they are to make array indexing simple */
		String[] tens_multiple = new String[] { "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy",
				"eighty", "ninety" };

		String[] tens_power = new String[] { "hundred", "thousand" };

		/* Used for debugging purpose only */
		//System.out.print(String.valueOf(num) + ": ");
		return String.valueOf(num);

	}

	// Driver Code
	public static void main(String[] args) {
		
		//System.out.println(Integer.toString(1555)+" is a string.");
		convert_to_words(Integer.toString(0).toCharArray());
/*		convert_to_words("523".toCharArray());
		convert_to_words("89".toCharArray());
		convert_to_words("8989".toCharArray());*/
	}
}
// This code is contributed
// by Mithun Kumar
