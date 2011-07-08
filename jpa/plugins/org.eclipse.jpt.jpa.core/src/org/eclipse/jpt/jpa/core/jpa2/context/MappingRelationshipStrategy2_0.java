/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.MappingRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.RelationshipStrategy;

/**
 * Strategy describing how two entities are joined via a
 * {@link RelationshipMapping}:<ul>
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
 * @see MappingRelationship
 */
public interface MappingRelationshipStrategy2_0
	extends RelationshipStrategy
{
	/**
	 * Select from the specified override relationship which of its strategies
	 * is the override strategy (<em>join column</em> or <em>join table</em>).
	 * An association override can only override the same strategy as the
	 * strategy used by its overridden mapping (i.e. if the overridden mapping
	 * uses the <em>join column</em> strategy, the association override must
	 * also use the <em>join column</em> strategy; it cannot change the strategy
	 * to <em>join table</em>).
	 * 
	 * Return <code>null</code> if the override relationship cannot be
	 * determined (typically because the association override's name does not
	 * match the name of an appropriate attribute mapping in the overridden
	 * mapped superclass or embeddable type).
	 */
	ReadOnlyRelationshipStrategy selectOverrideStrategy(ReadOnlyOverrideRelationship2_0 overrideRelationship);
}
