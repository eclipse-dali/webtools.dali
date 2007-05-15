/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import java.util.Iterator;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IMulti Relationship Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getFetch <em>Fetch</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getJoinTable <em>Join Table</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getOrderBy <em>Order By</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getMapKey <em>Map Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMultiRelationshipMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IMultiRelationshipMapping extends INonOwningMapping
{
	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType
	 * @see #setFetch(DefaultLazyFetchType)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMultiRelationshipMapping_Fetch()
	 * @model
	 * @generated
	 */
	DefaultLazyFetchType getFetch();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.DefaultLazyFetchType
	 * @see #getFetch()
	 * @generated
	 */
	void setFetch(DefaultLazyFetchType value);

	/**
	 * Returns the value of the '<em><b>Join Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Table</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMultiRelationshipMapping_JoinTable()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	IJoinTable getJoinTable();

	/**
	 * All multi-relationship mappings have a join table, even if it has to be
	 * calculated from default settings.  However, it is important to note 
	 * whether the table *is* default or whether it is specified.
	 */
	boolean isJoinTableSpecified();

	/**
	 * Returns the value of the '<em><b>Order By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order By</em>' containment reference.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMultiRelationshipMapping_OrderBy()
	 * @model containment="true" required="true" changeable="false"
	 * @generated
	 */
	IOrderBy getOrderBy();

	/**
	 * Returns the value of the '<em><b>Map Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key</em>' attribute.
	 * @see #setMapKey(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIMultiRelationshipMapping_MapKey()
	 * @model
	 * @generated
	 */
	String getMapKey();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping#getMapKey <em>Map Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key</em>' attribute.
	 * @see #getMapKey()
	 * @generated
	 */
	void setMapKey(String value);

	Iterator<String> candidateMapKeyNames();
}
