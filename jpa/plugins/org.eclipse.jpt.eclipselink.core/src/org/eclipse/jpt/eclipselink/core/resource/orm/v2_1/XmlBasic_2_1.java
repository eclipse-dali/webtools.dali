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

package org.eclipse.jpt.eclipselink.core.resource.orm.v2_1;

import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReturnInsert;
import org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Basic 21</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getReturnInsert <em>Return Insert</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getReturnUpdate <em>Return Update</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlBasic_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlBasic_2_1 extends XmlBasic_1_1
{
	/**
	 * Returns the value of the '<em><b>Return Insert</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Return Insert</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Insert</em>' containment reference.
	 * @see #setReturnInsert(XmlReturnInsert)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlBasic_2_1_ReturnInsert()
	 * @model containment="true"
	 * @generated
	 */
	XmlReturnInsert getReturnInsert();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getReturnInsert <em>Return Insert</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Insert</em>' containment reference.
	 * @see #getReturnInsert()
	 * @generated
	 */
	void setReturnInsert(XmlReturnInsert value);

	/**
	 * Returns the value of the '<em><b>Return Update</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Return Update</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Return Update</em>' attribute.
	 * @see #setReturnUpdate(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlBasic_2_1_ReturnUpdate()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getReturnUpdate();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlBasic_2_1#getReturnUpdate <em>Return Update</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Return Update</em>' attribute.
	 * @see #getReturnUpdate()
	 * @generated
	 */
	void setReturnUpdate(Boolean value);

} // XmlBasic_2_1
