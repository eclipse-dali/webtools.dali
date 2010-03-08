/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
 * Relationship (1:1, 1:m, m:1, m:m) mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface RelationshipMapping
	extends AttributeMapping, Fetchable
{
	/**
	 * Return the meta-information used to populate the entities of the 
	 * relationship
	 */
	RelationshipReference getRelationshipReference();

	/**
	 * Return the relationship owner or null if this is the owning side
 	 * or it is a unidirectional mapping.
	 */
	RelationshipMapping getRelationshipOwner();
	
	
	// **************** target entity **************************************
	
	String getTargetEntity();
	
	String getSpecifiedTargetEntity();
	void setSpecifiedTargetEntity(String value);
		String SPECIFIED_TARGET_ENTITY_PROPERTY = "specifiedTargetEntity"; //$NON-NLS-1$
	
	String getDefaultTargetEntity();
		String DEFAULT_TARGET_ENTITY_PROPERTY = "defaultTargetEntity"; //$NON-NLS-1$
	
	Entity getResolvedTargetEntity();
		String RESOLVED_TARGET_ENTITY_PROPERTY = "resolvedTargetEntity"; //$NON-NLS-1$
	
	/**
	 * Return all attribute names on the target entity, provided target entity
	 * resolves
	 */
	Iterator<String> allTargetEntityAttributeNames();
	
	/**
	 * Return the char to be used for browsing or creating the target entity IType.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getTargetEntityEnclosingTypeSeparator();
		
	// **************** cascade **************************************

	Cascade getCascade();
}
