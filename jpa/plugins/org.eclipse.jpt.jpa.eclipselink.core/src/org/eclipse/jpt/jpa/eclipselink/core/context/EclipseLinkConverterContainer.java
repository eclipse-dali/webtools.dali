/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;

/**
 * EclipseLink converter container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface EclipseLinkConverterContainer
	extends JpaContextNode
{
	Iterable<EclipseLinkConverter> getConverters();
	
	int getConvertersSize();

	int getNumberSupportedConverters();

	
	//************ customConverters *********************
	
	/**
	 * Return a list iterator of the custom converters.
	 * This will not be null.
	 */
	ListIterable<? extends EclipseLinkCustomConverter> getCustomConverters();

	/**
	 * Return the number of custom converters.
	 */
	int getCustomConvertersSize();

	/**
	 * Add a custom converter to the converter holder, return the object representing it.
	 */
	EclipseLinkCustomConverter addCustomConverter(int index);

	/**
	 * Remove the custom converter at the index from the converter holder.
	 */
	void removeCustomConverter(int index);

	/**
	 * Remove the custom converter at from the converter holder.
	 */
	void removeCustomConverter(EclipseLinkCustomConverter converter);

	/**
	 * Move the custom converter from the source index to the target index.
	 */
	void moveCustomConverter(int targetIndex, int sourceIndex);

	String CUSTOM_CONVERTERS_LIST = "customConverters"; //$NON-NLS-1$

	
	//************ object type converters *********************

	/**
	 * Return a list iterator of the object type converters.
	 * This will not be null.
	 */
	ListIterable<? extends EclipseLinkObjectTypeConverter> getObjectTypeConverters();

	/**
	 * Return the number of object type converters.
	 */
	int getObjectTypeConvertersSize();

	/**
	 * Add a object type converter to the converter holder, return the object representing it.
	 */
	EclipseLinkObjectTypeConverter addObjectTypeConverter(int index);

	/**
	 * Remove the object type converter at the index from the converter holder.
	 */
	void removeObjectTypeConverter(int index);

	/**
	 * Remove the object type converter at from the converter holder.
	 */
	void removeObjectTypeConverter(EclipseLinkObjectTypeConverter converter);

	/**
	 * Move the object type converter from the source index to the target index.
	 */
	void moveObjectTypeConverter(int targetIndex, int sourceIndex);

	String OBJECT_TYPE_CONVERTERS_LIST = "objectTypeConverters"; //$NON-NLS-1$
	
	
	//************ struct converters *********************

	/**
	 * Return a list iterator of the struct converters.
	 * This will not be null.
	 */
	ListIterable<? extends EclipseLinkStructConverter> getStructConverters();

	/**
	 * Return the number of struct converters.
	 */
	int getStructConvertersSize();

	/**
	 * Add a struct converter to the converter holder, return the object representing it.
	 */
	EclipseLinkStructConverter addStructConverter(int index);

	/**
	 * Remove the struct converter at the index from the converter holder.
	 */
	void removeStructConverter(int index);

	/**
	 * Remove the struct converter at from the converter holder.
	 */
	void removeStructConverter(EclipseLinkStructConverter converter);

	/**
	 * Move the struct converter from the source index to the target index.
	 */
	void moveStructConverter(int targetIndex, int sourceIndex);

	String STRUCT_CONVERTERS_LIST = "structConverters"; //$NON-NLS-1$

	
	//************ type converters *********************

	/**
	 * Return a list iterator of the type converters.
	 * This will not be null.
	 */
	ListIterable<? extends EclipseLinkTypeConverter> getTypeConverters();

	/**
	 * Return the number of type converters.
	 */
	int getTypeConvertersSize();

	/**
	 * Add a type converter to the converter holder, return the object representing it.
	 */
	EclipseLinkTypeConverter addTypeConverter(int index);

	/**
	 * Remove the type converter at the index from the converter holder.
	 */
	void removeTypeConverter(int index);

	/**
	 * Remove the type converter at from the converter holder.
	 */
	void removeTypeConverter(EclipseLinkTypeConverter converter);

	/**
	 * Move the type converter from the source index to the target index.
	 */
	void moveTypeConverter(int targetIndex, int sourceIndex);

	String TYPE_CONVERTERS_LIST = "typeConverters"; //$NON-NLS-1$
}
