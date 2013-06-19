/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
 * table generator
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface TableGenerator
	extends DbGenerator
{
	int DEFAULT_INITIAL_VALUE = 0;

	Class<TableGenerator> getGeneratorType();

	// ********** table **********

	/**
	 * Return the specified table name if present, otherwise return the default
	 * table name.
	 */
	String getTableName();
	String getSpecifiedTableName();
	void setSpecifiedTableName(String tableName);
		String SPECIFIED_TABLE_NAME_PROPERTY = "specifiedTableName"; //$NON-NLS-1$
	String getDefaultTableName();
		String DEFAULT_TABLE_NAME_PROPERTY = "defaultTableName"; //$NON-NLS-1$


	// ********** schema **********

	/**
	 * Return the specified schema if present, otherwise return the default
	 * schema.
	 */
	String getSchema();
	String getSpecifiedSchema();
	void setSpecifiedSchema(String value);
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchema"; //$NON-NLS-1$
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema"; //$NON-NLS-1$


	// ********** catalog **********

	/**
	 * Return the specified catalog if present, otherwise return the default
	 * catalog.
	 */
	String getCatalog();
	String getSpecifiedCatalog();
	void setSpecifiedCatalog(String value);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalog"; //$NON-NLS-1$
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$


	// ********** primary key column name **********

	/**
	 * Return the specified primary key colum name if present, otherwise return
	 * the default primary key colum name.
	 */
	String getPkColumnName();
	String getSpecifiedPkColumnName();
	void setSpecifiedPkColumnName(String value);
		String SPECIFIED_PK_COLUMN_NAME_PROPERTY = "specifiedPkColumnName"; //$NON-NLS-1$
	String getDefaultPkColumnName();
		String DEFAULT_PK_COLUMN_NAME_PROPERTY = "defaultPkColumnName"; //$NON-NLS-1$


	// ********** value column name **********

	/**
	 * Return the specified value colum name if present, otherwise return
	 * the default value colum name.
	 */
	String getValueColumnName();
	String getSpecifiedValueColumnName();
	void setSpecifiedValueColumnName(String value);
		String SPECIFIED_VALUE_COLUMN_NAME_PROPERTY = "specifiedValueColumnName"; //$NON-NLS-1$
	String getDefaultValueColumnName();
		String DEFAULT_VALUE_COLUMN_NAME_PROPERTY = "defaultValueColumnName"; //$NON-NLS-1$


	// ********** primary key column value **********

	/**
	 * Return the specified primary key colum value if present, otherwise return
	 * the default primary key colum value.
	 */
	String getPkColumnValue();
	String getSpecifiedPkColumnValue();
	void setSpecifiedPkColumnValue(String value);
		String SPECIFIED_PK_COLUMN_VALUE_PROPERTY = "specifiedPkColummValue"; //$NON-NLS-1$
	String getDefaultPkColumnValue();
		String DEFAULT_PK_COLUMN_VALUE_PROPERTY = "defaultPkColummValue"; //$NON-NLS-1$


	// ********** unique constraints **********

	<T extends SpecifiedUniqueConstraint> Iterable<T> getUniqueConstraints();
	int getUniqueConstraintsSize();
	SpecifiedUniqueConstraint getUniqueConstraint(int index);
	SpecifiedUniqueConstraint addUniqueConstraint();
	SpecifiedUniqueConstraint addUniqueConstraint(int index);
	void removeUniqueConstraint(int index);
	void removeUniqueConstraint(SpecifiedUniqueConstraint uniqueConstraint);
	void moveUniqueConstraint(int targetIndex, int sourceIndex);
		String UNIQUE_CONSTRAINTS_LIST = "uniqueConstraints"; //$NON-NLS-1$


	// ********** database stuff **********

	/**
	 * Return the generator's database table.
	 * Return null if the generator's table (name) is invalid.
	 */
	Table getDbTable();

}
