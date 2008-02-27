/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;


public interface NamedColumn extends JpaContextNode
{
	String getName();

	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultNameProperty";

	String getSpecifiedName();
	void setSpecifiedName(String value);
		String SPECIFIED_NAME_PROPERTY = "specifiedNameProperty";


	String getColumnDefinition();

	void setColumnDefinition(String value);
		String COLUMN_DEFINITION_PROPERTY = "columnDefinitionProperty";


	/**
	 * Return the wrapper for the datasource column
	 */
	Column dbColumn();

	/**
	 * Return the wrapper for the datasource table
	 */
	Table dbTable();

	/**
	 * Return whether the column is found on the datasource.
	 */
	boolean isResolved();

	Owner owner();
	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner
	{
		/**
		 * Return the type mapping that contains the column.
		 */
		TypeMapping typeMapping();

		/**
		 * Return the wrapper for the datasource table for the given table name
		 */
		Table dbTable(String tableName);
		
		/**
		 * Return the default column name
		 */
		String defaultColumnName();
	}
}
