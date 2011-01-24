/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Strategy describing how two entities are related, either for a
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
 * @see ReadOnlyAssociationOverride
 * @see ReadOnlyRelationship
 */
public interface ReadOnlyJoiningStrategy
	extends JpaContextNode
{
	/**
	 * Return the joining strategy's relationship reference.
	 */
	ReadOnlyRelationship getRelationship();
	
	/**
	 * Return the table name associated with the joining strategy's columns.
	 * The join table name, for instance, or in the case of a bi-directional relationship, 
	 * the table of the owning relationship.
	 */
	String getTableName();
}
