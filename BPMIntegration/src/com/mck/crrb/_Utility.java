/**
 * 
 */
package com.mck.crrb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
import java.lang.reflect.Field;
import java.util.Set;

import teamworks.TWObject;
*/
/**
 * @author akatre
 *
 */
public class _Utility {
	public static final int INDEX_NOT_FOUND = -1;

	/*
	 * <p>This code is a modified version of StringUtils.stripStart method.</p>
	 * <p>Trims any of a set of characters from the start of a String except if the set character is the last one in the string.
	 * This method avoids removing of the last character in the string even if it matches the character to trim.</p>
	 *
	 * <p>A {@code null} input String returns {@code null}.
	 * An empty string ("") input returns the empty string.</p>
	 *
	 * <p>If the stripChars String is {@code null}, whitespace is
	 * stripped as defined by {@link Character#isWhitespace(char)} 
	 * except for the last character.</p>
	 *
	 * <pre>
	 * Utility.trimLeadingChars(null, *)               = null
	 * Utility.trimLeadingChars("000000", "0")         = "0"
	 * Utility.trimLeadingChars("00001234", "0")       = "1234"
	 * Utility.trimLeadingChars("0000a", "0")          = "a"
	 * Utility.trimLeadingChars("    3a  ", null)      = "3a  "
	 * </pre>
	 *
	 * @param str  the String to remove characters from, may be null
	 * @param stripChars  the characters to remove, null treated as whitespace
	 * @return the trimmed String, {@code null} if null String input
	 */
	public static String trimLeadingChars(final String str, final String trimChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (trimChars == null) {
            while (start != strLen - 1 && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (trimChars.isEmpty()) {
            return str;
        } else {
            while (start != strLen - 1 && trimChars.indexOf(str.charAt(start)) != INDEX_NOT_FOUND) {
                start++;
            }
        }
        return str.substring(start);	
	}
	
	/*
	 * Compares date components of the two dates - ignoring the time and time zone values.
	 * 
	 * @param d1 the first date to use in comparison; order of dates doesn't matter if using zero for equal dates
	 * @param d2 the second date to use in comparison; order of dates doesn't matter if using zero for equal dates
	 * @return non-zero value if the dates are different; zero if equal
	 */
	public static int compareDates(Date d1, Date d2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(d1);
		int year1 = cal1.get(Calendar.YEAR);
		int month1 = cal1.get(Calendar.MONTH);
		int day1 = cal1.get(Calendar.DAY_OF_MONTH);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(d2);
		int year2 = cal2.get(Calendar.YEAR);
		int month2 = cal2.get(Calendar.MONTH);
		int day2 = cal2.get(Calendar.DAY_OF_MONTH);
		
//		System.out.println("d1: " + year1 + "/" + month1 + "/" + day1);
//		System.out.println("d2: " + year2 + "/" + month2 + "/" + day2);
//		System.out.println("yf: " + (year1 - year2));
//		System.out.println("mf: " + (month1 - month2));
//		System.out.println("df: " + (day1 - day2));
//		System.out.println("return: " + (year1 - year2 | month1 - month2 | day1 - day2));
		return year1 - year2 | month1 - month2 | day1 - day2;
	}
	
	/*
	public CorrectionRow TWtoJavaCorrectionRow(TWObject twCorrectionRow) {
		this.twCorrectionRow = twCorrectionRow;
		this.invoiceId = twCorrectionRow.getPropertyValue("invoiceId").toString();
		Set<String> props = twCorrectionRow.getPropertyNames();
		for (String prop: props) {
			Field field;
			try {
				field = CorrectionRow.class.getDeclaredField(prop);
				field.getType()
				field.set(this, twCorrectionRow.getPropertyValue(prop));
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} 
	*/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

		try {
			
		    String d1Str = "05/20/2018 07:00:00";
			Date d1 = sd.parse(d1Str);

		    String d2Str = "05/20/2017 08:02:01";
		    Date d2 = sd.parse(d2Str);
		    
		    System.out.println(compareDates(d1, d2));
		    
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		
		System.out.println(">" + trimLeadingChars("0000032", "0") + "<");
		System.out.println(">" + trimLeadingChars("00000a", "0") + "<");
		System.out.println(">" + trimLeadingChars("asdf", "0") + "<");
		System.out.println(">" + trimLeadingChars("00000", "0") + "<");
		System.out.println(">" + trimLeadingChars("    3a  ", null) + "<");
	}

}
