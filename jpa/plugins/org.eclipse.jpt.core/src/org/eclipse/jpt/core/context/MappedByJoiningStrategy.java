/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.Iterator;

/**
 * Joining strategy that depends on another mapping (the owning side of the 
 * relationship) to join the two entities.  Uses "mappedBy" meta-data.
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
 * @see {@link OwnableRelationshipReference}
 */
public interface MappedByJoiningStrategy
	extends JoiningStrategy
{
	/**
	 * String associated with changes to the attribute property of this object
	 */
	String MAPPED_BY_ATTRIBUTE_PROPERTY = 
		"mappedByAttributeProperty"; //$NON-NLS-1$
	
	/**
	 * Return the attribute of this object.  A null indicates that the resource
	 * element does not exist
	 */
	String getMappedByAttribute();
	
	/**
	 * Set the attribute of this object.  A null will result in removal of the 
	 * resource element
	 */
	void setMappedByAttribute(String newAttribute);
	
	/**
	 * Return an iterator of possible attribute names that this object might use
	 */
	Iterator<String> candidateMappedByAttributeNames();
	
	/**
	 * Return whether this strategy's relationship is owned by the given other
	 * relationship mapping
	 */
	boolean relationshipIsOwnedBy(RelationshipMapping otherMapping);
}
