/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;

/**
 * EclipseLink object type converter
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface EclipseLinkObjectTypeConverter
	extends EclipseLinkConverter
{
	// ********** data type **********

	String getDataType();	
	void setDataType(String dataType);
		String DATA_TYPE_PROPERTY = "dataType"; //$NON-NLS-1$
		
	
	// ********** object type **********

	String getObjectType();	
	void setObjectType(String objectType);
		String OBJECT_TYPE_PROPERTY = "objectType"; //$NON-NLS-1$

	
	// ********** conversion values **********

	/**
	 * Return the converter's conversion values.
	 */
	ListIterable<? extends EclipseLinkConversionValue> getConversionValues();
		String CONVERSION_VALUES_LIST = "conversionValues"; //$NON-NLS-1$

	/**
	 * Return the number of conversion values.
	 */
	int getConversionValuesSize();

	/**
	 * Add a conversion value to the converter at the specified index
	 * and return it.
	 */
	EclipseLinkConversionValue addConversionValue(int index);

	/**
	 * Add a conversion value to the converter and return it.
	 */
	EclipseLinkConversionValue addConversionValue();

	/**
	 * Remove the conversion value at the specified index.
	 */
	void removeConversionValue(int index);

	/**
	 * Remove the specified conversion value.
	 */
	void removeConversionValue(EclipseLinkConversionValue conversionValue);

	/**
	 * Move the conversion value from the specified source index to the
	 * specified target index.
	 */
	void moveConversionValue(int targetIndex, int sourceIndex);


	// ********** data values **********

	/**
	 * Return the converter's data values.
	 */
	Iterable<String> getDataValues();

	/**
	 * Return the number of data values.
	 */
	int getDataValuesSize();

	
	// ********** default object value **********
	
	String getDefaultObjectValue();
	void setDefaultObjectValue(String value);
		String DEFAULT_OBJECT_VALUE_PROPERTY = "defaultObjectValue"; //$NON-NLS-1$
}
