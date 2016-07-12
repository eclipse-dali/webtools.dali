/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * JPA temporal/map key temporal converter
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
public interface BaseTemporalConverter
	extends Converter
{
	Class<BaseTemporalConverter> getConverterType();

	String[] TEMPORAL_MAPPING_SUPPORTED_TYPES = {
		java.util.Date.class.getName(),
		java.util.Calendar.class.getName(),
		java.util.GregorianCalendar.class.getName()
	};
	
	TemporalType getTemporalType();
	void setTemporalType(TemporalType temporalType);
		String TEMPORAL_TYPE_PROPERTY = "temporalType"; //$NON-NLS-1$

	/**
	 * A transformer that returns an {@link BaseTemporalConverter} if the passed
	 * in {@link Converter} can be cast as such;
	 * otherwise, it returns <code>null</code>.
	 */
	Transformer<Converter, BaseTemporalConverter> CONVERTER_TRANSFORMER = new ConverterTransformer<>(BaseTemporalConverter.class);
}
