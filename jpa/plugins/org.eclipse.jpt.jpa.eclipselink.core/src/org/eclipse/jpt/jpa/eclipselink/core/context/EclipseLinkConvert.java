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

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;

/**
 * EclipseLink convert (not to be confused with EclipseLink converter)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 2.1
 */
public interface EclipseLinkConvert
	extends Converter
{

	EclipseLinkPersistenceUnit getPersistenceUnit();

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
	 * A transformer that returns an {@link EclipseLinkConvert} if the passed
	 * in {@link Converter} can be cast as such;
	 * otherwise, it returns <code>null</code>.
	 */
	Transformer<Converter, EclipseLinkConvert> CONVERTER_TRANSFORMER = new ConverterTransformer();
	class ConverterTransformer
		extends TransformerAdapter<Converter, EclipseLinkConvert>
	{
		@Override
		public EclipseLinkConvert transform(Converter converter) {
			return (converter.getType() == EclipseLinkConvert.class) ? (EclipseLinkConvert) converter : null;
		}
	}
}
