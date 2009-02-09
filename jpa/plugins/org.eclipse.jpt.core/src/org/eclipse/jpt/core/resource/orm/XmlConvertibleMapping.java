/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.jpt.core.resource.xml.JpaEObject;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Xml Convertible Mapping</b></em>'.
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
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#isLob <em>Lob</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#getTemporal <em>Temporal</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#getEnumerated <em>Enumerated</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlConvertibleMapping()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlConvertibleMapping extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Lob</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lob</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lob</em>' attribute.
	 * @see #setLob(boolean)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlConvertibleMapping_Lob()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 * @generated
	 */
	boolean isLob();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#isLob <em>Lob</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lob</em>' attribute.
	 * @see #isLob()
	 * @generated
	 */
	void setLob(boolean value);

	/**
	 * Returns the value of the '<em><b>Temporal</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.TemporalType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Temporal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #setTemporal(TemporalType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlConvertibleMapping_Temporal()
	 * @model
	 * @generated
	 */
	TemporalType getTemporal();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#getTemporal <em>Temporal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Temporal</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.TemporalType
	 * @see #getTemporal()
	 * @generated
	 */
	void setTemporal(TemporalType value);

	/**
	 * Returns the value of the '<em><b>Enumerated</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.EnumType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumerated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #setEnumerated(EnumType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlConvertibleMapping_Enumerated()
	 * @model
	 * @generated
	 */
	EnumType getEnumerated();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlConvertibleMapping#getEnumerated <em>Enumerated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumerated</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.EnumType
	 * @see #getEnumerated()
	 * @generated
	 */
	void setEnumerated(EnumType value);

	/**
	 * Return the {@link TextRange} for the enumerated element.  If the enumerated element 
	 * does not exist return the {@link TextRange} for the basic element.
	 */
	TextRange getEnumeratedTextRange();
	
	/**
	 * Return the {@link TextRange} for the temporal element.  If the temporal element 
	 * does not exist return the {@link TextRange} for the basic element.
	 */
	TextRange getTemporalTextRange();
	
	/**
	 * Return the {@link TextRange} for the lob element.  If the lob element 
	 * does not exist return the {@link TextRange} for the basic element.
	 */
	TextRange getLobTextRange();

} // ConvertableMapping
