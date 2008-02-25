/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;


public interface Generator extends JpaContextNode
{

	String getName();
	void setName(String value);
		String NAME_PROPERTY = "nameProperty";

	Integer getInitialValue();

	Integer getDefaultInitialValue();
		String DEFAULT_INITIAL_VALUE_PROPERTY = "defaultInitialValueProperty";

	Integer getSpecifiedInitialValue();
	void setSpecifiedInitialValue(Integer value);
		String SPECIFIED_INITIAL_VALUE_PROPERTY = "specifiedInitialValueProperty";


	Integer getAllocationSize();

	Integer getDefaultAllocationSize();
		Integer DEFAULT_ALLOCATION_SIZE = Integer.valueOf(50);
		String DEFAULT_ALLOCATION_SIZE_PROPERTY = "defaultAllocationSizeProperty";
	
	Integer getSpecifiedAllocationSize();
	void setSpecifiedAllocationSize(Integer value);
		String SPECIFIED_ALLOCATION_SIZE_PROPERTY = "specifiedAllocationSizeProperty";

}
