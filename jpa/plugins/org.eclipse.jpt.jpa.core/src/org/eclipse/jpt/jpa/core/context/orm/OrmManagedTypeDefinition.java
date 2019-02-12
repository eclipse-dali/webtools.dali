/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.ManagedType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlManagedType;

/**
 * Map an <code>orm.xml</code> context managed type and resource managed type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 3.3
 */
public interface OrmManagedTypeDefinition {

	/**
	 * Return a class that corresponds to the context managed type's type.
	 * @see ManagedType#getManagedTypeType()
	 */
	Class<? extends ManagedType> getContextManagedTypeType();

	/**
	 * Return a class that corresponds to the resource managed type's type.
	 * @see XmlManagedType#getManagedTypeType()
	 */
	Class<? extends XmlManagedType> getResourceManagedTypeType();

	/**
	 * Build a context managed type for the specified parent 
	 * and resource managed type, using the specified factory.
	 */
	OrmManagedType buildContextManagedType(JpaContextModel parent, XmlManagedType resourceManagedType, OrmXmlContextModelFactory factory);
}
