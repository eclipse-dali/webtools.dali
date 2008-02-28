/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PersistentAttribute extends JpaContextNode, JpaStructureNode
{
	String getName();
		String NAME_PROPERTY = "nameProperty";

	/**
	 * Return the key for the attribute's mapping.
	 * The key may be for either the "specified" mapping or, if the "specified"
	 * mapping is missing, the "default" mapping.
	 */
	String mappingKey();

	/**
	 * Return the key for the attribute's "default" mapping.
	 */
	String defaultMappingKey();

	/**
	 * Return the attribute's "specified" mapping, or if it is null
	 * return the "default" mapping.  WIll not return null.
	 */
	AttributeMapping getMapping();

	/**
	 * Return the attribute's "specified" mapping, could be null
	 */
	AttributeMapping getSpecifiedMapping();
	
	/**
	 * Clients should call this method to set the attribute's mapping.
	 * Passing in a null key will cause the "specified" mapping to be
	 * cleared and the attribute's mapping to be its "default" mapping.
	 */
	void setSpecifiedMappingKey(String key);
		String SPECIFIED_MAPPING_PROPERTY = "specifiedMappingProperty";
		String DEFAULT_MAPPING_PROPERTY = "defaultMappingProperty";
	
	TypeMapping typeMapping();

	PersistentType persistentType();
	
	/**
	 * If the attribute is mapped to a primary key column, return the
	 * column's name, otherwise return null.
	 */
	String primaryKeyColumnName();

	/**
	 * Return whether the attribute's "attribute" mapping can be overridden.
	 */
	boolean isOverridableAttribute();

	/**
	 * Return whether the attribute's "association" mapping can be overridden.
	 */
	boolean isOverridableAssociation();

	/**
	 * Return whether the attribute's "attribute" mapping is for an ID.
	 */
	boolean isIdAttribute();
	
	/**
	 * Return whether this attribute actually has a textual representation
	 * in its underlying resource (false = no)
	 */
	boolean isVirtual();
}
