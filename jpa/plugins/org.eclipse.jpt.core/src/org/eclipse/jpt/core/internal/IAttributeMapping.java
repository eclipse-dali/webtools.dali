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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IAttribute Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIAttributeMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IAttributeMapping extends IJpaSourceObject
{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	IPersistentAttribute getPersistentAttribute();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
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
}