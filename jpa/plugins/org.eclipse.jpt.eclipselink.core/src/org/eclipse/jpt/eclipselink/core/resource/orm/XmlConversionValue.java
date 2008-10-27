/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.orm;

import org.eclipse.jpt.core.resource.common.JpaEObject;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Conversion Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getDataValue <em>Data Value</em>}</li>
 *   <li>{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getObjectValue <em>Object Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlConversionValue extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Data Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Value</em>' attribute.
	 * @see #setDataValue(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue_DataValue()
	 * @model
	 * @generated
	 */
	String getDataValue();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getDataValue <em>Data Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Value</em>' attribute.
	 * @see #getDataValue()
	 * @generated
	 */
	void setDataValue(String value);

	/**
	 * Returns the value of the '<em><b>Object Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object Value</em>' attribute.
	 * @see #setObjectValue(String)
	 * @see org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmPackage#getXmlConversionValue_ObjectValue()
	 * @model
	 * @generated
	 */
	String getObjectValue();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.eclipselink.core.resource.orm.XmlConversionValue#getObjectValue <em>Object Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object Value</em>' attribute.
	 * @see #getObjectValue()
	 * @generated
	 */
	void setObjectValue(String value);
	
	/**
	 * Return the {@link TextRange} for the data-value attribute.
	 */
	public TextRange getDataValueTextRange();
	
	/**
	 * Return the {@link TextRange} for the object-value attribute.
	 */
	public TextRange getObjectValueTextRange();

} // XmlConversionValue
