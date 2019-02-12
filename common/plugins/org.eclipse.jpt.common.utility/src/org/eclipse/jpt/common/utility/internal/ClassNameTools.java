/*******************************************************************************
 * Copyright (c) 2005, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.util.Arrays;

/**
 * Convenience methods related to Java class names as returned by
 * {@link java.lang.Class#getName()}.
 */
public final class ClassNameTools {

	public static final String VOID = ClassTools.VOID.getName();
	public static final char[] VOID_CHAR_ARRAY = VOID.toCharArray();
	public static final String VOID_WRAPPER = ClassTools.VOID_WRAPPER.getName();
	public static final char[] VOID_WRAPPER_CHAR_ARRAY = VOID_WRAPPER.toCharArray();

	public static final char REFERENCE_CLASS_CODE = 'L';
	public static final char REFERENCE_CLASS_NAME_DELIMITER = ';';

	public static final String BRACKETS = "[]"; //$NON-NLS-1$
	public static final char[] BRACKETS_CHAR_ARRAY = BRACKETS.toCharArray();


	// ********** is array **********

	/**
	 * Return whether the specified class is an array type.
	 * @see java.lang.Class#getName()
	 */
	public static boolean isArray(String className) {
		return className.charAt(0) == '[';
	}

	/**
	 * @see #isArray(String)
	 */
	public static boolean isArray(char[] className) {
		return className[0] == '[';
	}


	// ********** array depth **********

	/**
	 * Return the "array depth" of the specified class.
	 * The depth is the number of dimensions for an array type.
	 * Non-array types have a depth of zero.
	 * @see java.lang.Class#getName()
	 */
	public static int arrayDepth(String className) {
		int depth = 0;
		while (className.charAt(depth) == '[') {
			depth++;
		}
		return depth;
	}

	/**
	 * @see #arrayDepth(String)
	 */
	public static int arrayDepth(char[] className) {
		int depth = 0;
		while (className[depth] == '[') {
			depth++;
		}
		return depth;
	}


	// ********** element type name **********

	/**
	 * Return the "element type" of the specified class.
	 * The element type is the base type held by an array type.
	 * Non-array types simply return themselves.
	 * @see java.lang.Class#getName()
	 */
	public static String elementTypeName(String className) {
		int depth = arrayDepth(className);
		return (depth == 0) ?
				className :  // the name is in the form: "java.lang.Object" or "I"
				elementTypeName_(className, depth);
	}

	/**
	 * Pre-condition: array depth is not zero
	 */
	private static String elementTypeName_(String className, int arrayDepth) {
		int last = className.length() - 1;
		if (className.charAt(arrayDepth) == REFERENCE_CLASS_CODE) {
			// the name is in the form: "[[[Ljava.lang.Object;"
			return className.substring(arrayDepth + 1, last);	// drop the trailing ';'
		}
		// the name is in the form: "[[[I"
		return forCode(className.charAt(last));
	}

	/**
	 * @see #elementTypeName(String)
	 */
	public static char[] elementTypeName(char[] className) {
		int depth = arrayDepth(className);
		return (depth == 0) ?
				className :  // the name is in the form: "java.lang.Object" or "I"
				elementTypeName_(className, depth);
	}

	/**
	 * Pre-condition: array depth is not zero
	 */
	private static char[] elementTypeName_(char[] className, int arrayDepth) {
		int last = className.length - 1;
		if (className[arrayDepth] == REFERENCE_CLASS_CODE) {
			// the name is in the form: "[[[Ljava.lang.Object;"
			return ArrayTools.subArray(className, arrayDepth + 1, last);	// drop the trailing ';'
		}
		// the name is in the form: "[[[I"
		return forCodeCharArray(className[last]);
	}


	// ********** component type name **********

