/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml One To Many 24</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4#getJoinFields <em>Join Fields</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4#isDeleteAll <em>Delete All</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlOneToMany_2_4()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlOneToMany_2_4 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Join Fields</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlJoinField_2_4}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Fields</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Fields</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlOneToMany_2_4_JoinFields()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlJoinField_2_4> getJoinFields();

	/**
	 * Returns the value of the '<em><b>Delete All</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delete All</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delete All</em>' attribute.
	 * @see #setDeleteAll(boolean)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlOneToMany_2_4_DeleteAll()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isDeleteAll();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlOneToMany_2_4#isDeleteAll <em>Delete All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete All</em>' attribute.
	 * @see #isDeleteAll()
	 * @generated
	 */
	void setDeleteAll(boolean value);

} // XmlOneToMany_2_4
