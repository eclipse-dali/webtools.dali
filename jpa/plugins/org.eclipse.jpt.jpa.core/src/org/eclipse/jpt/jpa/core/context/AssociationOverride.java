/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Association override
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AssociationOverride
	extends Override_
{
	/**
	 * Return the overridden relationship mapping.
	 * Return <code>null</code> if it does not exist.
	 * This relationship mapping will be found in the mapped superclass
	 * or embeddable type, not in the parent (entity/embedded mapping/
	 * element collection mapping).
	 */
	RelationshipMapping getMapping();

	OverrideRelationship getRelationship();
}
