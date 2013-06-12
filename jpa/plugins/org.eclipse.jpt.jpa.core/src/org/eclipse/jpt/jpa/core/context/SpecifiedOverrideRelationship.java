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
 * Association override relationship
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see SpecifiedAssociationOverride
 * 
 * @version 2.3
 * @since 2.3
 */
public interface SpecifiedOverrideRelationship
	extends OverrideRelationship,
			SpecifiedJoinColumnRelationship
{
	/**
	 * Called when a default override is converted into a specified override.
	 * As a result both the receiver and the argument will both be from either
	 * Java or <code>orm.xml</code> (but that is superfluous, for now).
	 * <p>
	 * @see org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer
	 * AssociationOverrideContainer for a list of clients
	 */
	void initializeFrom(VirtualOverrideRelationship virtualRelationship);
}
