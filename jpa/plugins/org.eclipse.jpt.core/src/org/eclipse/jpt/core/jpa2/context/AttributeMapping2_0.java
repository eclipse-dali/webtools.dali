/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;

/**
 * JPA 2.0 attribute mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface AttributeMapping2_0
	extends AttributeMapping
{
	/**
	 * Return the Canonical Metamodel field corresponding to the mapping.
	 * Return <code>null</code> if the mapping is not to be part of the
	 * Canonical Metamodel.
	 */
	MetamodelField getMetamodelField();

	/**
	 * Return the name of the mapping's type as used in the Canonical Metamodel.
	 */
	String getMetamodelTypeName();

	@SuppressWarnings("nls")
	Iterable<String> STANDARD_METAMODEL_FIELD_MODIFIERS =
		new ArrayIterable<String>(new String[] { "public", "static", "volatile" });
}
