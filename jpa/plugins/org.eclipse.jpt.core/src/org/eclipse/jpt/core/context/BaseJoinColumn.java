/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.db.Table;

/**
 * Specified
 * <ul>
 * <li>join column
 * <li>primary key join column
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface BaseJoinColumn
	extends NamedColumn, ReadOnlyBaseJoinColumn
{
	void setSpecifiedReferencedColumnName(String value);

	/**
	 * Return the wrapper for the referenced column datasource table
	 */
	Table getReferencedColumnDbTable();

	boolean referencedColumnIsResolved();

	boolean isVirtual();


	// ********** owner **********

	/**
	 * Interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides).
	 */
	interface Owner
		extends ReadOnlyBaseJoinColumn.Owner, NamedColumn.Owner
	{
		/**
		 * Return the wrapper for the datasource table for the referenced column
		 */
		Table getReferencedColumnDbTable();
	}
}