	/**
	 * Return the specified class's component type.
	 * Return <code>null</code> if the specified class is not an array type.
	 * @see java.lang.Class#getName()
	 */
	public static String componentTypeName(String className) {
		switch (arrayDepth(className)) {
			case 0:
				return null;
			case 1:
				return elementTypeName_(className, 1);
			default:
				return className.substring(1);
		}
	}

	/**
	 * @see #componentTypeName(String)
	 */
	public static char[] componentTypeName(char[] className) {
		switch (arrayDepth(className)) {
			case 0:
				return null;
			case 1:
				return elementTypeName_(className, 1);
			default:
				return ArrayTools.subArray(className, 1, className.length);
		}
	}


	// ********** type declaration **********

	/**
	 * Return the type declaration for the specified class name; e.g.<ul>
	 * <li><code>"int"</code> returns <code>"int"</code>
	 * <li><code>"[I"</code> returns <code>"int[]"</code>
	 * <li><code>"java.lang.String"</code> returns <code>"java.lang.String"</code>
	 * <li><code>"[[[Ljava.lang.String;"</code> returns <code>"java.lang.String[][][]"</code>
	 * </ul>
	 * @see java.lang.Class#getName()
	 */
	public static String typeDeclaration(String className) {
		int arrayDepth = arrayDepth(className);
		return (arrayDepth == 0) ? className : typeDeclaration_(className, arrayDepth);
	}

	/**
	 * Pre-condition: array depth is not zero
	 */
	private static String typeDeclaration_(String className, int arrayDepth) {
		String elementTypeName = elementTypeName_(className, arrayDepth);
		StringBuilder sb = new StringBuilder(elementTypeName.length() + (arrayDepth << 1));
		sb.append(elementTypeName);
		for (int i = 0; i < arrayDepth; i++) {
			sb.append(BRACKETS);
		}
		return sb.toString();
	}

	/**
	 * @see #typeDeclaration(String)
	 */
	public static char[] typeDeclaration(char[] className) {
		int arrayDepth = arrayDepth(className);
		return (arrayDepth == 0) ? className : typeDeclaration_(className, arrayDepth);
	}

	/**
	 * Pre-condition: array depth is not zero
	 */
	private static char[] typeDeclaration_(char[] className, int arrayDepth) {
		char[] elementTypeName = elementTypeName_(className, arrayDepth);
		StringBuilder sb = new StringBuilder(elementTypeName.length + (arrayDepth << 1));
		sb.append(elementTypeName);
		for (int i = 0; i < arrayDepth; i++) {
			sb.append(BRACKETS);
		}
		return StringBuilderTools.convertToCharArray(sb);
	}


	// ********** package/simple name **********

	/**
	 * Return the specified class's simple name.
	 * Return an empty string if the specified class is anonymous.
	 * <p>
	 * The simple name of an array type is the simple name of the
	 * component type with <code>"[]"</code> appended. In particular,
	 * the simple name of an array type whose component type is
	 * anonymous is simply <code>"[]"</code>.
	 * @see java.lang.Class#getSimpleName()
	 */
	public static String simpleName(String className) {
		return isArray(className) ?
				simpleName(componentTypeName(className)) + BRACKETS : // recurse
				simpleName_(className);
	}

	/**
	 * Pre-condition: specified class is not an array type
	 */
	private static String simpleName_(String className) {
		int index = className.lastIndexOf('$');
		if (index == -1) {  // "top-level" class - strip package name
			return className.substring(className.lastIndexOf('.') + 1);
		}

		int len = className.length();
		for (int i = ++index; i < len; i++) {
			if ( ! charIsAsciiDigit(className.charAt(i))) {
				return className.substring(i);  // "member" or "local" class
			}
		}
		// all the characters past the '$' are ASCII digits ("anonymous" class)
		return StringTools.EMPTY_STRING;
	}

	/**
	 * @see #simpleName(String)
	 */
	public static char[] simpleName(char[] className) {
		return isArray(className) ?
				ArrayTools.addAll(simpleName(componentTypeName(className)), BRACKETS_CHAR_ARRAY) : // recurse
				simpleName_(className);
	}

