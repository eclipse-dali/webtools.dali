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

import org.eclipse.jpt.jpa.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Cascade Type 20</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCascadeType_2_0#isCascadeDetach <em>Cascade Detach</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlCascadeType_2_0()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlCascadeType_2_0 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Cascade Detach</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cascade Detach</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cascade Detach</em>' attribute.
	 * @see #setCascadeDetach(boolean)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlCascadeType_2_0_CascadeDetach()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isCascadeDetach();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlCascadeType_2_0#isCascadeDetach <em>Cascade Detach</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cascade Detach</em>' attribute.
	 * @see #isCascadeDetach()
	 * @generated
	 */
	void setCascadeDetach(boolean value);

} // XmlCascadeType_2_0
