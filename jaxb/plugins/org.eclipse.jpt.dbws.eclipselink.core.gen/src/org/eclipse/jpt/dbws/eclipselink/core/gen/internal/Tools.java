/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.core.gen.internal;

import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.text.MessageFormat;
import java.util.logging.Level;

/**
 *  Tools
 */
public final class Tools
{
	/** empty string */
	public static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final String USER_DIRECTORY = System.getProperty("user.dir");	//$NON-NLS-1$

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

	
	public static File buildDirectory(String dirName) {
    	File currentDir = currentWorkingDirectory();
    	File dir = new File(currentDir, dirName);
    	dir.mkdir();
		return dir;
	}
	
	/**
	 * Return a file representing the current working directory.
	 */
	public static File currentWorkingDirectory() {
		return new File(USER_DIRECTORY); //$NON-NLS-1$
	}
	
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

	public static String extractDirectory(String path) {
		if( ! path.contains(File.separator)) {
			return EMPTY_STRING;	
		}
		return path.substring(0, path.lastIndexOf(File.separator));
	}
	
	public static void logMessage(Level level, String message) {
		if(level == SEVERE) {
			System.err.println('\n' + message);
		}
		else {
			System.out.println('\n' + message);
		}
	}
	
	// ********** NLS utilities **********

	public static String getString(String key) {
		return JptDbwsCoreMessages.getString(key);
	}
	
	public static String bind(String key, Object argument) {
		return MessageFormat.format(getString(key), argument);
	}
}
