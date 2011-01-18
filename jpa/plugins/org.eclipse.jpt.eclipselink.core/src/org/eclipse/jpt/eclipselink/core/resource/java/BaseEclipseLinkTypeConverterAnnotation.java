/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
 * Common protocol among:
 *     org.eclipse.persistence.annotations.TypeConverter
 *     org.eclipse.persistence.annotations.ObjectTypeConverter
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface BaseEclipseLinkTypeConverterAnnotation
	extends EclipseLinkNamedConverterAnnotation
{
	/**
	 * Corresponds to the 'dataType' element of the TypeConverter annotation.
	 * Returns null if the element does not exist in Java.
	 */
	String getDataType();
		String DATA_TYPE_PROPERTY = "dataType"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'dataType' element of the TypeConverter annotation.
	 * Set to null to remove the element.
	 */
	void setDataType(String dataType);
	
	/**
	 * Return the {@link TextRange} for the 'dataType' element. If the element 
	 * does not exist return the {@link TextRange} for the TypeConverter annotation.
	 */
	TextRange getDataTypeTextRange(CompilationUnit astRoot);

	/**
	 * Return the fully-qualified data type name as resolved by the
	 * AST's bindings.
	 * <pre>
	 *     &#64;TypeConverter(dataType = MyDataType.class)
	 * </pre>
	 * will return <code>"model.MyDataType"</code> if there is an import for
	 * <code>model.MyDataType</code>.
	 */
	String getFullyQualifiedDataType();

	/**
	 * Corresponds to the 'objectType' element of the TypeConverter annotation.
	 * Returns null if the element does not exist in Java.
	 */
	String getObjectType();
		String OBJECT_TYPE_PROPERTY = "objectType"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'objectType' element of the TypeConverter annotation.
	 * Set to null to remove the element.
	 */
	void setObjectType(String objectType);
	
	/**
	 * Return the {@link TextRange} for the 'objectType' element. If the element 
	 * does not exist return the {@link TextRange} for the TypeConverter annotation.
	 */
	TextRange getObjectTypeTextRange(CompilationUnit astRoot);

	/**
	 * Return the fully-qualified object type name as resolved by the
	 * AST's bindings.
	 * <pre>
	 *     &#64;TypeConverter(dataType = MyObjectType.class)
	 * </pre>
	 * will return <code>"model.MyObjectType"</code> if there is an import for
	 * <code>model.MyObjectType</code>.
	 */
	String getFullyQualifiedObjectType();
}
