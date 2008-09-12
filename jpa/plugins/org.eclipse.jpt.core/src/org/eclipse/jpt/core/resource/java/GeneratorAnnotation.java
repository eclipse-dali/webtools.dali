/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface GeneratorAnnotation
	extends JavaResourceNode
{
	/**
	 * Corresponds to the name element of the TableGenerator or SequenceGenerator annotation.
	 * Returns null if the name element does not exist in java.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	String getName();
	
	/**
	 * Corresponds to the name element of the TableGenerator or SequenceGenerator annotation.
	 * Set to null to remove the name element.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	void setName(String name);
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the initialValue element of the TableGenerator or SequenceGenerator annotation.
	 * Returns null if the initialValue element does not exist in java.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	Integer getInitialValue();
	
	/**
	 * Corresponds to the initialValue element of the TableGenerator or SequenceGenerator annotation.
	 * Set to null to remove the initialValue element.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	void setInitialValue(Integer initialValue);
		String INITIAL_VALUE_PROPERTY = "initialValue"; //$NON-NLS-1$
		
	/**
	 * Corresponds to the allocationSize element of the TableGenerator or SequenceGenerator annotation.
	 * Returns null if the allocationSize element does not exist in java.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	Integer getAllocationSize();
	
	/**
	 * Corresponds to the allocationSize element of the TableGenerator or SequenceGenerator annotation.
	 * Set to null to remove the allocationSize element.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	void setAllocationSize(Integer allocationSize);
		String ALLOCATION_SIZE_PROPERTY = "allocationSize"; //$NON-NLS-1$
	
	/**
	 * Return the {@link TextRange} for the name element.  If the name element 
	 * does not exist return the {@link TextRange} for the *Generator annotation.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the initialValue element.  If the initialValue element 
	 * does not exist return the {@link TextRange} for the *Generator annotation.
	 */
	TextRange getInitialValueTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the allocationSize element.  If the allocationSize element 
	 * does not exist return the {@link TextRange} for the *Generator annotation.
	 */
	TextRange getAllocationSizeTextRange(CompilationUnit astRoot);


}
