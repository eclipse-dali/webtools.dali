/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.context.Converter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;

public interface ConverterHolder extends JpaContextNode
{
	//************ converters *********************
	
	/**
	 * Return a list iterator of the converters.
	 * This will not be null.
	 */
	<T extends Converter> ListIterator<T> converters();

	/**
	 * Return the number of converters.
	 */
	int convertersSize();

	/**
	 * Add a converter to the converter holder, return the object representing it.
	 */
	Converter addConverter(int index);

	/**
	 * Remove the converter at the index from the converter holder.
	 */
	void removeConverter(int index);

	/**
	 * Remove the converter at from the converter holder.
	 */
	void removeConverter(Converter converter);

	/**
	 * Move the converter from the source index to the target index.
	 */
	void moveConverter(int targetIndex, int sourceIndex);

	String CONVERTERS_LIST = "convertersList"; //$NON-NLS-1$

	
	//************ object type converters *********************

	/**
	 * Return a list iterator of the object type converters.
	 * This will not be null.
	 */
	<T extends ObjectTypeConverter> ListIterator<T> objectTypeConverters();

	/**
	 * Return the number of object type converters.
	 */
	int objectTypeConvertersSize();

	/**
	 * Add a object type converter to the converter holder, return the object representing it.
	 */
	ObjectTypeConverter addObjectTypeConverter(int index);

	/**
	 * Remove the object type converter at the index from the converter holder.
	 */
	void removeObjectTypeConverter(int index);

	/**
	 * Remove the object type converter at from the converter holder.
	 */
	void removeObjectTypeConverter(ObjectTypeConverter converter);

	/**
	 * Move the object type converter from the source index to the target index.
	 */
	void moveObjectTypeConverter(int targetIndex, int sourceIndex);

	String OBJECT_TYPE_CONVERTERS_LIST = "objectTypeConvertersList"; //$NON-NLS-1$
	
	
	//************ struct converters *********************

	/**
	 * Return a list iterator of the struct converters.
	 * This will not be null.
	 */
	<T extends StructConverter> ListIterator<T> structConverters();

	/**
	 * Return the number of struct converters.
	 */
	int structConvertersSize();

	/**
	 * Add a struct converter to the converter holder, return the object representing it.
	 */
	StructConverter addStructConverter(int index);

	/**
	 * Remove the struct converter at the index from the converter holder.
	 */
	void removeStructConverter(int index);

	/**
	 * Remove the struct converter at from the converter holder.
	 */
	void removeStructConverter(StructConverter converter);

	/**
	 * Move the struct converter from the source index to the target index.
	 */
	void moveStructConverter(int targetIndex, int sourceIndex);

	String STRUCT_CONVERTERS_LIST = "structConvertersList"; //$NON-NLS-1$

	
	//************ type converters *********************

	/**
	 * Return a list iterator of the type converters.
	 * This will not be null.
	 */
	<T extends TypeConverter> ListIterator<T> typeConverters();

	/**
	 * Return the number of type converters.
	 */
	int typeConvertersSize();

	/**
	 * Add a type converter to the converter holder, return the object representing it.
	 */
	TypeConverter addTypeConverter(int index);

	/**
	 * Remove the type converter at the index from the converter holder.
	 */
	void removeTypeConverter(int index);

	/**
	 * Remove the type converter at from the converter holder.
	 */
	void removeTypeConverter(TypeConverter converter);

	/**
	 * Move the type converter from the source index to the target index.
	 */
	void moveTypeConverter(int targetIndex, int sourceIndex);

	String TYPE_CONVERTERS_LIST = "typeConvertersList"; //$NON-NLS-1$

}