/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility;

import java.io.PrintWriter;

/**
 * This interface describes a Java type; i.e. its "element type"
 * and its "array depth". The element type is referenced by name,
 * allowing us to reference classes that are not (or cannot be) loaded.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <p>
 * This interface is not intended to be implemented by clients.
 */
public interface JavaType {

	/**
	 * Return the name of the type's "element type".
	 * A member type will have one or more <code>'$'</code> characters in its name.
	 */
	String getElementTypeName();

	/**
	 * Return the type's "array depth".
	 */
	int getArrayDepth();

	/**
	 * Return whether the type is an array (i.e. its "array depth" is greater
	 * than zero).
	 */
	boolean isArray();

	/**
	 * Return whether the type is a "primitive" (e.g. <code>int</code>, <code>float</code>).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	boolean isPrimitive();

	/**
	 * Return whether the type is a "primitive wrapper" (e.g. {@link java.lang.Integer},
	 * {@link java.lang.Float}).
	 * <p>
	 * <strong>NB:</strong> <code>void.class.isPrimitive() == true</code>
	 */
	boolean isPrimitiveWrapper();

	/**
	 * Return whether the type is a "variable primitive" (e.g. <code>int</code>, <code>float</code>,
	 * but not <code>void</code>).
	 * <p>
	 * <strong>NB:</strong> variables cannot be declared <code>void</code>
	 */
	boolean isVariablePrimitive();

	/**
	 * Return whether the type is a "variable primitive wrapper" (e.g.
	 * {@link java.lang.Integer}, {@link java.lang.Float},
	 * but not {@link java.lang.Void}).
	 * <p>
	 * <strong>NB:</strong> variables cannot be declared <code>void</code>
	 */
	boolean isVariablePrimitiveWrapper();

	/**
	 * Return the class corresponding to the type's element type and array depth.
	 */
	Class<?> getJavaClass() throws ClassNotFoundException;

	/**
	 * Return the version of the type's name that matches that
	 * returned by {@link java.lang.Class#getName()}
	 * (e.g. <code>"[[J"</code>, <code>"[Ljava.lang.Object;"</code>,
	 * <code>"java.util.Map$Entry"</code>).
	 */
	String getJavaClassName();

	/**
	 * Return whether the type is equal to the specified type.
	 */
	boolean equals(String otherElementTypeName, int otherArrayDepth);

	/**
	 * Return whether the type describes to the specified type.
	 */
	boolean describes(String className);

	/**
	 * Return whether the type describes to the specified type.
	 */
	boolean describes(Class<?> javaClass);

	/**
	 * Return whether the type is equal to the specified type.
	 */
	boolean equals(JavaType other);

	/**
	 * Return the version of the type's name that can be used in source code:<ul>
	 * <li><code>"[[J"</code> => <code>"long[][]"</code>
	 * <li><code>"java.util.Map$Entry"</code> => <code>"java.util.Map.Entry"</code>
	 * </ul>
	 */
	String declaration();

	/**
	 * Append the version of the type's name that can be used in source code:<ul>
	 * <li><code>"[[J"</code> => <code>"long[][]"</code>
	 * <li><code>"java.util.Map$Entry"</code> => <code>"java.util.Map.Entry"</code>
	 * </ul>
	 */
	void appendDeclarationTo(StringBuilder sb);

	/**
	 * Print the version of the type's name that can be used in source code:<ul>
	 * <li><code>"[[J"</code> => <code>"long[][]"</code>
	 * <li><code>"java.util.Map$Entry"</code> => <code>"java.util.Map.Entry"</code>
	 * </ul>
	 */
	void printDeclarationOn(PrintWriter pw);

}
