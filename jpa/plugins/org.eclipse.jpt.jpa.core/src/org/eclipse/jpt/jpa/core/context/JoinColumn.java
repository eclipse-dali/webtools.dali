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
 * Join column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JoinColumn
	extends BaseJoinColumn, BaseColumn
{
	// ********** parent adapter **********

	/**
	 * Interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides).
	 */
	interface ParentAdapter
		extends BaseJoinColumn.ParentAdapter, TableColumn.ParentAdapter
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
