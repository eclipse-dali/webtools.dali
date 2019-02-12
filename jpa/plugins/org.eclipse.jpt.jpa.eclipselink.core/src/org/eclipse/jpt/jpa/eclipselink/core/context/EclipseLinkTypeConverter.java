/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

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
	Class<EclipseLinkTypeConverter> getConverterType();

	String getDataType();
	void setDataType(String dataType);
		String DATA_TYPE_PROPERTY = "dataType"; //$NON-NLS-1$

	String getFullyQualifiedDataType();	
		String FULLY_QUALIFIED_DATA_TYPE_PROPERTY = "fullyQualifiedDataType"; //$NON-NLS-1$

		
	String getObjectType();	
	void setObjectType(String objectType);
		String OBJECT_TYPE_PROPERTY = "objectType"; //$NON-NLS-1$

	String getFullyQualifiedObjectType();	
		String FULLY_QUALIFIED_OBJECT_TYPE_PROPERTY = "fullyQualifiedObjectType"; //$NON-NLS-1$

}
