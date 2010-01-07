/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.db.Table;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AttributeOverride extends BaseOverride, Column.Owner
{
	Column getColumn();

	AttributeOverride.Owner getOwner();
	
	AttributeOverride setVirtual(boolean virtual);
	
	interface Owner extends BaseOverride.Owner
	{
		/**
		 * Return the column of the mapping or attribute override with the given attribute name.
		 * Return null if it does not exist.  This column mapping
		 * will be found in the mapped superclass (or embeddable), not in the owning entity
		 */
		Column resolveOverriddenColumn(String attributeName);

		/**
		 * Return the name of the table which the column belongs to by default
		 */
		String getDefaultTableName();

		/**
		 * Return whether the 'table' element is allowed to be specified explicitly.
		 * It is not allowed for join columns inside of join tables.
		 */
		boolean tableIsAllowed();

		/**
		 * return whether the given table cannot be explicitly specified
		 * in the column's 'table' element
		 */
		boolean tableNameIsInvalid(String tableName);

		/**
		 * Return the database table for the specified table name
		 */
		Table getDbTable(String tableName);
	}
}