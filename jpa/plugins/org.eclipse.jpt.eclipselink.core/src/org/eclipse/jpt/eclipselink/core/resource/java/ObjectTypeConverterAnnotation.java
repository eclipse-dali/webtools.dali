/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Java resource model interface that corresponds to the Eclipselink
 * annotation org.eclipse.persistence.annotations.ObjectTypeConverter
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
public interface ObjectTypeConverterAnnotation extends TypeConverterAnnotation
{
	
	String ANNOTATION_NAME = EclipseLinkJPA.OBJECT_TYPE_CONVERTER;

	/**
	 * Corresponds to the conversionValues element of the ObjectTypeConverter annotation.
	 * Returns an empty iterator if the conversionValues element does not exist in java.
	 */
	ListIterator<ConversionValueAnnotation> conversionValues();
	
	ConversionValueAnnotation conversionValueAt(int index);
	
	int indexOfConversionValue(ConversionValueAnnotation conversionValue);
	
	int conversionValuesSize();

	ConversionValueAnnotation addConversionValue(int index);
	
	void removeConversionValue(int index);
	
	void moveConversionValue(int targetIndex, int sourceIndex);
	
		String CONVERSION_VALUES_LIST = "conversionValuesList";
		
		
	/**
	 * Corresponds to the defaultObjectValue element of the ObjectTypeConverter annotation.
	 * Returns null if the defaultObjectValue element does not exist in java.
	 */
	String getDefaultObjectValue();
	
	/**
	 * Corresponds to the defaultObjectValue element of the ObjectTypeConverter annotation.
	 * Set to null to remove the defaultObjectValue element.
	 */
	void setDefaultObjectValue(String value);
		String DEFAULT_OBJECT_VALUE_PROPERTY = "defaultObjectValueProperty";
		
	/**
	 * Return the {@link TextRange} for the defaultObjectValue element.  If the defaultObjectValue element 
	 * does not exist return the {@link TextRange} for the ObjectTypeConverter annotation.
	 */
	TextRange getDefaultObjectValueTextRange(CompilationUnit astRoot);
	
}
