/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal.util;

import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.NameTools;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Utility methods for entity generation.
 */
public class EntityGenTools {

	/**
	 * Convert the specified (database) identifier to a unique "Java style"
	 * class name.
	 * @see #convertToUniqueJavaStyleIdentifier(String, boolean, Collection)
	 */
	public static String convertToUniqueJavaStyleClassName(String identifier, Collection<String> classNames) {
		return convertToUniqueJavaStyleIdentifier(identifier, true, classNames);
	}

	/**
	 * Convert the specified (database) identifier to a unique "Java style"
	 * attribute (field/property) name.
	 * @see #convertToUniqueJavaStyleIdentifier(String, boolean, Collection)
	 */
	public static String convertToUniqueJavaStyleAttributeName(String identifier, Collection<String> attributeNames) {
		return convertToUniqueJavaStyleIdentifier(identifier, false, attributeNames);
	}

	/**
	 * Convert the specified (database) identifier to a unique "Java style"
	 * identifier:
	 *     - if the identifier is all-caps, convert underscores to "camel case"
	 *     - if the identifier is not all-caps, leave it unchanged (except, possibly, for the first letter)
	 *     - convert to a legal Java identifier
	 *         - eliminate illegal characters
	 *         - if the result is a reserved word, modify it slightly
	 * If the result is already one of the specified existing identifiers
	 * (ignoring case so we don't have filename collisions on Windows),
	 * modify it slightly again.
	 *     "FOO" => "Foo" or "foo"
	 *     "FOO_BAR" => "FooBar" or "fooBar"
	 *     "PACKAGE" => "Package" or "package_"
	 */
	public static String convertToUniqueJavaStyleIdentifier(String identifier, boolean capitalizeFirstLetter, Collection<String> identifiers) {
		String result = identifier;
		if (StringTools.stringIsUppercase(result) || StringTools.stringIsLowercase(result)) {
			// leave mixed case identifiers alone?
			result = StringTools.convertUnderscoresToCamelCase(result, capitalizeFirstLetter);
		} else {
			result = capitalizeFirstLetter ? StringTools.capitalize(result) : StringTools.uncapitalize(result);
		}
		result = NameTools.convertToJavaIdentifier(result);
		// assume that converting to a unique name will not result in a Java reserved word
		// (since no Java reserved words end with a number)
		result = NameTools.uniqueNameForIgnoreCase(result, identifiers);
		return result;
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private EntityGenTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
