/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Read-only join column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyJoinColumn
	extends ReadOnlyBaseJoinColumn, ReadOnlyBaseColumn
{
	/**
	 * Interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides).
	 */
	interface Owner
		extends ReadOnlyBaseJoinColumn.Owner
		// ReadOnlyBaseColumn does not define an Owner
	{
		/**
		 * The target of the relationship will usually be the target entity.
		 * In the case of a target foreign key relationship the source and target
		 * are swapped.
		 */
		Entity getRelationshipTarget();

		/**
		 * return the join column's attribute name
		 */
		String getAttributeName();
	}
}
