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
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.IPersistentAttribute#getMappingKey <em>Mapping Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIPersistentAttribute()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IPersistentAttribute extends IJpaContentNode
{
	/**
	 * Returns the value of the '<em><b>Mapping Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapping Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Key</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.JpaCorePackage#getIPersistentAttribute_MappingKey()
	 * @model required="true" changeable="false"
	 * @generated
	 */
	String getMappingKey();

	void setMappingKey(String value, boolean default_);

	/**
	 * Returns an iterator on all mapping keys that are available for this attribute.
	 */
	Iterator candidateMappingKeys();

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
	 * @model kind="operation"
	 * @generated
	 */
	String getName();

	/**
	 * Return the java Attribute that this IPersistentAttribute is associated with
	 * @return
	 */
	Attribute getAttribute();

	String defaultKey();

	/**
	 * If the attribute is mapped to a primary key column, return the
	 * column's name, otherwise return null.
	 */
	String primaryKeyColumnName();
}