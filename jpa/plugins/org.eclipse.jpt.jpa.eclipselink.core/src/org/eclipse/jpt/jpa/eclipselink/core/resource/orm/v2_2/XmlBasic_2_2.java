/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2;

import org.eclipse.jpt.common.core.resource.xml.EBaseObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Basic 22</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2#getIndex <em>Index</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasic_2_2()
 * @model kind="class" interface="true" abstract="true"
 * @extends EBaseObject
 * @generated
 */
public interface XmlBasic_2_2 extends EBaseObject
{
	/**
	 * Returns the value of the '<em><b>Index</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index</em>' containment reference.
	 * @see #setIndex(XmlIndex_2_2)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.EclipseLinkOrmV2_2Package#getXmlBasic_2_2_Index()
	 * @model containment="true"
	 * @generated
	 */
	XmlIndex_2_2 getIndex();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.XmlBasic_2_2#getIndex <em>Index</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index</em>' containment reference.
	 * @see #getIndex()
	 * @generated
	 */
	void setIndex(XmlIndex_2_2 value);

} // XmlBasic_2_2
