/**
 * 
 */
package com.mck.crrb;
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
		System.out.println(">" + trimLeadingChars("0000032", "0") + "<");
		System.out.println(">" + trimLeadingChars("00000a", "0") + "<");
		System.out.println(">" + trimLeadingChars("asdf", "0") + "<");
		System.out.println(">" + trimLeadingChars("00000", "0") + "<");
		System.out.println(">" + trimLeadingChars("    3a  ", null) + "<");
	}

}
