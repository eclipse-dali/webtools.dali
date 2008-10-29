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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Java resource model interface that corresponds to the Eclipselink
 * annotation org.eclipse.persistence.annotations.TypeConverter
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
public interface TypeConverterAnnotation extends NamedConverterAnnotation
{
	String ANNOTATION_NAME = EclipseLinkJPA.TYPE_CONVERTER;
	
	/**
	 * Corresponds to the dataType element of the TypeConverter annotation.
	 * Returns null if the dataType element does not exist in java.
	 */
	String getDataType();
	
	/**
	 * Corresponds to the dataType element of the TypeConverter annotation.
	 * Set to null to remove the dataType element.
	 */
	void setDataType(String value);
		String DATA_TYPE_PROPERTY = "dataTypeProperty"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the objectType element of the TypeConverter annotation.
	 * Returns null if the objectType element does not exist in java.
	 */
	String getObjectType();
	
	/**
	 * Corresponds to the objectType element of the TypeConverter annotation.
	 * Set to null to remove the objectType element.
	 */
	void setObjectType(String value);
		String OBJECT_TYPE_PROPERTY = "objectTypeProperty"; //$NON-NLS-1$
	
	/**
	 * Return the {@link TextRange} for the dataType element.  If the dataType element 
	 * does not exist return the {@link TextRange} for the TypeConverter annotation.
	 */
	TextRange getDataTypeTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the objectType element.  If the objectType element 
	 * does not exist return the {@link TextRange} for the TypeConverter annotation.
	 */
	TextRange getObjectTypeTextRange(CompilationUnit astRoot);
}
