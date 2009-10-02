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

package org.eclipse.jpt.eclipselink.core.resource.orm.v1_1;

import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlGeneratorContainer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Basic 11</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1#getGeneratedValue <em>Generated Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlBasic_1_1()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlBasic_1_1 extends XmlGeneratorContainer
{
	/**
	 * Returns the value of the '<em><b>Generated Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generated Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generated Value</em>' containment reference.
	 * @see #setGeneratedValue(XmlGeneratedValue)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.EclipseLinkOrmV1_1Package#getXmlBasic_1_1_GeneratedValue()
	 * @model containment="true"
	 * @generated
	 */
	XmlGeneratedValue getGeneratedValue();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.v1_1.XmlBasic_1_1#getGeneratedValue <em>Generated Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generated Value</em>' containment reference.
	 * @see #getGeneratedValue()
	 * @generated
	 */
	void setGeneratedValue(XmlGeneratedValue value);

} // XmlBasic_1_1
