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

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.db.Table;

/**
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
public interface BaseJoinColumn
	extends NamedColumn
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

	/**
	 * Return the (best guess) text location of the join column's
	 * referenced column name.
	 */
	TextRange getReferencedColumnNameTextRange();
	

	// ********** referenced column **********

	/**
	 * Return the wrapper for the referenced column datasource table
	 */
	Table getReferencedColumnDbTable();

	boolean referencedColumnIsResolved();


	// ********** parent adapter **********

	/**
	 * Interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides).
	 */
	interface ParentAdapter
		extends NamedColumn.ParentAdapter
	{
		/**
		 * Return the wrapper for the datasource table for the referenced column
		 */
		Table getReferencedColumnDbTable();
		
		/**
		 * Return the number of join columns in the parent's list the join
		 * column belongs to.
		 */
		int getJoinColumnsSize();
	}
}
