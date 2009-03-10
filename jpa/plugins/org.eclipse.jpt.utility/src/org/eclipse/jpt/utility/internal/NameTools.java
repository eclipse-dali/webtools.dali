/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.beans.Introspector;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.SortedSet;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

/**
 * Various helper methods for generating names.
 */
public final class NameTools {

	/**
	 * Given a "root" name and a set of existing names, generate a unique
	 * name that is either the "root" name or some variation on the "root"
	 * name (e.g. "root2", "root3",...). The names are case-sensitive
	 * (i.e. "Root" and "root" are both allowed).
	 */
	public static String uniqueNameFor(String rootName, Iterator<String> existingNames) {
		return uniqueNameFor(rootName, CollectionTools.set(existingNames));
	}
	
	/**
	 * Given a "root" name and a set of existing names, generate a unique
	 * name that is either the "root" name or some variation on the "root"
	 * name (e.g. "root2", "root3",...). The names are case-sensitive
	 * (i.e. "Root" and "root" are both allowed).
	 */
	public static String uniqueNameFor(String rootName, Collection<String> existingNames) {
		return uniqueNameFor(rootName, existingNames, rootName);
	}

	/**
	 * Given a "root" name and a set of existing names, generate a unique
	 * name that is either the "root" name or some variation on the "root"
	 * name (e.g. "root2", "root3",...). The names are NOT case-sensitive
	 * (i.e. "Root" and "root" are NOT both allowed).
	 */
	public static String uniqueNameForIgnoreCase(String rootName, Iterator<String> existingNames) {
		return uniqueNameForIgnoreCase(rootName, CollectionTools.set(existingNames));
	}

	/**
	 * Given a "root" name and a set of existing names, generate a unique
	 * name that is either the "root" name or some variation on the "root"
	 * name (e.g. "root2", "root3",...). The names are NOT case-sensitive
	 * (i.e. "Root" and "root" are NOT both allowed).
	 */
	public static String uniqueNameForIgnoreCase(String rootName, Collection<String> existingNames) {
		return uniqueNameFor(rootName, convertToLowerCase(existingNames), rootName.toLowerCase());
	}

	/**
	 * use the suffixed "template" name to perform the comparisons, but RETURN
	 * the suffixed "root" name; this allows case-insensitive comparisons
	 * (i.e. the "template" name has been morphed to the same case as
	 * the "existing" names, while the "root" name has not, but the "root" name
	 * is what the client wants morphed to be unique)
	 */
	private static String uniqueNameFor(String rootName, Collection<String> existingNames, String templateName) {
		if ( ! existingNames.contains(templateName)) {
			return rootName;
		}
		String uniqueName = templateName;
		for (int suffix = 2; true; suffix++) {
			if ( ! existingNames.contains(uniqueName + suffix)) {
				return rootName.concat(String.valueOf(suffix));
			}
		}
	}

	/**
	 * Convert the specified collection of strings to a collection of the same
	 * strings converted to lower case.
	 */
	private static HashSet<String> convertToLowerCase(Collection<String> strings) {
		HashSet<String> result = new HashSet<String>(strings.size());
		for (String string : strings) {
			result.add(string.toLowerCase());
		}
		return result;
	}

	/**
	 * Build a fully-qualified name for the specified database object.
	 * Variations:
	 *     catalog.schema.name
	 *     catalog..name
	 *     schema.name
	 *     name
	 */
	public static String buildQualifiedDatabaseObjectName(String catalog, String schema, String name) {
		if (name == null) {
			return null;
		}
		if ((catalog == null) && (schema == null)) {
			return name;
		}

		StringBuilder sb = new StringBuilder(100);
		if (catalog != null) {
			sb.append(catalog);
			sb.append('.');
		}
		if (schema != null) {
			sb.append(schema);
		}
		sb.append('.');
		sb.append(name);
		return sb.toString();
	}

	/**
	 * The set of reserved words in the Java programming language.
	 * These words cannot be used as identifiers (i.e. names).
	 * http://java.sun.com/docs/books/tutorial/java/nutsandbolts/_keywords.html
	 */
	@SuppressWarnings("nls")
	public static final String[] JAVA_RESERVED_WORDS = new String[] {
				"abstract",
				"assert",  // jdk 1.4
				"boolean",
				"break",
				"byte",
				"case",
				"catch",
				"char",
				"class",
				"const",  // unused
				"continue",
				"default",
				"do",
				"double",
				"else",
				"enum",  // jdk 1.5
				"extends",
				"false",
				"final",
				"finally",
				"float",
				"for",
				"goto",  // unused
				"if",
				"implements",
				"import",
				"instanceof",
				"int",
				"interface",
				"long",
				"native",
				"new",
				"null",
				"package",
				"private",
				"protected",
				"public",
				"return",
				"short",
				"static",
				"strictfp",  // jdk 1.2
				"super",
				"switch",
				"synchronized",
				"this",
				"throw",
				"throws",
				"transient",
				"true",
				"try",
				"void",
				"volatile",
				"while"
			};

	/**
	 * The set of reserved words in the Java programming language.
	 * These words cannot be used as identifiers (i.e. names).
	 * http://java.sun.com/docs/books/tutorial/java/nutsandbolts/_keywords.html
	 */
	public static final SortedSet<String> JAVA_RESERVED_WORDS_SET = 
		Collections.unmodifiableSortedSet(CollectionTools.sortedSet(JAVA_RESERVED_WORDS));

