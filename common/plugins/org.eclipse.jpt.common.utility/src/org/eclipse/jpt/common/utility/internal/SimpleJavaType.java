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

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;

import org.eclipse.jpt.common.utility.JavaType;

/**
 * Straightforward implementation of the {@link JavaType} interface.
 */
public final class SimpleJavaType
	implements JavaType, Cloneable, Serializable
{
	/**
	 * store the type as a name, so we can reference classes
	 * that are not loaded
	 */
	private final String elementTypeName;

	/**
	 * non-array types have an array depth of zero
	 */
	private final int arrayDepth;

	private static final String BRACKETS = "[]"; //$NON-NLS-1$
	private static final long serialVersionUID = 1L;

	/**
	 * Cache the "standard" java types for performance 
	 * Defining this as java.lang*, java.util*, java.sql.* for array dimensionality of 0
	 * Make this a HashMap for performance, duplicate creation shouldn't be an issue.
	 */
	private static final HashMap<String, JavaType> stardardJavaTypesCache = new HashMap<String, JavaType>();

	// ********** constructors **********

	/**
	 * Use this factory method for performance. Standard java types will be cached.
	 */
	//TODO SimpleJavaType(String, int) used incorrectly when building method parameter types
	//buildSimpleType(java.lang.String[], 1) - this gets passed in for a method parameter String[]
	public static JavaType buildSimpleJavaType(String elementTypeName, int arrayDepth) {
		if (arrayDepth == 0) {
			JavaType javaType = stardardJavaTypesCache.get(elementTypeName);
			if (javaType != null) {
				return javaType;
			}
			if (elementTypeName.startsWith("java.lang.") || //$NON-NLS-1$
					elementTypeName.startsWith("java.util.") || //$NON-NLS-1$
					elementTypeName.startsWith("java.sql.")) { //$NON-NLS-1$
				javaType = new SimpleJavaType(elementTypeName, arrayDepth);
				stardardJavaTypesCache.put(elementTypeName, javaType);
				return javaType;
			}
		}
		return new SimpleJavaType(elementTypeName, arrayDepth);
	}

	/**
	 * Construct a Java type with the specified element type and array depth.
	 */
	public SimpleJavaType(String elementTypeName, int arrayDepth) {
		super();
		if ((elementTypeName == null) || (elementTypeName.length() == 0)) {
			throw new IllegalArgumentException("The element type name is required."); //$NON-NLS-1$
		}
		if (ClassNameTools.arrayDepth(elementTypeName) != 0) {		// e.g. "[Ljava.lang.Object;"
			throw new IllegalArgumentException("The element type must not be an array: " + elementTypeName + '.'); //$NON-NLS-1$
		}
		if (arrayDepth < 0) {
			throw new IllegalArgumentException("The array depth must be greater than or equal to zero: " + arrayDepth + '.'); //$NON-NLS-1$
		}
		if (elementTypeName.equals(void.class.getName()) && (arrayDepth != 0)) {
			throw new IllegalArgumentException("'void' must have an array depth of zero: " + arrayDepth + '.'); //$NON-NLS-1$
		}
		this.elementTypeName = elementTypeName;
		this.arrayDepth = arrayDepth;
	}

	/**
	 * Construct a Java type for the specified class.
	 * The class name can be in one of the following forms:<ul><code>
	 * <li>java.lang.Object
	 * <li>int
	 * <li>java.util.Map$Entry
	 * <li>[Ljava.lang.Object;
	 * <li>[I
	 * <li>[Ljava.util.Map$Entry;
	 * </code></ul>
	 */
	public SimpleJavaType(String javaClassName) {
		this(ClassNameTools.elementTypeName(javaClassName), ClassNameTools.arrayDepth(javaClassName));
	}

	/**
	 * Construct a Java type for the specified class.
	 */
	public SimpleJavaType(Class<?> javaClass) {
		this(javaClass.getName());
	}


	// ********** accessors **********

	public String getElementTypeName() {
		return this.elementTypeName;
	}

	public int getArrayDepth() {
		return this.arrayDepth;
	}


	// ********** queries **********

	public boolean isArray() {
		return this.arrayDepth > 0;
	}

	public boolean isPrimitive() {
		return (this.arrayDepth == 0) && ClassNameTools.isPrimitive(this.elementTypeName);
	}

	public boolean isPrimitiveWrapper() {
		return (this.arrayDepth == 0) && ClassNameTools.isPrimitiveWrapper(this.elementTypeName);
	}

	public boolean isVariablePrimitive() {
		return (this.arrayDepth == 0) && ClassNameTools.isVariablePrimitive(this.elementTypeName);
	}

	public boolean isVariablePrimitiveWrapper() {
		return (this.arrayDepth == 0) && ClassNameTools.isVariablePrimitiveWrapper(this.elementTypeName);
	}

	public Class<?> getJavaClass() throws ClassNotFoundException {
		return ClassTools.forTypeDeclaration(this.elementTypeName, this.arrayDepth);
	}

	public String getJavaClassName() {
		return TypeDeclarationTools.className(this.elementTypeName, this.arrayDepth);
	}


	// ********** comparison **********

	public boolean equals(String otherElementTypeName, int otherArrayDepth) {
		return (this.arrayDepth == otherArrayDepth)
			&& this.elementTypeName.equals(otherElementTypeName);
	}

	public boolean describes(String className) {
		return this.equals(ClassNameTools.elementTypeName(className), ClassNameTools.arrayDepth(className));
	}

	public boolean describes(Class<?> javaClass) {
		return this.describes(javaClass.getName());
	}

	public boolean equals(JavaType other) {
		return this.equals(other.getElementTypeName(), other.getArrayDepth());
	}

	@Override
	public boolean equals(Object o) {
		return (this == o) ? true : (o instanceof JavaType) ? this.equals((JavaType) o) : false;
	}

	@Override
	public int hashCode() {
		return this.elementTypeName.hashCode() ^ this.arrayDepth;
	}


	// ********** printing and displaying **********

	public String declaration() {
		if (this.arrayDepth == 0) {
			return this.getElementTypeNameDeclaration();
		}
		StringBuilder sb = new StringBuilder(this.elementTypeName.length() + (2 * this.arrayDepth));
		this.appendDeclarationTo(sb);
		return sb.toString();
	}

	public void appendDeclarationTo(StringBuilder sb) {
		sb.append(this.getElementTypeNameDeclaration());
		for (int i = this.arrayDepth; i-- > 0; ) {
			sb.append(BRACKETS);
		}
	}

	public void printDeclarationOn(PrintWriter pw) {
		pw.print(this.getElementTypeNameDeclaration());
		for (int i = this.arrayDepth; i-- > 0; ) {
			pw.print(BRACKETS);
		}
	}

	/**
	 * The <code>'$'</code> version of the name is used in {@link Class#forName(String)},
	 * but the <code>'.'</code> version of the name is used in source code.
	 * Very irritating....
	 */
	private String getElementTypeNameDeclaration() {
		return this.elementTypeName.replace('$', '.');
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append('(');
		this.appendDeclarationTo(sb);
		sb.append(')');
		return sb.toString();
	}


	// ********** cloning **********

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}
}
