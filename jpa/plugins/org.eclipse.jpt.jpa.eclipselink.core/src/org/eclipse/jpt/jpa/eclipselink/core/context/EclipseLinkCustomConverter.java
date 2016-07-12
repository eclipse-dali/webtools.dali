/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * EclipseLink custom converter
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
public interface EclipseLinkCustomConverter
	extends EclipseLinkConverterClassConverter
{
	String ECLIPSELINK_CONVERTER_CLASS_NAME = "org.eclipse.persistence.mappings.converters.Converter"; //$NON-NLS-1$

	Class<EclipseLinkCustomConverter> getConverterType();

	/**
	 * A transformer that returns an {@link EclipseLinkCustomConverter} if the passed
	 * in {@link EclipseLinkConverter} can be cast as such;
	 * otherwise, it returns <code>null</code>.
	 */
	Transformer<EclipseLinkConverter, EclipseLinkCustomConverter> CONVERTER_TRANSFORMER = new ConverterTransformer<>(EclipseLinkCustomConverter.class);
}
