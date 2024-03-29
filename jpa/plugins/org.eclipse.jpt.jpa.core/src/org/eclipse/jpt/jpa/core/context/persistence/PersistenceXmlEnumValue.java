/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.persistence;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;

/**
 * An enum that is used in the persistence.xml properties can implement this interface
 * and take advantage of helper methods in AbstractPersistenceUnitProperties for
 * getting and setting properties.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface PersistenceXmlEnumValue
{
	/**
	 * Return the value to be used in the property key/value pair
	 */
	String getPropertyValue();

	TransformerAdapter<Enum<?>, String> ENUM_NAME_TRANSFORMER = new EnumNameTransformer();
	class EnumNameTransformer
		extends TransformerAdapter<Enum<?>, String>
	{
		@Override
		public String transform(Enum<?> e) {
			return e.name();
		}
	}
}
