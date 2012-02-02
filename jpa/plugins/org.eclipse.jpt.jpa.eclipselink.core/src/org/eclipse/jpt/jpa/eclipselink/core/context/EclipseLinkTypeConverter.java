/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jdt.core.IType;

/**
 * EclipseLink type converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 2.1
 */
public interface EclipseLinkTypeConverter
	extends EclipseLinkConverter
{
	String getDataType();	
	void setDataType(String dataType);
		String DATA_TYPE_PROPERTY = "dataType"; //$NON-NLS-1$

	/**
	 * Return the {@link IType} that is resolved from the data type class name
	 * or null if none exists.
	 */
	IType getDataTypeJdtType();	
		
	String getObjectType();	
	void setObjectType(String objectType);
		String OBJECT_TYPE_PROPERTY = "objectType"; //$NON-NLS-1$

	/**
	 * Return the {@link IType} that is resolved from the object type class name
	 * or null if none exists.
	 */
	IType getObjectTypeJdtType();	

}
