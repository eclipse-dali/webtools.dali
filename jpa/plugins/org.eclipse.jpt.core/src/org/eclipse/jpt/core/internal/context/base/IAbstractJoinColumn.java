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


public interface IAbstractJoinColumn extends INamedColumn
{
	String getReferencedColumnName();
	String getDefaultReferencedColumnName();
	String getSpecifiedReferencedColumnName();
	void setSpecifiedReferencedColumnName(String value);
		String SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY = "specifiedReferencedColumnName";
		String DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY = "defaultReferencedColumnName";
	
	/**
	 * Return the wrapper for the datasource referenced column
	 */
	Column dbReferencedColumn();

	/**
	 * Return whether the reference column is found on the datasource
	 */
	boolean isReferencedColumnResolved();

	/**
	 * Return the (best guess) text location of the referenced column name
	 */
	ITextRange referencedColumnNameTextRange(CompilationUnit astRoot);

	boolean isVirtual();

	interface Owner extends INamedColumn.Owner
	{
		/**
		 * Return the wrapper for the datasource table for the referenced column
		 */
		Table dbReferencedColumnTable();

		int joinColumnsSize();
		
		int indexOf(IAbstractJoinColumn joinColumn);
		
		boolean isVirtual(IAbstractJoinColumn joinColumn);
	}
}
