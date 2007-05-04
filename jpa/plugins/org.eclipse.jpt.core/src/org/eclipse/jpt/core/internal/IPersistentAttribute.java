/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IPersistent Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIPersistentAttribute()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IPersistentAttribute extends IJpaContentNode
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	IAttributeMapping getMapping();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	ITypeMapping typeMapping();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * <!-- begin-user-doc -->
	 * Return the key for the attribute's mapping.
	 * The key may be for either the "specified" mapping or, if the "specified"
	 * mapping is missing, the "default" mapping.
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	String mappingKey();

	/**
	 * <!-- begin-user-doc -->
	 * Return the key for the attribute's "default" mapping.
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	String defaultMappingKey();

	/**
	 * Clients should call this method to set the attribute's mapping.
	 * Passing in a null key will cause the "specified" mapping to be
	 * cleared and the attribute's mapping to be its "default" mapping.
	 */
	void setSpecifiedMappingKey(String key);

	/**
	 * Return all the attribute's mapping keys.
	 */
	Iterator<String> candidateMappingKeys();

	/**
	 * Return the attribute's Java attribute.
	 */
	Attribute getAttribute();

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
}
