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

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;

/**
 * Convenience methods related to dealing with Java source code level
 * type declarations (i.e. text descriptions of Java types as they appear
 * in Java source code as opposed to the value returned by
 * {@link java.lang.Class#getName()}).
 */
public final class TypeDeclarationTools {

	// ********** is array **********

	/**
	 * Return whether the specified type declaration is an array type; e.g.<ul>
	 * <li><code>"int"</code> returns <code>false</code>
	 * <li><code>"int[]"</code> returns <code>true</code>
	 * <li><code>"java.lang.String"</code> returns <code>false</code>
	 * <li><code>"java.lang.String[][][]"</code> returns <code>true</code>
	 * </ul>
	 */
	public static boolean isArray(String typeDeclaration) {
		return isArray_(StringTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static boolean isArray_(String typeDeclaration) {
		return StringTools.last(typeDeclaration) == ']';
	}

	/**
	 * @see #isArray(String)
	 */
	public static boolean isArray(char[] typeDeclaration) {
		return isArray_(CharArrayTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static boolean isArray_(char[] typeDeclaration) {
		return CharArrayTools.last(typeDeclaration) == ']';
	}


	// ********** array depth **********

	/**
	 * Return the array depth for the specified type declaration; e.g.<ul>
	 * <li><code>"int[]"</code> returns <code>1</code>
	 * <li><code>"java.lang.String[][][]"</code> returns <code>3</code>
	 * </ul>
	 */
	public static int arrayDepth(String typeDeclaration) {
		return arrayDepth_(StringTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	static int arrayDepth_(String typeDeclaration) {
		int last = typeDeclaration.length() - 1;
		int depth = 0;
		int close = last;
		while (typeDeclaration.charAt(close) == ']') {
			if (typeDeclaration.charAt(close - 1) == '[') {
				depth++;
			} else {
				throw new IllegalArgumentException("invalid type declaration: " + typeDeclaration); //$NON-NLS-1$
			}
			close = last - (depth << 1);
		}
		return depth;
	}

	/**
	 * @see #arrayDepth(String)
	 */
	public static int arrayDepth(char[] typeDeclaration) {
		return arrayDepth_(CharArrayTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	static int arrayDepth_(char[] typeDeclaration) {
		int last = typeDeclaration.length - 1;
		int depth = 0;
		int close = last;
		while (typeDeclaration[close] == ']') {
			if (typeDeclaration[close - 1] == '[') {
				depth++;
			} else {
				throw new IllegalArgumentException("invalid type declaration: " + String.copyValueOf(typeDeclaration)); //$NON-NLS-1$
			}
			close = last - (depth << 1);
		}
		return depth;
	}


	// ********** element type name **********

	/**
	 * Return the element type name for the specified type declaration; e.g.<ul>
	 * <li><code>"int[]"</code> returns <code>"int"</code>
	 * <li><code>"java.lang.String[][][]"</code> returns <code>"java.lang.String"</code>
	 * </ul>
	 */
	public static String elementTypeName(String typeDeclaration) {
		typeDeclaration = StringTools.removeAllWhitespace(typeDeclaration);
		return elementTypeName_(typeDeclaration, arrayDepth_(typeDeclaration));
	}

	/**
	 * @see #elementTypeName(String)
	 */
	public static char[] elementTypeName(char[] typeDeclaration) {
		typeDeclaration = CharArrayTools.removeAllWhitespace(typeDeclaration);
		return elementTypeName_(typeDeclaration, arrayDepth_(typeDeclaration));
	}

	/**
	 * Return the element type name for the specified type declaration; e.g.<ul>
	 * <li><code>"int[]"</code> returns <code>"int"</code>
	 * <li><code>"java.lang.String[][][]"</code> returns <code>"java.lang.String"</code>
	 * </ul>
	 * Useful for clients that have already queried the type declaration's array depth.
	 */
	public static String elementTypeName(String typeDeclaration, int arrayDepth) {
		return elementTypeName_(StringTools.removeAllWhitespace(typeDeclaration), arrayDepth);
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	static String elementTypeName_(String typeDeclaration, int arrayDepth) {
		return typeDeclaration.substring(0, typeDeclaration.length() - (arrayDepth << 1));
	}

	/**
	 * @see #elementTypeName(String, int)
	 */
	public static char[] elementTypeName(char[] typeDeclaration, int arrayDepth) {
		return elementTypeName_(CharArrayTools.removeAllWhitespace(typeDeclaration), arrayDepth);
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	static char[] elementTypeName_(char[] typeDeclaration, int arrayDepth) {
		return ArrayTools.subArray(typeDeclaration, 0, typeDeclaration.length - (arrayDepth << 1));
	}


	// ********** component type declaration **********

	/**
	 * Return the specified type declaration's component type.
	 * Return <code>null</code> if the specified type declaration is not an array type.
	 */
	public static String componentTypeDeclaration(String typeDeclaration) {
		return componentTypeDeclaration_(StringTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static String componentTypeDeclaration_(String typeDeclaration) {
		int arrayDepth = arrayDepth_(typeDeclaration);
		return (arrayDepth == 0) ? null : elementTypeName_(typeDeclaration, 1);
	}

	/**
	 * @see #componentTypeDeclaration(String)
	 */
	public static char[] componentTypeDeclaration(char[] typeDeclaration) {
		return componentTypeDeclaration_(CharArrayTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static char[] componentTypeDeclaration_(char[] typeDeclaration) {
		int arrayDepth = arrayDepth_(typeDeclaration);
		return (arrayDepth == 0) ? null : elementTypeName_(typeDeclaration, 1);
	}


	// ********** class name **********

	/**
	 * Return the class name for the specified type declaration; e.g.<ul>
	 * <li><code>"int"</code> returns <code>"int"</code>
	 * <li><code>"int[]"</code> returns <code>"[I"</code>
	 * <li><code>"java.lang.String"</code> returns <code>"java.lang.String"</code>
	 * <li><code>"java.lang.String[][][]"</code> returns <code>"[[[Ljava.lang.String;"</code>
	 * </ul>
	 * @see java.lang.Class#getName()
	 */
	public static String className(String typeDeclaration) {
		return className_(StringTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static String className_(String typeDeclaration) {
		int arrayDepth = arrayDepth_(typeDeclaration);
		String elementTypeName = elementTypeName_(typeDeclaration, arrayDepth);
		return className(elementTypeName, arrayDepth);
	}

	/**
	 * @see #className(String)
	 */
	public static char[] className(char[] typeDeclaration) {
		return className_(CharArrayTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static char[] className_(char[] typeDeclaration) {
		int arrayDepth = arrayDepth_(typeDeclaration);
		char[] elementTypeName = elementTypeName_(typeDeclaration, arrayDepth);
		return className(elementTypeName, arrayDepth);
	}

	/**
	 * Return the class name for the specified type declaration.
	 * @see java.lang.Class#getName()
	 */
	public static String className(String elementTypeName, int arrayDepth) {
		// non-array
		if (arrayDepth == 0) {
			return elementTypeName;
		}

		if (elementTypeName.equals(ClassNameTools.VOID)) {
			throw new IllegalArgumentException('\'' + ClassNameTools.VOID + "' must have an array depth of zero: " + arrayDepth + '.'); //$NON-NLS-1$
		}
		// array
		StringBuilder sb = new StringBuilder(100);
		for (int i = arrayDepth; i-- > 0; ) {
			sb.append('[');
		}

		// look for a primitive first
		char prim = ClassNameTools.primitiveClassCode(elementTypeName);
		if (prim == 0) {
			ClassNameTools.appendReferenceNameTo(elementTypeName, sb);
		} else {
			sb.append(prim);
		}

		return sb.toString();
	}

	/**
	 * @see #className(String, int)
	 */
	public static char[] className(char[] elementTypeName, int arrayDepth) {
		// non-array
		if (arrayDepth == 0) {
			return elementTypeName;
		}

		if (Arrays.equals(elementTypeName, ClassNameTools.VOID_CHAR_ARRAY)) {
			throw new IllegalArgumentException('\'' + ClassNameTools.VOID + "' must have an array depth of zero: " + arrayDepth + '.'); //$NON-NLS-1$
		}
		// array
		StringBuilder sb = new StringBuilder(100);
		for (int i = arrayDepth; i-- > 0; ) {
			sb.append('[');
		}

		// look for a primitive first
		char prim = ClassNameTools.primitiveClassCode(elementTypeName);
		if (prim == 0) {
			ClassNameTools.appendReferenceNameTo(elementTypeName, sb);
		} else {
			sb.append(prim);
		}

		return StringBuilderTools.convertToCharArray(sb);
	}


	// ********** package/simple name **********

	/**
	 * Return the specified type declaration's simple name.
	 */
	public static String simpleName(String typeDeclaration) {
		return simpleName_(StringTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static String simpleName_(String typeDeclaration) {
		return typeDeclaration.substring(typeDeclaration.lastIndexOf('.') + 1);
	}

	/**
	 * @see #simpleName(String)
	 */
	public static char[] simpleName(char[] typeDeclaration) {
		return simpleName_(CharArrayTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static char[] simpleName_(char[] typeDeclaration) {
		return ArrayTools.subArray(typeDeclaration, ArrayTools.lastIndexOf(typeDeclaration, '.') + 1);
	}

	/**
	 * Return the specified type declaration's package name (e.g.
	 * <code>"java.lang.Object"</code> returns
	 * <code>"java.lang"</code>).
	 * Return an empty string if the specified class is:<ul>
	 * <li>in the "default" package
	 * <li>an array class
	 * <li>a primtive class
	 * </ul>
	 */
	public static String packageName(String typeDeclaration) {
		return packageName_(StringTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static String packageName_(String typeDeclaration) {
		if (isArray_(typeDeclaration)) {
			return StringTools.EMPTY_STRING;
		}
		int lastPeriod = typeDeclaration.lastIndexOf('.');
		return (lastPeriod == -1) ? StringTools.EMPTY_STRING : typeDeclaration.substring(0, lastPeriod);
	}

	/**
	 * @see #packageName(String)
	 */
	public static char[] packageName(char[] typeDeclaration) {
		return packageName_(CharArrayTools.removeAllWhitespace(typeDeclaration));
	}

	/**
	 * Pre-condition: no whitespace in the type declaration.
	 */
	private static char[] packageName_(char[] typeDeclaration) {
		if (isArray_(typeDeclaration)) {
			return CharArrayTools.EMPTY_CHAR_ARRAY;
		}
		int lastPeriod = ArrayTools.lastIndexOf(typeDeclaration, '.');
		return (lastPeriod == -1) ? CharArrayTools.EMPTY_CHAR_ARRAY : ArrayTools.subArray(typeDeclaration, 0, lastPeriod);
	}


	// ********** java.lang classes **********

	/**
	 * Return whether the specified "simple" class name is a public class in the
	 * current release of the <code>java.lang</code> package (i.e. a class that
	 * can be declared with a "simple" name and no corresponding
	 * <code>import</code> statement).
	 * <p>
	 * The current release is jdk 1.8.
	 */
	public static boolean isJavaLangClass(String simpleClassName) {
		return ArrayTools.binarySearch(JAVA_LANG_CLASS_NAMES_ARRAY, simpleClassName);
	}

	/**
	 * @see #isJavaLangClass(String)
	 */
	public static boolean isJavaLangClass(char[] simpleClassName) {
		return isJavaLangClass(String.copyValueOf(simpleClassName));
	}

	/**
	 * JDK 1.5
	 * @see #isJavaLangClass(String)
	 */
	public static boolean isJavaLangClass5(String simpleClassName) {
		return ArrayTools.binarySearch(JAVA_LANG_CLASS_NAMES_ARRAY_5, simpleClassName);
	}

	/**
	 * @see #isJavaLangClass5(String)
	 */
	public static boolean isJavaLangClass5(char[] simpleClassName) {
		return isJavaLangClass5(String.copyValueOf(simpleClassName));
	}

	// JDK 1.5
	@SuppressWarnings("nls")
	private static final String[] JAVA_LANG_CLASS_NAMES_ARRAY_5 = ArrayTools.sort(new String[] {
		"AbstractMethodError",
		"Appendable",
		"ArithmeticException",
		"ArrayIndexOutOfBoundsException",
		"ArrayStoreException",
		"AssertionError",
		"Boolean",
		"Byte",
		"Character",
		"Character.Subset",
		"Character.UnicodeBlock",
		"CharSequence",
		"Class",
		"ClassCastException",
		"ClassCircularityError",
		"ClassFormatError",
		"ClassLoader",
		"ClassNotFoundException",
		"Cloneable",
		"CloneNotSupportedException",
		"Comparable",
		"Compiler",
		"Deprecated",
		"Double",
		"Enum",
		"EnumConstantNotPresentException",
		"Error",
		"Exception",
		"ExceptionInInitializerError",
		"Float",
		"IllegalAccessError",
		"IllegalAccessException",
		"IllegalArgumentException",
		"IllegalMonitorStateException",
		"IllegalStateException",
		"IllegalThreadStateException",
		"IncompatibleClassChangeError",
		"IndexOutOfBoundsException",
		"InheritableThreadLocal",
		"InstantiationError",
		"InstantiationException",
		"Integer",
		"InternalError",
		"InterruptedException",
		"Iterable",
		"LinkageError",
		"Long",
		"Math",
		"NegativeArraySizeException",
		"NoClassDefFoundError",
		"NoSuchFieldError",
		"NoSuchFieldException",
		"NoSuchMethodError",
		"NoSuchMethodException",
		"NullPointerException",
		"Number",
		"NumberFormatException",
		"Object",
		"OutOfMemoryError",
		"Override",
		"Package",
		"Process",
		"ProcessBuilder",
		"Readable",
		"Runnable",
		"Runtime",
		"RuntimeException",
		"RuntimePermission",
		"SecurityException",
		"SecurityManager",
		"Short",
		"StackOverflowError",
		"StackTraceElement",
		"StrictMath",
		"String",
		"StringBuffer",
		"StringBuilder",
		"StringIndexOutOfBoundsException",
		"SuppressWarnings",
		"System",
		"Thread",
		"Thread.State",
		"Thread.UncaughtExceptionHandler",
		"ThreadDeath",
		"ThreadGroup",
		"ThreadLocal",
		"Throwable",
		"TypeNotPresentException",
		"UnknownError",
		"UnsatisfiedLinkError",
		"UnsupportedClassVersionError",
		"UnsupportedOperationException",
		"VerifyError",
		"VirtualMachineError",
		"Void",
	});

	/**
	 * JDK 1.5.
	 */
	public static final Iterable<String> JAVA_LANG_CLASS_NAMES_5 = IterableTools.iterable(JAVA_LANG_CLASS_NAMES_ARRAY_5);

	/**
	 * JDK 1.6
	 * @see #isJavaLangClass(String)
	 */
	public static boolean isJavaLangClass6(String simpleClassName) {
		return ArrayTools.binarySearch(JAVA_LANG_CLASS_NAMES_ARRAY_6, simpleClassName);
	}

	/**
	 * @see #isJavaLangClass6(String)
	 */
	public static boolean isJavaLangClass6(char[] simpleClassName) {
		return isJavaLangClass6(String.copyValueOf(simpleClassName));
	}

	// JDK 1.6 - no changes from jdk 1.5
	private static final String[] JAVA_LANG_CLASS_NAMES_ARRAY_6 = JAVA_LANG_CLASS_NAMES_ARRAY_5;

	/**
	 * JDK 1.6.
	 */
	public static final Iterable<String> JAVA_LANG_CLASS_NAMES_6 = IterableTools.iterable(JAVA_LANG_CLASS_NAMES_ARRAY_6);

	/**
	 * JDK 1.7
	 * @see #isJavaLangClass(String)
	 */
	public static boolean isJavaLangClass7(String simpleClassName) {
		return ArrayTools.binarySearch(JAVA_LANG_CLASS_NAMES_ARRAY_7, simpleClassName);
	}

	/**
	 * @see #isJavaLangClass7(String)
	 */
	public static boolean isJavaLangClass7(char[] simpleClassName) {
		return isJavaLangClass7(String.copyValueOf(simpleClassName));
	}

	// jdk 1.7
	@SuppressWarnings("nls")
	private static final String[] JAVA_LANG_CLASS_NAMES_ARRAY_7 = ArrayTools.sort(
		ArrayTools.concatenate(
			JAVA_LANG_CLASS_NAMES_ARRAY_6,
			new String[] {
				"AutoCloseable",
				"BootstrapMethodError",
				"Character.UnicodeScript",
				"ClassValue",
				"ProcessBuilder.Redirect",
				"ProcessBuilder.Redirect.Type",
				"ReflectiveOperationException",
				"SafeVarargs"
			}
		)
	);

	/**
	 * JDK 1.7.
	 */
	public static final Iterable<String> JAVA_LANG_CLASS_NAMES_7 = IterableTools.iterable(JAVA_LANG_CLASS_NAMES_ARRAY_7);

	/**
	 * JDK 1.8
	 * @see #isJavaLangClass(String)
	 */
	public static boolean isJavaLangClass8(String simpleClassName) {
		return ArrayTools.binarySearch(JAVA_LANG_CLASS_NAMES_ARRAY_8, simpleClassName);
	}

	/**
	 * @see #isJavaLangClass8(String)
	 */
	public static boolean isJavaLangClass8(char[] simpleClassName) {
		return isJavaLangClass8(String.copyValueOf(simpleClassName));
	}

	// jdk 1.7
	@SuppressWarnings("nls")
	private static final String[] JAVA_LANG_CLASS_NAMES_ARRAY_8 = ArrayTools.sort(
		ArrayTools.concatenate(
			JAVA_LANG_CLASS_NAMES_ARRAY_7,
			new String[] {
				"FunctionalInterface"
			}
		)
	);

	/**
	 * JDK 1.8.
	 */
	public static final Iterable<String> JAVA_LANG_CLASS_NAMES_8 = IterableTools.iterable(JAVA_LANG_CLASS_NAMES_ARRAY_8);

	// the current release is jdk 1.8
	private static final String[] JAVA_LANG_CLASS_NAMES_ARRAY = JAVA_LANG_CLASS_NAMES_ARRAY_8;

	/**
	 * The current release is jdk 1.8.
	 */
	public static final Iterable<String> JAVA_LANG_CLASS_NAMES = IterableTools.iterable(JAVA_LANG_CLASS_NAMES_ARRAY);


	// ********** load class **********

	/**
	 * Return the class for the specified type declaration.
	 */
	public static Class<?> loadClass(String typeDeclaration) {
		return ClassTools.forTypeDeclaration(typeDeclaration);
	}

	/**
	 * Return the class for the specified type declaration.
	 */
	public static Class<?> loadClass(String typeDeclaration, ClassLoader classLoader) {
		return ClassTools.forTypeDeclaration(typeDeclaration, classLoader);
	}

	/**
	 * Return the class for the specified type declaration.
	 */
	public static Class<?> loadClass_(String typeDeclaration) throws ClassNotFoundException {
		return ClassTools.forTypeDeclaration_(typeDeclaration);
	}

	/**
	 * Return the class for the specified type declaration.
	 */
	public static Class<?> loadClass_(String typeDeclaration, ClassLoader classLoader) throws ClassNotFoundException {
		return ClassTools.forTypeDeclaration_(typeDeclaration, classLoader);
	}

	/**
	 * Return the class for the specified type declaration.
	 */
	public static Class<?> loadClass(char[] typeDeclaration) {
		return ClassTools.forTypeDeclaration(typeDeclaration);
	}

	/**
	 * Return the class for the specified type declaration.
	 */
	public static Class<?> loadClass(char[] typeDeclaration, ClassLoader classLoader) {
		return ClassTools.forTypeDeclaration(typeDeclaration, classLoader);
	}

	/**
	 * Return the class for the specified type declaration.
	 */
	public static Class<?> loadClass_(char[] typeDeclaration) throws ClassNotFoundException {
		return ClassTools.forTypeDeclaration_(typeDeclaration);
	}

	/**
	 * Return the class for the specified type declaration.
	 */
	public static Class<?> loadClass_(char[] typeDeclaration, ClassLoader classLoader) throws ClassNotFoundException {
		return ClassTools.forTypeDeclaration_(typeDeclaration, classLoader);
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private TypeDeclarationTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
