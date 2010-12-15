/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.utility.MethodSignature;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * Java source code or binary method
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JavaResourceMethod
	extends JavaResourceMember
{
	/**
	 * The Java resource method's name does not change.
	 */
	String getName();

	/**
	 * Return whether the Java resource persistent attribute is for the specified
	 * method.
	 */
	boolean isFor(MethodSignature methodSignature, int occurrence);

	/**
	 * Return whether the method's return type implements or extends the specified
	 * type.
	 */
	boolean returnTypeIsSubTypeOf(String typeName);

	/**
	 * Return whether the method's return type is a "variable" primitive type
	 * (i.e. any primitive type except 'void').
	 */
	boolean returnTypeIsVariablePrimitive();

	/**
	 * @see java.lang.reflect.Modifier
	 */
	int getReturnTypeModifiers();
		String RETURN_TYPE_MODIFIERS_PROPERTY = "returnTypeModifiers"; //$NON-NLS-1$

	/**
	 * Return the resolved, qualified name of the method's return type
	 * (e.g. "java.util.Collection" or "byte[]").
	 * If the return type is an array, this name will include the appropriate number
	 * of bracket pairs.
	 * This name will not include the return type's generic type arguments
	 * (e.g. "java.util.Collection<java.lang.String>" will only return
	 * "java.util.Collection").
	 * @see #returnTypeTypeArgumentNames()
	 */
	String getReturnTypeName();
		String RETURN_TYPE_NAME_PROPERTY = "returnTypeName"; //$NON-NLS-1$

	/**
	 * Return whether the method return type is an interface.
	 */
	boolean returnTypeIsInterface();
		String RETURN_TYPE_IS_INTERFACE_PROPERTY = "returnTypeIsInterface"; //$NON-NLS-1$

	/**
	 * Return whether the method return type is an enum.
	 */
	boolean returnTypeIsEnum();
		String RETURN_TYPE_IS_ENUM_PROPERTY = "returnTypeIsEnum"; //$NON-NLS-1$

	/**
	 * Return the names of the method return type's superclasses.
	 */
	ListIterable<String> getReturnTypeSuperclassNames();
		String RETURN_TYPE_SUPERCLASS_NAMES_LIST = "returnTypeSuperclassNames"; //$NON-NLS-1$

	/**
	 * Return the names of the method return type's interfaces.
	 */
	Iterable<String> getReturnTypeInterfaceNames();
		String RETURN_TYPE_INTERFACE_NAMES_COLLECTION = "returnTypeInterfaceNames"; //$NON-NLS-1$

	/**
	 * Return the names of the method return type's type arguments.
	 * The name for any argument that is an array will contain the appropriate
	 * number of bracket pairs.
	 * The names will not include any further generic type arguments.
	 */
	ListIterable<String> getReturnTypeTypeArgumentNames();
		String RETURN_TYPE_TYPE_ARGUMENT_NAMES_LIST = "returnTypeTypeArgumentNames"; //$NON-NLS-1$

	int getReturnTypeTypeArgumentNamesSize();

	String getReturnTypeTypeArgumentName(int index);

	ListIterable<String> getParameterTypeNames();
		String PARAMETER_TYPE_NAMES_LIST = "parameterTypeNames"; //$NON-NLS-1$

	int getParametersSize();
}
