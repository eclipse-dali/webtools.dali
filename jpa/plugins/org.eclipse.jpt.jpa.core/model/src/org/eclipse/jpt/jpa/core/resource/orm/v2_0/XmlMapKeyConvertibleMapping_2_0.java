/*******************************************************************************
 *  Copyright (c) 2011 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.orm.v2_0;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.resource.orm.EnumType;
import org.eclipse.jpt.jpa.core.resource.orm.TemporalType;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Map Key Convertible Mapping 20</b></em>'.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMapKeyConvertibleMapping_2_0#getMapKeyTemporal <em>Map Key Temporal</em>}</li>
 *   <li>{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMapKeyConvertibleMapping_2_0#getMapKeyEnumerated <em>Map Key Enumerated</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlMapKeyConvertibleMapping_2_0()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlMapKeyConvertibleMapping_2_0 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Map Key Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.TemporalType
	 * @see #setMapKeyTemporal(TemporalType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlMapKeyConvertibleMapping_2_0_MapKeyTemporal()
	 * @model
	 * @generated
	 */
	TemporalType getMapKeyTemporal();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMapKeyConvertibleMapping_2_0#getMapKeyTemporal <em>Map Key Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Temporal</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.TemporalType
	 * @see #getMapKeyTemporal()
	 * @generated
	 */
	void setMapKeyTemporal(TemporalType value);

	/**
	 * Returns the value of the '<em><b>Map Key Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.jpa.core.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.EnumType
	 * @see #setMapKeyEnumerated(EnumType)
	 * @see org.eclipse.jpt.jpa.core.resource.orm.v2_0.OrmV2_0Package#getXmlMapKeyConvertibleMapping_2_0_MapKeyEnumerated()
	 * @model
	 * @generated
	 */
	EnumType getMapKeyEnumerated();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.core.resource.orm.v2_0.XmlMapKeyConvertibleMapping_2_0#getMapKeyEnumerated <em>Map Key Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.jpa.core.resource.orm.EnumType
	 * @see #getMapKeyEnumerated()
	 * @generated
	 */
	void setMapKeyEnumerated(EnumType value);

	/**
	 * Return the {@link TextRange} for the enumerated element.  If the enumerated element 
	 * does not exist return the {@link TextRange} for the basic element.
	 */
	TextRange getMapKeyEnumeratedTextRange();
	
	/**
	 * Return the {@link TextRange} for the temporal element.  If the temporal element 
	 * does not exist return the {@link TextRange} for the basic element.
	 */
	TextRange getMapKeyTemporalTextRange();

} // XmlMapKeyConvertibleMapping_2_0
