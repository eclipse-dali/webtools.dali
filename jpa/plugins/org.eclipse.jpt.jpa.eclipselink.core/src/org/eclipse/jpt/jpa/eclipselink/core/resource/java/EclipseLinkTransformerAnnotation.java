/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Common protocol among:
 *     org.eclipse.persistence.annotations.ReadTransformer
 *     org.eclipse.persistence.annotations.WriteTransformer
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface EclipseLinkTransformerAnnotation
	extends Annotation
{
	/**
	 * Corresponds to the 'transformerClass' element of the *Transformer annotation.
	 * Return null if the element does not exist in Java.
	 * Return the portion of the value preceding ".class".
	 * <pre>
	 *     &#64;ReadTransformer(transformerClass=Employee.class)
	 * </pre>
	 * will return "Employee".
	 */
	String getTransformerClass();
		String TRANSFORMER_CLASS_PROPERTY = "transformerClass"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'transformerClass' element of the *Transformer annotation.
	 * Set to null to remove the element.
	 */
	void setTransformerClass(String transformerClass);

	/**
	 * Return the {@link TextRange} for the 'transformerClass' element. If the element 
	 * does not exist return the {@link TextRange} for the *Transformer annotation.
	 */
	TextRange getTransformerClassTextRange(CompilationUnit astRoot);


	/**
	 * Corresponds to the 'method' element of the *Transformer annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getMethod();
		String METHOD_PROPERTY = "method"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'method' element of the *Transformer annotation.
	 * Set to null to remove the element.
	 */
	void setMethod(String method);

	/**
	 * Return the {@link TextRange} for the 'method' element. If the element 
	 * does not exist return the {@link TextRange} for the *Transformer annotation.
	 */
	TextRange getMethodTextRange(CompilationUnit astRoot);

}
