package com.henry.wilds.util;

/**
 * This class contains string utilities
 * @author Samuel Henry
 * @since July 6, 2014
 * @version 1.0
 */
public class StringUtil {
	
	/**
	 * Checks if a string's characters are all valid
	 * @param check The string to check
	 * @return True if the string is valid, false otherwise
	 */
	public static boolean isValidString(String check) {
		for(int i = 0; i < check.length(); i++) {
			char c = check.charAt(i);
			
			if(c == ' ' || c == '-' || c == '_') continue;
			if('0' <= c  && c <= '9') continue;
			if('a' <= c && c <= 'z') continue;
			if('A' <= c && c <= 'Z') continue;
			
			return false;
		}
		
		return true;
	}
	
	/**
	 * This method splits a string with the regular expression provided,
	 * and returns the value in the array that corresponds with the
	 * array position. It also assigns the value that is returned to the
	 * string that is split.
	 * @param toSplit The string to split
	 * @param regex The regular expression to split by
	 * @param arrayPos The position in the split array to return
	 * @return The split string
	 */
	public static int getIntegerFromSplitPos(String toSplit, String regex, int arrayPos) {
		int ret = -1;
		
		try {
			ret = Integer.parseInt(toSplit.split(regex)[arrayPos]);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

}