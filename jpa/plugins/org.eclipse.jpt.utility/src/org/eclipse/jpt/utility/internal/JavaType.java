/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.PrintWriter;
import java.io.Serializable;
import java.text.Collator;

/**
 * This class describes a Java type; i.e. its "element type"
 * and its "array depth". The element type is referenced by name,
 * allowing us to reference classes that are not (or cannot be) loaded.
 */
public final class JavaType
	implements Comparable<JavaType>, Cloneable, Serializable
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

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a Java type with the specified element type and array depth.
	 */
	public JavaType(String elementTypeName, int arrayDepth) {
		super();
		if ((elementTypeName == null) || (elementTypeName.length() == 0)) {
			throw new IllegalArgumentException("The element type name is required.");
		}
		if (ClassTools.arrayDepthForClassNamed(elementTypeName) != 0) {		// e.g. "[Ljava.lang.Object;"
			throw new IllegalArgumentException("The element type must not be an array: " + elementTypeName + '.');
		}
		if (arrayDepth < 0) {
			throw new IllegalArgumentException("The array depth must be greater than or equal to zero: " + arrayDepth + '.');
		}
		if (elementTypeName.equals(void.class.getName()) && (arrayDepth != 0)) {
			throw new IllegalArgumentException("'void' must have an array depth of zero: " + arrayDepth + '.');
		}
		this.elementTypeName = elementTypeName;
		this.arrayDepth = arrayDepth;
	}

	/**
	 * Construct a Java type for the specified class.
	 * The class name can be in one of the following forms:
	 *     java.lang.Object
	 *     int
	 *     java.util.Map$Entry
	 *     [Ljava.lang.Object;
	 *     [I
	 *     [Ljava.util.Map$Entry;
	 */
	public JavaType(String javaClassName) {
		this(ClassTools.elementTypeNameForClassNamed(javaClassName), ClassTools.arrayDepthForClassNamed(javaClassName));
	}

	/**
	 * Construct a Java type for the specified class.
	 */
	public JavaType(Class<?> javaClass) {
		this(javaClass.getName());
	}


	// ********** accessors **********

	/**
	 * Return the name of the type's "element type".
	 * A member type will have one or more '$' characters in its name.
	 */
	public String elementTypeName() {
		return this.elementTypeName;
	}

	/**
	 * Return the type's "array depth".
	 */
	public int arrayDepth() {
		return this.arrayDepth;
	}


	// ********** queries **********

	public boolean isArray() {
		return this.arrayDepth > 0;
	}

	public boolean isPrimitive() {
		return (this.arrayDepth == 0) && ClassTools.classNamedIsNonReference(this.elementTypeName);
	}

	/**
	 * Return the class corresponding to the type's element type and array depth.
	 */
	public Class<?> javaClass() throws ClassNotFoundException {
		return ClassTools.classForTypeDeclaration(this.elementTypeName, this.arrayDepth);
	}

	/**
	 * Return the version of the type's name that matches that
	 * returned by java.lang.Class#getName()
	 * (e.g. "[[J", "[Ljava.lang.Object;", "java.util.Map$Entry").
	 */
	public String javaClassName() {
		return ClassTools.classNameForTypeDeclaration(this.elementTypeName, this.arrayDepth);
	}


	// ********** comparison **********

	public boolean equals(String otherElementTypeName, int otherArrayDepth) {
		return (this.arrayDepth == otherArrayDepth)
			&& this.elementTypeName.equals(otherElementTypeName);
	}

	public boolean describes(String className) {
		return this.equals(ClassTools.elementTypeNameForClassNamed(className), ClassTools.arrayDepthForClassNamed(className));
	}

	public boolean describes(Class<?> javaClass) {
		return this.describes(javaClass.getName());
	}

	public boolean equals(JavaType other) {
		return this.equals(other.elementTypeName, other.arrayDepth);
	}

	@Override
	public boolean equals(Object o) {
		return (o instanceof JavaType) ? this.equals((JavaType) o) : false;
	}

	@Override
	public int hashCode() {
		return this.elementTypeName.hashCode() ^ this.arrayDepth;
	}

	public int compareTo(JavaType jt) {
		int x = Collator.getInstance().compare(this.elementTypeName, jt.elementTypeName);
		return (x != 0) ? x : (this.arrayDepth - jt.arrayDepth);
	}


	// ********** printing and displaying **********

	/**
	 * Return the version of the type's name that can be used in source code:
	 *     "[[J" => "long[][]"
	 *     "java.util.Map$Entry" => "java.util.Map.Entry"
	 */
	public String declaration() {
		if (this.arrayDepth == 0) {
			return this.elementTypeNameDeclaration();
		}
		StringBuilder sb = new StringBuilder(this.elementTypeName.length() + (2 * this.arrayDepth));
		this.appendDeclarationTo(sb);
		return sb.toString();
	}

	/**
	 * Append the version of the type's name that can be used in source code:
	 *     "[[J" => "long[][]"
	 *     "java.util.Map$Entry" => "java.util.Map.Entry"
	 */
	public void appendDeclarationTo(StringBuilder sb) {
		sb.append(this.elementTypeNameDeclaration());
		for (int i = this.arrayDepth; i-- > 0; ) {
			sb.append("[]");
		}
	}

	/**
	 * Print the version of the type's name that can be used in source code:
	 *     "[[J" => "long[][]"
	 *     "java.util.Map$Entry" => "java.util.Map.Entry"
	 */
	public void printDeclarationOn(PrintWriter pw) {
		pw.print(this.elementTypeNameDeclaration());
		for (int i = this.arrayDepth; i-- > 0; ) {
			pw.print("[]");
		}
	}

	/**
	 * The '$' version of the name is used in Class.forName(String),
	 * but the '.' verions of the name is used in source code.
	 * Very irritating....
	 */
	private String elementTypeNameDeclaration() {
		return this.elementTypeName.replace('$', '.');
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(ClassTools.shortClassNameForObject(this));
		sb.append('(');
		this.appendDeclarationTo(sb);
		sb.append(')');
		return sb.toString();
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

}
