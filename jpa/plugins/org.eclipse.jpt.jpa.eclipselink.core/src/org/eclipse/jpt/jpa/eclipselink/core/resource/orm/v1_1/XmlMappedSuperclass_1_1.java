/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1;

import org.eclipse.jpt.jpa.core.resource.xml.JpaEObject;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPrimaryKey;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Mapped Superclass 11</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1#getPrimaryKey <em>Primary Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlMappedSuperclass_1_1()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlMappedSuperclass_1_1 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Primary Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primary Key</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Key</em>' containment reference.
	 * @see #setPrimaryKey(XmlPrimaryKey)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlMappedSuperclass_1_1_PrimaryKey()
	 * @model containment="true"
	 * @generated
	 */
	XmlPrimaryKey getPrimaryKey();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v1_1.XmlMappedSuperclass_1_1#getPrimaryKey <em>Primary Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Key</em>' containment reference.
	 * @see #getPrimaryKey()
	 * @generated
	 */
	void setPrimaryKey(XmlPrimaryKey value);

} // XmlMappedSuperclass_1_1
