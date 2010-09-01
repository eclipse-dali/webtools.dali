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

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 2.0
 */
public interface AssociationOverride
	extends BaseOverride
{
	AssociationOverrideRelationshipReference getRelationshipReference();
	
	AssociationOverride.Owner getOwner();
	
	AssociationOverride setVirtual(boolean virtual);
	
	void initializeFrom(AssociationOverride oldAssociationOverride);

	interface Owner extends BaseOverride.Owner
	{
		/**
		 * Return the relationship mapping with the given attribute name.
		 * Return null if it does not exist.  This relationship mapping
		 * will be found in the mapped superclass, not in the owning entity
		 */
		RelationshipMapping getRelationshipMapping(String attributeName);
	}
}