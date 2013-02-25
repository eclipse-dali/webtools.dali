/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;

/**
 * Corresponds to a ConversionValue resource model object
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
public interface EclipseLinkConversionValue
	extends JpaContextModel
{
	EclipseLinkObjectTypeConverter getParent();

	String getDataValue();	
	void setDataValue(String dataValue);
		String DATA_VALUE_PROPERTY = "dataValue"; //$NON-NLS-1$
	Transformer<EclipseLinkConversionValue, String> DATA_VALUE_TRANSFORMER = new DataValueTransformer();
	class DataValueTransformer
		extends TransformerAdapter<EclipseLinkConversionValue, String>
	{
		@Override
		public String transform(EclipseLinkConversionValue conversionValue) {
			return conversionValue.getDataValue();
		}
	}
		
	String getObjectValue();	
	void setObjectValue(String objectValue);
		String OBJECT_VALUE_PROPERTY = "objectValue"; //$NON-NLS-1$
	Transformer<EclipseLinkConversionValue, String> OBJECT_VALUE_TRANSFORMER = new ObjectValueTransformer();
	class ObjectValueTransformer
		extends TransformerAdapter<EclipseLinkConversionValue, String>
	{
		@Override
		public String transform(EclipseLinkConversionValue conversionValue) {
			return conversionValue.getObjectValue();
		}
	}

	/**
	 * Return whether the conversion value has the same state as the specified
	 * conversion value.
	 */
	boolean isEquivalentTo(EclipseLinkConversionValue conversionValue);
}
