/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.core.utility.TextRange;


/**
 * <ul>
 * <li>column
 * <li>join column
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TableColumn
	extends NamedColumn
{
	// ********** table **********

	String getSpecifiedTableName();
		String SPECIFIED_TABLE_NAME_PROPERTY = "specifiedTableName"; //$NON-NLS-1$
	String getDefaultTableName();
		String DEFAULT_TABLE_NAME_PROPERTY = "defaultTable"; //$NON-NLS-1$

	/**
	 * Return the (best guess) text location of the column's table name.
	 */
	TextRange getTableNameValidationTextRange();


	// ********** validation **********

	/**
	 * Return whether the column's table name is invalid.
	 */
	boolean tableNameIsInvalid();

	/**
	 * Return a list of table names that are valid for this column
	 */
	Iterable<String> getCandidateTableNames();


	// ********** parent adapter **********

	/**
	 * Interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides).
	 */
	interface ParentAdapter
		extends NamedColumn.ParentAdapter
	{
		/**
		 * return whether the given table cannot be explicitly specified
		 * in the column's 'table' element
		 */
		boolean tableNameIsInvalid(String tableName);

		/**
		 * Return a list of table names that are valid for this column
		 */
		Iterable<String> getCandidateTableNames();
	}
}
