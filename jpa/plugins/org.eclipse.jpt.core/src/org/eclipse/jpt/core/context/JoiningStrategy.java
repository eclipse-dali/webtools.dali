/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.db.Table;

/**
 * Represents how the information in two entities are joined together via a 
 * {@link RelationshipMapping}
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 * 
 * @see {@link RelationshipMapping}
 * @see {@link RelationshipReference}
 */
public interface JoiningStrategy extends JpaContextNode
{
	/**
	 * Return the relationship reference that owns this strategy
	 */
	RelationshipReference getRelationshipReference();
	
	/**
	 * Add this strategy to the relationship reference
	 */
	void addStrategy();
	
	/**
	 * Remove this strategy from the relationship reference
	 */
	void removeStrategy();
	
	/**
	 * Return whether the mapping can be overridden with an association override
	 */
	boolean isOverridableAssociation();
	
	/**
	 * Return the table name associated with columns on this joining strategy.
	 * The join table name, for instance, or in the case of a bi-directional relationship, 
	 * the table of the owning relationship.
	 */
	String getTableName();
	
	/**
	 * Return the database table for the specified table name
	 */
	Table getDbTable(String tableName);

}
