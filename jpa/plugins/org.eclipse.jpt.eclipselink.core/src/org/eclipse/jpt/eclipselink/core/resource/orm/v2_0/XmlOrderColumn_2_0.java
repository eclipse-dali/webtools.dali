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

package org.eclipse.jpt.eclipselink.core.resource.orm.v2_0;

import org.eclipse.jpt.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Order Column 20</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.XmlOrderColumn_2_0#getValidationMode <em>Validation Mode</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlOrderColumn_2_0()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlOrderColumn_2_0 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Validation Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderColumnValidationModeType_2_0}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validation Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderColumnValidationModeType_2_0
	 * @see #setValidationMode(OrderColumnValidationModeType_2_0)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlOrderColumn_2_0_ValidationMode()
	 * @model
	 * @generated
	 */
	OrderColumnValidationModeType_2_0 getValidationMode();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.XmlOrderColumn_2_0#getValidationMode <em>Validation Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validation Mode</em>' attribute.
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v2_0.OrderColumnValidationModeType_2_0
	 * @see #getValidationMode()
	 * @generated
	 */
	void setValidationMode(OrderColumnValidationModeType_2_0 value);

} // XmlOrderColumn_2_0
