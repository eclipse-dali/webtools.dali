/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;



public interface RelationshipMapping extends AttributeMapping, Fetchable
{

	// **************** target entity **************************************
	
	String getTargetEntity();

	String getSpecifiedTargetEntity();
	void setSpecifiedTargetEntity(String value);
		String SPECIFIED_TARGET_ENTITY_PROPERTY = "specifiedTargetEntityProperty";

	String getDefaultTargetEntity();
		String DEFAULT_TARGET_ENTITY_PROPERTY = "defaultTargetEntityProperty";

	Entity getResolvedTargetEntity();
		String RESOLVED_TARGET_ENTITY_PROPERTY = "resolvedTargetEntityProperty";
	
	/**
	 * Return whether the specified 'targetEntity' is valid.
	 */
	boolean targetEntityIsValid(String targetEntity);

	
	// **************** cascade **************************************

	Cascade getCascade();


	/**
	 * Return the Entity that owns this relationship mapping
	 * @return
	 */
	Entity getEntity();

	/**
	 * Return whether this mapping is the owning side of the relationship.
	 * Either this is a unidirectional mapping or it is the owning side of a bidirectional
	 * relationship.  If bidirectional, the owning side is the side that doesn't specify
	 * mappedBy.  This is the side where a join table would be specified
	 */
	boolean isRelationshipOwner();
}