/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.beans.Introspector;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.SortedSet;

import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;

/**
 * Various helper methods for generating names.
 */
public final class NameTools {

	// ********** unique names **********

	/**
	 * @see #uniqueName(String, Collection)
	 */
	public static String uniqueName(String rootName, Iterable<String> existingNames) {
		return uniqueName(rootName, CollectionTools.hashSet(existingNames));
	}
	
	/**
	 * Given a "root" name and a set of existing names, generate a unique
	 * name that is either the "root" name or some variation on the "root"
	 * name (e.g. <code>"root2"</code>, <code>"root3"</code>,...).
	 * The names are case-sensitive (i.e. <code>"Root"</code> and
	 * <code>"root"</code> are allowed to co-exist).
	 */
	public static String uniqueName(String rootName, Collection<String> existingNames) {
		return uniqueName(rootName, existingNames, rootName);
	}

	/**
	 * @see #uniqueNameIgnoreCase(String, Collection)
	 */
	public static String uniqueNameIgnoreCase(String rootName, Iterable<String> existingNames) {
		return uniqueNameIgnoreCase(rootName, CollectionTools.hashSet(existingNames));
	}

	/**
	 * Given a "root" name and a set of existing names, generate a unique
	 * name that is either the "root" name or some variation on the "root"
	 * name (e.g. <code>"root2"</code>, <code>"root3"</code>,...).
	 * The names are <em>not</em> case-sensitive (i.e. <code>"Root"</code> and
	 * <code>"root"</code> are <em>not</em> both allowed).
	 */
	public static String uniqueNameIgnoreCase(String rootName, Collection<String> existingNames) {
		return uniqueName(rootName, convertToLowerCase(existingNames), rootName.toLowerCase());
	}

	/**
	 * Use the suffixed "template" name to perform the comparisons, but <em>return</em>
	 * the suffixed "root" name; this allows case-insensitive comparisons
	 * (i.e. the "template" name has been morphed to the same case as
	 * the "existing" names, while the "root" name has not, but the "root" name
	 * is what the client wants morphed to be unique).
	 */
	private static String uniqueName(String rootName, Collection<String> existingNames, String templateName) {
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


	// ********** qualified name **********

	/**
	 * Build a fully-qualified name for the specified name segments.
	 * Typical database variations:<ul>
	 * <li><code>catalog.schema.name</code>
	 * <li><code>catalog..name</code>
	 * <li><code>schema.name</code>
	 * <li><code>name</code>
	 * </ul>
	 */
	public static String buildQualifiedName(String... segments) {
		StringBuilder sb = new StringBuilder(100);
		boolean next = false;
		for (String segment : segments) {
			if (next) {
				sb.append('.');
			}
			if (segment != null) {
				next = true;
				sb.append(segment);
			}
		}
		return sb.toString();
	}


	// ********** Java identifiers **********

	/**
	 * The set of reserved words in the Java programming language.
	 * These words cannot be used as identifiers (i.e. names).
	 * <p>
	 * <a href="http://java.sun.com/docs/books/tutorial/java/nutsandbolts/_keywords.html">
	 * Java Language Keywords</a>
	 */
	@SuppressWarnings("nls")
	private static final String[] JAVA_RESERVED_WORDS_ARRAY = new String[] {
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
	 * <p>
	 * <a href="http://java.sun.com/docs/books/tutorial/java/nutsandbolts/_keywords.html">
	 * Java Language Keywords</a>
	 */
	public static final SortedSet<String> JAVA_RESERVED_WORDS = 
		Collections.unmodifiableSortedSet(CollectionTools.treeSet(JAVA_RESERVED_WORDS_ARRAY));

	/**
	 * Return whether the specified string consists of Java identifier
	 * characters (but may be a reserved word).
	 */
	public static boolean consistsOfJavaIdentifierCharacters(String string) {
		int len = string.length();
		return (len != 0) &&
				consistsOfJavaIdentifierCharacters(string, len);
	}

	/**
	 * Pre-condition: the specified string is not empty.
	 */
	private static boolean consistsOfJavaIdentifierCharacters(String string, int len) {
		if ( ! Character.isJavaIdentifierStart(string.charAt(0))) {
			return false;
		}
		for (int i = len; i-- > 1; ) {  // NB: end with 1
			if ( ! Character.isJavaIdentifierPart(string.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @see #consistsOfJavaIdentifierCharacters(String)
	 */
	public static boolean consistsOfJavaIdentifierCharacters(char[] string) {
		int len = string.length;
		return (len != 0) &&
				consistsOfJavaIdentifierCharacters(string, len);
	}

	/**
	 * Pre-condition: the specified string is not empty.
	 */
	private static boolean consistsOfJavaIdentifierCharacters(char[] string, int len) {
		if ( ! Character.isJavaIdentifierStart(string[0])) {
			return false;
		}
		for (int i = len; i-- > 1; ) {  // NB: end with 1
			if ( ! Character.isJavaIdentifierPart(string[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return whether the specified string is a valid Java identifier.
	 */
	public static boolean isLegalJavaIdentifier(String string) {
		return consistsOfJavaIdentifierCharacters(string)
				&& ! JAVA_RESERVED_WORDS.contains(string);
	}

	/**
	 * @see #isLegalJavaIdentifier(String)
	 */
	public static boolean isLegalJavaIdentifier(char[] string) {
		return consistsOfJavaIdentifierCharacters(string)
				&& ! JAVA_RESERVED_WORDS.contains(new String(string));
	}

	/**
	 * Convert the specified string to a valid Java identifier
	 * by substituting an underscore (<code>'_'</code>) for any invalid characters
	 * in the string and appending an underscore (<code>'_'</code>) to the string if
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
		if (JAVA_RESERVED_WORDS.contains(string)) {
			// a reserved word is a valid identifier, we just need to tweak it a bit
			checkCharIsJavaIdentifierPart(c);
			return convertToJavaIdentifier(string + c, c);
		}
		char[] array = string.toCharArray();
		return convertToJavaIdentifier_(array, c) ? new String(array) : string;
	}

	/**
	 * @see #convertToJavaIdentifier(String)
	 */
	public static char[] convertToJavaIdentifier(char[] string) {
		return convertToJavaIdentifier(string, '_');
	}

	/**
	 * @see #convertToJavaIdentifier(String, char)
	 */
	public static char[] convertToJavaIdentifier(char[] string, char c) {
		if (string.length == 0) {
			return string;
		}
		if (JAVA_RESERVED_WORDS.contains(new String(string))) {
			// a reserved word is a valid identifier, we just need to tweak it a bit
			checkCharIsJavaIdentifierPart(c);
			return convertToJavaIdentifier(ArrayTools.add(string, c), c);
		}
		char[] copy = string.clone();
		return convertToJavaIdentifier_(copy, c) ? copy : string;
	}

	/**
	 * Pre-condition: The specified string is not empty.
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
	 * @see Introspector#decapitalize(String)
	 */
	@SuppressWarnings("nls")
	public static String convertGetterOrSetterMethodNameToPropertyName(String methodName) {
		int beginIndex = 0;
		int len = methodName.length();
		if (methodName.startsWith("get") && (len > 3)) {
			beginIndex = 3;
		} else if (methodName.startsWith("set") && (len > 3)) {
			beginIndex = 3;
		} else if (methodName.startsWith("is") && (len > 2)) {
			beginIndex = 2;
		} else {
			return methodName;  // return method name unchanged?
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
