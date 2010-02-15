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

import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface NamedColumn
	extends JpaContextNode
{
	String getName();

	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultName"; //$NON-NLS-1$

	String getSpecifiedName();
	void setSpecifiedName(String value);
		String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$

	/**
	 * Return the table name for this column.  Columns that don't have a
	 * specified table still have a table that they belong to.
	 */
	String getTable();

	String getColumnDefinition();

	void setColumnDefinition(String value);
		String COLUMN_DEFINITION_PROPERTY = "columnDefinition"; //$NON-NLS-1$


	/**
	 * Return the wrapper for the datasource column
	 */
	Column getDbColumn();

	/**
	 * Return the wrapper for the datasource table
	 */
	Table getDbTable();

	/**
	 * Return whether the column is found on the datasource.
	 */
	boolean isResolved();
	
	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner {
		/**
		 * Return the type mapping that contains the column.
		 */
		TypeMapping getTypeMapping();

		/**
		 * Return the name of the table which the column belongs to by default
		 */
		String getDefaultTableName();

		/**
		 * Return the database table for the specified table name
		 */
		Table getDbTable(String tableName);
		
		/**
		 * Return the default column name
		 */
		String getDefaultColumnName();

		/**
		 * Return a validation message for the column's name not resolving on the 
		 * table either specified or default. Use the given text range in the message
		 */
		IMessage buildUnresolvedNameMessage(NamedColumn column, TextRange textRange);
	}
}
