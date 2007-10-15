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

}
