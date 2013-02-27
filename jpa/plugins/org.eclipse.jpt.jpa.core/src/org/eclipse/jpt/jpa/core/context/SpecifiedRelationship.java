/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see RelationshipMapping
 * @see SpecifiedAssociationOverride
 *
 * @version 2.3
 * @since 2.2
 */
public interface SpecifiedRelationship
	extends Relationship
{
	SpecifiedRelationshipStrategy getStrategy();


	// ********** conversions **********

	void initializeFrom(Relationship oldRelationship);

	void initializeFromMappedByRelationship(SpecifiedMappedByRelationship oldRelationship);

	void initializeFromJoinColumnRelationship(JoinColumnRelationship oldRelationship);

	void initializeFromJoinTableRelationship(JoinTableRelationship oldRelationship);

	// we only have a single "client" of the primary key relationship (1:1 mapping)
}
