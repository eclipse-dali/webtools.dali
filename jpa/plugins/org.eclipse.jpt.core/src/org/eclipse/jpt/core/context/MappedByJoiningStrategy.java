/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;

/**
 * "Mapped by" joining strategy
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see RelationshipMapping
 * @see OwnableRelationshipReference
 * 
 * @version 2.2
 * @since 2.2
 */
public interface MappedByJoiningStrategy
	extends JoiningStrategy
{
	void initializeFrom(MappedByJoiningStrategy oldStrategy);
	
	/**
	 * String associated with changes to the attribute property of this object
	 */
	String MAPPED_BY_ATTRIBUTE_PROPERTY = "mappedByAttribute"; //$NON-NLS-1$
	
	/**
	 * Return the attribute of this object.  A null indicates that the resource
	 * element does not exist
	 */
	String getMappedByAttribute();
	
	/**
	 * Set the attribute of this object.  A null will result in removal of the 
	 * resource element
	 */
	void setMappedByAttribute(String attribute);
	
	/**
	 * Return the possible attribute names the strategy might use.
	 */
	Iterator<String> candidateMappedByAttributeNames();
	
	/**
	 * Return whether this strategy's relationship is owned by the given other
	 * relationship mapping
	 */
	boolean relationshipIsOwnedBy(RelationshipMapping otherMapping);
}
