/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;


public interface IGenerator extends IJpaContextNode
{

	String getName();
	void setName(String value);
		String NAME_PROPERTY = "nameProperty";

	int getInitialValue();

	int getDefaultInitialValue();
		String DEFAULT_INITIAL_VALUE_PROPERTY = "defaultInitialValueProperty";

	int getSpecifiedInitialValue();
	void setSpecifiedInitialValue(int value);
		String SPECIFIED_INITIAL_VALUE_PROPERTY = "specifiedInitialValueProperty";


	int getAllocationSize();

	int getDefaultAllocationSize();
		int DEFAULT_ALLOCATION_SIZE = 50;
		String DEFAULT_ALLOCATION_SIZE_PROPERTY = "defaultAllocationSizeProperty";
	
	int getSpecifiedAllocationSize();
	void setSpecifiedAllocationSize(int value);
		String SPECIFIED_ALLOCATION_SIZE_PROPERTY = "specifiedAllocationSizeProperty";

}
