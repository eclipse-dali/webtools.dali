/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jpt.common.utility.MethodSignature;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * Java source code or binary method
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.0
 */
public interface JavaResourceMethod
		extends JavaResourceAttribute {
	
	// ***** method name *****
	
	/**
	 * The Java resource method's name does not change.
	 */
	String getMethodName();
	
	
	// ***** parameter type names *****
	
	String PARAMETER_TYPE_NAMES_LIST = "parameterTypeNames"; //$NON-NLS-1$
	
	ListIterable<String> getParameterTypeNames();
		
	String getParameterTypeName(int index);
	
	int getParametersSize();
	
	
	// ***** constructor *****
	
	String CONSTRUCTOR_PROPERTY = "constructor"; //$NON-NLS-1$
	
	boolean isConstructor();
	
	
	// ***** misc *****
	
	/**
	 * Return whether the Java resource persistent attribute is for the specified
	 * method.
	 */
	boolean isFor(MethodSignature methodSignature, int occurrence);

	/**
	 * Call this instead of initialize(CompilationUnit)
	 * TODO remove initialize(CompilationUnit) from the hierarchy
	 */
	void initialize(MethodDeclaration methodDeclaration);

	/**
	 * Call this instead of synchronizeWith(CompilationUnit)
	 * TODO remove synchronizeWith(CompilationUnit) from the hierarchy
	 */
	void synchronizeWith(MethodDeclaration methodDeclaration);
}
