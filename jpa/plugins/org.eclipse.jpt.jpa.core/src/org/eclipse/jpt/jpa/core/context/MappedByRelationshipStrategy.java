/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * "Mapped by" relationship strategy
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see RelationshipMapping
 * @see MappedByRelationship
 * 
 * @version 2.2
 * @since 2.2
 */
public interface MappedByRelationshipStrategy
	extends RelationshipStrategy
{
	void initializeFrom(MappedByRelationshipStrategy oldStrategy);
	
	/**
	 * String associated with changes to the attribute property of this object
	 */
	String MAPPED_BY_ATTRIBUTE_PROPERTY = "mappedByAttribute"; //$NON-NLS-1$
	
	/**
	 * Return the name of the attribute that maps the relationship.
	 */
	String getMappedByAttribute();
	
	/**
	 * Set the name of the attribute that maps the relationship.
	 */
	void setMappedByAttribute(String attribute);
	
	/**
	 * Return the possible attribute names the strategy might use.
	 */
	Iterable<String> getCandidateMappedByAttributeNames();
	
	/**
	 * Return whether this strategy's relationship is owned by the given other
	 * relationship mapping
	 */
	boolean relationshipIsOwnedBy(RelationshipMapping otherMapping);
}
