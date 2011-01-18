/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * JPA attribute mapping that has a converter (e.g. basic, ID, version).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 2.3
 * @since 2.1
 */
public interface ConvertibleMapping
	extends AttributeMapping
{
	/**
	 * Return the mapping's converter. Never <code>null</code>.
	 */
	Converter getConverter();
		String CONVERTER_PROPERTY = "converter"; //$NON-NLS-1$

	/**
	 * Set the converter type, adding the appropriate converter to the resource
	 * model and removing any other converters. Clear all converters if the
	 * specified type is <code>null</code>.
	 */
	void setConverter(Class<? extends Converter> converterType);
}
