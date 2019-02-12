/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Interface used by persistence unit to gather up persistent types.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.3
 */
public interface PersistentTypeContainer
	extends ManagedTypeContainer
{
	/**
	 * Return the container's persistent types. The returned list may contain
	 * multiple persistent types for the same Java class; e.g.<ul>
	 * <li>the same type is specified in both the <code>persistence.xml</code> and
	 *     <code>orm.xml</code> files
	 * <Li>the same type is specified multiple times in the same
	 *     <code>persistence.xml</code> or <code>orm.xml</code> file
	 * <li>the same type is in a jar file specified in the
	 *     <code>persistence.xml</code> file and is specified in the
	 *     <code>persistence.xml</code> file and/or an <code>orm.xml</code> file
	 * </ul>
	 */
	Iterable<? extends PersistentType> getPersistentTypes();

	Transformer<PersistentTypeContainer, Iterable<? extends PersistentType>> TRANSFORMER = new PersistentTypesTransformer();
	class PersistentTypesTransformer
		extends TransformerAdapter<PersistentTypeContainer, Iterable<? extends PersistentType>>
	{
		@Override
		public Iterable<? extends PersistentType> transform(PersistentTypeContainer container) {
			return container.getPersistentTypes();
		}
	}

	/**
	 * Return the persistent type with the specified name.
	 * Return <code>null</code> if the persistent type is not found.
	 * If the persistent unit has more than one persistent type with the
	 * specified name, return the first one found, using the following
	 * search order:<ul>
	 * <li>mapping files
	 * <li>classes
	 * <li>jar files
	 * </ul>
	 */
	PersistentType getPersistentType(String typeName);
}
