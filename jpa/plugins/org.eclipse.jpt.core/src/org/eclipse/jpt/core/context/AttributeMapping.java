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

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AttributeMapping extends JpaContextNode
{
	PersistentAttribute getPersistentAttribute();

	boolean isDefault();

	/**
	 * Return a unique key for the IPersistentAttributeMapping.  If this is defined in
	 * an extension they should be equal.
	 */
	String getKey();

	/**
	 * If the mapping is for a primary key column, return the column's name,
	 * otherwise return null.
	 */
	String getPrimaryKeyColumnName();

	/**
	 * Return the mapping for the attribute mapping's attribute's type.
	 */
	TypeMapping getTypeMapping();

	/**
	 * Return whether the "attribute" mapping can be overridden.
	 */
	boolean isOverridableAttributeMapping();

	/**
	 * Return whether the "association" mapping can be overridden.
	 */
	boolean isOverridableAssociationMapping();

	/**
	 * Return whether the "attribute" mapping is for an ID.
	 */
	boolean isIdMapping();
}