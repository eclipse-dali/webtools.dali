/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Convenience methods related to Java class names as returned by
 * {@link java.lang.Class#getName()}.
 */
public final class ClassName {

	public static final String VOID_CLASS_NAME = ReflectionTools.VOID_CLASS.getName();
	public static final String VOID_WRAPPER_CLASS_NAME = ReflectionTools.VOID_WRAPPER_CLASS.getName();

	public static final char REFERENCE_CLASS_CODE = 'L';
	public static final char REFERENCE_CLASS_NAME_DELIMITER = ';';

	/**
	 * Return whether the specified class is an array type.
	 * @see java.lang.Class#getName()
	 */
	public static boolean isArray(String className) {
		return className.charAt(0) == '[';
	}

	/**
	 * Return the "element type" of the specified class.
	 * The element type is the base type held by an array type.
	 * Non-array types simply return themselves.
	 * @see java.lang.Class#getName()
	 */
	public static String getElementTypeName(String className) {
		int depth = getArrayDepth(className);
		if (depth == 0) {
			// the name is in the form: "java.lang.Object" or "int"
			return className;
		}
		return getElementTypeName_(className, depth);
	}

	/**
	 * pre-condition: array depth is not zero
	 */
	private static String getElementTypeName_(String className, int arrayDepth) {
		int last = className.length() - 1;
		if (className.charAt(arrayDepth) == REFERENCE_CLASS_CODE) {
			// the name is in the form: "[[[Ljava.lang.Object;"
			return className.substring(arrayDepth + 1, last);	// drop the trailing ';'
		}
		// the name is in the form: "[[[I"
		return forCode(className.charAt(last));
	}

	/**
	 * Return the "array depth" of the specified class.
	 * The depth is the number of dimensions for an array type.
	 * Non-array types have a depth of zero.
	 * @see java.lang.Class#getName()
	 */
	public static int getArrayDepth(String className) {
		int depth = 0;
		while (className.charAt(depth) == '[') {
			depth++;
		}
		return depth;
	}

	/**
	 * Return the specified class's component type.
	 * Return <code>null</code> if the specified class is not an array type.
	 * @see java.lang.Class#getName()
	 */
	public static String getComponentTypeName(String className) {
		switch (getArrayDepth(className)) {
			case 0:
				return null;
			case 1:
				return getElementTypeName_(className, 1);
			default:
				return className.substring(1);
		}
	}

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
	public static String getSimpleName(String className) {
		return isArray(className) ?
				getSimpleName(getComponentTypeName(className)) + "[]" : //$NON-NLS-1$
				getSimpleName_(className);
	}

	/**
	 * pre-condition: specified class is not an array type
	 */
	private static String getSimpleName_(String className) {
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
		return ""; //$NON-NLS-1$
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
	public static String getPackageName(String className) {
		if (isArray(className)) {
			return ""; //$NON-NLS-1$
		}
		int lastPeriod = className.lastIndexOf('.');
		return (lastPeriod == -1) ? "" : className.substring(0, lastPeriod); //$NON-NLS-1$
	}

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
	 * {@link Character#isDigit(char)} returns <code>true</code> for some non-ASCII
	 * digits. This method does not.
	 */
	private static boolean charIsAsciiDigit(char c) {
		return ('0' <= c) && (c <= '9');
	}

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
	 * Return whether the specified class is a primitive
	 * class (i.e. <code>void</code> or one of the primitive variable classes,
	 * <code>boolean</code>, <code>int</code>, <code>float</code>, etc.).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	public static boolean isPrimitive(String className) {
		if (isArray(className) || (className.length() > ReflectionTools.MAX_PRIMITIVE_CLASS_NAME_LENGTH)) {
			return false;  // performance tweak
		}
		for (ReflectionTools.Primitive primitive : ReflectionTools.PRIMITIVES) {
			if (className.equals(primitive.javaClass.getName())) {
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
		if (isArray(className) || (className.length() > ReflectionTools.MAX_PRIMITIVE_WRAPPER_CLASS_NAME_LENGTH)) {
			return false;  // performance tweak
		}
		for (ReflectionTools.Primitive primitive : ReflectionTools.PRIMITIVES) {
			if (className.equals(primitive.wrapperClass.getName())) {
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
			&& ( ! className.equals(VOID_CLASS_NAME));
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
			&& ( ! className.equals(VOID_WRAPPER_CLASS_NAME));
	}

	/**
	 * Return the name of the primitive wrapper class corresponding to the specified
	 * primitive class. Return <code>null</code> if the specified class is not a primitive.
	 */
	public static String getWrapperClassName(String primitiveClassName) {
		for (ReflectionTools.Primitive primitive : ReflectionTools.PRIMITIVES) {
			if (primitive.javaClass.getName().equals(primitiveClassName)) {
				return primitive.wrapperClass.getName();
			}
		}
		return null;
	}

	/**
	 * Return the name of the primitive class corresponding to the specified
	 * primitive wrapper class. Return <code>null</code> if the specified class
	 * is not a primitive wrapper.
	 */
	public static String getPrimitiveClassName(String primitiveWrapperClassName) {
		for (ReflectionTools.Primitive primitive : ReflectionTools.PRIMITIVES) {
			if (primitive.wrapperClass.getName().equals(primitiveWrapperClassName)) {
				return primitive.javaClass.getName();
			}
		}
		return null;
	}
	
	/**
	 * Return whether the two class names are equivalent, given autoboxing.
	 * (e.g. "java.lang.Integer" and "int" should be equivalent)
	 */
	public static boolean areAutoboxEquivalents(String className1, String className2) {
		return Tools.valuesAreEqual(className1, className2)
			|| Tools.valuesAreEqual(getPrimitiveClassName(className1), className2)
			|| Tools.valuesAreEqual(getWrapperClassName(className1), className2);
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
	 * Return the primitive class name for the specified primitive class code.
	 * Return <code>null</code> if the specified code
	 * is not a primitive class code.
	 * @see java.lang.Class#getName()
	 */
	public static String forCode(char classCode) {
		Class<?> primitiveClass = ReflectionTools.getClassForCode(classCode);
		return (primitiveClass == null) ? null : primitiveClass.getName();
	}

	/**
	 * Return the class code for the specified primitive class.
	 * Return <code>0</code> if the specified class
	 * is not a primitive class.
	 * @see java.lang.Class#getName()
	 */
	public static char getCodeForClassName(String primitiveClassName) {
		if (( ! isArray(primitiveClassName)) && (primitiveClassName.length() <= ReflectionTools.MAX_PRIMITIVE_CLASS_NAME_LENGTH)) {
			for (ReflectionTools.Primitive primitive : ReflectionTools.PRIMITIVES) {
				if (primitive.javaClass.getName().equals(primitiveClassName)) {
					return primitive.code;
				}
			}
		}
		return 0;
	}

	static void append(String className, StringBuilder sb) {
		sb.append(REFERENCE_CLASS_CODE);
		sb.append(className);
		sb.append(REFERENCE_CLASS_NAME_DELIMITER);
	}


	// ********** suppressed constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private ClassName() {
		super();
		throw new UnsupportedOperationException();
	}

}
