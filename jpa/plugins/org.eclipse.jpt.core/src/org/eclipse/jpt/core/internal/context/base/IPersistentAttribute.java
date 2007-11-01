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


public interface IPersistentAttribute extends IJpaContextNode
{
	String getName();
		String NAME_PROPERTY = "nameProperty";

//	IAttributeMapping getMapping();

//	String mappingKey();

//	String defaultMappingKey();

	/**
	 * Clients should call this method to set the attribute's mapping.
	 * Passing in a null key will cause the "specified" mapping to be
	 * cleared and the attribute's mapping to be its "default" mapping.
	 */
//	void setSpecifiedMappingKey(String key);

	ITypeMapping typeMapping();
//
//	/**
//	 * If the attribute is mapped to a primary key column, return the
//	 * column's name, otherwise return null.
//	 */
//	String primaryKeyColumnName();
//
//	/**
//	 * Return whether the attribute's "attribute" mapping can be overridden.
//	 */
//	boolean isOverridableAttribute();
//
//	/**
//	 * Return whether the attribute's "association" mapping can be overridden.
//	 */
//	boolean isOverridableAssociation();
//
//	/**
//	 * Return whether the attribute's "attribute" mapping is for an ID.
//	 */
//	boolean isIdAttribute();
}
