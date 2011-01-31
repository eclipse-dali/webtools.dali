/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Common protocol among
 *     javax.persistence.SequenceGenerator
 *     javax.persistence.TableGenerator
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface GeneratorAnnotation
	extends Annotation
{
	/**
	 * Corresponds to the 'name' element of the *Generator annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'name' element of the *Generator annotation.
	 * Set to null to remove the element.
	 */
	void setName(String name);

	/**
	 * Return the {@link TextRange} for the 'name' element. If the element 
	 * does not exist return the {@link TextRange} for the *Generator annotation.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);


	/**
	 * Corresponds to the 'initialValue' element of the *Generator annotation.
	 * Return null if the element does not exist in Java.
	 */
	Integer getInitialValue();
		String INITIAL_VALUE_PROPERTY = "initialValue"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'initialValue' element of the *Generator annotation.
	 * Set to null to remove the element.
	 */
	void setInitialValue(Integer initialValue);

	/**
	 * Return the {@link TextRange} for the 'initialValue' element. If the element 
	 * does not exist return the {@link TextRange} for the *Generator annotation.
	 */
	TextRange getInitialValueTextRange(CompilationUnit astRoot);


	/**
	 * Corresponds to the 'allocationSize' element of the *Generator annotation.
	 * Return null if the element does not exist in Java.
	 */
	Integer getAllocationSize();
		String ALLOCATION_SIZE_PROPERTY = "allocationSize"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'allocationSize' element of the *Generator annotation.
	 * Set to null to remove the element.
	 */
	void setAllocationSize(Integer allocationSize);

	/**
	 * Return the {@link TextRange} for the 'allocationSize' element. If the element 
	 * does not exist return the {@link TextRange} for the *Generator annotation.
	 */
	TextRange getAllocationSizeTextRange(CompilationUnit astRoot);


}
