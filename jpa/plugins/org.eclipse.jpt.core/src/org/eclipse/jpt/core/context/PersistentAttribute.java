/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.core.JpaStructureNode;

/**
 * Persistent "attribute" (field or property)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistentAttribute
	extends JpaContextNode, JpaStructureNode, AccessHolder
{
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Return the key for the attribute's mapping.
	 * The key may be for either the "specified" mapping or, if the "specified"
	 * mapping is missing, the "default" mapping.
	 */
	String getMappingKey();

	/**
	 * Return the key for the attribute's "default" mapping.
	 */
	String getDefaultMappingKey();

	/**
	 * Return the attribute's "specified" mapping, or if it is null
	 * return the attribute's "default" mapping. Do not return null.
	 */
	AttributeMapping getMapping();
		String DEFAULT_MAPPING_PROPERTY = "defaultMapping"; //$NON-NLS-1$

	/**
	 * Return the attribute's "specified" mapping - could be null.
	 */
	AttributeMapping getSpecifiedMapping();
		String SPECIFIED_MAPPING_PROPERTY = "specifiedMapping"; //$NON-NLS-1$
	
	/**
	 * Clients should call this method to set the attribute's mapping.
	 * Passing in a null key will cause the "specified" mapping to be
	 * cleared and the attribute's mapping to be its "default" mapping.
	 */
	void setSpecifiedMappingKey(String key);
	
	TypeMapping getTypeMapping();

	PersistentType getPersistentType();
	
	/**
	 * If the attribute is mapped to a primary key column, return the
	 * column's name, otherwise return null.
	 */
	String getPrimaryKeyColumnName();

	/**
	 * Return whether the attribute's mapping is for an ID.
	 */
	boolean isIdAttribute();
	
	/**
	 * Return whether this attribute actually has a textual representation
	 * in its underlying resource (false = no).
	 */
	boolean isVirtual();

}