	/**
	 * Pre-condition: specified class is not an array type
	 */
	private static char[] simpleName_(char[] className) {
		int index = ArrayTools.lastIndexOf(className, '$');
		if (index == -1) {  // "top-level" class - strip package name
			return ArrayTools.subArray(className, (ArrayTools.lastIndexOf(className, '.') + 1), className.length);
		}

		int len = className.length;
		for (int i = ++index; i < len; i++) {
			if ( ! charIsAsciiDigit(className[i])) {
				return ArrayTools.subArray(className, i, className.length);  // "member" or "local" class
			}
		}
		// all the characters past the '$' are ASCII digits ("anonymous" class)
		return CharArrayTools.EMPTY_CHAR_ARRAY;
	}

	/**
	 * Return the specified class's package name (e.g.
	 * <code>"java.lang.Object"</code> returns
	 * <code>"java.lang"</code>).
	 * Return an empty string if the specified class is:<ul>
	 * <li>in the "default" package
	 * <li>an array class
	 * <li>a primtive class
	 * </ul>
	 * @see java.lang.Class#getPackage()
	 * @see java.lang.Package#getName()
	 */
	public static String packageName(String className) {
		if (isArray(className)) {
			return StringTools.EMPTY_STRING;
		}
		int lastPeriod = className.lastIndexOf('.');
		return (lastPeriod == -1) ? StringTools.EMPTY_STRING : className.substring(0, lastPeriod);
	}

	/**
	 * @see #packageName(String)
	 */
	public static char[] packageName(char[] className) {
		if (isArray(className)) {
			return CharArrayTools.EMPTY_CHAR_ARRAY;
		}
		int lastPeriod = ArrayTools.lastIndexOf(className, '.');
		return (lastPeriod == -1) ?
				CharArrayTools.EMPTY_CHAR_ARRAY :
				ArrayTools.subArray(className, 0, lastPeriod);
	}


	// ********** top-level/member/local/anonymous **********

	/**
	 * Return whether the specified class is a "top-level" class,
	 * as opposed to a "member", "local", or "anonymous" class,
	 * using the standard JDK naming conventions (i.e. the class
	 * name does NOT contain a <code>'$'</code>).
	 * A "top-level" class can be either the "primary" (public) class defined
	 * in a file/compilation unit (i.e. the class with the same name as its
	 * file's simple base name) or a "non-primary" (package visible) class
	 * (i.e. the other top-level classes defined in a file).
	 * A "top-level" class can contain any of the other types of classes. 
	 * @see java.lang.Class#getName()
	 */
	public static boolean isTopLevel(String className) {
		if (isArray(className)) {
			return false;
		}
		return className.lastIndexOf('$') == -1;
	}

	/**
	 * @see #isTopLevel(String)
	 */
	public static boolean isTopLevel(char[] className) {
		if (isArray(className)) {
			return false;
		}
		return ArrayTools.lastIndexOf(className, '$') == -1;
	}

	/**
	 * Return whether the specified class is a "member" class,
	 * as opposed to a "top-level", "local", or "anonymous" class,
	 * using the standard JDK naming convention (i.e. the class
	 * name ends with a <code>'$'</code> followed by a legal class name; e.g.
	 * <code>"TopLevelClass$1LocalClass$MemberClass"</code>).
	 * A "member" class can be either "nested" (static) or "inner";
	 * but there is no way to determine which from the class's name.
	 * A "member" class can contain "local", "anonymous", or other
	 * "member" classes; and vice-versa.
	 * @see java.lang.Class#getName()
	 */
	public static boolean isMember(String className) {
		if (isArray(className)) {
			return false;
		}
		int index = className.lastIndexOf('$');
		if (index == -1) {
			return false;	// "top-level" class
		}
		// the character immediately after the dollar sign cannot be an ASCII digit
		return ! charIsAsciiDigit(className.charAt(++index));
	}