	/**
	 * Return the set of Java programming language reserved words.
	 * These words cannot be used as identifiers (i.e. names).
	 * http://java.sun.com/docs/books/tutorial/java/nutsandbolts/_keywords.html
	 */
	public static Iterator<String> javaReservedWords() {
		return new ArrayIterator<String>(JAVA_RESERVED_WORDS);
	}

	/**
	 * Return whether the specified string consists of Java identifier
	 * characters (but may be a reserved word).
	 */
	public static boolean stringConsistsOfJavaIdentifierCharacters(String string) {
		if (string.length() == 0) {
			return false;
		}
		return stringConsistsOfJavaIdentifierCharacters_(string.toCharArray());
	}

	/**
	 * Return whether the specified string consists of Java identifier
	 * characters (but may be a reserved word).
	 */
	public static boolean stringConsistsOfJavaIdentifierCharacters(char[] string) {
		if (string.length == 0) {
			return false;
		}
		return stringConsistsOfJavaIdentifierCharacters_(string);
	}

	/**
	 * The specified string must not be empty.
	 */
	private static boolean stringConsistsOfJavaIdentifierCharacters_(char[] string) {
		if ( ! Character.isJavaIdentifierStart(string[0])) {
			return false;
		}
		for (int i = string.length; i-- > 1; ) {  // NB: end with 1
			if ( ! Character.isJavaIdentifierPart(string[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified string is a valid Java identifier.
	 */
	public static boolean stringIsLegalJavaIdentifier(String string) {
		return stringConsistsOfJavaIdentifierCharacters(string)
				&& ! JAVA_RESERVED_WORDS_SET.contains(string);
	}

	/**
	 * Return whether the specified string is a valid Java identifier.
	 */
	public static boolean stringIsLegalJavaIdentifier(char[] string) {
		return stringConsistsOfJavaIdentifierCharacters(string)
				&& ! JAVA_RESERVED_WORDS_SET.contains(new String(string));
	}

	/**
	 * Convert the specified string to a valid Java identifier
	 * by substituting an underscore '_' for any invalid characters
	 * in the string and appending an underscore '_' to the string if
	 * it is a Java reserved word.
	 */
	public static String convertToJavaIdentifier(String string) {
		return convertToJavaIdentifier(string, '_');
	}

	/**
	 * Convert the specified string to a valid Java identifier
	 * by substituting the specified character for any invalid characters
	 * in the string and, if necessary, appending the specified character
	 * to the string until it is not a Java reserved word.
	 */
	public static String convertToJavaIdentifier(String string, char c) {
		if (string.length() == 0) {
			return string;
		}
		if (JAVA_RESERVED_WORDS_SET.contains(string)) {
			// a reserved word is a valid identifier, we just need to tweak it a bit
			checkCharIsJavaIdentifierPart(c);
			return convertToJavaIdentifier(string + c, c);
		}
		char[] array = string.toCharArray();
		return convertToJavaIdentifier_(array, c) ? new String(array) : string;
	}

	/**
	 * Convert the specified string to a valid Java identifier
	 * by substituting an underscore '_' for any invalid characters
	 * in the string and appending an underscore '_' to the string if
	 * it is a Java reserved word.
	 */
	public static char[] convertToJavaIdentifier(char[] string) {
		return convertToJavaIdentifier(string, '_');
	}

	/**
	 * Convert the specified string to a valid Java identifier
	 * by substituting the specified character for any invalid characters
	 * in the string and, if necessary, appending the specified character
	 * to the string until it is not a Java reserved word.
	 */
	public static char[] convertToJavaIdentifier(char[] string, char c) {
		if (string.length == 0) {
			return string;
		}
		if (JAVA_RESERVED_WORDS_SET.contains(new String(string))) {
			// a reserved word is a valid identifier, we just need to tweak it a bit
			checkCharIsJavaIdentifierPart(c);
			return convertToJavaIdentifier(CollectionTools.add(string, c), c);
		}
		convertToJavaIdentifier_(string, c);
		return string;
	}

	/**
	 * The specified string must not be empty.
	 * Return whether the string was modified.
	 */
	private static boolean convertToJavaIdentifier_(char[] string, char c) {
		boolean mod = false;
		if ( ! Character.isJavaIdentifierStart(string[0])) {
			checkCharIsJavaIdentifierStart(c);
			string[0] = c;
			mod = true;
		}
		checkCharIsJavaIdentifierPart(c);
		for (int i = string.length; i-- > 1; ) {  // NB: end with 1
			if ( ! Character.isJavaIdentifierPart(string[i])) {
				string[i] = c;
				mod = true;
			}
		}
		return mod;
	}

	private static void checkCharIsJavaIdentifierStart(char c) {
		if ( ! Character.isJavaIdentifierStart(c)) {
			throw new IllegalArgumentException("invalid Java identifier start char: '" + c + '\'');  //$NON-NLS-1$
		}
	}

	private static void checkCharIsJavaIdentifierPart(char c) {
		if ( ! Character.isJavaIdentifierPart(c)) {
			throw new IllegalArgumentException("invalid Java identifier part char: '" + c + '\'');  //$NON-NLS-1$
		}
	}

	/**
	 * Convert the specified method name to a property name.
	 */
	public static String convertGetterMethodNameToPropertyName(String methodName) {
		int beginIndex = 0;
		if (methodName.startsWith("get")) { //$NON-NLS-1$
			beginIndex = 3;
		} else if (methodName.startsWith("is")) { //$NON-NLS-1$
			beginIndex = 2;
		}
		return Introspector.decapitalize(methodName.substring(beginIndex));
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private NameTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
