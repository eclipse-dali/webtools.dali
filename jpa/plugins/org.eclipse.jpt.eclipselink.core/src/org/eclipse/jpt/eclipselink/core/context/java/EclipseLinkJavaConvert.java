/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.java;

import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;

/**
 * Corresponds to a Convert resource model object
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
public interface EclipseLinkJavaConvert extends EclipseLinkConvert, JavaJpaContextNode, JavaConverter
{
	
	String ECLIPSE_LINK_CONVERTER = "eclipseLinkConverter";
	
	String getConverterName();
	
	String getDefaultConverterName();
		String DEFAULT_CONVERTER_NAME_PROPERTY = "defaultConverterNameProperty";

	String getSpecifiedConverterName();
	
	void setSpecifiedConverterName(String converterName);
		String SPECIFIED_CONVERTER_NAME_PROPERTY = "specifiedConverterNameProperty";
	
	/**
	 * Reserved name for specifying a serialized object converter.  In this
	 * case there does not need to be a corresponding @Converter defined.
	 */
	String SERIALIZED_CONVERTER = "serialized";
	
	/**
	 * Reserved name for specifying a class instance converter.  Will use a ClassInstanceConverter
	 * on the associated mapping.  When using a ClassInstanceConverter the database representation is a 
	 * String representing the Class name and the object-model representation is an instance 
	 * of that class built with a no-args constructor
	 * In this case there does not need to be a corresponding @Converter defined.
	 */
	String CLASS_INSTANCE_CONVERTER = "class-instance";
	
	/**
	 * Reserved name for specifying no converter.  This can be used to override a situation where either 
	 *  another converter is defaulted or another converter is set.
	 *  In this case there does not need to be a corresponding @Converter defined.
	 */
	String NO_CONVERTER = "none";
	
	String DEFAULT_CONVERTER_NAME = NO_CONVERTER;

}
