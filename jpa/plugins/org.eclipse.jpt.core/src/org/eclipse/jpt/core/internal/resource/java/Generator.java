/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;


public interface Generator extends JavaResource
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
		String NAME_PROPERTY = "nameProperty";
	
	/**
	 * Corresponds to the initialValue element of the TableGenerator or SequenceGenerator annotation.
	 * Returns -1 if the initialValue element does not exist in java.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	int getInitialValue();
	
	/**
	 * Corresponds to the initialValue element of the TableGenerator or SequenceGenerator annotation.
	 * Set to -1 to remove the initialValue element.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	void setInitialValue(int initialValue);
		String INITIAL_VALUE_PROPERTY = "initialValueProperty";
	
	/**
	 * Corresponds to the allocationSize element of the TableGenerator or SequenceGenerator annotation.
	 * Returns -1 if the allocationSize element does not exist in java.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	int getAllocationSize();
	
	/**
	 * Corresponds to the allocationSize element of the TableGenerator or SequenceGenerator annotation.
	 * Set to -1 to remove the allocationSize element.  If no other memberValuePairs exist
	 * the *Generator annotation will be removed as well.
	 */
	void setAllocationSize(int allocationSize);
		String ALLOCATION_SIZE_PROPERTY = "allocationSizeProperty";
	
	/**
	 * Return the ITextRange for the name element.  If the name element 
	 * does not exist return the ITextRange for the *Generator annotation.
	 */
	ITextRange nameTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the initialValue element.  If the initialValue element 
	 * does not exist return the ITextRange for the *Generator annotation.
	 */
	ITextRange initialValueTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the allocationSize element.  If the allocationSize element 
	 * does not exist return the ITextRange for the *Generator annotation.
	 */
	ITextRange allocationSizeTextRange(CompilationUnit astRoot);


}