	/**
	 * @see #isMember(String)
	 */
	public static boolean isMember(char[] className) {
		if (isArray(className)) {
			return false;
		}
		int index = ArrayTools.lastIndexOf(className, '$');
		if (index == -1) {
			return false;	// "top-level" class
		}
		// the character immediately after the dollar sign cannot be an ASCII digit
		return ! charIsAsciiDigit(className[++index]);
	}

	/**
	 * Return whether the specified class is a "local" class,
	 * as opposed to a "top-level", "member", or "anonymous" class,
	 * using the standard JDK naming convention (i.e. the class name
	 * ends with <code>"$nnnXXX"</code>,
	 * where the <code>'$'</code> is
	 * followed by a series of numeric digits which are followed by the
	 * local class name; e.g. <code>"TopLevelClass$1LocalClass"</code>).
	 * A "local" class can contain "member", "anonymous", or other
	 * "local" classes; and vice-versa.
	 * @see java.lang.Class#getName()
	 */
	public static boolean isLocal(String className) {
		if (isArray(className)) {
			return false;
		}
		int index = className.lastIndexOf('$');
		if (index == -1) {
			return false;	// "top-level" class
		}
		if ( ! charIsAsciiDigit(className.charAt(++index))) {
			return false;  // "member" class
		}
		int len = className.length();
		for (int i = ++index; i < len; i++) {
			if ( ! charIsAsciiDigit(className.charAt(i))) {
				return true;
			}
		}
		// all the characters past the '$' are ASCII digits ("anonymous" class)
		return false;
	}

	/**
	 * @see #isLocal(String)
	 */
	public static boolean isLocal(char[] className) {
		if (isArray(className)) {
			return false;
		}
		int index = ArrayTools.lastIndexOf(className, '$');
		if (index == -1) {
			return false;	// "top-level" class
		}
		if ( ! charIsAsciiDigit(className[++index])) {
			return false;  // "member" class
		}
		int len = className.length;
		for (int i = ++index; i < len; i++) {
			if ( ! charIsAsciiDigit(className[i])) {
				return true;
			}
		}
		// all the characters past the '$' are ASCII digits ("anonymous" class)
		return false;
	}

	/**
	 * Return whether the specified class is an "anonymous" class,
	 * as opposed to a "top-level", "member", or "local" class,
	 * using the standard JDK naming convention (i.e. the class
	 * name ends with <code>"$nnn"</code> where all the characters past the
	 * last <code>'$'</code> are ASCII numeric digits;
	 * e.g. <code>"TopLevelClass$1"</code>).
	 * An "anonymous" class can contain "member", "local", or other
	 * "anonymous" classes; and vice-versa.
	 * @see java.lang.Class#getName()
	 */
	public static boolean isAnonymous(String className) {
		if (isArray(className)) {
			return false;
		}
		int index = className.lastIndexOf('$');
		if (index == -1) {
			return false;	// "top-level" class
		}
		if ( ! charIsAsciiDigit(className.charAt(++index))) {
			return false;  // "member" class
		}
		int len = className.length();
		for (int i = ++index; i < len; i++) {
			if ( ! charIsAsciiDigit(className.charAt(i))) {
				return false;  // "local" class
			}
		}
		// all the characters past the '$' are ASCII digits ("anonymous" class)
		return true;
	}

	/**
	 * @see #isAnonymous(String)
	 */
	public static boolean isAnonymous(char[] className) {
		if (isArray(className)) {
			return false;
		}
		int index = ArrayTools.lastIndexOf(className, '$');
		if (index == -1) {
			return false;	// "top-level" class
		}
		if ( ! charIsAsciiDigit(className[++index])) {
			return false;  // "member" class
		}
		int len = className.length;
		for (int i = ++index; i < len; i++) {
			if ( ! charIsAsciiDigit(className[i])) {
				return false;  // "local" class
			}
		}
		// all the characters past the '$' are ASCII digits ("anonymous" class)
		return true;
	}

