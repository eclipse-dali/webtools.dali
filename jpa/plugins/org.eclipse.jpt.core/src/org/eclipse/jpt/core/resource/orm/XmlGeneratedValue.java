/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.orm;

import org.eclipse.jpt.core.resource.common.JpaEObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generated Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue#getGenerator <em>Generator</em>}</li>
 *   <li>{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue#getStrategy <em>Strategy</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlGeneratedValue()
 * @model kind="class" interface="true" abstract="true"
 * @extends JpaEObject
 * @generated
 */
public interface XmlGeneratedValue extends JpaEObject
{
	/**
	 * Returns the value of the '<em><b>Generator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generator</em>' attribute.
	 * @see #setGenerator(String)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlGeneratedValue_Generator()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 * @generated
	 */
	String getGenerator();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue#getGenerator <em>Generator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generator</em>' attribute.
	 * @see #getGenerator()
	 * @generated
	 */
	void setGenerator(String value);

	/**
	 * Returns the value of the '<em><b>Strategy</b></em>' attribute.
	 * The default value is <code>"TABLE"</code>.
	 * The literals are from the enumeration {@link org.eclipse.jpt.core.resource.orm.GenerationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Strategy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.GenerationType
	 * @see #setStrategy(GenerationType)
	 * @see org.eclipse.jpt.core.resource.orm.OrmPackage#getXmlGeneratedValue_Strategy()
	 * @model default="TABLE"
	 * @generated
	 */
	GenerationType getStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.jpt.core.resource.orm.XmlGeneratedValue#getStrategy <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Strategy</em>' attribute.
	 * @see org.eclipse.jpt.core.resource.orm.GenerationType
	 * @see #getStrategy()
	 * @generated
	 */
	void setStrategy(GenerationType value);

} // GeneratedValue
