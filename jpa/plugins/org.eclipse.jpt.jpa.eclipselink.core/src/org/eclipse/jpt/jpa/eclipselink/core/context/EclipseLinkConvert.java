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

import org.eclipse.jpt.jpa.core.context.Converter;

/**
 * EclipseLink convert (not to be confused with EclipseLink converter)
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
public interface EclipseLinkConvert
	extends Converter
{
	String getConverterName();
	
	String getSpecifiedConverterName();
	void setSpecifiedConverterName(String converterName);
		String SPECIFIED_CONVERTER_NAME_PROPERTY = "specifiedConverterName"; //$NON-NLS-1$
	
	String getDefaultConverterName();
		String DEFAULT_CONVERTER_NAME_PROPERTY = "defaultConverterName"; //$NON-NLS-1$

	/**
	 * Reserved name for specifying a serialized object converter.
	 * In this case a corresponding @CustomConverter is unnecessary.
	 */
	String SERIALIZED_CONVERTER = "serialized"; //$NON-NLS-1$
	
	/**
	 * Reserved name for specifying a class instance converter.
	 * Will use a ClassInstanceConverter
	 * on the associated mapping. When using a ClassInstanceConverter the database representation is a 
	 * String representing the Class name and the object-model representation is an instance 
	 * of that class built with the zero-argument constructor.
	 * In this case a corresponding @CustomConverter is unnecessary.
	 */
	String CLASS_INSTANCE_CONVERTER = "class-instance"; //$NON-NLS-1$
	
	/**
	 * Reserved name for specifying no converter.
	 * This can be used to override a situation where either 
	 * another converter is defaulted or another converter is set.
	 * In this case a corresponding @CustomConverter is unnecessary.
	 */
	String NO_CONVERTER = "none"; //$NON-NLS-1$
	
	String[] RESERVED_CONVERTER_NAMES = {NO_CONVERTER, CLASS_INSTANCE_CONVERTER, SERIALIZED_CONVERTER};
	
	String DEFAULT_CONVERTER_NAME = NO_CONVERTER;

	/**
	 * Return the mapping's converter.
	 */
	EclipseLinkConverter getConverter();
		String CONVERTER_PROPERTY = "converter"; //$NON-NLS-1$
	
	/**
	 * Possible values for the converter type are:<ul>
	 * <li>{@link EclipseLinkCustomConverter}
	 * <li>{@link EclipseLinkTypeConverter}
	 * <li>{@link EclipseLinkObjectTypeConverter}
	 * <li>{@link EclipseLinkStructConverter}
	 * <li><code>null</code>
	 * </ul>
	 */
	void setConverter(Class<? extends EclipseLinkConverter> converterType);
}
