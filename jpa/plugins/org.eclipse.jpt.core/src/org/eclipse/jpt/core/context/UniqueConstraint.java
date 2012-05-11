/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * 
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
public interface UniqueConstraint
	extends JpaContextNode
{
	
	// **************** column names **************************************

	/**
	 * Return a list iterator of the column names.
	 * This will not be null.
	 */
	ListIterator<String> columnNames();
	
	/**
	 * Return the number of column names.
	 */
	int columnNamesSize();
		
	/**
	 * Add a column name to the list at the given index
	 */
	void addColumnName(int index, String columnName);
	
	/**
	 * Remove the column name at the given index from the unique constraint
	 */
	void removeColumnName(int index);
	
	/**
	 * Remove the column name from the unique constraint
	 */
	void removeColumnName(String columnName);
	
	/**
	 * Move the column name from the source index to the target index.
	 */
	void moveColumnName(int targetIndex, int sourceIndex);
		String COLUMN_NAMES_LIST = "columnNames"; //$NON-NLS-1$
		
	/**
	 * All containers must implement this interface.
	 */
	interface Owner
	{
		Iterator<String> candidateUniqueConstraintColumnNames();
	}
}
