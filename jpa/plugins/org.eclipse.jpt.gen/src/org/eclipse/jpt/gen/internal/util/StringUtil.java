/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal.util;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class StringUtil
{
	/**
	 * Pads a string by adding a sequence of an arbitrary character at the beginning.
	 * @param padChar The character to be used for padding.
	 * @param len The desired length after padding.
	 * @return The padded string. For example if <code>str</code> is "f4e" and <code>padChar</code> is '0' 
	 * and <code>len</code> is 6 then this method returns "000f4e"
	 */
	public static String padLeft(String str, char padChar, int len) {
		if(str == null) {
			return null;
		}
		int strLen = str.length();
		if (strLen < len) {
			StringBuffer buffer = new StringBuffer(len);
			int count = len-strLen;
			for (int i = 0; i < count; ++i)
				buffer.append(padChar);
			buffer.append(str);
			str = buffer.toString();
		}
		return str;
	}
	/**
	 * Inserts a given character at the beginning and at the end of the specified string.
	 * For example if the string is <tt>extreme</tt> and the char is <tt>'</tt> then 
	 * the returned string is <tt>'exterme'</tt>.
	 */
	public static String quote(String str, char c) {
		assert(str != null);
		StringBuffer buffer = new StringBuffer(str.length()+2);
		buffer.append(c);
		buffer.append(str);
		buffer.append(c);
		return buffer.toString();
	}
	public static String doubleQuote(String str) {
		return quote(str, '"');
	}
	/**
	 * Removes the first and last single or double quotes (if they exist).
	 */
	public static String unquote(String quoted) {
		if (quoted != null && quoted.length() >= 2){
			int len = quoted.length();
			char firstChar = quoted.charAt(0);
			char lastChar = quoted.charAt(len-1);
			if (firstChar == lastChar && (firstChar == '\'' || firstChar == '"')) {
				return quoted.substring(1, len-1);
			}
		}
		return quoted;
	}
	/**
	 * Truncates a string and adds "..." in the result string.
	 * If the string length is less or equal to the max len then 
	 * the original string is returned.
	 */
	public static String truncate(String s, int maxLen) {
		if (s == null) {
			return null;
		}
		int len = s.length();
		if (len > maxLen) {
			int segmentLen = maxLen/2;
			s = s.substring(0, segmentLen) + "..." + s.substring(len-segmentLen);
		}
		return s;
	}
	/**
	 * Returns a string containing the same character repeated.
	 */
	public static String repeat(char c, int count) {
		StringBuffer buffer = new StringBuffer(count);
		for (int i = 0; i < count; ++i) {
			buffer.append(c);
		}
		return buffer.toString();
	}
	/**
	 * Returns the given string unless it is emtpty where it returns null.
	 */
	public static String nullIfEmpty(String s) {
		if (s != null && s.length() == 0) {
			s = null;
		}
		return s;
	}
	/**
     * Returns a string containing the same characters as the argument string 
	 * except that the control characters are replaced by the their hex code.
	 * For example if the string is "ab\nc" the returned string is "ab{0xA}c".
	 */
	public static String getVisibleString(String s) {
		if (s == null)
			return null;
		int len = s.length();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < len; ++i) {
			char c = s.charAt(i);
			if (c <= 0x1F || (c == 0x20 && (i == 0 || i == len-1))) {
				buffer.append("(0x" + Integer.toHexString((int)c).toUpperCase() + ")");
			}
			else buffer.append(c);
		}
		return buffer.toString();
	}
	/**
	 * Replaces a portion of string.
	 * @param str The original string.
	 * @param offset The offset in the original string where the replace starts
	 * @param len The replace length the original string
	 * @param replaceStr The replacement string
	 */
	public static String strReplace(String str, int offset, int len, String replaceStr) {
		StringBuffer buffer = new StringBuffer(str.length()-len+replaceStr.length());
		buffer.append(str.substring(0, offset));
		buffer.append(replaceStr);
		buffer.append(str.substring(offset+len));
		
		return buffer.toString();
	}
	public static String strReplace(String str, String pattern, String replaceStr)
	{
		if(str == null) {
			return null;
		}
		if(pattern == null || pattern.equals("")) {
			return str;
		}
		int index = str.indexOf(pattern);
		if (index < 0)
			return str;
		
		if (replaceStr == null)
			replaceStr = "";
		return str.substring(0, index) + replaceStr + str.substring(index + pattern.length());
	}
	public static String strReplaceAll(String str, String pattern, String replaceStr)
	{
		if(str == null) {
			return null;
		}
		if (replaceStr == null)
			replaceStr = "";
		if(pattern == null || pattern.equals("")) {
			return str;
		}
		int index = str.indexOf(pattern);
		while (index >= 0) {
			str = str.substring(0, index) + replaceStr + str.substring(index + pattern.length());
			index = str.indexOf(pattern, index+replaceStr.length());
		}
		return str;
	}
	public static String strInsert(String str, int index, String insertStr)
	{
		return str.substring(0, index)
				+ insertStr
				+ str.substring(index);
	}
    /**
     * Tokenize the specified string into a <code>List</code> of
     * words.
     * If the string specified is <code>null</code> or empty, this
     * method will return <code>null</code>.
     * 
     * @param s      The string to tokenize into a <code>List</code>.
     * @param sep    The separator character to use to split
     *               the string.
     * @param trim   If <code>true</code>, run <code>trim</code> on
     *               each element in the result <code>List</code>.
     * 
     * @return A <code>List</code> containing all tokenized words
     *         in the parameter string.
     *         Each element is of type <code>String</code>.
     */
	public static List<String> strToList(String s, char sep, boolean trim)
	{
	//ex: if sep is ';' then s should be someting like "Red;Black"
		if (s == null || s.length() == 0)
			return null;
			
		ArrayList<String> result = new ArrayList<String>();
		
		char delimiters[] = {sep};
		java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(s, new String(delimiters), true/*returnDelimiters*/);
		String lastTok=null;
		while (tokenizer.hasMoreTokens()) {
			String tok = tokenizer.nextToken();
			if (tok.length()==1 && tok.charAt(0)==sep){//delimiter
			    if (tok.equals(lastTok)||lastTok==null/*first element is empty*/)
			        result.add("");
			}
			else{
			    if (trim)
			        tok = tok.trim();
			    result.add(tok);
			}
			lastTok=tok;
		}
		if(lastTok.length()==1 && lastTok.charAt(0)==sep)//last element is empty
		    result.add("");
		result.trimToSize();
		return result;
	}
	public static List<String> strToList(String s, char sep)
	{
		return strToList(s, sep, false/*trim*/);
	}
	
	@SuppressWarnings("unchecked")
	public static String listToStr(Collection a, char sep)
	{
		return listToStr(a, String.valueOf(sep));
	}
	
	public static String listToStr(Collection<Object> a, String sep) {
		//reverse of strToList
		if (a == null)
			return null;
		int count = a.size();
		if (count == 0)
			return null;
		
		StringBuffer buffer = null;
		for (Iterator<Object> iter = a.iterator(); iter.hasNext(); )
		{
			Object obj = iter.next();
			if (obj == null)
				continue;
			
			if (buffer == null)
				buffer = new StringBuffer();
			else
				buffer.append(sep);
			if (obj instanceof String)
				buffer.append((String)obj);
			else
				buffer.append(obj);
		}
		return (buffer != null) ? buffer.toString() : null;
	}
	/**
	 * Convert the text of a String into a Map of Strings, where each
	 * key and value in the Map is of type String.
	 * 
	 * @param s      The string to be converted to a Map.
	 * @param sep1   The separator between keys and their
	 *               values.
	 * @param sep2   The separator between key-value pairs.
	 * 
	 * @return The string converted to a Map.
	 */
	public static java.util.Map<String, String> strToMap(String s, char sep1, char sep2)
	{
		return strToMap(s, sep1, sep2, false/*lowercaseKeys*/);
	}
	/**
	 * Convert the text of a String into a Map of Strings, where each
	 * key and value in the Map is of type String.
	 * This form also allows you to specify that all keys will be
	 * converted to lower-case before adding to the Map.
	 * 
	 * @param s      The string to be converted to a Map.
	 * @param sep1   The separator between keys and their
	 *               values.
	 * @param sep2   The separator between key-value pairs.
	 * @param lowercaseKeys
	 *               Whether to convert keys to lower case
	 *               before adding to the Map.
	 * 
	 * @return The string converted to a Map.
	 */
	public static java.util.Map<String, String> strToMap(String s, char sep1, char sep2, boolean lowercaseKeys)
	{
		//ex: if sep1 is ':' and sep2 is ',' then s should be something like "color:Red,size:XL"
		
		if (s == null || s.length() == 0) {
			return Collections.emptyMap();
		}
			
		java.util.List<String> a = strToList(s, sep2);
		if (a == null) {
			return Collections.emptyMap();
		}
		
		java.util.HashMap<String, String> hm = new java.util.HashMap<String, String>();
		int count = a.size();
		for (int i = 0; i < count; ++i)
		{
			String s2 = (String)a.get(i); //ex: color:Red
			int pos = s2.indexOf(sep1);
			if (pos >= 0)
			{
				String name = s2.substring(0, pos);
				String val = s2.substring(pos+1);
				if (lowercaseKeys)
					name = name.toLowerCase();
				hm.put(name, val);
			}
		}
		return hm;
	}
	
	@SuppressWarnings("unchecked")
	public static String mapToStr(java.util.Map hm, char sep1, char sep2)
	//reverse of strToMap
	{
		if (hm == null || hm.isEmpty())
			return null;
		
		StringBuffer buffer = new StringBuffer();
		java.util.Iterator<java.util.Map.Entry> iter = hm.entrySet().iterator();
		while (iter.hasNext()) {
			java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
			buffer.append(entry.getKey());
			buffer.append(sep1);
			buffer.append(entry.getValue());
			if (iter.hasNext()) {
				buffer.append(sep2);
			}
		}
		return buffer.toString();
	}
    /**
     * Perform a <em>case insensitive</em> comparison between
     * the string representations of two objects.
     * 
     * @param obj1   The first object to compare.
     * @param obj2   The second object to compare.
     * 
     * @return <code>true</code> if both objects have the
     *         same case-insensitive string representation.
     */
	public static boolean compareAsStrings(Object obj1, Object obj2)
	{	
		if (obj1 == null || obj2 == null)
			return obj1 == obj2;
		
		String s1, s2;
		if (obj1 instanceof String) {
			s1 = (String)obj1;
        } else {
			s1 = obj1.toString();
        }
		if (obj2 instanceof String) {
			s2 = (String)obj2;
        }
        else {
			s2 = obj2.toString();
        }
		
		return s1.equalsIgnoreCase(s2);
	}
	/**
	 * Tests whether a string starts with any of a list of strings.
	 */
	public static boolean startsWithAny(String s, List<String> prefixes) {
		int count = prefixes.size();
		for (int i = 0; i < count; ++i) {
			if (s.startsWith((String)prefixes.get(i))) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Returns the argument string with the first char upper-case.
	 */
	public static String initUpper(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	/**
	 * Returns the argument string with the first char lower-case.
	 */
	public static String initLower(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}
		return Character.toLowerCase(str.charAt(0)) + str.substring(1);
	}
	/**
	 * Tests whether all characters in the given string are upper
	 * case.
	 */
	public static boolean isUpperCase(String s) {
		return s.toUpperCase().equals(s);
	}
	/**
	 * Returns the first non-white char index starting from the 
	 * specified index.
	 */
	public static int skipWhiteSpaces(String str, int index) {
		int len = str.length();
		while (index < len) {
			if (!Character.isWhitespace(str.charAt(index))) {
				break;
			}
			++index;
		}
		return index;
	}
	/**
	 * Collapses consecutive white spaces into one space.
	 */
	public static String collapseWhiteSpaces(String str){
	    String result=null;
	    if (str!=null){
	        StringBuffer buffer=new StringBuffer();
	        boolean isInWhiteSpace=false;
	        for (int i=0;i<str.length();i++){
	            char c=str.charAt(i);
	            if (Character.isWhitespace(c)){
	                isInWhiteSpace=true;
	            }
	            else {
	                if (isInWhiteSpace)
	                    buffer.append(" ");
	                isInWhiteSpace=false;   
	                buffer.append(c);
	            }
	        }
	        result=buffer.toString();
	    }
	    return result;
	}
	
	/**
	 * Utility methods used to convert DB object names to  
	 * appropriate Java type and field name 
	 */
	public static String pluralise(String name) {
		String result = name;
		if (name.length() == 1) {
			result += 's';
		} else if (!seemsPluralised(name)) {
			String lower = name.toLowerCase();
			if (!lower.endsWith("data")) { //orderData --> orderDatas is dumb
				char secondLast = lower.charAt(name.length() - 2);
				if (!isVowel(secondLast) && lower.endsWith("y")) {
					// city, body etc --> cities, bodies
					result = name.substring(0, name.length() - 1) + "ies";
				} else if (lower.endsWith("ch") || lower.endsWith("s")) {
					// switch --> switches  or bus --> buses
					result = name + "es";
				} else {
					result = name + "s";
				}
			}
		}
		return result;
	}

	public static String singularise(String name) {
		String result = name;
		if (seemsPluralised(name)) {
			String lower = name.toLowerCase();
			if (lower.endsWith("ies")) {
				// cities --> city
				result = name.substring(0, name.length() - 3) + "y";
			} else if (lower.endsWith("ches") || lower.endsWith("ses")) {
				// switches --> switch or buses --> bus
				result = name.substring(0, name.length() - 2);
			} else if (lower.endsWith("s")) {
				// customers --> customer
				result = name.substring(0, name.length() - 1);
			}
		}
		return result;
	}
	private final static boolean isVowel(char c) {
		boolean vowel = false;
		vowel |= c == 'a';
		vowel |= c == 'e';
		vowel |= c == 'i';
		vowel |= c == 'o';
		vowel |= c == 'u';
		vowel |= c == 'y';
		return vowel;
	}
	private static boolean seemsPluralised(String name) {
		name = name.toLowerCase();
		boolean pluralised = false;
		pluralised |= name.endsWith("es");
		pluralised |= name.endsWith("s");
		pluralised &= !(name.endsWith("ss") || name.endsWith("us"));
		return pluralised;
	}
	
	/**
	 * Returns the package name of a class name.
	 * For example if given <code>oracle.util.ObjectUtil</code> it would return 
	 * <code>oracle.util</code>. If the class is not in a package then null is returned.
	 */
	public static String getPackageName(String className) {
		if(className == null) {
			return null;
		}
		int lastDotIndex = className.lastIndexOf('.');
		if (lastDotIndex < 0)
			return null;
		return className.substring(0, lastDotIndex);
	}
	/**
	 * Returns the class name given a full class name.
	 * For example if given <code>oracle.util.ObjectUtil</code> it would return 
	 * <code>ObjectUtil</code>
	 */
	public static String getClassName(String fullClassName) {
		if(fullClassName == null) {
			return null;
		}
		int lastDotIndex = fullClassName.lastIndexOf('.');
		if (lastDotIndex < 0)
			return fullClassName;
		return fullClassName.substring(lastDotIndex+1);
	}

	
	/**
	 * Converts a database column name to a Java variable name (<em>first letter
	 * not capitalized</em>).
	 */
	public static String columnNameToVarName(String columnName) {
		return dbNameToVarName(columnName);
	}
	/**
	 * Converts a database table name to a Java variable name (<em>first letter
	 * not capitalized</em>).
	 */
	public static String tableNameToVarName(String tableName) {
		return dbNameToVarName(tableName);
	}
	/**
	 * Converts a database name (table or column) to a java name (<em>first letter
	 * not capitalized</em>). employee_name or employee-name -> employeeName
	 */
	private static String dbNameToVarName(String s) {
		if ("".equals(s)) {
			return s;
		}
		StringBuffer result = new StringBuffer();

		boolean capitalize = true;
		boolean lastCapital = false;
		boolean lastDecapitalized = false;
		String p = null;
		for (int i = 0; i < s.length(); i++) {
			String c = s.substring(i, i + 1);
			if ("_".equals(c) || " ".equals(c)) {
				capitalize = true;
				continue;
			}

			if (c.toUpperCase().equals(c)) {
				if (lastDecapitalized && !lastCapital) {
					capitalize = true;
				}
				lastCapital = true;
			} else {
				lastCapital = false;
			}

			if (capitalize) {
				if (p == null || !p.equals("_")) {
					result.append(c.toUpperCase());
					capitalize = false;
					p = c;
				} else {
					result.append(c.toLowerCase());
					capitalize = false;
					p = c;
				}
			} else {
				result.append(c.toLowerCase());
				lastDecapitalized = true;
				p = c;
			}

		}
		/*this was using StringUtil.initLower. Changed to Introspector.decapitalize so that 
		 * it returns the correct bean property name when called from columnNameToVarName.
		 * This is necessary because otherwise URL would be uRL which would cause 
		 * an "The property uRL is undefined for the type xx" error because 
		 * Introspector.getBeanInfo (used by JavaTypeIntrospector) returns 
		 * the property name as URL.*/
		String resultStr = Introspector.decapitalize(result.toString());
		if (resultStr.equals("class")) {
			// "class" is illegal becauseOf Object.getClass() clash
			resultStr = "clazz";
		}
		return resultStr;
	}
	
	/**
	 * Compare two objects. If both String, ignore case
	 * @param o1
	 * @param o2
	 * @param ignoreCaseIfStr
	 * @return
	 */
	public static boolean equalObjects(Object o1, Object o2, boolean ignoreCaseIfStr)
	{
		if (o1 == o2) {
			return true;
		}
	    boolean result;
	    if (o1 == null || o2 == null) {
	    	return false; //we already checked o1 == o2 above
	    }
	    if (ignoreCaseIfStr && o1 instanceof String && o2 instanceof String)
	        result = ((String)o1).equalsIgnoreCase((String)o2);
	    else
	        result = o1.equals(o2);
	
	    return result;
	}

	public static boolean equalObjects(Object o1, Object o2)
	{
	    return equalObjects(o1, o2, false/*ignoreCaseIfStr*/);
	}
	
}

