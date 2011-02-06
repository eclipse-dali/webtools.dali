/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Collection Mapping 20</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.XmlCollectionMapping_2_0#getMapKeyConvert <em>Map Key Convert</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlCollectionMapping_2_0()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlCollectionMapping_2_0 extends XmlMapKeyAssociationOverrideContainer_2_0
{
	/**
	 * Returns the value of the '<em><b>Map Key Convert</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Map Key Convert</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Map Key Convert</em>' attribute.
	 * @see #setMapKeyConvert(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.EclipseLinkOrmV2_0Package#getXmlCollectionMapping_2_0_MapKeyConvert()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getMapKeyConvert();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_0.XmlCollectionMapping_2_0#getMapKeyConvert <em>Map Key Convert</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Map Key Convert</em>' attribute.
	 * @see #getMapKeyConvert()
	 * @generated
	 */
	void setMapKeyConvert(String value);

} // XmlCollectionMapping_2_0
