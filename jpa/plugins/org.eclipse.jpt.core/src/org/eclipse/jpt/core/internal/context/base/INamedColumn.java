/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.db.internal.Column;
import org.eclipse.jpt.db.internal.Table;


public interface INamedColumn extends IJpaContextNode
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

	/**
	 * Return the (best guess) text location of the column's name.
	 */
	ITextRange nameTextRange(CompilationUnit astRoot);

	
	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner
	{
		/**
		 * Return the type mapping that contains the column.
		 */
		ITypeMapping typeMapping();

		/**
		 * Return the column owner's text range. This can be returned by the
		 * column when its annotation is not present.
		 */
		ITextRange validationTextRange(CompilationUnit astRoot);

		/**
		 * Return the wrapper for the datasource table for the given table name
		 */
		Table dbTable(String tableName);
	}
}
