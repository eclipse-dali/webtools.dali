/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;

public interface EclipseLinkConverterHolder extends JpaContextNode
{
	//************ customConverters *********************
	
	/**
	 * Return a list iterator of the custom converters.
	 * This will not be null.
	 */
	<T extends EclipseLinkCustomConverter> ListIterator<T> customConverters();

	/**
	 * Return the number of custom converters.
	 */
	int customConvertersSize();

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
	<T extends EclipseLinkObjectTypeConverter> ListIterator<T> objectTypeConverters();

	/**
	 * Return the number of object type converters.
	 */
	int objectTypeConvertersSize();

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
	<T extends EclipseLinkStructConverter> ListIterator<T> structConverters();

	/**
	 * Return the number of struct converters.
	 */
	int structConvertersSize();

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
	<T extends EclipseLinkTypeConverter> ListIterator<T> typeConverters();

	/**
	 * Return the number of type converters.
	 */
	int typeConvertersSize();

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