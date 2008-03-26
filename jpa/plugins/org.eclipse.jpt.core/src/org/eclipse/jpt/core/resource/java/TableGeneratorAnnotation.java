/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Corresponds to the javax.persistence.TableGenerator annotation
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TableGeneratorAnnotation extends GeneratorAnnotation
{
	
	String ANNOTATION_NAME = JPA.TABLE_GENERATOR;

	/**
	 * Corresponds to the table element of the TableGenerator annotation.
	 * Returns null if the table element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getTable();
	
	/**
	 * Corresponds to the table element of the TableGenerator annotation.
	 * Set to null to remove the table element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setTable(String table);
		String TABLE_PROPERTY = "tableProperty";
		
	/**
	 * Corresponds to the catalog element of the TableGenerator annotation.
	 * Returns null if the catalog element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getCatalog();
	
	/**
	 * Corresponds to the catalog element of the TableGenerator annotation.
	 * Set to null to remove the catalog element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setCatalog(String catalog);
		String CATALOG_PROPERTY = "catalogProperty";
	
	/**
	 * Corresponds to the schema element of the TableGenerator annotation.
	 * Returns null if the schema element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getSchema();
	
	/**
	 * Corresponds to the schema element of the TableGenerator annotation.
	 * Set to null to remove the schema element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setSchema(String schema);
		String SCHEMA_PROPERTY = "schemaProperty";
	
	/**
	 * Corresponds to the pkColumnName element of the TableGenerator annotation.
	 * Returns null if the pkColumnName element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getPkColumnName();
	
	/**
	 * Corresponds to the pkColumnName element of the TableGenerator annotation.
	 * Set to null to remove the pkColumnName element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setPkColumnName(String pkColumnName);
		String PK_COLUMN_NAME_PROPERTY = "pkColumnNameProperty";
	
	/**
	 * Corresponds to the valueColumnName element of the TableGenerator annotation.
	 * Returns null if the valueColumnName element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getValueColumnName();
	
	/**
	 * Corresponds to the valueColumnName element of the TableGenerator annotation.
	 * Set to null to remove the valueColumnName element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setValueColumnName(String valueColumnName);
		String VALUE_COLUMN_NAME_PROPERTY = "valueColumnNameProperty";
	
	/**
	 * Corresponds to the pkColumnValue element of the TableGenerator annotation.
	 * Returns null if the pkColumnValue element does not exist in java.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	String getPkColumnValue();
	
	/**
	 * Corresponds to the pkColumnValue element of the TableGenerator annotation.
	 * Set to null to remove the pkColumnValue element.  If no other memberValuePairs exist
	 * the TableGenerator annotation will be removed as well.
	 */
	void setPkColumnValue(String pkColumnValue);
		String PK_COLUMN_VALUE_PROPERTY = "pkColumnValueProperty";
	
	ListIterator<UniqueConstraint> uniqueConstraints();
	
	UniqueConstraint uniqueConstraintAt(int index);
	
	int indexOfUniqueConstraint(UniqueConstraint uniqueConstraint);
	
	int uniqueConstraintsSize();

	UniqueConstraint addUniqueConstraint(int index);
	
	void removeUniqueConstraint(int index);
	
	void moveUniqueConstraint(int targetIndex, int sourceIndex);
		String UNIQUE_CONSTRAINTS_LIST = "uniqueConstraintsList";

	/**
	 * Return the {@link TextRange} for the table element.  If the table element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getTableTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the catalog element.  If the catalog element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getCatalogTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the schema element.  If the schema element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getSchemaTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the pkColumnName element.  If the pkColumnName element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getPkColumnNameTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the valueColumnName element.  If the valueColumnName element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getValueColumnNameTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the pkColumnValue element.  If the pkColumnValue element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getPkColumnValueTextRange(CompilationUnit astRoot);

}
