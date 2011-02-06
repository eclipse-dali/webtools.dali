/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm.v2_0;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.jpa.core.resource.orm.EnumType;
import org.eclipse.jpt.jpa.core.resource.orm.TemporalType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Multi Relationship Mapping 20</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0#getMapKeyClass <em>Map Key Class</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0#getMapKeyTemporal <em>Map Key Temporal</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0#getMapKeyEnumerated <em>Map Key Enumerated</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0#getMapKeyColumn <em>Map Key Column</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0#getMapKeyJoinColumns <em>Map Key Join Columns</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlMultiRelationshipMapping_2_0()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlMultiRelationshipMapping_2_0 extends XmlOrderable_2_0, XmlMapKeyAttributeOverrideContainer_2_0
{
	/**
	 * Returns the value of the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Class</em>' containment reference.
	 * @see #setMapKeyClass(XmlClassReference)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlMultiRelationshipMapping_2_0_MapKeyClass()
	 * @model containment="true"
	 * @generated
	 */
	XmlClassReference getMapKeyClass();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0#getMapKeyClass <em>Map Key Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Class</em>' containment reference.
	 * @see #getMapKeyClass()
	 * @generated
	 */
	void setMapKeyClass(XmlClassReference value);

	/**
	 * Returns the value of the '<em><b>Map Key Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.TemporalType
	 * @see #setMapKeyTemporal(TemporalType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlMultiRelationshipMapping_2_0_MapKeyTemporal()
	 * @model
	 * @generated
	 */
	TemporalType getMapKeyTemporal();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0#getMapKeyTemporal <em>Map Key Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.TemporalType
	 * @see #getMapKeyTemporal()
	 * @generated
	 */
	void setMapKeyTemporal(TemporalType value);

	/**
	 * Returns the value of the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.EnumType
	 * @see #setMapKeyEnumerated(EnumType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlMultiRelationshipMapping_2_0_MapKeyEnumerated()
	 * @model
	 * @generated
	 */
	EnumType getMapKeyEnumerated();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0#getMapKeyEnumerated <em>Map Key Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.EnumType
	 * @see #getMapKeyEnumerated()
	 * @generated
	 */
	void setMapKeyEnumerated(EnumType value);

	/**
	 * Returns the value of the '<em><b>Map Key Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Column</em>' containment reference.
	 * @see #setMapKeyColumn(XmlColumn)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlMultiRelationshipMapping_2_0_MapKeyColumn()
	 * @model containment="true"
	 * @generated
	 */
	XmlColumn getMapKeyColumn();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMultiRelationshipMapping_2_0#getMapKeyColumn <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Column</em>' containment reference.
	 * @see #getMapKeyColumn()
	 * @generated
	 */
	void setMapKeyColumn(XmlColumn value);

	/**
	 * Returns the value of the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlMultiRelationshipMapping_2_0_MapKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlJoinColumn> getMapKeyJoinColumns();

} // XmlMultiRelationshipMapping_2_0
