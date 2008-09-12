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

import org.eclipse.jpt.db.Column;
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
public interface BaseJoinColumn
	extends NamedColumn
{
	String getReferencedColumnName();
	String getSpecifiedReferencedColumnName();
	void setSpecifiedReferencedColumnName(String value);
		String SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY = "specifiedReferencedColumnName"; //$NON-NLS-1$
	String getDefaultReferencedColumnName();
		String DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY = "defaultReferencedColumnName"; //$NON-NLS-1$
	
	/**
	 * Return the wrapper for the datasource referenced column
	 */
	Column getReferencedDbColumn();

	/**
	 * Return whether the reference column is found on the datasource
	 */
	boolean isReferencedColumnResolved();

	boolean isVirtual();

	interface Owner extends NamedColumn.Owner
	{
		/**
		 * Return the wrapper for the datasource table for the referenced column
		 */
		Table getReferencedColumnDbTable();
		
		boolean isVirtual(BaseJoinColumn joinColumn);
	}
}
