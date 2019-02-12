/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.jpa.core.context.MappingRelationship;

/**
 * <code>orm.xml</code> mapping relationship (1:1, 1:m, m:1, m:m)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.2
 */
public interface OrmMappingRelationship
	extends MappingRelationship
{
	OrmRelationshipMapping getMapping();

	/**
	 * Called when converting one <code>orm.xml</code> relationship mapping to
	 * another; to preserve any specified strategies.
	 * Typically handled via delegation to {@link #initializeOn(OrmMappingRelationship)}.
	 * <p>
	 * <strong>NB:</strong> 
	 * There is no corresponding method on the Java mapping relationship because
	 * Java mapping conversions simply change the mapping annotation and
	 * leave the [sibling] "relationship" annotations in place;
	 * while <code>orm.xml</code> mapping conversions must move
	 * the [nested] "relationship" XML elements to the new mapping
	 * XML element.
	 */
	void initializeFrom(OrmMappingRelationship oldRelationship);

	/**
	 * Called by {@link #initializeFrom(OrmMappingRelationship)} (on the old
	 * relationship) so we can use <em>double-dispatching</em>.
	 * 
	 * @see #initializeFromJoinColumnRelationship(OrmJoinColumnRelationship)
	 * @see #initializeFromJoinTableRelationship(OrmJoinTableRelationship)
	 * @see #initializeFromMappedByRelationship(OrmMappedByRelationship)
	 * @see #initializeFromPrimaryKeyJoinColumnRelationship(OrmPrimaryKeyJoinColumnRelationship)
	 */
	void initializeOn(OrmMappingRelationship newRelationship);

	/**
	 * <em>Double-dispatch</em>
	 * @see #initializeOn(OrmMappingRelationship)
	 */
	void initializeFromJoinColumnRelationship(OrmJoinColumnRelationship oldRelationship);

	/**
	 * <em>Double-dispatch</em>
	 * @see #initializeOn(OrmMappingRelationship)
	 */
	void initializeFromJoinTableRelationship(OrmJoinTableRelationship oldRelationship);

	/**
	 * <em>Double-dispatch</em>
	 * @see #initializeOn(OrmMappingRelationship)
	 */
	void initializeFromMappedByRelationship(OrmMappedByRelationship oldRelationship);

	/**
	 * <em>Double-dispatch</em>
	 * <p>
	 * <strong>NB:</strong> For now, only one-to-one mappings use a primary key
	 * join column relationship....
	 * @see #initializeOn(OrmMappingRelationship)
	 */
	void initializeFromPrimaryKeyJoinColumnRelationship(OrmPrimaryKeyJoinColumnRelationship oldRelationship);
}
