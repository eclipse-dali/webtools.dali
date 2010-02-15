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

import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

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
	 * @see #getReferencedDbColumn()
	 */
	boolean isReferencedColumnResolved();

	/**
	 * Return the wrapper for the referenced column datasource table
	 */
	Table getReferencedColumnDbTable();

	boolean isVirtual();

	interface Owner extends NamedColumn.Owner
	{
		/**
		 * Return the wrapper for the datasource table for the referenced column
		 */
		Table getReferencedColumnDbTable();
		
		boolean isVirtual(BaseJoinColumn joinColumn);
		
		/**
		 * return the size of the joinColumns collection this join column is a part of
		 */
		int joinColumnsSize();

		/**
		 * On a virtual object validation message for when the column name does not resolve on the table
		 */
		IMessage buildUnresolvedReferencedColumnNameMessage(BaseJoinColumn column, TextRange textRange);

		/**
		 * Validation message for when multiple join columns exist and the
		 * name is not specified and the owner is virtual
		 */
		IMessage buildUnspecifiedNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange);

		/**
		 * Validation message for when multiple join columns exist and the 
		 * referenced column name is not specified and the owner is virtual
		 */
		IMessage buildUnspecifiedReferencedColumnNameMultipleJoinColumnsMessage(BaseJoinColumn column, TextRange textRange);
	}
}
