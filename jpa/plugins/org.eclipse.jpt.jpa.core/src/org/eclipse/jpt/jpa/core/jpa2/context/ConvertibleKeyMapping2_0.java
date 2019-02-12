/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.Converter;

/**
 * JPA attribute mapping that has a map key converter (JPA 2.0 collection mappings)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.1
 * @since 3.1
 */
public interface ConvertibleKeyMapping2_0
	extends AttributeMapping2_0
{
	/**
	 * Return the mapping's key converter. Never <code>null</code>.
	 */
	Converter getMapKeyConverter();
		String MAP_KEY_CONVERTER_PROPERTY = "mapKeyConverter"; //$NON-NLS-1$

	/**
	 * Set the key converter type, adding the appropriate converter to the resource
	 * model and removing any other converters. Clear all converters if the
	 * specified type is <code>null</code>.
	 */
	void setMapKeyConverter(Class<? extends Converter> converterType);
}
