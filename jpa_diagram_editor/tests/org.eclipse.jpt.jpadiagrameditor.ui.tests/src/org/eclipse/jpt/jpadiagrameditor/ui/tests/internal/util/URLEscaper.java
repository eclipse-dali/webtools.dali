/*******************************************************************************
 * Copyright (c) 2011, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util;

@SuppressWarnings("nls")
public class URLEscaper {
	private static final String specialChars = " <>#%{}|^~[]`;?@=&$";
	private static final String escapeCodes  = "%20%3C%3E%23%25%7B%7D%7C%5E%7E%5B%5D%60%3B%3F%40%3D%26%24";
	
	public static String escape(String s) {
		if (s == null)
			return null;
		StringBuffer res = new StringBuffer("");
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			int ind = specialChars.indexOf(ch);
			res.append(((ind >= 0) ? escapeCodes.substring(ind * 3, ind * 3 + 3) : ch));   
		}
		return res.toString();
	}
}
