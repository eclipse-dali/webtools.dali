/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAccessMethodsHolder;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Type Mapping 21</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTypeMapping_2_1#getParentClass <em>Parent Class</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlTypeMapping_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlTypeMapping_2_1 extends XmlAccessMethodsHolder
{

	/**
	 * Returns the value of the '<em><b>Parent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Class</em>' attribute.
	 * @see #setParentClass(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlTypeMapping_2_1_ParentClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getParentClass();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlTypeMapping_2_1#getParentClass <em>Parent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Class</em>' attribute.
	 * @see #getParentClass()
	 * @generated
	 */
	void setParentClass(String value);
} // XmlTypeMapping_2_1
