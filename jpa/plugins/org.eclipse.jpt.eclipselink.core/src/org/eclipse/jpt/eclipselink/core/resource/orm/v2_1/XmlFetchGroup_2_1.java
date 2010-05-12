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

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlFetchAttribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Fetch Group 21</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getLoad <em>Load</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroup_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlFetchGroup_2_1 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroup_2_1_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlFetchAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroup_2_1_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlFetchAttribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Load</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Load</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Load</em>' attribute.
	 * @see #setLoad(Boolean)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlFetchGroup_2_1_Load()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.BooleanObject"
	 * @generated
	 */
	Boolean getLoad();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_1.XmlFetchGroup_2_1#getLoad <em>Load</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Load</em>' attribute.
	 * @see #getLoad()
	 * @generated
	 */
	void setLoad(Boolean value);

} // XmlFetchGroup_2_1
