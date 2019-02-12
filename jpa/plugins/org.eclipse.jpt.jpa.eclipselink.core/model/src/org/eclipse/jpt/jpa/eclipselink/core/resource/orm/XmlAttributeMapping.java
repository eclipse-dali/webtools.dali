/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.eclipselink.core.resource.orm;

import org.eclipse.jpt.common.core.utility.TextRange;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Attribute Mapping</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping#getAttributeType <em>Attribute Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAttributeMapping()
 * @model kind="class" interface="true" abstract="true"
 * @generated
 */
public interface XmlAttributeMapping 
	extends org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping, 
			XmlAccessMethodsHolder, XmlPropertyContainer
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
	 * @see org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlAttributeMapping_AttributeType()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getAttributeType();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping#getAttributeType <em>Attribute Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Attribute Type</em>' attribute.
	 * @see #getAttributeType()
	 * @generated
	 */
	void setAttributeType(String value);

	/**
	 * Sets the value of the '<em>Attribute Type</em>' attribute and/or 
	 * '<em>Target Entity </em>' or '<em>Target Class </em>' attribute for a virtual attribute mapping.
	 * These will be null depending on the mapping type
	 * 
	 * @see EclipseLinkOrmPersistentType#addVirtualAttribute(String, String, String, String);
	 */
	void setVirtualAttributeTypes(String attributeType, String targetType);

	TextRange getAttributeTypeTextRange();

} // XmlAttributeMapping
