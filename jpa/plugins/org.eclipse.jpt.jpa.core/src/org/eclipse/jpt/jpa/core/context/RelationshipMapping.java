/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * JPA relationship (1:1, 1:m, m:1, m:m) mapping.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 2.0
 */
public interface RelationshipMapping
	extends FetchableMapping
{
	/**
	 * Return the meta-information used to populate the entities of the 
	 * relationship
	 */
	MappingRelationship getRelationship();

	/**
	 * Return the relationship owner or <code>null</code> if this is the owning side
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
	
	/**
	 * Return all attribute names on the target entity, provided target entity
	 * resolves
	 */
	Iterable<String> getAllTargetEntityAttributeNames();

	/**
	 * Return the names of non-transient attribute mappings on the target entity, 
	 * provided target entity resolves
	 */
	Iterable<String> getTargetEntityNonTransientAttributeNames();
	
	/**
	 * Return the char to be used for browsing or creating the target entity IType.
	 * @see org.eclipse.jdt.core.IType#getFullyQualifiedName(char)
	 */
	char getTargetEntityEnclosingTypeSeparator();

	/**
	 * If the target entity is specified, this will return it fully qualified.
	 * If not specified, it returns the default target entity, which is always
	 * fully qualified
	 */
	String getFullyQualifiedTargetEntity();
		String FULLY_QUALIFIED_TARGET_ENTITY_PROPERTY = "fullyQualifiedTargetEntity"; //$NON-NLS-1$


	// **************** cascade **************************************

	Cascade getCascade();
}
