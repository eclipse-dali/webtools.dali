/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.jpa.db.Table;

/**
 * Read-only
 * <ul>
 * <li>join column
 * <li>primary key join column
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyBaseJoinColumn
	extends ReadOnlyNamedColumn
{
	// ********** referenced column name **********

	/**
	 * Return the specified referenced column name if present,
	 * otherwise return the default referenced column name.
	 */
	String getReferencedColumnName();
	String getSpecifiedReferencedColumnName();
		String SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY = "specifiedReferencedColumnName"; //$NON-NLS-1$
	String getDefaultReferencedColumnName();
		String DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY = "defaultReferencedColumnName"; //$NON-NLS-1$
	

	// ********** misc **********

	/**
	 * Return the wrapper for the referenced column datasource table
	 */
	Table getReferencedColumnDbTable();

	boolean referencedColumnIsResolved();

	// TODO bjv rename?
	boolean isVirtual();

	boolean isDefault();


	// ********** owner **********

	/**
	 * Interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides).
	 */
	interface Owner
		extends ReadOnlyNamedColumn.Owner
	{
		/**
		 * Return the wrapper for the datasource table for the referenced column
		 */
		Table getReferencedColumnDbTable();

		boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn);
		
		/**
		 * Return the number of join columns in the owner's list the join
		 * column belongs to.
		 */
		int joinColumnsSize();
	}
}
