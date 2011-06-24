/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3;

import org.eclipse.emf.common.util.EList;

import org.eclipse.jpt.jpa.core.resource.xml.JpaEObject;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Embeddable 23</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getPlsqlRecords <em>Plsql Records</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getPlsqlTables <em>Plsql Tables</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getStruct <em>Struct</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEmbeddable_2_3()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlEmbeddable_2_3 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Plsql Records</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlPlsqlRecord_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plsql Records</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plsql Records</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEmbeddable_2_3_PlsqlRecords()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlPlsqlRecord_2_3> getPlsqlRecords();

	/**
	 * Returns the value of the '<em><b>Plsql Tables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPlsqlTable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plsql Tables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plsql Tables</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEmbeddable_2_3_PlsqlTables()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlPlsqlTable> getPlsqlTables();

	/**
	 * Returns the value of the '<em><b>Struct</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Struct</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Struct</em>' containment reference.
	 * @see #setStruct(XmlStruct_2_3)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlEmbeddable_2_3_Struct()
	 * @model containment="true"
	 * @generated
	 */
	XmlStruct_2_3 getStruct();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlEmbeddable_2_3#getStruct <em>Struct</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Struct</em>' containment reference.
	 * @see #getStruct()
	 * @generated
	 */
	void setStruct(XmlStruct_2_3 value);

} // XmlEmbeddable_2_3
