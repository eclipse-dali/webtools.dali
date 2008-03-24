/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility;

import java.io.PrintWriter;

/**
 * This interface describes a Java type; i.e. its "element type"
 * and its "array depth". The element type is referenced by name,
 * allowing us to reference classes that are not (or cannot be) loaded.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface JavaType
	extends Comparable<JavaType>
{

	/**
	 * Return the name of the type's "element type".
	 * A member type will have one or more '$' characters in its name.
	 */
	String elementTypeName();

	/**
	 * Return the type's "array depth".
	 */
	int arrayDepth();

	boolean isArray();

	/**
	 * NB: void.class.isPrimitive() == true
	 */
	boolean isPrimitive();

	/**
	 * NB: void.class.isPrimitive() == true
	 */
	boolean isPrimitiveWrapper();

	/**
	 * NB: variables cannot be declared 'void'
	 */
	boolean isVariablePrimitive();

	/**
	 * NB: variables cannot be declared 'void'
	 */
	boolean isVariablePrimitiveWrapper();

	/**
	 * Return the class corresponding to the type's element type and array depth.
	 */
	Class<?> javaClass() throws ClassNotFoundException;

	/**
	 * Return the version of the type's name that matches that
	 * returned by java.lang.Class#getName()
	 * (e.g. "[[J", "[Ljava.lang.Object;", "java.util.Map$Entry").
	 */
	String javaClassName();

	boolean equals(String otherElementTypeName, int otherArrayDepth);

	boolean describes(String className);

	boolean describes(Class<?> javaClass);

	boolean equals(JavaType other);

	/**
	 * Return the version of the type's name that can be used in source code:
	 *     "[[J" => "long[][]"
	 *     "java.util.Map$Entry" => "java.util.Map.Entry"
	 */
	String declaration();

	/**
	 * Append the version of the type's name that can be used in source code:
	 *     "[[J" => "long[][]"
	 *     "java.util.Map$Entry" => "java.util.Map.Entry"
	 */
	void appendDeclarationTo(StringBuilder sb);

	/**
	 * Print the version of the type's name that can be used in source code:
	 *     "[[J" => "long[][]"
	 *     "java.util.Map$Entry" => "java.util.Map.Entry"
	 */
	void printDeclarationOn(PrintWriter pw);

}
