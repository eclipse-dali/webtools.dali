/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;

/**
 * column or join column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface BaseColumn
	extends NamedColumn, ReadOnlyBaseColumn
{
	void setSpecifiedTable(String table);
	void setSpecifiedUnique(Boolean unique);
	void setSpecifiedNullable(Boolean nullable);
	void setSpecifiedInsertable(Boolean insertable);
	void setSpecifiedUpdatable(Boolean updatable);

	boolean tableNameIsInvalid();

	//TODO This is used by ColumnComposite to get a list of possible associated tables,
	//but right now that list isn't going to update in the UI except when we repopulate
	/**
	 * Return a list of table names that are valid for this column
	 */
	Iterator<String> candidateTableNames();


	// ********** owner **********

	/**
	 * Interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides).
	 */
	interface Owner
		extends NamedColumn.Owner
		// ReadOnlyBaseColumn does not define an Owner
	{
		/**
		 * return whether the given table cannot be explicitly specified
		 * in the column's 'table' element
		 */
		boolean tableNameIsInvalid(String tableName);
		
		/**
		 * Return a list of table names that are valid for this column
		 */
		Iterator<String> candidateTableNames();
	}
}
