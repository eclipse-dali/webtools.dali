/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context;

import java.util.ListIterator;

/**
 * Corresponds to a ObjectTypeConverter resource model object
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
public interface EclipseLinkObjectTypeConverter extends EclipseLinkNamedConverter
{
	String getDataType();	
	void setDataType(String dataType);
		String DATA_TYPE_PROPERTY = "dataTypeProperty"; //$NON-NLS-1$
		
	String getObjectType();	
	void setObjectType(String objectType);
		String OBJECT_TYPE_PROPERTY = "objectTypeProperty"; //$NON-NLS-1$

	
	// **************** conversion values **************************************

	/**
	 * Return a list iterator of the conversion values.
	 * This will not be null.
	 */
	<T extends EclipseLinkConversionValue> ListIterator<T> conversionValues();

	/**
	 * Return the number of conversion values.
	 */
	int conversionValuesSize();

	/**
	 * Add a conversion value to the object type mapping return the object 
	 * representing it.
	 */
	EclipseLinkConversionValue addConversionValue(int index);

	/**
	 * Add a conversion value to the object type mapping return the object 
	 * representing it.
	 */
	EclipseLinkConversionValue addConversionValue();

	/**
	 * Remove the conversion value at the given index from the entity.
	 */
	void removeConversionValue(int index);

	/**
	 * Remove the conversion value from the entity.
	 */
	void removeConversionValue(EclipseLinkConversionValue conversionValue);

	/**
	 * Move the conversion values from the source index to the target index.
	 */
	void moveConversionValue(int targetIndex, int sourceIndex);
		String CONVERSION_VALUES_LIST = "conversionValuesList"; //$NON-NLS-1$

	
	/**
	 * Returns a ListIterator of the ConversionValue dataValues.
	 * @return
	 */
	ListIterator<String> dataValues();
	
	
	// **************** default object value **************************************
	
	String getDefaultObjectValue();
	void setDefaultObjectValue(String defaultObjectValue);
		String DEFAULT_OBJECT_VALUE_PROPERTY = "defaultObjectValueProperty";

}
