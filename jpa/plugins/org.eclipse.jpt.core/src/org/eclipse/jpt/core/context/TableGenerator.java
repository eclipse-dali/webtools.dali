/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.db.Schema;
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
public interface TableGenerator extends Generator
{
	Integer DEFAULT_INITIAL_VALUE = Integer.valueOf(0);

	String getTable();
	
	String getDefaultTable();
		String DEFAULT_TABLE_PROPERTY = "defaultTableProperty";
	String getSpecifiedTable();
	void setSpecifiedTable(String value);
		String SPECIFIED_TABLE_PROPERTY = "specifiedTableProperty";



	String getCatalog();

	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalogProperty";

	String getSpecifiedCatalog();
	void setSpecifiedCatalog(String value);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalogProperty";



	String getSchema();

	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchemaProperty";

	String getSpecifiedSchema();
	void setSpecifiedSchema(String value);
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchemaProperty";



	String getPkColumnName();

	String getDefaultPkColumnName();
		String DEFAULT_PK_COLUMN_NAME_PROPERTY = "defaultPkColumnNameProperty";

	String getSpecifiedPkColumnName();
	void setSpecifiedPkColumnName(String value);
		String SPECIFIED_PK_COLUMN_NAME_PROPERTY = "specifiedPkColumnNameProperty";


	String getValueColumnName();

	String getDefaultValueColumnName();
		String DEFAULT_VALUE_COLUMN_NAME_PROPERTY = "defaultValueColumnNameProperty";
	
	String getSpecifiedValueColumnName();
	void setSpecifiedValueColumnName(String value);
		String SPECIFIED_VALUE_COLUMN_NAME_PROPERTY = "specifiedValueColumnNameProperty";


	String getPkColumnValue();

	String getDefaultPkColumnValue();
		String DEFAULT_PK_COLUMN_VALUE_PROPERTY = "defaultPkColummValueProperty";

	String getSpecifiedPkColumnValue();
	void setSpecifiedPkColumnValue(String value);
		String SPECIFIED_PK_COLUMN_VALUE_PROPERTY = "specifiedPkColummValueProperty";


//	EList<IUniqueConstraint> getUniqueConstraints();

//	IUniqueConstraint createUniqueConstraint(int index);

	/**
	 * Return a db Schema object with the specified/default schema name.
	 * This can return null if no Schema exists on the database with that name.
	 */
	Schema getDbSchema();

	/**
	 * Return a db Table object with the specified/default table name.
	 * This can return null if no Table exists on the database with that name.
	 */
	Table getDbTable();


//	class UniqueConstraintOwner implements IUniqueConstraint.Owner
//	{
//		private final ITableGenerator tableGenerator;
//
//		public UniqueConstraintOwner(ITableGenerator tableGenerator) {
//			super();
//			this.tableGenerator = tableGenerator;
//		}
//
//		public Iterator<String> candidateUniqueConstraintColumnNames() {
//			return this.tableGenerator.dbTable().columnNames();
//		}
//	}
}