	/**
	 * {@link Character#isDigit(char)} returns <code>true</code> for some non-ASCII
	 * digits. This method does not.
	 */
	private static boolean charIsAsciiDigit(char c) {
		return ('0' <= c) && (c <= '9');
	}


	// ********** reference/primitive **********

	/**
	 * Return whether the specified class is a "reference"
	 * class (i.e. neither <code>void</code> nor one of the primitive variable classes,
	 * <code>boolean</code>, <code>int</code>, <code>float</code>, etc.).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	public static boolean isReference(String className) {
		return ! isPrimitive(className);
	}

	/**
	 * @see #isReference(String)
	 */
	public static boolean isReference(char[] className) {
		return ! isPrimitive(className);
	}

	/**
	 * Return whether the specified class is a primitive
	 * class (i.e. <code>void</code> or one of the primitive variable classes,
	 * <code>boolean</code>, <code>int</code>, <code>float</code>, etc.).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	public static boolean isPrimitive(String className) {
		if (isArray(className) || (className.length() > ClassTools.MAX_PRIMITIVE_CLASS_NAME_LENGTH)) {
			return false;  // performance tweak
		}
		for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
			if (className.equals(primitive.javaClass.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see #isPrimitive(String)
	 */
	public static boolean isPrimitive(char[] className) {
		if (isArray(className) || (className.length > ClassTools.MAX_PRIMITIVE_CLASS_NAME_LENGTH)) {
			return false;  // performance tweak
		}
		for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
			if (Arrays.equals(className, primitive.javaClassName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified class is a primitive wrapper
	 * class (i.e. <code>java.lang.Void</code> or one of the primitive
	 * variable wrapper classes, <code>java.lang.Boolean</code>,
	 * <code>java.lang.Integer</code>, <code>java.lang.Float</code>, etc.).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	public static boolean isPrimitiveWrapper(String className) {
		if (isArray(className) || (className.length() > ClassTools.MAX_PRIMITIVE_WRAPPER_CLASS_NAME_LENGTH)) {
			return false;  // performance tweak
		}
		for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
			if (className.equals(primitive.wrapperClass.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see #isPrimitiveWrapper(String)
	 */
	public static boolean isPrimitiveWrapper(char[] className) {
		if (isArray(className) || (className.length > ClassTools.MAX_PRIMITIVE_WRAPPER_CLASS_NAME_LENGTH)) {
			return false;  // performance tweak
		}
		for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
			if (Arrays.equals(className, primitive.wrapperClassName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified class is a "variable" primitive
	 * class (i.e. <code>boolean</code>, <code>int</code>, <code>float</code>, etc.,
	 * but not <code>void</code>).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	public static boolean isVariablePrimitive(String className) {
		return isPrimitive(className)
			&& ( ! className.equals(VOID));
	}

	/**
	 * @see #isVariablePrimitive(String)
	 */
	public static boolean isVariablePrimitive(char[] className) {
		return isPrimitive(className)
			&& ( ! Arrays.equals(className, VOID_CHAR_ARRAY));
	}

	/**
	 * Return whether the specified class is a "variable" primitive wrapper
	 * class (i.e. <code>java.lang.Boolean</code>,
	 * <code>java.lang.Integer</code>, <code>java.lang.Float</code>, etc.,
	 * but not <code>java.lang.Void</code>).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	public static boolean isVariablePrimitiveWrapper(String className) {
		return isPrimitiveWrapper(className)
			&& ( ! className.equals(VOID_WRAPPER));
	}

	/**
	 * @see #isVariablePrimitiveWrapper(String)
	 */
	public static boolean isVariablePrimitiveWrapper(char[] className) {
		return isPrimitiveWrapper(className)
			&& ( ! Arrays.equals(className, VOID_WRAPPER_CHAR_ARRAY));
	}

	/**
	 * Return the name of the primitive wrapper class corresponding to the specified
	 * primitive class. Return <code>null</code> if the specified class is not a primitive.
	 */
	public static String primitiveWrapperClassName(String primitiveClassName) {
		for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
			if (primitive.javaClass.getName().equals(primitiveClassName)) {
				return primitive.wrapperClass.getName();
			}
		}
		return null;
	}

	/**
	 * @see #primitiveWrapperClassName(String)
	 */
	public static char[] primitiveWrapperClassName(char[] primitiveClassName) {
		for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
			if (Arrays.equals(primitive.javaClassName, primitiveClassName)) {
				return primitive.wrapperClassName;
			}
		}
		return null;
	}

	/**
	 * Return the name of the primitive class corresponding to the specified
	 * primitive wrapper class. Return <code>null</code> if the specified class
	 * is not a primitive wrapper.
	 */
	public static String primitiveClassName(String primitiveWrapperClassName) {
		for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
			if (primitive.wrapperClass.getName().equals(primitiveWrapperClassName)) {
				return primitive.javaClass.getName();
			}
		}
		return null;
	}
	
	/**
	 * @see #primitiveClassName(String)
	 */
	public static char[] primitiveClassName(char[] primitiveWrapperClassName) {
		for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
			if (Arrays.equals(primitive.wrapperClassName, primitiveWrapperClassName)) {
				return primitive.javaClassName;
			}
		}
		return null;
	}
	
	/**
	 * Return whether the two class names are equivalent, given autoboxing.
	 * (e.g. <code>"java.lang.Integer"</code> and <code>"int"</code> are equivalent)
	 */
	public static boolean isAutoboxEquivalent(String className1, String className2) {
		return ObjectTools.equals(className1, className2)
			|| ObjectTools.equals(primitiveClassName(className1), className2)
			|| ObjectTools.equals(primitiveWrapperClassName(className1), className2);
	}

	/**
	 * @see #isAutoboxEquivalent(String, String)
	 */
	public static boolean isAutoboxEquivalent(char[] className1, char[] className2) {
		return Arrays.equals(className1, className2)
			|| Arrays.equals(primitiveClassName(className1), className2)
			|| Arrays.equals(primitiveWrapperClassName(className1), className2);
	}


	// ********** primitive codes **********

	/**
	 * Return the primitive class name for the specified primitive class code.
	 * Return <code>null</code> if the specified code
	 * is not a primitive class code.
	 * @see java.lang.Class#getName()
	 */
	public static String forCode(int classCode) {
		return forCode((char) classCode);
	}

	/**
	 * @see #forCode(int)
	 */
	public static char[] forCodeCharArray(int classCode) {
		return forCodeCharArray((char) classCode);
	}

	/**
	 * Return the primitive class name for the specified primitive class code.
	 * Return <code>null</code> if the specified code
	 * is not a primitive class code.
	 * @see java.lang.Class#getName()
	 */
	public static String forCode(char classCode) {
		Class<?> primitiveClass = ClassTools.primitiveForCode(classCode);
		return (primitiveClass == null) ? null : primitiveClass.getName();
	}

	/**
	 * @see #forCode(char)
	 */
	public static char[] forCodeCharArray(char classCode) {
		Class<?> primitiveClass = ClassTools.primitiveForCode(classCode);
		return (primitiveClass == null) ? null : primitiveClass.getName().toCharArray();
	}

	/**
	 * Return the class code for the specified primitive class.
	 * Return <code>0</code> if the specified class
	 * is not a primitive class.
	 * @see java.lang.Class#getName()
	 */
	public static char primitiveClassCode(String primitiveClassName) {
		if (( ! isArray(primitiveClassName)) && (primitiveClassName.length() <= ClassTools.MAX_PRIMITIVE_CLASS_NAME_LENGTH)) {
			for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
				if (primitive.javaClass.getName().equals(primitiveClassName)) {
					return primitive.code;
				}
			}
		}
		return 0;
	}

	/**
	 * @see #primitiveClassCode(String)
	 */
	public static char primitiveClassCode(char[] primitiveClassName) {
		if (( ! isArray(primitiveClassName)) && (primitiveClassName.length <= ClassTools.MAX_PRIMITIVE_CLASS_NAME_LENGTH)) {
			for (ClassTools.Primitive primitive : ClassTools.PRIMITIVES) {
				if (Arrays.equals(primitive.javaClassName, primitiveClassName)) {
					return primitive.code;
				}
			}
		}
		return 0;
	}

	static void appendReferenceNameTo(String className, StringBuilder sb) {
		sb.append(REFERENCE_CLASS_CODE);
		sb.append(className);
		sb.append(REFERENCE_CLASS_NAME_DELIMITER);
	}

	static void appendReferenceNameTo(char[] className, StringBuilder sb) {
		sb.append(REFERENCE_CLASS_CODE);
		sb.append(className);
		sb.append(REFERENCE_CLASS_NAME_DELIMITER);
	}


	// ********** instantiation **********

	/**
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 */
	public static Object newInstance(String className) {
		return newInstance(className, null);
	}

	/**
	 * @see #newInstance(String)
	 */
	public static Object newInstance(char[] className) {
		return newInstance(className, null);
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and argument.
	 */
	public static Object newInstance(String className, Class<?> parameterType, Object argument) {
		return newInstance(className, parameterType, argument, null);
	}

	/**
	 * @see #newInstance(String, Class, Object)
	 */
	public static Object newInstance(char[] className, Class<?> parameterType, Object argument) {
		return newInstance(className, parameterType, argument, null);
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and arguments.
	 */
	public static Object newInstance(String className, Class<?>[] parameterTypes, Object... arguments) {
		return newInstance(className, parameterTypes, arguments, null);
	}

	/**
	 * @see #newInstance(String, Class[], Object[])
	 */
	public static Object newInstance(char[] className, Class<?>[] parameterTypes, Object... arguments) {
		return newInstance(className, parameterTypes, arguments, null);
	}

	/**
	 * Return a new instance of the specified class,
	 * using the class's default (zero-argument) constructor.
	 * Use the specified class loader to load the class.
	 */
	public static Object newInstance(String className, ClassLoader classLoader) {
		return ClassTools.newInstance(ClassTools.forName(className, false, classLoader));
	}

	/**
	 * @see #newInstance(String, ClassLoader)
	 */
	public static Object newInstance(char[] className, ClassLoader classLoader) {
		return ClassTools.newInstance(ClassTools.forName(className, false, classLoader));
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter type and argument.
	 * Use the specified class loader to load the class.
	 */
	public static Object newInstance(String className, Class<?> parameterType, Object argument, ClassLoader classLoader) {
		return ClassTools.newInstance(ClassTools.forName(className, false, classLoader), parameterType, argument);
	}

	/**
	 * @see #newInstance(String, Class, Object, ClassLoader)
	 */
	public static Object newInstance(char[] className, Class<?> parameterType, Object argument, ClassLoader classLoader) {
		return ClassTools.newInstance(ClassTools.forName(className, false, classLoader), parameterType, argument);
	}

	/**
	 * Return a new instance of the specified class,
	 * given the constructor parameter types and arguments.
	 * Use the specified class loader to load the class.
	 */
	public static Object newInstance(String className, Class<?>[] parameterTypes, Object[] arguments, ClassLoader classLoader) {
		return ClassTools.newInstance(ClassTools.forName(className, false, classLoader), parameterTypes, arguments);
	}

	/**
	 * @see #newInstance(String, Class[], Object[], ClassLoader)
	 */
	public static Object newInstance(char[] className, Class<?>[] parameterTypes, Object[] arguments, ClassLoader classLoader) {
		return ClassTools.newInstance(ClassTools.forName(className, false, classLoader), parameterTypes, arguments);
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ClassNameTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
