/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
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
 * Corresponds to the JPA annotation
 * javax.persistence.TableGenerator
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.0
 */
public interface TableGeneratorAnnotation
	extends GeneratorAnnotation
{
	String ANNOTATION_NAME = JPA.TABLE_GENERATOR;

	/**
	 * Corresponds to the 'table' element of the TableGenerator annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getTable();
		String TABLE_PROPERTY = "table"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'table' element of the TableGenerator annotation.
	 * Set to null to remove the element.
	 */
	void setTable(String table);

	/**
	 * Return the {@link TextRange} for the 'table' element. If the element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getTableTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'table' element.
	 * Return false if the element does not exist.
	 */
	boolean tableTouches(int pos, CompilationUnit astRoot);


	/**
	 * Corresponds to the 'schema' element of the TableGenerator annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getSchema();
		String SCHEMA_PROPERTY = "schema"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'schema' element of the TableGenerator annotation.
	 * Set to null to remove the element.
	 */
	void setSchema(String schema);

	/**
	 * Return the {@link TextRange} for the 'schema' element. If the element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getSchemaTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'schema' element.
	 * Return false if the element does not exist.
	 */
	boolean schemaTouches(int pos, CompilationUnit astRoot);


	/**
	 * Corresponds to the 'catalog' element of the TableGenerator annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getCatalog();
		String CATALOG_PROPERTY = "catalog"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'catalog' element of the TableGenerator annotation.
	 * Set to null to remove the element.
	 */
	void setCatalog(String catalog);

	/**
	 * Return the {@link TextRange} for the 'catalog' element. If the element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getCatalogTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'catalog' element.
	 * Return false if the element does not exist.
	 */
	boolean catalogTouches(int pos, CompilationUnit astRoot);


	/**
	 * Corresponds to the 'pkColumnName' element of the TableGenerator annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getPkColumnName();
		String PK_COLUMN_NAME_PROPERTY = "pkColumnName"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'pkColumnName' element of the TableGenerator annotation.
	 * Set to null to remove the element.
	 */
	void setPkColumnName(String pkColumnName);

	/**
	 * Return the {@link TextRange} for the 'pkColumnName' element. If the element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getPkColumnNameTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'pkColumnName' element.
	 * Return false if the element does not exist.
	 */
	boolean pkColumnNameTouches(int pos, CompilationUnit astRoot);


	/**
	 * Corresponds to the 'valueColumnName' element of the TableGenerator annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getValueColumnName();
		String VALUE_COLUMN_NAME_PROPERTY = "valueColumnName"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'valueColumnName' element of the TableGenerator annotation.
	 * Set to null to remove the element.
	 */
	void setValueColumnName(String valueColumnName);

	/**
	 * Return the {@link TextRange} for the 'valueColumnName' element. If the element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getValueColumnNameTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'valueColumnName' element.
	 * Return false if the element does not exist.
	 */
	boolean valueColumnNameTouches(int pos, CompilationUnit astRoot);


	/**
	 * Corresponds to the 'pkColumnValue' element of the TableGenerator annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getPkColumnValue();
		String PK_COLUMN_VALUE_PROPERTY = "pkColumnValue"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'pkColumnValue' element of the TableGenerator annotation.
	 * Set to null to remove the element.
	 */
	void setPkColumnValue(String pkColumnValue);

	/**
	 * Return the {@link TextRange} for the 'pkColumnValue' element. If the element 
	 * does not exist return the {@link TextRange} for the TableGenerator annotation.
	 */
	TextRange getPkColumnValueTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified position touches the 'pkColumnValue' element.
	 * Return false if the element does not exist.
	 */
	boolean pkColumnValueTouches(int pos, CompilationUnit astRoot);


	/**
	 * Corresponds to the 'uniqueConstraints' element of the TableGenerator annotation.
	 * Return null if the element does not exist in Java.
	 */
	ListIterator<UniqueConstraintAnnotation> uniqueConstraints();
		String UNIQUE_CONSTRAINTS_LIST = "uniqueConstraints"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'uniqueConstraints' element of the TableGenerator annotation.
	 */
	int uniqueConstraintsSize();

	/**
	 * Corresponds to the 'uniqueConstraints' element of the TableGenerator annotation.
	 */
	UniqueConstraintAnnotation uniqueConstraintAt(int index);

	/**
	 * Corresponds to the 'uniqueConstraints' element of the TableGenerator annotation.
	 */
	int indexOfUniqueConstraint(UniqueConstraintAnnotation uniqueConstraint);

	/**
	 * Corresponds to the 'uniqueConstraints' element of the TableGenerator annotation.
	 */
	UniqueConstraintAnnotation addUniqueConstraint(int index);

	/**
	 * Corresponds to the 'uniqueConstraints' element of the TableGenerator annotation.
	 */
	void moveUniqueConstraint(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'uniqueConstraints' element of the TableGenerator annotation.
	 */
	void removeUniqueConstraint(int index);

}
