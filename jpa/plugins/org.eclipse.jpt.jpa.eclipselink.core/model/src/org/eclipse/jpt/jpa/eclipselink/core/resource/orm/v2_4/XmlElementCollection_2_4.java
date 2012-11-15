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

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Element Collection 24</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4#getField <em>Field</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4#isDeleteAll <em>Delete All</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlElementCollection_2_4()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlElementCollection_2_4 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Field</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Field</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Field</em>' containment reference.
	 * @see #setField(XmlField_2_4)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlElementCollection_2_4_Field()
	 * @model containment="true"
	 * @generated
	 */
	XmlField_2_4 getField();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4#getField <em>Field</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Field</em>' containment reference.
	 * @see #getField()
	 * @generated
	 */
	void setField(XmlField_2_4 value);

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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.EclipseLinkOrmV2_4Package#getXmlElementCollection_2_4_DeleteAll()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isDeleteAll();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_4.XmlElementCollection_2_4#isDeleteAll <em>Delete All</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Delete All</em>' attribute.
	 * @see #isDeleteAll()
	 * @generated
	 */
	void setDeleteAll(boolean value);

} // XmlElementCollection_2_4
