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
 * Mapping relationship<ul>
 * <li>1:1
 * <li>1:m
 * <li>m:1
 * <li>m:m
 * </ul>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingRelationship
	extends Relationship
{
	/**
	 * Return whether the relationship's mapping is the relationship owner.
	 * @see RelationshipMapping#isRelationshipOwner()
	 */
	boolean isOwner();
	
	/**
	 * Return whether the specified mapping owns the relationship.
	 * @see RelationshipMapping#isOwnedBy(AttributeMapping)
	 */
	boolean isOwnedBy(RelationshipMapping mapping);

	/**
	 * Return whether the relationship's mapping is a target foreign key
	 * relationship.
	 * A one-to-many mapping with a join column will have the foreign key
	 * in the target table.
	 */
	boolean isTargetForeignKey();

	/**
	 * Return whether the relationship's mapping can be overridden with an
	 * association override.
	 */
	boolean isOverridable();
}
