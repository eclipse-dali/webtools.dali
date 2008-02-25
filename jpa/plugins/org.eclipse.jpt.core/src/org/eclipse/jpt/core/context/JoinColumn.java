/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;


public interface JoinColumn extends AbstractColumn, AbstractJoinColumn
{
	JoinColumn.Owner owner();

	/**
	 * interface allowing join columns to be used in multiple places
	 * (e.g. 1:1 mappings and join tables)
	 */
	interface Owner extends AbstractJoinColumn.Owner, AbstractColumn.Owner
	{
		/**
		 * return whether the specified table cannot be explicitly specified
		 * in the join column's 'table' element
		 */
		boolean tableNameIsInvalid(String tableName);

		/**
		 * return whether the join column's table can be specified explicitly
		 */
		boolean tableIsAllowed();

		/**
		 * return the entity referenced by the join column
		 */
		Entity targetEntity();

		/**
		 * return the join column's attribute name
		 */
		String attributeName();

		/**
		 * return the relationship mapping for this join column
		 */
		RelationshipMapping relationshipMapping();
		
		/**
		 * return the size of the joinColumns collection this join column is a part of
		 */
		int joinColumnsSize();
	}
}
