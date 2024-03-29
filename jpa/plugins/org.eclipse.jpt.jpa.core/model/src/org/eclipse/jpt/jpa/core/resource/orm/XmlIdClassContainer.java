/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License 2.0 which 
 *  accompanies this distribution, and is available at 
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.core.resource.orm;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Id Class Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.XmlIdClassContainer#getIdClass <em>Id Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdClassContainer()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlIdClassContainer extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Id Class</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Class</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Class</em>' containment reference.
	 * @see #setIdClass(XmlClassReference)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.OrmPackage#getXmlIdClassContainer_IdClass()
	 * @model containment="true"
	 * @generated
	 */
	XmlClassReference getIdClass();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.XmlIdClassContainer#getIdClass <em>Id Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Class</em>' containment reference.
	 * @see #getIdClass()
	 * @generated
	 */
	void setIdClass(XmlClassReference value);

} // XmlIdClassContainer
