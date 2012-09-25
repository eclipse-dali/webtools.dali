/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Read-only
 * <ul>
 * <li>column
 * <li>join column
 * <li>primary key join column
 * <li>discriminator column
 * <li>order column
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyNamedColumn
	extends JpaContextNode
{
	// ********** name **********

	/**
	 * Return the specified name if present, otherwise return the default
	 * name.
	 */
	String getName();
	String getSpecifiedName();
		String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$
	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultName"; //$NON-NLS-1$

	/**
	 * Return the (best guess) text location of the column's name.
	 */
	TextRange getNameTextRange();

	// ********** table **********

	/**
	 * Return the name of the column's table. A column that does not have a
	 * <em>specified</em> table still has a table (as determined by
	 * the column's owner).
	 */
	String getTable();


	// ********** column definition **********

	String getColumnDefinition();
		String COLUMN_DEFINITION_PROPERTY = "columnDefinition"; //$NON-NLS-1$

	// ********** database stuff **********

	/**
	 * Return the column's datasource table.
	 */
	Table getDbTable();
		String DB_TABLE_PROPERTY = "dbTable"; //$NON-NLS-1$

	/**
	 * Return whether the column is found on the datasource.
	 */
	boolean isResolved();


	// ********** misc **********

	/**
	 * Return whether the column has a textual representation
	 * in its underlying resource.
	 */
	boolean isVirtual();


	// ********** owner **********

	/**
	 * Interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides).
	 */
	interface Owner
	{
		/**
		 * Return the name of the table which the column belongs to by default.
		 */
		String getDefaultTableName();

		/**
		 * Return the default column name.
		 */
		String getDefaultColumnName(ReadOnlyNamedColumn column);

		/**
		 * Return the database table for the specified table name.
		 */
		Table resolveDbTable(String tableName);

		JptValidator buildColumnValidator(ReadOnlyNamedColumn column);

		/**
		 * Return the column owner's text range. This can be returned by the
		 * column when its annotation is not present.
		 */
		TextRange getValidationTextRange();
	}
}
