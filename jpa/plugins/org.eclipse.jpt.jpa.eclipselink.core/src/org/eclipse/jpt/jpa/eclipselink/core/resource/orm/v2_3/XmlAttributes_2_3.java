/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3;

import org.eclipse.emf.common.util.EList;

import org.eclipse.jpt.jpa.core.resource.xml.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Attributes 23</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3#getStructures <em>Structures</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3#getArrays <em>Arrays</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlAttributes_2_3()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlAttributes_2_3 extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Structures</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlStructure_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Structures</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Structures</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlAttributes_2_3_Structures()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlStructure_2_3> getStructures();

	/**
	 * Returns the value of the '<em><b>Arrays</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlArray_2_3}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arrays</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arrays</em>' containment reference list.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.EclipseLinkOrmV2_3Package#getXmlAttributes_2_3_Arrays()
	 * @model containment="true"
	 * @generated
	 */
	EList<XmlArray_2_3> getArrays();

} // XmlAttributes_2_3
