/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.db.internal.Schema;

public interface ITable extends IJpaContextNode
{
	String getName();
	
	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultNameProperty";

	String getSpecifiedName();
	void setSpecifiedName(String value);
		String SPECIFIED_NAME_PROPERTY = "specifiedNameProperty";

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


//	EList<IUniqueConstraint> getUniqueConstraints();
//	IUniqueConstraint createUniqueConstraint(int index);

	org.eclipse.jpt.db.internal.Table dbTable();

	Schema dbSchema();
	
	/**
	 * Return true if this table is connected to a datasource
	 */
	boolean isConnected();

	/** 
	 * Return true if this table's schema can be resolved to a schema on the active connection
	 */
	boolean hasResolvedSchema();

	/** 
	 * Return true if this can be resolved to a table on the active connection
	 */
	boolean isResolved();

	ITextRange nameTextRange(CompilationUnit astRoot);

	ITextRange schemaTextRange(CompilationUnit astRoot);
	
	ITextRange catalogTextRange(CompilationUnit astRoot);

//
//	class UniqueConstraintOwner implements IUniqueConstraint.Owner
//	{
//		private final ITable table;
//
//		public UniqueConstraintOwner(ITable table) {
//			super();
//			this.table = table;
//		}
//
//		public Iterator<String> candidateUniqueConstraintColumnNames() {
//			return this.table.dbTable().columnNames();
//		}
//	}
}
