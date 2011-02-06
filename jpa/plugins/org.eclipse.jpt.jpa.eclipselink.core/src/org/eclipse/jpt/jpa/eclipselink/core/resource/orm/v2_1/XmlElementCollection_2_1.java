/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlBatchFetchHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlJoinFetch;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Element Collection 21</b></em>'.
 *  
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 *  * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1#getAttributeType <em>Attribute Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlElementCollection_2_1()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlElementCollection_2_1 extends XmlJoinFetch, XmlBatchFetchHolder
{
	/**
	 * Returns the value of the '<em><b>Attribute Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Type</em>' attribute.
	 * @see #setAttributeType(String)
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLinkOrmV2_1Package#getXmlElementCollection_2_1_AttributeType()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getAttributeType();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.XmlElementCollection_2_1#getAttributeType <em>Attribute Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Type</em>' attribute.
	 * @see #getAttributeType()
	 * @generated
	 */
	void setAttributeType(String value);

} // XmlElementCollection_2_1
