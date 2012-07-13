/*******************************************************************************
* Copyright (c) 2010, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.schemagen.internal;

import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.text.MessageFormat;
import java.util.logging.Level;

/**
 *  Tools
 */
public final class Tools
{
	/** default file name used by the schemagen */
	static public String GEN_DEFAULT_NAME = "schema";   //$NON-NLS-1$
	
	/** empty string */
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$

	// ********** queries **********

	/**
	 * Return whether the specified string is null, empty, or contains
	 * only whitespace characters.
	 */
	public static boolean stringIsEmpty(String string) {
		if (string == null) {
			return true;
		}
		int len = string.length();
		if (len == 0) {
			return true;
		}
		return stringIsEmpty_(string.toCharArray(), len);
	}	

	private static boolean stringIsEmpty_(char[] s, int len) {
		for (int i = len; i-- > 0; ) {
			if ( ! Character.isWhitespace(s[i])) {
				return false;
			}
		}
		return true;
	}

	// ********** short name manipulation **********

	/**
	 * Strip the extension from the specified file name
	 * and return the result. If the file name has no
	 * extension, it is returned unchanged
	 * File#basePath()
	 */
	public static String stripExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index == -1) {
			return fileName;
		}
		return fileName.substring(0, index);
	}

	public static String stripProtocol(String uri) {

		 return uri.replaceFirst("http://", EMPTY_STRING);
	}


	public static String appendXsdExtension(String name) {

		 return name + ".xsd";		//$NON-NLS-1$
	}

	public static String extractFileNumber(String fileName) {

		 String result = stripExtension(fileName);
		 if(Tools.stringIsEmpty(result)) {
			 return EMPTY_STRING;
		 }
		 return result.replaceFirst(GEN_DEFAULT_NAME, EMPTY_STRING);
	}

	public static String extractDirectory(String path) {
		if( ! path.contains(File.separator)) {
			return EMPTY_STRING;	
		}
		return path.substring(0, path.lastIndexOf(File.separator));
	}
	
	private static void logMessage_(Level level, String message) {
		if(level == SEVERE) {
			System.err.println('\n' + message);
		}
		else {
			System.out.println('\n' + message);
		}
	}
	
	public static void logMessage(Level level, String key, Object argument) {
		Tools.logMessage_(level, Tools.bind(key, argument));
	}
	
	public static void logMessage(Level level, String key) {
		Tools.logMessage_(level, Tools.getString(key));
	}

	// ********** NLS utilities **********

	public static String getString(String key) {
		return JptEclipseLinkJaxbCoreMessages.getString(key);
	}
	
	public static String bind(String key, Object argument) {
		return MessageFormat.format(getString(key), argument);
	}
}
