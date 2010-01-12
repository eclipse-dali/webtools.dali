/*******************************************************************************
 *  Copyright (c) 2009, 2010 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.resource.orm.v2_0;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverrideContainer;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverrideContainer;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlMapKeyClass;
import org.eclipse.jpt.core.resource.orm.XmlOrderable;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Element Collection 20</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getTargetClass <em>Target Class</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getFetch <em>Fetch</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKey <em>Map Key</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyClass <em>Map Key Class</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyTemporal <em>Map Key Temporal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyEnumerated <em>Map Key Enumerated</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyAttributeOverrides <em>Map Key Attribute Overrides</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyColumn <em>Map Key Column</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyJoinColumns <em>Map Key Join Columns</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getColumn <em>Column</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getCollectionTable <em>Collection Table</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlElementCollection_2_0 extends XmlConvertibleMapping, XmlOrderable, XmlAttributeOverrideContainer, XmlAssociationOverrideContainer
{
	/**
	 * Returns the value of the '<em><b>Target Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Class</em>' attribute.
	 * @see #setTargetClass(String)
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_TargetClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getTargetClass();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getTargetClass <em>Target Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Class</em>' attribute.
	 * @see #getTargetClass()
	 * @generated
	 */
	void setTargetClass(String value);

	/**
	 * Returns the value of the '<em><b>Fetch</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.FetchType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fetch</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.FetchType
	 * @see #setFetch(FetchType)
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_Fetch()
	 * @model
	 * @generated
	 */
	FetchType getFetch();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getFetch <em>Fetch</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fetch</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.FetchType
	 * @see #getFetch()
	 * @generated
	 */
	void setFetch(FetchType value);

	/**
	 * Returns the value of the '<em><b>Map Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key</em>' containment reference.
	 * @see #setMapKey(MapKey)
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_MapKey()
	 * @model containment="true"
	 * @generated
	 */
	MapKey getMapKey();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKey <em>Map Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key</em>' containment reference.
	 * @see #getMapKey()
	 * @generated
	 */
	void setMapKey(MapKey value);

	/**
	 * Returns the value of the '<em><b>Map Key Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Class</em>' containment reference.
	 * @see #setMapKeyClass(XmlMapKeyClass)
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_MapKeyClass()
	 * @model containment="true"
	 * @generated
	 */
	XmlMapKeyClass getMapKeyClass();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyClass <em>Map Key Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Class</em>' containment reference.
	 * @see #getMapKeyClass()
	 * @generated
	 */
	void setMapKeyClass(XmlMapKeyClass value);

	/**
	 * Returns the value of the '<em><b>Map Key Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #setMapKeyTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_MapKeyTemporal()
	 * @model
	 * @generated
	 */
	TemporalType getMapKeyTemporal();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyTemporal <em>Map Key Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #getMapKeyTemporal()
	 * @generated
	 */
	void setMapKeyTemporal(TemporalType value);

	/**
	 * Returns the value of the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #setMapKeyEnumerated(EnumType)
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_MapKeyEnumerated()
	 * @model
	 * @generated
	 */
	EnumType getMapKeyEnumerated();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyEnumerated <em>Map Key Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #getMapKeyEnumerated()
	 * @generated
	 */
	void setMapKeyEnumerated(EnumType value);

	/**
	 * Returns the value of the '<em><b>Map Key Attribute Overrides</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlAttributeOverride}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Attribute Overrides</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Attribute Overrides</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_MapKeyAttributeOverrides()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlAttributeOverride> getMapKeyAttributeOverrides();

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
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_MapKeyColumn()
	 * @model containment="true"
	 * @generated
	 */
	XmlColumn getMapKeyColumn();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getMapKeyColumn <em>Map Key Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Column</em>' containment reference.
	 * @see #getMapKeyColumn()
	 * @generated
	 */
	void setMapKeyColumn(XmlColumn value);

	/**
	 * Returns the value of the '<em><b>Map Key Join Columns</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.core.resource.orm.XmlJoinColumn}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Join Columns</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Join Columns</em>' containment reference list.
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_MapKeyJoinColumns()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlJoinColumn> getMapKeyJoinColumns();

	/**
	 * Returns the value of the '<em><b>Column</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column</em>' containment reference.
	 * @see #setColumn(XmlColumn)
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_Column()
	 * @model containment="true"
	 * @generated
	 */
	XmlColumn getColumn();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getColumn <em>Column</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column</em>' containment reference.
	 * @see #getColumn()
	 * @generated
	 */
	void setColumn(XmlColumn value);

	/**
	 * Returns the value of the '<em><b>Collection Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Collection Table</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Collection Table</em>' containment reference.
	 * @see #setCollectionTable(XmlCollectionTable)
	 * @see org.eclipse.jpt.core.resource.orm.v2_0.OrmV2_0Package#getXmlElementCollection_2_0_CollectionTable()
	 * @model containment="true"
	 * @generated
	 */
	XmlCollectionTable getCollectionTable();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.v2_0.XmlElementCollection_2_0#getCollectionTable <em>Collection Table</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Collection Table</em>' containment reference.
	 * @see #getCollectionTable()
	 * @generated
	 */
	void setCollectionTable(XmlCollectionTable value);

	/**
	 * Return the {@link TextRange} for the target-class attribute.
	 */
	TextRange getTargetClassTextRange();

} // XmlElementCollection_2_0
