/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.Iterator;

/**
 * Read-only database unique constraint
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyUniqueConstraint
	extends JpaContextNode
{
	/**
	 * Return the unique constraint's column names.
	 */
	Iterable<String> getColumnNames();
		String COLUMN_NAMES_LIST = "columnNames"; //$NON-NLS-1$

	/**
	 * Return the number of column names in the unique constraint.
	 */
	int getColumnNamesSize();

	/**
	 * Return the column name at the specified index.
	 */
	String getColumnName(int index);


	// ********** owner **********

	/**
	 * All containers must implement this interface.
	 */
	interface Owner {
		Iterator<String> candidateUniqueConstraintColumnNames();
	}
}
