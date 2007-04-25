/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.mappings;

import org.eclipse.jpt.core.internal.IAttributeMapping;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IRelationship Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getTargetEntity <em>Target Entity</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getSpecifiedTargetEntity <em>Specified Target Entity</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getDefaultTargetEntity <em>Default Target Entity</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getResolvedTargetEntity <em>Resolved Target Entity</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIRelationshipMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface IRelationshipMapping extends IAttributeMapping
{
	/**
	 * Returns the value of the '<em><b>Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Entity</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIRelationshipMapping_TargetEntity()
	 * @model changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	String getTargetEntity();

	/**
	 * Returns the value of the '<em><b>Specified Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Specified Target Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Specified Target Entity</em>' attribute.
	 * @see #setSpecifiedTargetEntity(String)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIRelationshipMapping_SpecifiedTargetEntity()
	 * @model
	 * @generated
	 */
	String getSpecifiedTargetEntity();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getSpecifiedTargetEntity <em>Specified Target Entity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specified Target Entity</em>' attribute.
	 * @see #getSpecifiedTargetEntity()
	 * @generated
	 */
	void setSpecifiedTargetEntity(String value);

	/**
	 * Returns the value of the '<em><b>Default Target Entity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Target Entity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Target Entity</em>' attribute.
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIRelationshipMapping_DefaultTargetEntity()
	 * @model changeable="false"
	 * @generated
	 */
	String getDefaultTargetEntity();

	/**
	 * Returns the value of the '<em><b>Resolved Target Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resolved Target Entity</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resolved Target Entity</em>' reference.
	 * @see #setResolvedTargetEntity(IEntity)
	 * @see org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage#getIRelationshipMapping_ResolvedTargetEntity()
	 * @model
	 * @generated
	 */
	IEntity getResolvedTargetEntity();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.internal.mappings.IRelationshipMapping#getResolvedTargetEntity <em>Resolved Target Entity</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resolved Target Entity</em>' reference.
	 * @see #getResolvedTargetEntity()
	 * @generated
	 */
	void setResolvedTargetEntity(IEntity value);

	/**
	 * Return whether the specified 'targetEntity' is valid.
	 */
	boolean targetEntityIsValid(String targetEntity);

	/**
	 * Return the fully qualified target entity.  If it is already specified
	 * as fully qualified then just return that.
	 * @return
	 */
	String fullyQualifiedTargetEntity();

	/**
	 * Return the Entity that owns this relationship mapping
	 * @return
	 */
	IEntity getEntity();
} // IRelationshipMapping
