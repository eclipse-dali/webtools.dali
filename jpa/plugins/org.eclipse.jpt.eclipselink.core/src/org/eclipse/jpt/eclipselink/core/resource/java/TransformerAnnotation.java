/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface TransformerAnnotation extends JavaResourceNode
{
	
	/**
	 * Corresponds to the transformerClass element of the *Transformer annotation.
	 * Returns null if the transformerClass element does not exist in java.
	 * Returns the portion of the transformerClass preceding the .class.
	 * <p>
	 *     &#64;ReadTransformer(transformerClass=Employee.class)
	 * </p>
	 * will return "Employee"
	 **/
	String getTransformerClass();
	
	/**
	 * Corresponds to the transformerClass element of the *Transformer annotation.
	 * Set to null to remove the transformerClass element.
	 */
	void setTransformerClass(String transformerClass);
		String TRANSFORMER_CLASS_PROPERTY = "transformerClassProperty";

	/**
	 * Corresponds to the method element of the *Transformer annotation.
	 * Returns null if the method element does not exist in java.
	 **/
	String getMethod();
	
	/**
	 * Corresponds to the method element of the *Transformer annotation.
	 * Set to null to remove the method element.
	 */
	void setMethod(String method);
		String METHOD_PROPERTY = "methodProperty";
		
	/**
	 * Return the {@link TextRange} for the transformerClass element.  If the transformerClass element 
	 * does not exist return the {@link TextRange} for the *Transformer annotation.
	 */
	TextRange getTransformerClassTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the method element.  If the method element 
	 * does not exist return the {@link TextRange} for the *Transformer annotation.
	 */
	TextRange getMethodTextRange(CompilationUnit astRoot);

}
