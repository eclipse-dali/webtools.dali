/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.utility.jdt;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Java source code or binary type binding
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface TypeBinding {
	
	/**
	 * Return the resolved, qualified name of the attribute's type
	 * (e.g. "java.util.Collection" or "byte[]").
	 * If the type is an array, this name will include the appropriate number
	 * of bracket pairs.
	 * This name will not include the type's generic type arguments
	 * (e.g. "java.util.Collection<java.lang.String>" will only return
	 * "java.util.Collection").
	 * This name will not include generic types, but will resolve to the most
	 * specific type available.
	 * (e.g. "?" will return "java.lang.Object", 
	 *  "T" (which extends Serializable in the class declaration) will return "java.io.Serializable")
	 */
	String getQualifiedName();
	
	/**
	 * Returns the unqualified version of {@link #getQualifiedName()}
	 */
	String getSimpleName();
	
	/**
	 * Return the package name (or null)
	 */
	String getPackageName();
	
	/**
	 * Return whether the type resolves to an interface type.
	 */
	boolean isInterface();
	
	/**
	 * Return whether the type resolves to an enum type.
	 */
	boolean isEnum();
	
	/**
	 * Return whether the type is a "variable" primitive type
	 * (i.e. any primitive type except 'void').
	 */
	boolean isVariablePrimitive();
	
	/**
	 * Return whether type represents a generic type *declaration*.
	 */
	boolean isGenericTypeDeclaration();
	
	/**
	 * Return whether type represents a member type *declaration*.
	 */
	boolean isMemberTypeDeclaration();
	
	/**
	 * Return whether the type implements or extends the specified
	 * type.
	 */
	boolean isSubTypeOf(String typeName);
	
	/**
	 * Return whether the type is an array.
	 */
	boolean isArray();
	
	/**
	 * Return the dimensionality of the array, 0 otherwise.
	 * (String[][] -> 2, Collection<String> -> 0)
	 */
	int getArrayDimensionality();
	
	/**
	 * Return the component type name of the array, null otherwise.
	 * (String[][] -> "java.lang.String", Collection<String> -> null)
	 */
	String getArrayComponentTypeName();
	
	/**
	 * Return the fully qualified names of the type arguments, if any.
	 * The name for any type argument that is an array will contain the appropriate
	 * number of bracket pairs.
	 * The names will not include any further generic type arguments.
	 * (e.g. Foo<Bar, int[], List<Bar>>  returns "barpackage.Bar", "int[]", "java.util.List"})
	 */
	ListIterable<String> getTypeArgumentNames();
	
	int getTypeArgumentNamesSize();
	
	String getTypeArgumentName(int index);
}
