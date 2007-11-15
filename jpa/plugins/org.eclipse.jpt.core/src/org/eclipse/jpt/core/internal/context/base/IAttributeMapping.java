/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

public interface IAttributeMapping extends IJpaContextNode
{
	IPersistentAttribute getPersistentAttribute();

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
	String primaryKeyColumnName();

	/**
	 * Return the mapping for the attribute mapping's attribute's type.
	 */
	ITypeMapping typeMapping();

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