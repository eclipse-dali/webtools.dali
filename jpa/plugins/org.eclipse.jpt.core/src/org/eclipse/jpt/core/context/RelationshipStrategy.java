/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
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
 * Strategy describing how two entities are joined, either for a
 * {@link RelationshipMapping} or an {@link AssociationOverride}:<ul>
 * <li>join column
 * <li>join table
 * <li>"mapped by"
 * <li>primary key join column
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see RelationshipMapping
 * @see AssociationOverride
 * @see Relationship
 * 
 * @version 2.3
 * @since 2.2
 */
public interface RelationshipStrategy
	extends ReadOnlyRelationshipStrategy
{
// TODO bjv rename to RelationshipStrategy - move to inside Relationship interface?
	Relationship getRelationship();

	/**
	 * Add this strategy to the relationship reference.
	 */
	void addStrategy();
	
	/**
	 * Remove this strategy from the relationship reference.
	 */
	void removeStrategy();
	
	/**
	 * Return whether the mapping can be overridden with an association override.
	 */
	boolean isOverridable();
	
	/**
	 * Return the database table for the specified table name.
	 */
	Table resolveDbTable(String tableName);

	/**
	 * Return whether the specified table cannot be explicitly specified
	 * in the column's <code>table</code> element.
	 */
	boolean tableNameIsInvalid(String tableName);

	/**
	 * Return a message description used when the column's table is not valid 
	 * in this context. This will be passed in as a parameter to a validation
	 * message. Here is an example where the description is what is returned 
	 * by the implementation:
	 * <p>location:
	 * Table "table name" for map key column "column name" 
	 * <p>description:
	 * does not match join table
	 */
	String getColumnTableNotValidDescription();
}
